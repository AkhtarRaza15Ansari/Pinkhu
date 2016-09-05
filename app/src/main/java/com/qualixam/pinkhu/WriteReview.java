package com.qualixam.pinkhu;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.qualixam.constant.dumpclass;
import com.squareup.picasso.Picasso;

public class WriteReview extends AppCompatActivity {

    int count = 0;
    ImageView one,two,three,four,five;
    ImageView mainimage;
    String id="",registerid,reviews;
    EditText review;
    Button btnSubmit;
    int arating = 0;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Pinkhu");
        // Set the padding to match the Status Bar height

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        prefs = getSharedPreferences("Pinkhu", MODE_PRIVATE);
        id = prefs.getString("id","");
        registerid = getIntent().getStringExtra("registerid");
        Log.d("meesage",id);
        review = (EditText) findViewById(R.id.review);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        mainimage = (ImageView)findViewById(R.id.mainimage);
        Picasso.with(WriteReview.this).load(R.drawable.cloud).into(mainimage);
        one = (ImageView) findViewById(R.id.one);
        two = (ImageView) findViewById(R.id.two);
        three = (ImageView) findViewById(R.id.three);
        four = (ImageView) findViewById(R.id.four);
        five = (ImageView) findViewById(R.id.five);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arating = 1;
                count = 1;
                one.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                two.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
                three.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
                four.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
                five.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arating = 2;
                count = 1;
                one.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                two.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                three.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
                four.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
                five.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arating = 3;
                count = 1;
                one.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                two.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                three.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                four.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
                five.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arating = 4;
                count = 1;
                one.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                two.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                three.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                four.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                five.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arating = 5;
                count = 1;
                one.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                two.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                three.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                four.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
                five.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviews = review.getText().toString();
                if(count != 1)
                {
                    dumpclass.Toastthis("Please select a rating",WriteReview.this);
                }
                else if(reviews.length()<1)
                {
                    dumpclass.Toastthis("Please write a review",WriteReview.this);
                }
                else{
                    new ReviewAsync().execute();
                }
            }
        });
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    @Override
    protected void onPause() {
        super.onPause();
        dumpclass.deleteCache(getApplicationContext());
    }
    public class ReviewAsync extends AsyncTask<Void,Void,Void>
    {
        String json;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            json = dumpclass.getReview(registerid,arating+"",reviews);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                onBackPressed();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
