package com.example.davidxie.esieassess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class SidebarActivity extends AppCompatActivity {

    String display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar);
        display = getIntent().getExtras().getString("display");
        final ImageView iv = (ImageView)findViewById(R.id.background);

        if (display.equals("all")) {
            assert iv != null;
            Picasso.with(this).load(R.drawable.side_all).fit().centerCrop().into(iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Picasso.with(SidebarActivity.this).load(R.drawable.side_all2).fit().centerCrop().into(iv);
                }
            });
        } else if (display.equals("settings")) {
            Picasso.with(this).load(R.drawable.side_settings).fit().centerCrop().into(iv);
        } else if (display.equals("data")) {
            Picasso.with(this).load(R.drawable.side_data).fit().centerCrop().into(iv);
        }
    }
}
