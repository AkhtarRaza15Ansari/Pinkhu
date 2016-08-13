package com.qualixam.pinkhu;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.qualixam.adapter.RecyclerAdapterSearch;
import com.qualixam.adapter.RecyclerAdapterSearch1;
import com.qualixam.constant.ListDataApi;
import com.qualixam.constant.dumpclass;
import com.qualixam.modal.DataObject;
import com.qualixam.modal.ListData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MedsCon extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {
    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter;
    String json_response;
    JSONArray object;
    String name = "";
    ArrayList results;
    private RecyclerView.LayoutManager mLayoutManager1;
    //List of type books this list will store type Book which is our data model
    private List<ListData> data;
    String type;
    String state="", city="", locality="";
    SwipeRefreshLayout swipeRefreshLayout;

    public MedsCon() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meds_con, container, false);

        name = SearchPageNew.name;
        city = SearchPageNew.city;
        state = SearchPageNew.state;
        locality = SearchPageNew.locality;

        mRecyclerView1 = (RecyclerView) view.findViewById(R.id.my_recycler_view1);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager1 = new GridLayoutManager(getActivity(), 1);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        new Type().execute();
                                    }
                                }
        );
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),Filter.class);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new Type().execute();
    }
    public void onResume()
    {
        super.onResume();
        if(MainActivity.refresh)
        {
            swipeRefreshLayout.setRefreshing(true);
            new Type().execute();
            MainActivity.refresh = false;
        }
    }

    public class Type extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                results = new ArrayList<ListData>();

                try{
                    locality = locality.replace(" ","%20");
                    name = name.replace(" ","%20");
                    city = city.replace(" ","%20");
                    state = state.replace(" ","%20");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                if(MainActivity.number ==1)
                {
                    json_response = dumpclass.getSortedDataList("2",name,locality,city,state);
                }
                else if(MainActivity.number ==2)
                {
                    json_response = dumpclass.getSortedDataListLocation("2",name,locality,city,state);
                } else if(MainActivity.number ==3)
                {
                    json_response = dumpclass.getSortedDataListRatings("2",name,locality,city,state);
                }
                else{
                    json_response = dumpclass.getDataList("2",name,locality,city,state);
                }
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
                    type = "2";
                    ListData data = new ListData(RegisterID,name, address, ratings,specialisation,
                            completeaddress, workinghours,phone,
                            distance, offers,about,website,email,type);
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
            mAdapter = new RecyclerAdapterSearch1(results,getActivity());
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

            type = "2";
            ListData obj = new ListData(RegisterID, name, address,ratings,specialisation,
                    completeaddress, workinghours,phone,
                    distance, offers,about,website,email,type);
            results.add(i, obj);
        }
        return results;
    }
    @Override
    public void onStop()
    {
        super.onStop();
    }
}
