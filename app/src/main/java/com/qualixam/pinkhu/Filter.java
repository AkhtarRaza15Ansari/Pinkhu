package com.qualixam.pinkhu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Filter extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvLocation,tvReview,tvTime;
    Context con;
    Spinner spFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        init();
        // Spinner Drop down elements
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
        });

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
    }
    public void init()
    {
        con = Filter.this;
        spFilter    = (Spinner) findViewById(R.id.spFilter);
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
