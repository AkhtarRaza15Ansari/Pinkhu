package com.qualixam.pinkhu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qualixam.constant.dumpclass;
import com.qualixam.modal.ReviewData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonateAnything extends AppCompatActivity {

    Toolbar toolbar;
    EditText name,contact,email,address,donationAmt;
    Button donate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_anything);
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
        setTitle("Donate Anything");
        name    = (EditText)    findViewById(R.id.name);
        contact    = (EditText)    findViewById(R.id.contact);
        email    = (EditText)    findViewById(R.id.email);
        address    = (EditText)    findViewById(R.id.address);
        donationAmt    = (EditText)    findViewById(R.id.donationAmt);
        donate    = (Button)    findViewById(R.id.donate);

        donate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(name.getText().toString().equals(""))
                {
                    Toast.makeText(DonateAnything.this, "Please enter name to proceed", Toast.LENGTH_SHORT).show();
                }
                else if(contact.getText().toString().equals(""))
                {
                    Toast.makeText(DonateAnything.this, "Please enter contact to proceed", Toast.LENGTH_SHORT).show();
                }
                else if(email.getText().toString().equals(""))
                {
                    Toast.makeText(DonateAnything.this, "Please enter email id to proceed", Toast.LENGTH_SHORT).show();
                }
                else if(address.getText().toString().equals(""))
                {
                    Toast.makeText(DonateAnything.this, "Please enter complete address to proceed", Toast.LENGTH_SHORT).show();
                }
                else if(donationAmt.getText().toString().equals(""))
                {
                    Toast.makeText(DonateAnything.this, "Please enter amount to proceed", Toast.LENGTH_SHORT).show();
                }
                else{
                    new DonateAsync().execute();
                }

            }
        });
    }

    public class DonateAsync extends AsyncTask<Void,Void,Void> {
        String strName="",strContact="",strEmail="",strAdd="",strAmt="";
        ArrayList<ReviewData> results;
        String json_response;
        JSONArray array;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            results = new ArrayList<ReviewData>();
            strName = name.getText().toString();
            strContact = contact.getText().toString();
            strEmail = email.getText().toString();
            strAdd = address.getText().toString();
            strAmt = donationAmt.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            strName = strName.replace(" ","%20");
            strEmail = strEmail.replace(" ","%20");
            strAmt = strAmt.replace(" ","%20");
            strContact = strContact.replace(" ","%20");
            strAdd = strAdd.replace(" ","%20");

            dumpclass.SendEmail(strName,strContact,strEmail,strAdd,strAmt);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onBackPressed();
        }
    }
}
