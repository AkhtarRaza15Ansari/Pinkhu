package com.qualixam.pinkhu;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.login.LoginManager;
import com.qualixam.constant.dumpclass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class NewUsers extends AppCompatActivity {
    ImageView mainimage;
    String firstname,lastname,password,email,mobile,confirm_password;
    EditText etFName,etLName,etPass,etConfPass,etEmail,etMob;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_users);
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
        btnSubmit   = (Button)   findViewById(R.id.btnSubmit);
        etFName     = (EditText) findViewById(R.id.firstname);
        etLName     = (EditText) findViewById(R.id.lastname);
        etPass      = (EditText) findViewById(R.id.password);
        etConfPass  = (EditText) findViewById(R.id.confirm);
        etEmail     = (EditText) findViewById(R.id.email);
        etMob       = (EditText) findViewById(R.id.Mobile);

        Intent intent = getIntent();
        String ifname = intent.getStringExtra("ifname");
        String ilname = intent.getStringExtra("ilname");
        String ipass = intent.getStringExtra("ipass");
        String icpass = intent.getStringExtra("icpass");
        String iemail = intent.getStringExtra("iemail");

        if(ifname.equals(""))
        {
            etPass.setEnabled(true);
            etConfPass.setEnabled(true);
        }
        else{
            etFName.setText(ifname);
            etLName.setText(ilname);
            etPass.setText(ipass);
            etConfPass.setText(icpass);
            etEmail.setText(iemail);
            etPass.setEnabled(false);
            etConfPass.setEnabled(false);
            etMob.requestFocus();
        }

        mainimage   = (ImageView)findViewById(R.id.mainimage);

        Picasso.with(NewUsers.this).load(R.drawable.cloud).into(mainimage);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstname = etFName.getText().toString();
                lastname = etLName.getText().toString();
                password = etPass.getText().toString();
                confirm_password = etConfPass.getText().toString();
                email = etEmail.getText().toString();
                mobile = etMob.getText().toString();

                if(!dumpclass.isValidEmail(email)){
                    dumpclass.Toastthis("Please enter a valid email",NewUsers.this);
                }
                else if(!dumpclass.isValidPhone(mobile)){
                    dumpclass.Toastthis("Please enter a valid mobile number",NewUsers.this);
                }
                else if(firstname.length()<1){
                    dumpclass.Toastthis("Please enter first name to proceed",NewUsers.this);
                }
                else if(lastname.length()<1){
                    dumpclass.Toastthis("Please enter last name to proceed",NewUsers.this);
                }
                else if(password.length()<1){
                    dumpclass.Toastthis("Please enter password to proceed",NewUsers.this);
                }
                else if(!(password.equals(confirm_password))){
                    dumpclass.Toastthis("Passwords did not match",NewUsers.this);
                }
                else{
                    new NewUserAsync().execute();
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

    public class NewUserAsync extends AsyncTask<Void,Void,Void>
    {
        String json;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            json = dumpclass.addNewUser(email,mobile,firstname,lastname,password);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONObject object = new JSONObject(json);
                if(object.getString("Message").equals("Successful")) {
                    LoginManager.getInstance().logOut();
                    onBackPressed();
                }else if(object.getString("Message").equals("Already present. Please try to signup."))
                    dumpclass.Toastthis("Already present. Please try to signup.",NewUsers.this);
                else
                    dumpclass.Toastthis("Something went wrong, Please try again.",NewUsers.this);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
