package com.qualixam.pinkhu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.appevents.AppEventsLogger;
import com.qualixam.constant.dumpclass;

import java.util.ArrayList;
import java.util.Locale;

public class Splash extends AppCompatActivity {
    ImageView imgsplash;
    private static int SPLASH_TIME_OUT = 4000;
    SharedPreferences prefs;
    public static Context context;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String permission1 = Manifest.permission.INTERNET;
    String permission2 = Manifest.permission.ACCESS_FINE_LOCATION;
    String permission3 = Manifest.permission.READ_EXTERNAL_STORAGE;
    String permission4 = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    String permission5 = Manifest.permission.RECEIVE_SMS;
    String permission6 = Manifest.permission.READ_CONTACTS;
    String permission7 = Manifest.permission.READ_PHONE_STATE;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgsplash = (ImageView) findViewById(R.id.imageSplash);
        prefs = getSharedPreferences("Pinkhu", Context.MODE_PRIVATE);
        context = Splash.this;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // only for gingerbread and newer versions
            requestPermission();
        }
        else{
            allpermission();
        }
        String value = String.valueOf(Locale.getDefault().getDisplayLanguage());
        Log.d("value",""+value);


    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        dumpclass.deleteCache(getApplicationContext());
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    private boolean checkPermission(String permission){
        int result = checkSelfPermission(permission);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(){

        if (checkPermission(permission1)&&checkPermission(permission2)&&checkPermission(permission3)&&checkPermission(permission4)
                &&checkPermission(permission5)&&checkPermission(permission6)&&checkPermission(permission7)){
            allpermission();
        } else {
            ArrayList<String> per = new ArrayList<String>();
            if(!checkPermission(permission1))
            {
                per.add(permission1);
            }
            if(!checkPermission(permission2))
            {
                per.add(permission2);
            }
            if(!checkPermission(permission3))
            {
                per.add(permission3);
            }
            if(!checkPermission(permission4))
            {
                per.add(permission4);
            }
            if(!checkPermission(permission5))
            {
                per.add(permission5);
            }
            if(!checkPermission(permission6))
            {
                per.add(permission6);
            }
            if(!checkPermission(permission7))
            {
                per.add(permission7);
            }
            String[] permissionarray = per.toArray(new String[per.size()]);
            requestPermissions(permissionarray, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("permission", "granted");
                    allpermission();
                } else {
                    Log.d("permission", "denied");
                    allpermission();
                }
                break;
            default:
                break;
        }
    }

    public void allpermission()
    {
        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //Display for 3 seconds
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    if(prefs.getString("logout","yes").equals("yes")) {
                        Intent i = new Intent(Splash.this,Login.class);
                        i.putExtra("type","");
                        startActivity(i);
                        finish();
                    }
                    else {
                        Intent i = new Intent(Splash.this,MainActivity.class);
                        i.putExtra("type","");
                        startActivity(i);
                        finish();
                    }
                }
            }
        };
        timer.start();
    }
}
