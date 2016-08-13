package com.qualixam.pinkhu;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qualixam.adapter.RecyclerAdapterSearch1;
import com.qualixam.adapter.RecyclerBookmarkAdapter;
import com.qualixam.constant.dumpclass;
import com.qualixam.modal.BookmarkData;
import com.qualixam.modal.ListData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookmarkPage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter;
    String json_response;
    JSONArray array;
    String name = "";
    ArrayList results;
    private RecyclerView.LayoutManager mLayoutManager1;
    private List<BookmarkData> data;
    String state="", city="", locality="";
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;
    String user_id;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_page);

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

        prefs = getSharedPreferences("Pinkhu", MODE_PRIVATE);
        user_id = prefs.getString("id","");

        mRecyclerView1 = (RecyclerView) findViewById(R.id.my_recycler_view1);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager1 = new GridLayoutManager(this, 1);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        new FetchBookmark().execute();
                                    }
                                }
        );
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new FetchBookmark().execute();
    }


    public class FetchBookmark extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                results = new ArrayList<BookmarkData>();
                json_response = dumpclass.getBookmark(user_id);
                array = new JSONArray(json_response);
                for(int i=0;i<array.length();i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    String bookmark_id      = object.getString("bookmark_id");
                    String register_id      = object.getString("register_id");
                    String user_id          = object.getString("user_id");
                    String register_name    = object.getString("register_name");
                    BookmarkData data       = new BookmarkData(bookmark_id,register_id,user_id,register_name);
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
            mAdapter = new RecyclerBookmarkAdapter(results,BookmarkPage.this);
            mRecyclerView1.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

}
