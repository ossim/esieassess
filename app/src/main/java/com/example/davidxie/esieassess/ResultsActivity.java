package com.example.davidxie.esieassess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ResultsActivity extends AppCompatActivity {
    int imageState;
    int resultImg;
    TextView resultText;
    LinearLayout sidebar;
    int resultValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultValue = getIntent().getExtras().getInt("result");
        setContentView(R.layout.activity_results);

        final ImageView iv = (ImageView)findViewById(R.id.imageView2);
        assert iv != null;
        Picasso.with(this).load(R.drawable.results).fit().centerCrop().into(iv);

        sidebar = (LinearLayout)findViewById(R.id.resultSidebar);

        if (resultValue == 1) {
            resultImg = R.drawable.final_1;
            resultText = (TextView)findViewById(R.id.resultRow1);
        } else if (resultValue == 2) {
            resultImg = R.drawable.final_2;
            resultText = (TextView)findViewById(R.id.resultRow2);
        } else if (resultValue == 3) {
            resultImg = R.drawable.final_3;
            resultText = (TextView)findViewById(R.id.resultRow3);
        } else if (resultValue == 4) {
            resultImg = R.drawable.final_4;
            resultText = (TextView)findViewById(R.id.resultRow4);
        } else if (resultValue == 5) {
            resultImg = R.drawable.final_5;
            resultText = (TextView)findViewById(R.id.resultRow5);
        } else {
            // result not carried in, failure
            resultImg = R.drawable.final_5;
            resultText = (TextView)findViewById(R.id.resultRow5);
        }

        assert resultText != null;
        resultText.setAlpha(1);

        imageState = 0;

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageState == 0) {
                    sidebar.setVisibility(View.INVISIBLE);
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
