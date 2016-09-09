package com.qualixam.pinkhu;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.qualixam.adapter.SecondRecyclerAdapter;
import com.qualixam.constant.dumpclass;
import com.qualixam.modal.DataObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    SharedPreferences prefs;

    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager mLayoutManager1;

    private FragmentDrawer drawerFragment;
    Toolbar toolbar;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    int[] resource = {
            R.drawable.aclinic,
            R.drawable.bclinic,
            R.drawable.cclinic,
            R.drawable.dclinic
    };
    int num = -1;
    int total_length;
    private LinearLayout dotsLayout;
    private int dotsCount;
    private TextView[] dots;
    public static LinearLayout cat1, cat2, cat3, cat4, cat5;
    int NUM_PAGES = 3;
    private static String LOG_TAG = "MainActivity";
    Timer timer;
    ImageView mainimage;
    private ArrayList<DataObject> notificationList;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    public static String address, locality, city, state, country, postalCode, knownName;
    ArrayList<String> tile_image,tile_title,tile_desc;
    ArrayList<String> array_images,array_url;

    private ImageView images,images1,
            images2,images3,
            images4,images5,
            images6,images7;
    private TextView textView1,textView2
            ,textView3,textView4,
            textView5,textView6,
            textView7,textView8,
            textView9,textView10
            ,textView11,textView12,
            textView13,textView14,
            textView15,textView16;
    private LinearLayout alltiles;
    public static boolean refresh = false;
    public static int number = 0;
    LinearLayout donate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        prefs = getSharedPreferences("Pinkhu", Context.MODE_PRIVATE);

        initViews();

        //setViewPagerItemsWithAdapter();
        //setUiPageViewController();
        total_length = resource.length;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set the padding to match the Status Bar height
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
        mGoogleApiClient.connect();


        donate = (LinearLayout) findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,DonateAnything.class);
                startActivity(i);
            }
        });

        mainimage = (ImageView) findViewById(R.id.mainimage);
        Picasso.with(MainActivity.this).load(R.drawable.cloud).into(mainimage);

        cat1 = (LinearLayout) findViewById(R.id.cat1);
        cat2 = (LinearLayout) findViewById(R.id.cat2);
        cat3 = (LinearLayout) findViewById(R.id.cat3);
        cat4 = (LinearLayout) findViewById(R.id.cat4);
        cat5 = (LinearLayout) findViewById(R.id.cat5);

        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchPageNew.class);
                i.putExtra("page", "one");
                i.putExtra("state", state);
                i.putExtra("city", city);
                i.putExtra("locality", locality);
                startActivity(i);
            }
        });
        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchPageShop.class);
                i.putExtra("page", "two");
                i.putExtra("state", state);
                i.putExtra("city", city);
                i.putExtra("locality", locality);
                startActivity(i);
            }
        });
        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchPageRentals.class);
                i.putExtra("page", "three");
                i.putExtra("state", state);
                i.putExtra("city", city);
                i.putExtra("locality", locality);
                startActivity(i);
            }
        });
        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchPageBanks.class);
                i.putExtra("page", "four");
                i.putExtra("state", state);
                i.putExtra("city", city);
                i.putExtra("locality", locality);
                startActivity(i);
            }
        });
        cat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchPageNGO.class);
                i.putExtra("page", "five");
                i.putExtra("state", state);
                i.putExtra("city", city);
                i.putExtra("locality", locality);
                startActivity(i);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(city!=null)
        {
            setTitle(""+city+" >");
        }
        else{
            setTitle("Select City");
        }

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Filter.class);
                startActivity(i);
            }
        });
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        pageSwitcher(7);


    }

    private ArrayList<DataObject> getDataSet(String json) throws Exception{

        JSONArray array = new JSONArray(json);
        JSONArray array1 = new JSONArray();
        ArrayList results = new ArrayList<DataObject>();
        for(int i = 0; i<array.length();i++)
        {
            JSONObject object = array.getJSONObject(i);

            String SliderNo = object.getString("SliderNo");
            String image = object.getString("SliderImage");
            if(SliderNo.equals("6")||SliderNo.equals("7")||SliderNo.equals("8")||SliderNo.equals("9")||SliderNo.equals("10")){
                array1.put(object);
                Log.d("slider",""+SliderNo);
                Log.d("image",""+image);
            }
        }
        for(int i=0;i<array1.length();i++)
        {
            JSONObject object = array1.getJSONObject(i);
            String image = object.getString("SliderImage");
            String url = object.getString("URL");
            DataObject obj = new DataObject(image, url);
            results.add(i, obj);
        }
        return results;
    }

    private ArrayList<String> getDataSetString(){
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            results.add("Item " + i);
        }
        return results;
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.chat) {
            Intent i = new Intent(MainActivity.this,ChatListActivity.class);
            startActivity(i);
            return true;
        }
        if(id == R.id.bookmark)
        {
            Intent i = new Intent(MainActivity.this,BookmarkPage.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (position) {
            case 0:
                break;
            case 1:
                Intent i = new Intent(MainActivity.this, Help.class);
                startActivity(i);
                break;
            case 2:
                Intent i2 = new Intent(MainActivity.this, TermsCondition.class);
                startActivity(i2);
                break;
            case 3:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                prefs.edit().putString("logout","yes").apply();
                                Intent i3 = new Intent(MainActivity.this, Login.class);
                                i3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i3);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                break;
            case 4:
                Intent i4 = new Intent(MainActivity.this, Help.class);
                startActivity(i4);
                break;
            case 5:
                Intent i5 = new Intent(MainActivity.this, Help.class);
                startActivity(i5);
                break;
            default:
                break;
        }

    }

    private void setUiPageViewController() {
        dotsLayout = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        dotsCount = myViewPagerAdapter.getCount();
        dots = new TextView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(22);
            dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            dotsLayout.addView(dots[i]);
        }

        dots[0].setTextColor(getResources().getColor(R.color.white));
    }

    private void setViewPagerItemsWithAdapter() {
        myViewPagerAdapter = new MyViewPagerAdapter(resource);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(viewPagerPageChangeListener);
    }
    private void setUPNewVP(ArrayList<String> images) {
        myViewPagerAdapter = new MyViewPagerAdapter(images);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(viewPagerPageChangeListener);
    }
    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            //num = position;
            for (int i = 0; i < dotsCount; i++) {
                dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mRecyclerView1 = (RecyclerView) findViewById(R.id.second_recycler_view);
        alltiles = (LinearLayout) findViewById(R.id.alltiles);

        tile_image = new ArrayList<>();
        tile_desc = new ArrayList<>();
        tile_title = new ArrayList<>();

        images = (ImageView) findViewById(R.id.images);
        images1 = (ImageView) findViewById(R.id.images1);
        images2 = (ImageView) findViewById(R.id.images2);
        images3 = (ImageView) findViewById(R.id.images3);
        images4 = (ImageView) findViewById(R.id.images4);
        images5 = (ImageView) findViewById(R.id.images5);
        images6 = (ImageView) findViewById(R.id.images6);
        images7 = (ImageView) findViewById(R.id.images7);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);
        textView8 = (TextView) findViewById(R.id.textView8);
        textView9 = (TextView) findViewById(R.id.textView9);
        textView10 = (TextView) findViewById(R.id.textView10);
        textView11 = (TextView) findViewById(R.id.textView11);
        textView12 = (TextView) findViewById(R.id.textView12);
        textView13 = (TextView) findViewById(R.id.textView13);
        textView14 = (TextView) findViewById(R.id.textView14);
        textView15 = (TextView) findViewById(R.id.textView15);
        textView16 = (TextView) findViewById(R.id.textView16);

        new SliderClass().execute();
        new getTiles().execute();

    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //isLocationEnabled();
            Log.d("log", "connection stage permission");
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {

        } else {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            try {
                getLocation(currentLatitude, currentLongitude);

                if(city!=null)
                {
                    setTitle(""+city+" >");
                }
                else{
                    setTitle("Select City");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;
        int resource[];
        ArrayList<String> arrImage = new ArrayList<>();

        public MyViewPagerAdapter(int[] resource) {
            this.resource = resource;
        }
        public MyViewPagerAdapter(ArrayList<String> images){
            this.arrImage = images;
        }
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_pager_item, container, false);

            ImageView tView = (ImageView) view.findViewById(R.id.PageNumber);
            try{
                Picasso.with(MainActivity.this).load("http://demo.digitaledgetech.in/admin/Slider/"+arrImage.get(position)).resize(225, 100).centerCrop().into(tView);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            ((ViewPager) container).addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this,BrowserActivity.class);
                    i.putExtra("url",array_url.get(position));
                    startActivity(i);
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return arrImage.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            ((ViewPager) container).removeView(view);
        }
    }

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {
                    if (num >= total_length) {
                        num = 0;
                    } else {
                        num++;
                    }
                    viewPager.setCurrentItem(num);
                }
            });

        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void getLocation(double latitude, double longitude) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        address = addresses.get(0).getAddressLine(0);
        locality = addresses.get(0).getSubLocality();
        city = addresses.get(0).getLocality();
        state = addresses.get(0).getAdminArea();
        country = addresses.get(0).getCountryName();
        postalCode = addresses.get(0).getPostalCode();
        knownName = addresses.get(0).getFeatureName();
        Log.d("address", ""+address);
        Log.d("locality", ""+locality);
        Log.d("city", ""+city);
        Log.d("state", ""+state);
        Log.d("country", ""+country);
        Log.d("postalCode", ""+postalCode);
        Log.d("knownName", ""+knownName);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName()+"", "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        dumpclass.deleteCache(getApplicationContext());
    }

    public class SliderClass extends AsyncTask<Void,Void,Void>
    {
        String json;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            array_images = new ArrayList<>();
            array_url = new ArrayList<>();
            notificationList = new ArrayList<DataObject>();
            mLayoutManager1 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            json = dumpclass.getSlider();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONArray array = new JSONArray(json);
                for(int i = 0; i<array.length();i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    String SliderNo = object.getString("SliderNo");

                    if(SliderNo.equals("1")||SliderNo.equals("2")||SliderNo.equals("3")||SliderNo.equals("4")||SliderNo.equals("5")){
                        String image = object.getString("SliderImage");
                        String url = object.getString("URL");
                        Log.d("slider",""+SliderNo);
                        Log.d("image",""+image);
                        array_images.add(image);
                        array_url.add(url);
                    }
                }
                setUPNewVP(array_images);
                setUiPageViewController();


                notificationList.addAll(getDataSet(json));
                mAdapter1 = new SecondRecyclerAdapter(notificationList, MainActivity.this);
                mRecyclerView1.setLayoutManager(mLayoutManager1);
                mRecyclerView1.setHasFixedSize(true);
                mRecyclerView1.setAdapter(mAdapter1);
                mAdapter1.notifyDataSetChanged();

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public class getTiles extends AsyncTask<Void,Void,Void>
    {
        String json;
        String TileImage,TileHeading,TileDescription;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            json = dumpclass.getTiles();
            try {
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    TileImage       = object.getString("TileImage");
                    TileHeading     = object.getString("TileHeading");
                    TileDescription = object.getString("TileDescription");
                    tile_image.     add(i,TileImage);
                    tile_title.     add(i,TileHeading);
                    tile_desc.      add(i,TileDescription);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            alltiles.setVisibility(View.VISIBLE);
            try {
                for(int i=0;i<tile_image.size();i++)
                {
                    if(i==0){
                        Picasso.with(MainActivity.this).load(tile_image.get(i)).into(images);
                        textView1.setText(tile_title.get(i));
                        textView2.setText(tile_desc.get(i));
                    }
                    if(i==1){
                        Picasso.with(MainActivity.this).load(tile_image.get(i)).into(images1);
                        textView3.setText(tile_title.get(i));
                        textView4.setText(tile_desc.get(i));
                    }
                    if(i==2){
                        Picasso.with(MainActivity.this).load(tile_image.get(i)).into(images2);
                        textView5.setText(tile_title.get(i));
                        textView6.setText(tile_desc.get(i));
                    }
                    if(i==3){
                        Picasso.with(MainActivity.this).load(tile_image.get(i)).into(images3);
                        textView7.setText(tile_title.get(i));
                        textView8.setText(tile_desc.get(i));
                    }
                    if(i==4){
                        Picasso.with(MainActivity.this).load(tile_image.get(i)).into(images4);
                        textView9.setText(tile_title.get(i));
                        textView10.setText(tile_desc.get(i));
                    }
                    if(i==5){
                        Picasso.with(MainActivity.this).load(tile_image.get(i)).into(images5);
                        textView11.setText(tile_title.get(i));
                        textView12.setText(tile_desc.get(i));
                    }
                    if(i==6){
                        Picasso.with(MainActivity.this).load(tile_image.get(i)).into(images6);
                        textView13.setText(tile_title.get(i));
                        textView14.setText(tile_desc.get(i));
                    }
                    if(i==7){
                        Picasso.with(MainActivity.this).load(tile_image.get(i)).into(images7);
                        textView15.setText(tile_title.get(i));
                        textView16.setText(tile_desc.get(i));
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        try{
            if(city!=null)
            {
                setTitle(""+city+" >");
            }
            else{
                setTitle("Select City");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
