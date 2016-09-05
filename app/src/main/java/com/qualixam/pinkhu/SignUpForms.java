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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.qualixam.constant.dumpclass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUpForms extends AppCompatActivity {
    String strtype,strid,strStates,strCity,strPincode;
    Intent i;
    Spinner spHourTo,spMinTo,spHourFrom,spMinFrom;
    ArrayList<String> hour,mins;
    Spinner spState,spCity,spPin,spType;
    ArrayList<String> arrTypeId,arrType,arrId,arrState,arrCity,arrPincode;
    String json_response;
    JSONArray object;
    Button btnSubmit;
    String RegisterID, Name,RegistrationNo,State, City, Pincode, TypeID, Mobile, EmergencyNo,
            Email, Website, WorkingFrom1, WorkingFrom2, WorkingTo1, WorkingTo2, IsCheck, Password,
            RegisterTypeID, Office, Floor, BuildingName, StreetName, Locality, Landmark;
    CheckBox etIsCheck;
    EditText etName,etRegistrationNo,etMobile, etEmergencyNo,etEmail, etWebsite, etPassword,
            etOffice, etFloor, etBuildingName, etStreetName, etLocality, etLandmark;
    ImageView mainimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_forms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        Picasso.with(SignUpForms.this).load(R.drawable.cloud).into(mainimage);
        i = getIntent();
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        etName = (EditText)findViewById(R.id.name);
        etRegistrationNo = (EditText)findViewById(R.id.registrationno);
        etMobile = (EditText)findViewById(R.id.contact);
        etEmergencyNo = (EditText)findViewById(R.id.emergencyno);
        etEmail = (EditText)findViewById(R.id.email);
        etWebsite = (EditText)findViewById(R.id.website);
        etPassword = (EditText)findViewById(R.id.password);
        etOffice = (EditText)findViewById(R.id.officeno);
        etFloor = (EditText)findViewById(R.id.floor);
        etBuildingName = (EditText)findViewById(R.id.buildingname);
        etStreetName = (EditText)findViewById(R.id.streetname);
        etLocality = (EditText)findViewById(R.id.locality);
        etLandmark = (EditText)findViewById(R.id.landmark);

        etIsCheck = (CheckBox) findViewById(R.id.alltime);

        spHourTo = (Spinner) findViewById(R.id.spHourTo);
        spMinTo = (Spinner) findViewById(R.id.spMinTo);
        spHourFrom = (Spinner) findViewById(R.id.spHourFrom);
        spMinFrom = (Spinner) findViewById(R.id.spMinFrom);
        spState = (Spinner) findViewById(R.id.spState);
        spType = (Spinner) findViewById(R.id.spType);
        spCity = (Spinner) findViewById(R.id.spCity);
        spPin = (Spinner) findViewById(R.id.spPincode);

        arrType = new ArrayList<String>();
        arrTypeId = new ArrayList<String>();
        mins = new ArrayList<String>();
        hour = new ArrayList<String>();
        mins = new ArrayList<String>();
        hour = new ArrayList<String>();

        hour = new ArrayList<String>();
        mins = new ArrayList<String>();

        hour.add("01");
        hour.add("02");
        hour.add("03");
        hour.add("04");
        hour.add("05");
        hour.add("06");
        hour.add("07");
        hour.add("08");
        hour.add("09");
        hour.add("10");
        hour.add("11");
        hour.add("12");
        hour.add("13");
        hour.add("14");
        hour.add("15");
        hour.add("16");
        hour.add("17");
        hour.add("18");
        hour.add("19");
        hour.add("20");
        hour.add("21");
        hour.add("22");
        hour.add("23");
        hour.add("24");

        mins.add("10");
        mins.add("20");
        mins.add("30");
        mins.add("40");
        mins.add("50");
        mins.add("00");

        strid = i.getStringExtra("id");
        strtype = i.getStringExtra("name");
        Log.d("id"+strid,"name"+strtype);

        ArrayAdapter<String> houradapter = new ArrayAdapter<String>(
                SignUpForms.this,
                R.layout.spinner_item,
                hour );
        ArrayAdapter<String> minadapter = new ArrayAdapter<String>(
                SignUpForms.this,
                R.layout.spinner_item,
                mins );

        spHourTo.setAdapter(houradapter);
        spHourTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMinTo.setAdapter(minadapter);
        spMinTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spHourFrom.setAdapter(houradapter);
        spHourFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMinFrom.setAdapter(minadapter);
        spMinFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        new Type().execute();
        new State().execute();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RegisterID = etRegisterID.getText().toString();
                Name= etName.getText().toString();
                RegistrationNo = etRegistrationNo.getText().toString();
                Mobile = etMobile.getText().toString();
                EmergencyNo = etEmergencyNo.getText().toString();
                Email = etEmail.getText().toString();
                Website = etWebsite.getText().toString();
                WorkingFrom1 = spHourFrom.getSelectedItem().toString();
                WorkingFrom2 = spMinFrom.getSelectedItem().toString();
                WorkingTo1 = spHourTo.getSelectedItem().toString();
                WorkingTo2 = spMinTo.getSelectedItem().toString();
                if(etIsCheck.isChecked())
                {
                    IsCheck ="1";
                }
                else {
                    IsCheck ="0";
                }
                Password = etPassword.getText().toString();
                RegisterTypeID = arrId.get(spType.getSelectedItemPosition());
                Office = etOffice.getText().toString();
                Floor = etFloor.getText().toString();
                BuildingName = etBuildingName.getText().toString();
                StreetName = etStreetName.getText().toString();
                Locality = etLocality.getText().toString();
                Landmark = etLandmark.getText().toString();
                if(Name.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the name", Toast.LENGTH_SHORT).show();
                }
                else if(RegistrationNo.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the registration number", Toast.LENGTH_SHORT).show();
                }
                else if(spState.getSelectedItemPosition()<0)
                {
                    Toast.makeText(SignUpForms.this, "Please select State", Toast.LENGTH_SHORT).show();
                }
                else if(spCity.getSelectedItemPosition()<0)
                {
                    Toast.makeText(SignUpForms.this, "Please select City", Toast.LENGTH_SHORT).show();
                }
                else if(spPin.getSelectedItemPosition()<0)
                {
                    Toast.makeText(SignUpForms.this, "Please select Pincode", Toast.LENGTH_SHORT).show();
                }
                else if(spType.getSelectedItemPosition()<0)
                {
                    Toast.makeText(SignUpForms.this, "Please select register type", Toast.LENGTH_SHORT).show();
                }
                else if(Mobile.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the Mobile number", Toast.LENGTH_SHORT).show();
                }
                else if(EmergencyNo.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the Emergency number", Toast.LENGTH_SHORT).show();
                }
                else if(Email.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the Email", Toast.LENGTH_SHORT).show();
                }
                else if(Website.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the Website", Toast.LENGTH_SHORT).show();
                }
                else if(Password.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the Password", Toast.LENGTH_SHORT).show();
                }
                else if(Office.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the Office no", Toast.LENGTH_SHORT).show();
                }
                else if(Floor.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the Floor no", Toast.LENGTH_SHORT).show();
                }
                else if(BuildingName.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the Building Name", Toast.LENGTH_SHORT).show();
                }
                else if(StreetName.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the Street Name", Toast.LENGTH_SHORT).show();
                }
                else if(Locality.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the Locality", Toast.LENGTH_SHORT).show();
                }
                else if(Landmark.equals(""))
                {
                    Toast.makeText(SignUpForms.this, "Please enter the Landmark", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        State = spState.getSelectedItem().toString();
                        City = spCity.getSelectedItem().toString();
                        Pincode = spPin.getSelectedItem().toString();
                        TypeID = arrTypeId.get(spType.getSelectedItemPosition());
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    new SubmitAccount().execute();
                }
            }
        });
    }
    public class SubmitAccount extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            json_response = dumpclass.RegisterVendor(RegisterID, Name, RegistrationNo, State,
                    City, Pincode, TypeID, Mobile, EmergencyNo, Email, Website,
                    WorkingFrom1, WorkingFrom2, WorkingTo1, WorkingTo2, IsCheck, Password,
                    RegisterTypeID, Office, Floor, BuildingName, StreetName, Locality, Landmark);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            onBackPressed();
        }
    }
    public class Type extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arrType = new ArrayList<>();
            arrTypeId = new ArrayList<>();
            arrId = new ArrayList<>();
            arrType.add("Select Register Type");
            arrId.add("0");
            arrTypeId.add("0");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                json_response = dumpclass.getSubcategory("ngo", strid);
                object = new JSONArray(json_response);
                for(int i =0;i<object.length();i++)
                {
                    JSONObject jsonObject = object.getJSONObject(i);
                    String type_id = jsonObject.getString("TypeID");
                    String name = jsonObject.getString("TypeName");
                    String id = jsonObject.getString("RegisterTypeID");
                    arrType.add(name);
                    arrTypeId.add(type_id);
                    arrId.add(id);
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        SignUpForms.this,
                        R.layout.spinner_item,
                        arrType );

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
    public class State extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arrState = new ArrayList<>();
            arrState.add("Select State");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                json_response = dumpclass.getStates();
                object = new JSONArray(json_response);
                for(int i =0;i<object.length();i++)
                {
                    JSONObject jsonObject = object.getJSONObject(i);
                    String state = jsonObject.getString("State");
                    arrState.add(state);
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        SignUpForms.this,
                        R.layout.spinner_item,
                        arrState );

                // Assign adapter to Spinner
                spState.setAdapter(arrayAdapter);
                spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position!=0)
                        {
                            strStates = spState.getSelectedItem().toString();
                            new City().execute();
                        }


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
    public class City extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arrCity = new ArrayList<>();
            arrCity.add("Select City");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                json_response = dumpclass.getCity(strStates);
                object = new JSONArray(json_response);
                for(int i =0;i<object.length();i++)
                {
                    JSONObject jsonObject = object.getJSONObject(i);
                    String name = jsonObject.getString("CITY");
                    arrCity.add(name);
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        SignUpForms.this,
                        R.layout.spinner_item,
                        arrCity );

                // Assign adapter to Spinner
                spCity.setAdapter(arrayAdapter);
                spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position!=0)
                        {
                            strCity = spCity.getSelectedItem().toString();
                            new Pincode().execute();
                        }
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
    public class Pincode extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arrPincode = new ArrayList<>();
            arrPincode.add("Select Pincode");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                json_response = dumpclass.getPincode(strStates, strCity);
                object = new JSONArray(json_response);
                for(int i =0;i<object.length();i++)
                {
                    JSONObject jsonObject = object.getJSONObject(i);
                    String name = jsonObject.getString("PinCode");
                    arrPincode.add(name);
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        SignUpForms.this,
                        R.layout.spinner_item,
                        arrPincode );

                // Assign adapter to Spinner
                spPin.setAdapter(arrayAdapter);
                spPin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position!=0)
                        {
                            strPincode = spPin.getSelectedItem().toString();
                        }
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
}