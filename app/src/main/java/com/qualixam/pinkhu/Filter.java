package com.qualixam.pinkhu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Filter extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvLocation,tvReview,tvTime;
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        init();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set the padding to match the Status Bar height
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.number = 1;
                MainActivity.refresh = true;
                onBackPressed();
            }
        });
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.number = 2;
                MainActivity.refresh = true;
                onBackPressed();
            }
        });
        tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.number = 3;
                MainActivity.refresh = true;
                onBackPressed();
            }
        });
    }
    public void init()
    {
        con = Filter.this;
        tvLocation = (TextView) findViewById(R.id.location);
        tvTime = (TextView) findViewById(R.id.lastupdated);
        tvReview = (TextView) findViewById(R.id.ratings);
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
