package com.example.davidxie.esieassess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ImageView iv = (ImageView)findViewById(R.id.imageView2);
        assert iv != null;
        Picasso.with(this).load(R.drawable.excellent).fit().centerCrop().into(iv);
    }
}
