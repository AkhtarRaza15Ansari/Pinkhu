package com.qualixam.pinkhu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.qualixam.constant.dumpclass;

import org.json.JSONObject;

import java.util.Arrays;

public class SignUpFragment extends Fragment {
    private CallbackManager callbackManager;
    SharedPreferences prefs;
    Intent i;
    String firstname,lastname,password,email,mobile,confirm_password;
    EditText etFName,etLName,etPass,etConfPass,etEmail,etMob;
    Button btnSubmit;
    public SignUpFragment() {
        // Required empty public constructor
    }
   /* private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            displayMessage(profile);
        }
        @Override
        public void onCancel() {
        }
        @Override
        public void onError(FacebookException e) {
        }
    };*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        prefs = getActivity().getSharedPreferences("Pinkhu", getActivity().MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_signup, container, false);
        btnSubmit   = (Button)   root.findViewById(R.id.btnSubmit);
        etFName     = (EditText) root.findViewById(R.id.firstname);
        etLName     = (EditText) root.findViewById(R.id.lastname);
        etPass      = (EditText) root.findViewById(R.id.password);
        etConfPass  = (EditText) root.findViewById(R.id.confirm);
        etEmail     = (EditText) root.findViewById(R.id.email);
        etMob       = (EditText) root.findViewById(R.id.Mobile);

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
                    dumpclass.Toastthis("Please enter a valid email",getActivity());
                }
                else if(!dumpclass.isValidPhone(mobile)){
                    dumpclass.Toastthis("Please enter a valid mobile number",getActivity());
                }
                else if(firstname.length()<1){
                    dumpclass.Toastthis("Please enter first name to proceed",getActivity());
                }
                else if(lastname.length()<1){
                    dumpclass.Toastthis("Please enter last name to proceed",getActivity());
                }
                else if(password.length()<1){
                    dumpclass.Toastthis("Please enter password to proceed",getActivity());
                }
                else if(!(password.equals(confirm_password))){
                    dumpclass.Toastthis("Passwords did not match",getActivity());
                }
                else{
                    new NewUserAsync().execute();
                }
            }
        });
        return root;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        i = getActivity().getIntent();
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        loginButton.setFragment(this);
        FacebookCallback<LoginResult> callback = null;
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                try {
                                    Log.d("LoginActivity", response.toString());
                                    Log.d("name", object.getString("first_name").toString());
                                    Log.d("email", object.getString("email").toString());
                                    JSONObject data = response.getJSONObject();
                                    if (data.has("picture")) {
                                        String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                        Log.d("profilePicUrl", profilePicUrl);

                                        email = object.getString("email");
                                        //ID = object.getString("id");
                                        firstname = object.getString("first_name");
                                        lastname = object.getString("last_name");
                                        password = "facebook";
                                        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                                        // Include dialog.xml file
                                        dialog.setContentView(R.layout.dialogphone);
                                        // Set dialog title
                                        dialog.setTitle("Please provide your mobile number");

                                        // set values for custom dialog components - text, image and button
                                        final EditText etMobile = (EditText) dialog.findViewById(R.id.etMobile);
                                        dialog.show();

                                        Button declineButton = (Button) dialog.findViewById(R.id.btnDone);
                                        // if decline button is clicked, close the custom dialog
                                        declineButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                // Close dialog
                                                if(etMobile.getText().toString().equals(""))
                                                {
                                                    dumpclass.Toastthis("Please enter mobile number",getActivity());
                                                }
                                                else if(etMobile.getText().toString().length()<10)
                                                {
                                                    dumpclass.Toastthis("Please enter correct mobile number",getActivity());
                                                }
                                                else{
                                                    mobile = etMobile.getText().toString();
                                                    new NewUserAsync().execute();
                                                    dialog.dismiss();
                                                }
                                            }
                                        });
                                        /*Intent i = new Intent(getActivity(), MainActivity.class);
                                        startActivity(i);*/
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender,cover,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
                callbackManager.onActivityResult(requestCode, resultCode, data);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onStop() {
        super.onStop();

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
                    getActivity().onBackPressed();
                }else if(object.getString("Message").equals("Already present. Please try to signup."))
                    dumpclass.Toastthis("Already present. Please try to signup.",getActivity());
                else
                    dumpclass.Toastthis("Something went wrong, Please try again.",getActivity());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
