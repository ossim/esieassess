package com.example.davidxie.esieassess;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by davidxie on 7/15/16.
 */
public class VideoActivity extends AppCompatActivity {
    private String TAG = VideoActivity.class.getName();

    private VideoView myVideoView;
    private ProgressDialog videoProgressDialog;
    private ProgressDialog cameraProgressDialog;
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private File mOutputFile;
    private Boolean isRecording;
    private ArrayList<Integer> videoArray;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Log.d(TAG, "onCreate 1");

        myVideoView = (VideoView) findViewById(R.id.video_view);

//
//        videoProgressDialog = new ProgressDialog(VideoActivity.this);
//        videoProgressDialog.setTitle("Preparing ");
//        videoProgressDialog.setMessage("Loading...");
//        videoProgressDialog.setCancelable(false);
//        videoProgressDialog.show();

        videoArray = new ArrayList<>();
        videoArray.add(R.raw.road_s0);
        videoArray.add(R.raw.s1);
        videoArray.add(R.raw.s2);
        videoArray.add(R.raw.s3);
        videoArray.add(R.raw.s4);
        videoArray.add(R.raw.s5);
        videoArray.add(R.raw.s6);
        videoArray.add(R.raw.s7);

        videoArray.remove(1 + (int)(Math.random() * ((videoArray.size() - 2) + 1)));
        videoArray.remove(1 + (int)(Math.random() * ((videoArray.size() - 2) + 1)));
        videoArray.remove(1 + (int)(Math.random() * ((videoArray.size() - 2) + 1)));
        videoArray.remove(1 + (int)(Math.random() * ((videoArray.size() - 2) + 1)));

        playVideo();

        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                // RXIE: Camera code caused crashes, take it out for now
//                // new MediaPrepareTask().execute(null, null, null);
//                try {
//                    setUpVideo();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                videoProgressDialog.dismiss();
                Log.d(TAG, "start playing video now");
                myVideoView.start();
            }
        });

        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (videoArray.size() < 1) {
                    Log.d(TAG, "video played");
//                mCamera.release();
//                mMediaRecorder.stop();
                    Intent intent = new Intent(VideoActivity.this, ResultsActivity.class);
                    startActivity(intent);
                } else {
                    playVideo();
                }
            }
        });
    }

    private void playVideo() {
        if (videoArray.size() > 0) {
            myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoArray.get(0)));
            videoArray.remove(0);
        } else {
            Intent intent = new Intent(VideoActivity.this, ResultsActivity.class);
            startActivity(intent);
        }
    }

    private String getPath() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!sdDir.exists() && !sdDir.mkdirs()) {
            Log.d(TAG, "Can't create directory to save image.");
            Toast.makeText(VideoActivity.this, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_" + date + ".jpg";

        String filename = sdDir.getPath() + File.separator + photoFile;

        File pictureFile = new File(filename);
        return filename;
    }

    private int findFrontFacingCamera() {
        int cameraId = 0;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }
    
    private void setUpVideo() throws IOException {
        try {
            mCamera = Camera.open(findFrontFacingCamera());
        } catch (Error e) {
            mCamera.release();
            e.printStackTrace();
        }

        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
        mMediaRecorder.setMaxDuration((int) 60000);
        CamcorderProfile camcorderProfile_HQ = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        mMediaRecorder.setProfile(camcorderProfile_HQ);
        mMediaRecorder.setOutputFile(getPath());

        mMediaRecorder.prepare();
        mMediaRecorder.start();
    }

}
