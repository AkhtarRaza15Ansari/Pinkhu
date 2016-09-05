package com.qualixam.pinkhu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.qualixam.constant.dumpclass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    Spinner spType;
    Button btnSubmit;
    ArrayList<String> strType;
    ArrayList<String> strId;
    String json_response;
    JSONArray object;
    ImageView mainimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Pinkhu");
        // Set the padding to match the Status Bar height
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
        mainimage = (ImageView)findViewById(R.id.mainimage);
        Picasso.with(SignUp.this).load(R.drawable.cloud).into(mainimage);
        spType = (Spinner) findViewById(R.id.spinnertype);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        new SignUpTask().execute();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("name", strType.get(spType.getSelectedItemPosition()));
                Log.d("id", strId.get(spType.getSelectedItemPosition()));
                if (spType.getSelectedItemPosition() != 0) {
                    Intent i = new Intent(SignUp.this, SignUpForms.class);
                    i.putExtra("name", strType.get(spType.getSelectedItemPosition()));
                    i.putExtra("id", strId.get(spType.getSelectedItemPosition()));
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    public class SignUpTask extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            strType = new ArrayList<>();
            strId = new ArrayList<>();
            strType.add("Select Register Type");
            strId.add("0");
            dumpclass.startprogress("Please wait", "Fetching data from server", SignUp.this, false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                json_response = dumpclass.getCategory();
                object = new JSONArray(json_response);
                for(int i =0;i<object.length();i++)
                {
                    JSONObject jsonObject = object.getJSONObject(i);
                    String name = jsonObject.getString("RegisterType");
                    String id = jsonObject.getString("RegisterTypeID");
                    strType.add(name);
                    strId.add(id);
                }
            }catch (Exception ex)
            {
                dumpclass.dismissprogress();
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dumpclass.dismissprogress();
            try {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        SignUp.this,
                        R.layout.spinner_item,
                        strType );

                // Assign adapter to Spinner
                spType.setAdapter(arrayAdapter);
                spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
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
    @Override
    protected void onPause() {
        super.onPause();
        dumpclass.deleteCache(getApplicationContext());
    }
}
