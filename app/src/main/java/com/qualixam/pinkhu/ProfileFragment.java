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

public class ProfileFragment extends Fragment {
    private CallbackManager callbackManager;
    SharedPreferences prefs;
    Intent i;
    EditText etEmail,etPassword;
    Button btnLogin,btnRegisterVendor;
    TextView tvSignUp,tvForgotPassword;
    String Email,Password,Name,ID,Last="";
    public ProfileFragment() {
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
        /*accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };*/
        /*profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };*/
        /*accessTokenTracker.startTracking();
        profileTracker.startTracking();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        etEmail = (EditText)root.findViewById(R.id.input_email);
        etPassword = (EditText)root.findViewById(R.id.input_password);
        btnLogin = (Button) root.findViewById(R.id.btnLogin);
        btnRegisterVendor = (Button) root.findViewById(R.id.btnRegisterVendor);
        tvSignUp = (TextView) root.findViewById(R.id.tvSignUp);
        tvForgotPassword = (TextView)root.findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ForgotPassword.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = etEmail.getText().toString();
                Password = etPassword.getText().toString();
                if(!dumpclass.isValidEmail(Email))
                {
                    dumpclass.Toastthis("Please enter a valid email address",getActivity());
                }
                else if(Password.length()<1){
                    dumpclass.Toastthis("Please enter a valid password",getActivity());
                }
                else{
                    new LoginAsync().execute();
                }
                /*Intent i = new Intent(getActivity(),MainActivity.class);
                startActivity(i);*/

            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),NewUsers.class);
                i.putExtra("ifname","");
                i.putExtra("ilname","");
                i.putExtra("ipass","");
                i.putExtra("icpass","");
                i.putExtra("iemail","");
                startActivity(i);
            }
        });
        btnRegisterVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SignUp.class);
                startActivity(i);
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

                                        Email = object.getString("email");
                                        ID = object.getString("id");
                                        Name = object.getString("first_name");
                                        Last = object.getString("last_name");
                                        Password = "facebook";

                                        new CheckUserAsync().execute();
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
        //signInWithGplus();
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
    /*private void displayMessage(Profile profile){
        if(profile != null){
            textView.setText(profile.getName());
            prefs = getActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE);
            Log.d("name",profile.getName());
            Log.d("ProfilePic", String.valueOf(profile.getProfilePictureUri(320, 240)));
            Log.d("id",profile.getId());
            SharedPreferences.Editor e = prefs.edit();
            e.putString("Name", profile.getName());
            e.putString("ProfilePic", String.valueOf(profile.getProfilePictureUri(320, 240)));
            e.putString("id",profile.getId());
            e.putString("logout","no");
            e.apply();
            Intent i = new Intent(getActivity(),Dashboard.class);
            i.putExtra("type","login");
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            getActivity().finish();
        }
    }*/
    @Override
    public void onResume() {
        super.onResume();
        /*Profile profile = Profile.getCurrentProfile();
        displayMessage(profile);*/
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
        /*if(logintype==0)
        {
            accessTokenTracker.stopTracking();
            profileTracker.stopTracking();
        }*/
    }

    public class CheckUserAsync extends AsyncTask<Void,Void,Void>
    {
        String json;
        String response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            json = dumpclass.getUserPresent(Email);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONObject object = new JSONObject(json);
                response = object.getString("response");
                LoginManager.getInstance().logOut();
                if(response.equals("0"))
                {
                    Intent i = new Intent(getActivity(),NewUsers.class);
                    i.putExtra("ifname",Name);
                    i.putExtra("ilname",Last);
                    i.putExtra("ipass",Password);
                    i.putExtra("icpass",Password);
                    i.putExtra("iemail",Email);
                    startActivity(i);
                }
                else{
                    new LoginAsync().execute();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class LoginAsync extends AsyncTask<Void,Void,Void>
    {
        String json;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            json = dumpclass.loginUser(Email,Password);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONObject object = new JSONObject(json);
                if(object.getString("statuscode").equals("500")){
                    LoginManager.getInstance().logOut();
                    dumpclass.Toastthis("Entered Username and Password are not found.",getActivity());
                }
                else{
                    String user_id = object.getString("User_ID");
                    String user_email = object.getString("User_Email");
                    String user_mobile = object.getString("User_Mobile");
                    String user_first = object.getString("User_FirstName");
                    String user_last = object.getString("User_LastName");
                    SharedPreferences.Editor e = prefs.edit();
                    e.putString("logout", "no");
                    e.putString("Name", user_first+" "+user_last);
                    e.putString("id", user_id);
                    e.putString("Mobile",user_mobile);
                    e.putString("email", user_email);
                    e.apply();
                    LoginManager.getInstance().logOut();
                    Intent i = new Intent(getActivity(),MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
