package com.qualixam.pinkhu;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.qualixam.adapter.ChatAdapter;
import com.qualixam.adapter.RecyclerAdapterSearch1;
import com.qualixam.constant.dumpclass;
import com.qualixam.modal.ChatListData;
import com.qualixam.modal.ListData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class ChatListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    String json_response;
    JSONArray array;
    ArrayList<String> allnumber, cntact, oldid,oldnamearr,newnamearr;//all number --> From device
    // cntact   --> From server

    ArrayList<String> newnumber, newid;
    int count = 0;

    ArrayList results;
    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager1;
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences prefs;
    static String owner_name="";
    static String owner_mobile = "";
    static String owner_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

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

        setTitle("Chat with");
        prefs = getSharedPreferences("Pinkhu", Context.MODE_PRIVATE);

        owner_id = prefs.getString("id","");
        owner_name = prefs.getString("Name","");
        owner_mobile = prefs.getString("Mobile","");

        allnumber = new ArrayList<>();
        cntact = new ArrayList<>();
        oldid = new ArrayList<>();
        oldnamearr = new ArrayList<>();
        newnamearr = new ArrayList<>();
        newnumber = new ArrayList<>();
        newid = new ArrayList<>();

        mRecyclerView1 = (RecyclerView) findViewById(R.id.my_recycler_view1);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager1 = new GridLayoutManager(ChatListActivity.this, 1);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        new getAllContacts().execute();
                                    }
                                }
        );
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new getAllContacts().execute();
    }

    public class AllNumbers extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                json_response = dumpclass.getAllNumber();
                array = new JSONArray(json_response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String User_Mobile = object.getString("User_Mobile");
                    String User_ID = object.getString("User_ID");
                    String User_LastName = object.getString("User_FirstName");
                    cntact.add(User_Mobile);
                    oldid.add(User_ID);
                    oldnamearr.add(User_LastName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            checkContacts();

        }
    }

    public class AsyncLoad extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            results = new ArrayList<ChatListData>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0;i<newnumber.size();i++)
            {
                ChatListData data = new ChatListData(newid.get(i),newnamearr.get(i),newnumber.get(i));
                results.add(i, data);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swipeRefreshLayout.setRefreshing(false);
            mAdapter = new ChatAdapter(results,ChatListActivity.this);
            mRecyclerView1.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }
    public class getAllContacts extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dumpclass.startprogress("Fetching Contacts","Please wait",ChatListActivity.this,false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            allnumber = fetchContacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dumpclass.dismissprogress();
            new AllNumbers().execute();
        }
    }
    public void checkContacts(){
        for(String s:allnumber)
        {
            count++;
            s = s.replace(" ","");
            s = s.replace("+91","");

            for(int i=0;i<cntact.size();i++)
            {
                //Log.d("number: "+cntact.get(i),"compare: "+s);
                if( s.contains( cntact.get(i) ) ) {
                    Log.d("success","true");

                    if (newnumber.contains(cntact.get(i)) || owner_mobile.equals(cntact.get(i))) {
                        Log.d("caught","true");

                    }
                    else{
                        newid.add(oldid.get(i));
                        newnumber.add(cntact.get(i));
                        newnamearr.add(oldnamearr.get(i));
                    }
                }
            }
        }

        for(int i=0;i<newnumber.size();i++)
        {
            Log.d("number: "+newnumber.get(i),"id: "+newid.get(i));
        }
        new AsyncLoad().execute();
    }
    public void checkContacts1(){
        for(String s:allnumber)
        {
            count++;
            //Log.d("number: ",cntact.get(count));
            for(int i=0;i<cntact.size();i++)
            {
                if( cntact.get(i).equals( s ) ) {
                    newid.add(oldid.get(count));
                    newnumber.add(cntact.get(count));
                }
            }

        }
        for(int i=0;i<newnumber.size();i++)
        {
            ArrayList<String> values=new ArrayList<String>();
            values = newnumber;
            HashSet<String> hashSet = new HashSet<String>();
            hashSet.addAll(values);
            values.clear();
            values.addAll(hashSet);
            Log.d("number: "+newnumber.get(i),"id: "+newid.get(i));
        }
    }

    public ArrayList<String> fetchContacts() {
        ArrayList<String> mobile = new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            //String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            mobile.add(phoneNumber);
        }
        phones.close();
        return mobile;
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
