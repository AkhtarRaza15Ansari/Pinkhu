package com.qualixam.pinkhu;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qualixam.adapter.RecyclerBookmarkAdapter;
import com.qualixam.constant.dumpclass;
import com.qualixam.modal.BookmarkData;
import com.qualixam.modal.DataObject;
import com.qualixam.modal.ReviewData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class DetailsPage extends AppCompatActivity {
    private FragmentDrawer drawerFragment;
    Toolbar toolbar;
    TextView map;
    int count;
    LinearLayout writeareview,call,bookmark;
    String yourAddress = "Digital Edge Technologies";
    String maps;
    private static String LOG_TAG = "MainActivity";
    ImageView mainimage,bookimageadd,bookimagedelete;
    LinearLayout reviewsection;
    Intent i;
    String user_id;
    SharedPreferences prefs;
    TextView tvWebsite,tvemail,tvname,tvaddress,tvspecialisation,tvcompleteAddress,tvworkinghours,tvabout,tvratings;
    String website,email,mobile,registerid,name,address,specialisation,completeAddress,workinghours,distance,offers,about,ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        tvname = (TextView) findViewById(R.id.name);
        tvaddress = (TextView) findViewById(R.id.area);
        tvspecialisation = (TextView) findViewById(R.id.circles);
        tvcompleteAddress = (TextView) findViewById(R.id.completeAddress);
        tvworkinghours = (TextView) findViewById(R.id.workinghours);
        tvabout = (TextView) findViewById(R.id.about);
        tvratings = (TextView) findViewById(R.id.ratinggs);
        tvWebsite = (TextView) findViewById(R.id.website);
        tvemail   = (TextView) findViewById(R.id.email);
        bookimageadd = (ImageView) findViewById(R.id.bookimageadd);
        bookimagedelete = (ImageView) findViewById(R.id.bookimagedelete);
        reviewsection   = (LinearLayout) findViewById(R.id.reviewsection);
        map = (TextView) findViewById(R.id.map);
        writeareview = (LinearLayout)findViewById(R.id.writeareview);
        call = (LinearLayout)findViewById(R.id.call);
        bookmark = (LinearLayout) findViewById(R.id.bookmark);
        mainimage = (ImageView)findViewById(R.id.mainimage);

        i = getIntent();
        try {
            registerid = i.getStringExtra("registerid");
            name = i.getStringExtra("name");
            address = i.getStringExtra("address");
            specialisation = i.getStringExtra("specialisation");
            completeAddress = i.getStringExtra("completeAddress");
            workinghours = i.getStringExtra("workinghours");
            distance = i.getStringExtra("distance");
            offers = i.getStringExtra("offers");
            about = i.getStringExtra("about");
            ratings = i.getStringExtra("ratings");
            website = i.getStringExtra("website");
            email = i.getStringExtra("email");
            mobile = i.getStringExtra("mobile");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        tvname.setText(name);
        tvaddress.setText(address);
        tvspecialisation.setText(specialisation);
        tvcompleteAddress.setText(completeAddress);
        tvworkinghours.setText(workinghours);
        tvabout.setText(about);
        tvratings.setText(ratings);
        tvWebsite.setText(website);
        tvemail.setText(email);
        maps = "http://maps.google.co.in/maps?q=" + completeAddress;


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:"+mobile);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });
        writeareview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsPage.this, WriteReview.class);
                i.putExtra("registerid",registerid);
                startActivity(i);
            }
        });
        Log.d("rating",registerid);
        Picasso.with(DetailsPage.this).load(R.drawable.cloud).into(mainimage);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(maps));
                startActivity(intent);
            }
        });
        tvratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setTitle("Details");

        user_id = prefs.getString("id","");

        new CheckBookmarks().execute();
        new FetchRatings().execute();
    }
    @Override
    protected void onPause() {
        super.onPause();
        dumpclass.deleteCache(getApplicationContext());
    }


    public class AddBookmarks extends AsyncTask<Void,Void,Void>
    {
        String json;
        String message;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            json = dumpclass.addBookmarkPost(registerid,user_id,name);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                JSONObject object = new JSONObject(json);
                message = object.getString("Message");
                if(message.equals("Successful"))
                {
                    dumpclass.Toastthis("Bookmark Successfully Added",DetailsPage.this);
                    new CheckBookmarks().execute();
                }
                else{
                    dumpclass.Toastthis("Failed to add. Please try again.",DetailsPage.this);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public class CheckBookmarks extends AsyncTask<Void,Void,Void>
    {
        String json;
        String response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            json = dumpclass.getCheckBookmark(registerid,user_id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                JSONObject object = new JSONObject(json);
                response = object.getString("response");
                if(response.equals("0"))
                {
                    bookimageadd.setVisibility(View.VISIBLE);
                    bookimagedelete.setVisibility(View.GONE);
                    bookmark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new AddBookmarks().execute();
                        }
                    });
                }
                else{
                    bookimageadd.setVisibility(View.GONE);
                    bookimagedelete.setVisibility(View.VISIBLE);
                    bookmark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new DeleteBookmarks().execute();
                        }
                    });
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public class DeleteBookmarks extends AsyncTask<Void,Void,Void>
    {
        String json;
        String response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            json = dumpclass.getDeleteBookmark(registerid,user_id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                JSONObject object = new JSONObject(json);
                response = object.getString("response");
                if(response.equals("1"))
                {
                    dumpclass.Toastthis("Bookmark Deleted",DetailsPage.this);
                    new CheckBookmarks().execute();
                }
                else{
                    dumpclass.Toastthis("Failed to delete. Please try again.",DetailsPage.this);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public class FetchRatings extends AsyncTask<Void,Void,Void> {

        ArrayList<ReviewData> results;
        String json_response;
        JSONArray array;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            results = new ArrayList<ReviewData>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                json_response = dumpclass.getAllReviews(registerid);
                array = new JSONArray(json_response);
                for(int i=0;i<array.length();i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    String ReviewID         = object.getString("ReviewID");
                    String LocationID       = object.getString("LocationID");
                    String Rating           = object.getString("Rating");
                    String Reviews          = object.getString("Reviews");

                    ReviewData data     = new ReviewData(ReviewID,LocationID,Rating,Reviews);
                    results.add(i, data);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<ReviewData> array = new ArrayList<>();
            array = results;
            for(int i=0;i<array.size();i++)
            {
                String text = array.get(i).getReviews();
                String rating = array.get(i).getRating();
                addLayout(text,rating);
            }

        }
    }
    private void addLayout(String str_text, String str_rating) {
        View layout2 = LayoutInflater.from(this).inflate(R.layout.ratings_layout, reviewsection, false);

        TextView text = (TextView) layout2.findViewById(R.id.text);
        TextView startext = (TextView) layout2.findViewById(R.id.startext);
        ImageView one = (ImageView) layout2.findViewById(R.id.star1);
        ImageView two = (ImageView) layout2.findViewById(R.id.star2);
        ImageView three = (ImageView) layout2.findViewById(R.id.star3);
        ImageView four = (ImageView) layout2.findViewById(R.id.star4);
        ImageView five = (ImageView) layout2.findViewById(R.id.star5);

        if(str_rating.equals("1"))
        {
            one.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            two.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
            three.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
            four.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
            five.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
        }
        else if(str_rating.equals("2"))
        {
            one.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            two.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            three.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
            four.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
            five.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
        }
        else if(str_rating.equals("3"))
        {
            one.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            two.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            three.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            four.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
            five.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
        }
        else if(str_rating.equals("4"))
        {
            one.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            two.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            three.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            four.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            five.setImageDrawable(getResources().getDrawable(R.drawable.outlinestar));
        }
        else if(str_rating.equals("5"))
        {
            one.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            two.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            three.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            four.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
            five.setImageDrawable(getResources().getDrawable(R.drawable.goldstar));
        }

        text.setText(str_text);
        startext.setText(str_rating);
        reviewsection.addView(layout2);
    }
}
