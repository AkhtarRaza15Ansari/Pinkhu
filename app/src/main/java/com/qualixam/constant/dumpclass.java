package com.qualixam.constant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by akhtarraza on 27/03/16.
 */
public class dumpclass extends Activity {

    public static String main_header = "http://demo.digitaledgetech.in/";
    // To fetch subcategory
    public static String subcategory = "mobileapp/getSubCategory.php";
    // To fetch category
    public static String category = "mobileapp/category.php";
    // To fetch states
    public static String states = "mobileapp/getStates.php";
    // To fetch city
    public static String city = "mobileapp/getCity.php";
    // To fetch pincode
    public static String pincode = "mobileapp/getPincode.php";

    public static String register = "mobileapp/RegisterVendor.php";

    public static String getData = "mobileapp/getStoresSearch.php";

    public static String getSortData = "mobileapp/getSortedList.php";

    public static String getSortDataLocation = "mobileapp/getSortedListDistance.php";

    public static String getSortDataRatings = "mobileapp/getSortedListRatings.php";

    public static String getAllData = "mobileapp/getAllStores.php";

    public static String slider = "mobileapp/getSlider.php";

    public static String tiles = "mobileapp/getTitle.php";

    public static String review = "mobileapp/ratestores.php";

    public static String new_user = "mobileapp/registerUser.php";

    public static String addBookmark = "mobileapp/addbookmarks.php";

    public static String getBookmark = "mobileapp/getbookmarks.php";

    public static String login = "mobileapp/loginPinkhu.php";

    public static String store_id = "mobileapp/getStoresById.php";

    public static String allNumber = "mobileapp/getAllNumbers.php";

    public static String allMessages = "mobileapp/getAllMessages.php";

    public static String insertMessage = "mobileapp/messaging.php";

    public static String checkbookmark = "mobileapp/checkbookmark.php";

    public static String deletebookmark = "mobileapp/deleteBookmark.php";

    public static String checkusers = "mobileapp/checkUsers.php";

    public static String getAllReviews = "mobileapp/getAllStoreReviews.php";

    public static String sendImage = "mobileapp/uploadfile.php";

    public static String getAllLocations = "mobileapp/listallfields.php";

