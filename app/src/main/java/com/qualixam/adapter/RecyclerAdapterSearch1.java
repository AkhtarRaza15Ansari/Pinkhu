package com.qualixam.adapter;

/**
 * Created by akhtarraza on 11/02/16.
 */
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qualixam.modal.ListData;
import com.qualixam.pinkhu.DetailsPage;
import com.qualixam.pinkhu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapterSearch1 extends RecyclerView
        .Adapter<RecyclerAdapterSearch1
        .DataObjectHolder> {
    public static String LOG_TAG = "MyRecyclerViewAdapter";
    static public ArrayList<ListData> mDataset;
    static Context context;
    public static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        LinearLayout lldistance;
        ImageView imageView;
        ImageView imageCall;
        ImageView imageShare;
        TextView name;
        TextView address;
        TextView specialisation;
        TextView completeAddress;
        TextView workinghours;
        TextView distance;
        TextView offers;
        TextView text_ratings;

        public DataObjectHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            specialisation = (TextView) itemView.findViewById(R.id.specialisation);
            completeAddress = (TextView) itemView.findViewById(R.id.completeAddress);
            workinghours = (TextView) itemView.findViewById(R.id.workinghours);
            distance = (TextView) itemView.findViewById(R.id.distance);
            offers = (TextView) itemView.findViewById(R.id.offers);
            text_ratings = (TextView) itemView.findViewById(R.id.text_ratings);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageCall = (ImageView) itemView.findViewById(R.id.call);
            imageShare = (ImageView) itemView.findViewById(R.id.share);

            lldistance = (LinearLayout) itemView.findViewById(R.id.lldistance);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                Intent detailIntent = new Intent(context, DetailsPage.class);
                int position = getAdapterPosition();
                detailIntent.putExtra("registerid",mDataset.get(position).getRegisterid());
                detailIntent.putExtra("name", mDataset.get(position).getName());
                detailIntent.putExtra("address",mDataset.get(position).getAddress());
                detailIntent.putExtra("specialisation",mDataset.get(position).getSpecialisation());
                detailIntent.putExtra("completeAddress",mDataset.get(position).getCompleteAddress());
                detailIntent.putExtra("workinghours",mDataset.get(position).getWorkingHours());
                detailIntent.putExtra("distance",mDataset.get(position).getName());
                detailIntent.putExtra("offers",mDataset.get(position).getOffers());
                detailIntent.putExtra("about",mDataset.get(position).getAbout());
                detailIntent.putExtra("ratings", mDataset.get(position).getRatings());
                detailIntent.putExtra("email",mDataset.get(position).getEmail());
                detailIntent.putExtra("mobile",mDataset.get(position).getPhone());
                detailIntent.putExtra("website", mDataset.get(position).getWebsite());
                context.startActivity(detailIntent);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RecyclerAdapterSearch1(ArrayList<ListData> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_search1, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.name.setText(""+ mDataset.get(position).getName());
        holder.address.setText(""+ mDataset.get(position).getAddress());
        holder.specialisation.setText(""+ mDataset.get(position).getSpecialisation());
        holder.completeAddress.setText(""+ mDataset.get(position).getCompleteAddress());
        holder.workinghours.setText(""+ mDataset.get(position).getWorkingHours());
        holder.distance.setText(""+ mDataset.get(position).getDistance() +" Kms");
        holder.offers.setText(""+ mDataset.get(position).getOffers());
        holder.text_ratings.setText(""+ mDataset.get(position).getRatings());

        Picasso.with(context).load(getImage(mDataset.get(position).getType())).into(holder.imageView);

        holder.imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mDataset.get(position).getPhone()));
                context.startActivity(intent);
            }
        });

        holder.imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mDataset.get(position).getName() + " @ " +
                        mDataset.get(position).getCompleteAddress() + " Specialises in " +
                        mDataset.get(position).getSpecialisation();
                callShareIntent(text);
            }
        });

        holder.lldistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maps = "http://maps.google.co.in/maps?q=" + mDataset.get(position).getCompleteAddress();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(maps));
                context.startActivity(intent);
            }
        });
    }
    private void callShareIntent(String text){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // Launch sharing dialog for image
        context.startActivity(Intent.createChooser(shareIntent, "Share Via"));
    }
    public void addItem(ListData dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
    public int getImage(String type)
    {
        int drawable = 0;
        switch (type){
            case "1":
                drawable = R.drawable.ic_store_mall_directory_white_24dp;
                break;
            case "2":
                drawable = R.drawable.ic_local_hospital_white_24dp;
                break;
            case "3":
                drawable = R.drawable.ic_local_drink_white_24dp;
                break;
            case "4":
                drawable = R.drawable.ic_local_pharmacy_white_24dp;
                break;
            case "5":
                drawable = R.drawable.ic_local_shipping_white_24dp;
                break;
            case "6":
                drawable = R.drawable.ic_local_hospital_white_24dp;
                break;
            case "7":
                drawable = R.drawable.ic_account_circle_white_24dp;
                break;
        }
        /*
    * 1     NGO
    * 2     Hospital
    * 3     MedBanks
    * 4     Shop
    * 5     Rentals
    * 6     Clinics
    * 7     Doctors
    * */
        return drawable;
    }
}
