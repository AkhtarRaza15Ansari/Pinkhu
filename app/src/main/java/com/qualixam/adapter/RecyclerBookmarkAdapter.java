package com.qualixam.adapter;

/**
 * Created by akhtarraza on 11/02/16.
 */
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qualixam.constant.dumpclass;
import com.qualixam.modal.BookmarkData;
import com.qualixam.modal.ListData;
import com.qualixam.pinkhu.DetailsPage;
import com.qualixam.pinkhu.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerBookmarkAdapter extends RecyclerView
        .Adapter<RecyclerBookmarkAdapter
        .DataObjectHolder> {
    public static String LOG_TAG = "MyRecyclerViewAdapter";
    static public ArrayList<BookmarkData> mDataset;
    static Context context;
    public static MyClickListener myClickListener;
    static String state="", city="", locality="";
    static ArrayList results;
    static String json_response;
    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView register_name;

        public DataObjectHolder(View itemView) {
            super(itemView);

            register_name = (TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

                int position = getAdapterPosition();
                new BookmarkExtends(mDataset.get(position).getRegister_id()).execute();

        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RecyclerBookmarkAdapter(ArrayList<BookmarkData> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_bookmark, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.register_name.setText(""+ mDataset.get(position).getRegister_name());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class BookmarkExtends extends AsyncTask<Void,Void,Void>
    {
        String register_id;
        String RegisterID,name,address,ratings,specialisation,completeaddress,workinghours,phone,distance
                ,offers,website,email;
        public BookmarkExtends(String register_id)
        {
            this.register_id = register_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                locality = locality.replace(" ","%20");
                city = city.replace(" ","%20");
                state = state.replace(" ","%20");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try {
                results = new ArrayList<ListData>();
                json_response = dumpclass.getStoreById(register_id,locality,city,state);
                JSONArray object = new JSONArray(json_response);
                JSONObject jsonObject = object.getJSONObject(0);

                    RegisterID = jsonObject.getString("RegisterID");
                    name = jsonObject.getString("Name");
                    address = jsonObject.getString("Address");
                    ratings = jsonObject.getString("ratings");
                    specialisation = jsonObject.getString("Specialisation");
                    completeaddress = jsonObject.getString("completeAddress");
                    workinghours = jsonObject.getString("workinghours");
                    phone = jsonObject.getString("ContactNo");
                    distance = jsonObject.getString("distance");
                    offers = jsonObject.getString("offers");
                    website = jsonObject.getString("Website");
                    email = jsonObject.getString("Email");


            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent detailIntent = new Intent(context, DetailsPage.class);
            detailIntent.putExtra("registerid",RegisterID);
            detailIntent.putExtra("name", name);
            detailIntent.putExtra("address",address);
            detailIntent.putExtra("specialisation",specialisation);
            detailIntent.putExtra("completeAddress",completeaddress);
            detailIntent.putExtra("workinghours",workinghours);
            detailIntent.putExtra("distance",distance);
            detailIntent.putExtra("offers",offers);
            detailIntent.putExtra("ratings", ratings);
            detailIntent.putExtra("email",email);
            detailIntent.putExtra("mobile",phone);
            detailIntent.putExtra("website", website);
            context.startActivity(detailIntent);
        }
    }
}