    public static String sendEmail = "mobileapp/sendEmail.php";

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public static boolean isValidPhone(String phoneNumber)
    {
        return PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber);
    }
    public static String jsonvalues;
    public static HttpClient httpClient;
    public static HttpResponse response;
    public static HttpPost httpPost;
    public static HttpEntity resEntity;
    public static String response_str;
    public static void Logthis(String key,String value)
    {
        Log.d(key, value);
    }
    public static void Toastthis(String message,Context context)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static ProgressDialog mProgressDialog;

    public static void function1(String url) throws Exception
    {
        String postReceiverUrl = url.trim();
        Log.v("TAG", "postURL: " + postReceiverUrl);

        // HttpClient
        httpClient = new DefaultHttpClient();

        // post header
        httpPost = new HttpPost(postReceiverUrl);
    }
    public static void function2() throws Exception
    {
        // execute HTTP post request
        response = httpClient.execute(httpPost);
        HttpEntity resEntity = response.getEntity();

        if (resEntity != null) {

            String responseStr = EntityUtils.toString(resEntity).trim();
            Log.v("TAG", "Response: " + responseStr);
            jsonvalues = responseStr;
            // you can add an if statement here and do other actions based on the response
        }
    }
    public static String getCategory() {
        try {
            String url = main_header + category;
            function1(url);
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }

    public static String getStates() {
        try {
            String url = main_header + states;
            function1(url);
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }

    public static String getCity(String states) {
        try {
            String url = main_header + city +"?state="+states;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("states", states));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }

    public static String getPincode(String states,String city) {
        try {
            String url = main_header + pincode+"?state="+states+"&city="+city;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("states", states));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }

    public static String getSubcategory(String category,String type) {
        try {
            String url = main_header + subcategory+"?category="+category+"&type="+type;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("category", category));
            nameValuePairs.add(new BasicNameValuePair("type", type));
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String RegisterVendor(String RegisterID,String Name,String RegistrationNo,String State,
    String City,String Pincode,String TypeID,String Mobile,String EmergencyNo,String Email,String Website,
    String WorkingFrom1,String WorkingFrom2,String WorkingTo1,String WorkingTo2,String IsCheck, String Password,
    String RegisterTypeID,String Office,String Floor,String BuildingName,String StreetName,String Locality,String Landmark
    ) {
        try {
            String url = main_header + register+"?Name="+Name+
                    "&RegistrationNo="+RegistrationNo+"&State="+State+"&City="+City+"&Pincode="+Pincode+
                    "&TypeID="+TypeID+"&ContactNo="+Mobile+"&EmergencyNo="+EmergencyNo+"&Email="+Email+
                    "&Website="+Website+"&WorkingFrom1="+WorkingFrom1+"&WorkingFrom2="+WorkingFrom2+
                    "&WorkingTo1="+WorkingTo1+"&WorkingTo2="+WorkingTo2+"&IsCheck="+IsCheck+"&Password="+Password+
                    "&RegisterTypeID="+RegisterTypeID+"&Office="+Office+"&Floor="+Floor+"&BuildingName="+BuildingName+
                    "&StreetName="+StreetName+"&Locality="+Locality+"&Landmark="+Landmark;
            function1(url);
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }


    public static String getDataList(String type,String name,String locality,String city,String state) {
        try {
            String url = main_header + getData +"?type_id="+type+"&name="+name
                    +"&locality="+locality+"&city="+city+"&state="+state;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("type_id", type));
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("locality", locality));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("state", state));
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String getSortedDataList(String type,String name,String locality,String city,String state) {
        try {
            String url = main_header + getSortData +"?type_id="+type+"&name="+name
                    +"&locality="+locality+"&city="+city+"&state="+state;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("type_id", type));
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("locality", locality));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("state", state));
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String getSortedDataListLocation(String type,String name,String locality,String city,String state) {
        try {
            String url = main_header + getSortDataLocation +"?type_id="+type+"&name="+name
                    +"&locality="+locality+"&city="+city+"&state="+state;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("type_id", type));
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("locality", locality));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("state", state));
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String getSortedDataListRatings(String type,String name,String locality,String city,String state) {
        try {
            String url = main_header + getSortDataRatings +"?type_id="+type+"&name="+name
                    +"&locality="+locality+"&city="+city+"&state="+state;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("type_id", type));
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("locality", locality));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("state", state));
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String getAllData(String name,String locality,String city,String state) {
        try {
            String url = main_header + getAllData +"?name="+name
                    +"&locality="+locality+"&city="+city+"&state="+state;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("locality", locality));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("state", state));
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String getSlider() {
        try {
            String url = main_header + slider;
            function1(url);
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String getTiles() {
        try {
            String url = main_header + tiles;
            function1(url);
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String getReview(String id,String rating,String reviews) {
        try {
            reviews = reviews.replace(" ","%20");
            String url = main_header + review +
                    "?LocationID="+id+"&Rating="+rating+"&Reviews="+reviews;
            function1(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("LocationID", id));
            nameValuePairs.add(new BasicNameValuePair("Rating", rating));
            nameValuePairs.add(new BasicNameValuePair("Reviews", reviews));
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String addNewUser(String email,String mobile,String first_name,
                                    String last_name,String password) {
        try {
            first_name = first_name.replace(" ","%20");
            last_name = last_name.replace(" ","%20");
            String url = main_header + new_user +
                    "?User_Email="+email+"&User_Mobile="+mobile+"&User_FirstName="+first_name+
                    "&User_LastName="+last_name+"&User_Password="+password;
            function1(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("User_Email", email));
            nameValuePairs.add(new BasicNameValuePair("User_Mobile", mobile));
            nameValuePairs.add(new BasicNameValuePair("User_FirstName", first_name));
            nameValuePairs.add(new BasicNameValuePair("User_LastName", last_name));
            nameValuePairs.add(new BasicNameValuePair("User_Password", password));
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String loginUser(String email,String password) {
        try {
            String url = main_header + login +
                    "?User_Email="+email+"&Password="+password;
            function1(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("User_Email", email));
            nameValuePairs.add(new BasicNameValuePair("Password", password));
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String addBookmark(String registerid,String userid,String registername) {
        try {
            //registername = registername.replace(" ","");
            Log.d("user",userid);
            String url = main_header + addBookmark;
                    //+ "?register_id="+registerid+"&user_id="+userid+"&register_name="+registername;
            function1(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("register_id", registerid));
            nameValuePairs.add(new BasicNameValuePair("user_id", userid));
            nameValuePairs.add(new BasicNameValuePair("register_name", registername));
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String getBookmark(String user_id) {
        try {
            String url = main_header + getBookmark +
                    "?user_id="+user_id;
            function1(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    //
    public static void startprogress(String message,String title,Context context,Boolean cancelable)
    {
        mProgressDialog = new ProgressDialog(context);
        // Set progressdialog title
        mProgressDialog.setTitle(title);
        // Set progressdialog message
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }
    public static void dismissprogress()
    {
        mProgressDialog.dismiss();
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static String addBookmarkPost(String registerid,String userid,String registername) {


        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("http://demo.digitaledgetech.in/mobileapp/addbookmarks.php");


        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("register_id", registerid));
        nameValuePair.add(new BasicNameValuePair("user_id", userid));
        nameValuePair.add(new BasicNameValuePair("register_name", registername));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            response = httpClient.execute(httpPost);
            // write response to log
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                String responseStr = EntityUtils.toString(resEntity).trim();
                Log.v("TAG", "Response: " + responseStr);
                jsonvalues = responseStr;
                // you can add an if statement here and do other actions based on the response
            }
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }
        return jsonvalues;
    }

    public static String getStoreById(String id,String locality,String city,String state) {
        try {
            String url = main_header + store_id +"?register_id="+id
                    +"&locality="+locality+"&city="+city+"&state="+state;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("register_id", id));
            nameValuePairs.add(new BasicNameValuePair("locality", locality));
            nameValuePairs.add(new BasicNameValuePair("city", city));
            nameValuePairs.add(new BasicNameValuePair("state", state));
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }

    public static String getAllNumber() {
        try {
            String url = main_header + allNumber;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }

    public static String getCheckBookmark(String register_id,String user_id) {
        try {
            String url = main_header + checkbookmark+"?register_id="+register_id+"&user_id="+user_id;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }

    public static String getDeleteBookmark(String register_id,String user_id) {
        try {
            String url = main_header + deletebookmark+"?register_id="+register_id+"&user_id="+user_id;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }

    public static String getAllReviews(String register_id) {
        try {
            String url = main_header + getAllReviews+"?LocationID="+register_id;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String insertChatMessage(String message_from,String message_to,String message,String type) {


        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        String url = main_header + insertMessage;
        HttpPost httpPost = new HttpPost(url);

        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("message_from", message_from));
        nameValuePair.add(new BasicNameValuePair("message_to", message_to));
        nameValuePair.add(new BasicNameValuePair("message", message));
        nameValuePair.add(new BasicNameValuePair("type", type));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            response = httpClient.execute(httpPost);
            // write response to log
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                String responseStr = EntityUtils.toString(resEntity).trim();
                Log.v("TAG", "Response: " + responseStr);
                jsonvalues = responseStr;
                // you can add an if statement here and do other actions based on the response
            }
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }
        return jsonvalues;
    }

    public static String getAllMessages(String from_id,String to_id) {
        try {
            String url = main_header + allMessages +"?from_id="+from_id
                    +"&to_id="+to_id;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("from_id", from_id));
            nameValuePairs.add(new BasicNameValuePair("to_id", to_id));
            function2();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }

    public static String getUserPresent(String user_email) {
        try {
            String url = main_header + checkusers+"?User_Email="+user_email;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String getAllLocations(String user_id) {
        try {
            String url = main_header + getAllLocations + "?id="+user_id;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static String SendEmail(String name,String contact_no,String email, String address,String amount) {
        try {
            String url = main_header + sendEmail + "?name="+name
                    +"&contact_no="+contact_no+"&email_id="+email+
                    "&donation_address="+address+"&donation_amount="+amount;
            function1(url);
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            function2();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonvalues;
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static String doFileUpload(String message_from, String message_to, String path, String type) {


        try {
            String url = main_header + sendImage;

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            MultipartEntity reqEntity = new MultipartEntity();

            reqEntity.addPart("message_from", new StringBody(message_from));
            reqEntity.addPart("message_to", new StringBody(message_to));
            reqEntity.addPart("type", new StringBody(type));

            if (!path.equals("")) {
                File fmain = new File(path);
                FileBody binmain = new FileBody(fmain);
                reqEntity.addPart("image", binmain);
            }
            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            response_str = EntityUtils.toString(resEntity);
            if (resEntity != null) {
                Log.i(" RESPONSE", response_str);
            }
        } catch (Exception ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
        return response_str;
    }
}
