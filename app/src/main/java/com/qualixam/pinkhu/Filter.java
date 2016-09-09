package com.qualixam.pinkhu;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qualixam.constant.dumpclass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Filter extends AppCompatActivity {
    Toolbar toolbar;
    //TextView tvLocation,tvReview,tvTime;
    Context con;
    //Spinner spFilter;
    Spinner spState, spCity;
    String strStates, strCity;
    ArrayList<String> arrState, arrCity;
    String json_response;
    JSONArray object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        init();



        /*// Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select your options");
        categories.add("Last Updated");
        categories.add("Nearest Location");
        categories.add("Most Rated");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spFilter.setAdapter(dataAdapter);
        spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                // Showing selected spinner item
                //Toast.makeText(Filter.this, "Selected: " + item, Toast.LENGTH_LONG).show();
                if(i==1)
                {
                    MainActivity.number = 1;
                    MainActivity.refresh = true;
                    onBackPressed();
                }
                else if(i==2)
                {
                    MainActivity.number = 2;
                    MainActivity.refresh = true;
                    onBackPressed();
                }
                else if(i==3)
                {
                    MainActivity.number = 3;
                    MainActivity.refresh = true;
                    onBackPressed();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

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
        setTitle("Select your city");
        new State().execute();
    }

    public void init() {
        con = Filter.this;
        //spFilter    = (Spinner) findViewById(R.id.spFilter);
        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spCity);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public class State extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arrState = new ArrayList<>();
            arrState.add("Select State");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                json_response = dumpclass.getStates();
                object = new JSONArray(json_response);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);
                    String state = jsonObject.getString("State");
                    arrState.add(state);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        Filter.this,
                        R.layout.spinner_item,
                        arrState);

                // Assign adapter to Spinner
                spState.setAdapter(arrayAdapter);
                spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            strStates = spState.getSelectedItem().toString();
                            strStates = strStates.replace(" ","%20");
                            new City().execute();
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class City extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arrCity = new ArrayList<>();
            arrCity.add("Select City");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                json_response = dumpclass.getCity(strStates);
                object = new JSONArray(json_response);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);
                    String name = jsonObject.getString("CITY");
                    arrCity.add(name);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        Filter.this,
                        R.layout.spinner_item,
                        arrCity);

                // Assign adapter to Spinner
                spCity.setAdapter(arrayAdapter);
                spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            strCity = spCity.getSelectedItem().toString();
                            MainActivity.state = strStates;
                            MainActivity.city = strCity;
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
