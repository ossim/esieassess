package com.example.davidxie.esieassess;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by davidxie on 7/15/16.
 */
public class VideoActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private String TAG = VideoActivity.class.getName();

    private VideoView myVideoView;
    private ArrayList<Integer> videoArray;
    public static SurfaceView mSurfaceView;
    public static SurfaceHolder mSurfaceHolder;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Log.d(TAG, "onCreate 1");

        // initializing video feed
        myVideoView = (VideoView) findViewById(R.id.video_view);

        videoArray = new ArrayList<>();
        videoArray.add(R.raw.road_s0);
        videoArray.add(R.raw.s1);
        videoArray.add(R.raw.s2);
        videoArray.add(R.raw.s3);
        videoArray.add(R.raw.s4);
        videoArray.add(R.raw.s5);
        videoArray.add(R.raw.s6);
        videoArray.add(R.raw.s7);

        videoArray.remove(1 + (int) (Math.random() * ((videoArray.size() - 2) + 1)));
        videoArray.remove(1 + (int) (Math.random() * ((videoArray.size() - 2) + 1)));
        videoArray.remove(1 + (int) (Math.random() * ((videoArray.size() - 2) + 1)));
        videoArray.remove(1 + (int) (Math.random() * ((videoArray.size() - 2) + 1)));

        setVideo();

        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                // RXIE: Camera code caused crashes, take it out for now
                Log.d(TAG, "start playing video now");
                // initialize video camera
                Intent intent = new Intent(VideoActivity.this, RecorderService.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startService(intent);

                myVideoView.start();
            }
        });

        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (videoArray.size() < 1) {
                    Log.d(TAG, "video played");

                    stopService(new Intent(VideoActivity.this, RecorderService.class));

                    Intent intent = new Intent(VideoActivity.this, ResultsActivity.class);

                    Random r = new Random();
                    int i1 = r.nextInt(5) + 1;

                    intent.putExtra("result", i1);
                    startActivity(intent);
                } else {
                    setVideo();
                }
            }
        });

        mSurfaceView = (SurfaceView) findViewById(R.id.camera_preview);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    private void setVideo() {
        if (videoArray.size() > 0) {
            myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoArray.get(0)));
            videoArray.remove(0);
        } else {
            Intent intent = new Intent(VideoActivity.this, ResultsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

}
