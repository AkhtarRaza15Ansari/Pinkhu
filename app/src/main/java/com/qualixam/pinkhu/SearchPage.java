package com.qualixam.pinkhu;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.qualixam.adapter.RecyclerAdapterSearch;
import com.qualixam.adapter.RecyclerAdapterSearch1;
import com.qualixam.constant.dumpclass;
import com.qualixam.modal.DataObject;
import com.qualixam.modal.ListData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter;
    String json_response;
    JSONArray object;
    String name = "";
    ArrayList results;
    private RecyclerView.LayoutManager mLayoutManager1;
    //List of type books this list will store type Book which is our data model
    private List<ListData> data;
    String state="", city="", locality="";
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        locality = getIntent().getStringExtra("locality");

        mRecyclerView1 = (RecyclerView) findViewById(R.id.my_recycler_view1);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager1 = new GridLayoutManager(this, 1);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //swipeRefreshLayout.setRefreshing(true);
                                        //new Type().execute();
                                    }
                                }
        );
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new Type().execute();
    }

    public class Type extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                locality = locality.replace(" ","%20");
                name = name.replace(" ","%20");
                city = city.replace(" ","%20");
                state = state.replace(" ","%20");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try {
                results = new ArrayList<ListData>();

                json_response = dumpclass.getAllData(name, locality, city, state);
                object = new JSONArray(json_response);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);
                    String RegisterID = jsonObject.getString("RegisterID");
                    String name = jsonObject.getString("Name");
                    String address = jsonObject.getString("Address");
                    String ratings = jsonObject.getString("ratings");
                    String specialisation = jsonObject.getString("Specialisation");
                    String completeaddress = jsonObject.getString("completeAddress");
                    String workinghours = jsonObject.getString("workinghours");
                    String phone = jsonObject.getString("ContactNo");
                    String distance = jsonObject.getString("distance");
                    String offers = jsonObject.getString("offers");
                    String about = jsonObject.getString("About");
                    String website = jsonObject.getString("Website");
                    String email = jsonObject.getString("Email");
                    String type = jsonObject.getString("type");
                    String lastupdated = jsonObject.getString("LastUpdated");
                    ListData data = new ListData(RegisterID,name, address,ratings,specialisation,
                            completeaddress, workinghours,phone,
                            distance, offers,about,website,email,type,lastupdated);
                    results.add(i, data);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swipeRefreshLayout.setRefreshing(false);
            mAdapter = new RecyclerAdapterSearch1(results,SearchPage.this);
            mRecyclerView1.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    //Our method to show list
    private void showList(){
        //String array to store all the book names
        String[] items = new String[data.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<data.size(); i++){
            //Storing names to string array
            items[i] = data.get(i).getName();
        }


    }

    private ArrayList<ListData> getDataSet() {
        ArrayList results = new ArrayList<ListData>();
        for (int i=0; i<data.size(); i++) {
            String RegisterID = ""+data.get(i).getRegisterid();
            String name = ""+data.get(i).getName();
            String address = ""+data.get(i).getAddress();
            String ratings = ""+data.get(i).getRatings();
            String specialisation = ""+data.get(i).getSpecialisation();
            String completeaddress = ""+data.get(i).getCompleteAddress();
            String workinghours = ""+data.get(i).getWorkingHours();
            String phone = ""+data.get(i).getPhone();
            String distance = ""+data.get(i).getDistance();
            String offers = ""+data.get(i).getOffers();
            String about = ""+data.get(i).getAbout();
            String email = ""+data.get(i).getEmail();
            String website = ""+data.get(i).getWebsite();
            String type = ""+data.get(i).getType();
            String lastupdated = "";
            ListData data = new ListData(RegisterID,name, address,ratings,specialisation,
                    completeaddress, workinghours,phone,
                    distance, offers,about,website,email,type,lastupdated);
            results.add(i, data);
        }
        return results;
    }
    
    @Override
    public void onStop()
    {
        super.onStop();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //UserFeedback.show( "SearchOnQueryTextSubmit: " + query);
                /*if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }*/

                myActionMenuItem.collapseActionView();
                //use the query to search your data somehow
                Log.d("query", query);
                name = query;
                //setupViewPager(viewPager);
                onRefresh();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });
        searchView.setQuery("",true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                name = "";
                new Type().execute();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            Log.d("query", query);
            name = query;
            new Type().execute();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        dumpclass.deleteCache(getApplicationContext());
    }
}
