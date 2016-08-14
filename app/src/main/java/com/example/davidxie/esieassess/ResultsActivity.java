package com.example.davidxie.esieassess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ResultsActivity extends AppCompatActivity {
    int imageState;
    int resultValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultValue = getIntent().getExtras().getInt("result");
        setContentView(R.layout.activity_results);

        final ImageView iv = (ImageView)findViewById(R.id.imageView2);
        assert iv != null;
        Picasso.with(this).load(R.drawable.result).fit().centerCrop().into(iv);

        imageState = 0;

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageState == 0) {
                    int resultImg;
                    if (resultValue == 1) {
                        resultImg = R.drawable.final_1;
                    } else if (resultValue == 2) {
                        resultImg = R.drawable.final_2;
                    } else if (resultValue == 3) {
                        resultImg = R.drawable.final_3;
                    } else if (resultValue == 4) {
                        resultImg = R.drawable.final_4;
                    } else if (resultValue == 5) {
                        resultImg = R.drawable.final_5;
                    } else {
                        // result not carried in, failure
                        resultImg = R.drawable.final_1;
                    }
                    Picasso.with(ResultsActivity.this).load(resultImg).fit().centerCrop().into(iv);
                    imageState = 1;
                } else {
                    Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
