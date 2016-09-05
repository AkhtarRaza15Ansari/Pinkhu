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

import com.qualixam.modal.ChatListData;
import com.qualixam.modal.ListData;
import com.qualixam.pinkhu.ChatPage;
import com.qualixam.pinkhu.DetailsPage;
import com.qualixam.pinkhu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView
        .Adapter<ChatAdapter
        .DataObjectHolder> {
    public static String LOG_TAG = "MyRecyclerViewAdapter";
    static public ArrayList<ChatListData> mDataset;
    static Context context;
    public static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView name;
        public DataObjectHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                Intent detailIntent = new Intent(context, ChatPage.class);
                int position = getAdapterPosition();
                detailIntent.putExtra("registerid",mDataset.get(position).getRegisterid());
                detailIntent.putExtra("name", mDataset.get(position).getName());
                context.startActivity(detailIntent);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public ChatAdapter(ArrayList<ChatListData> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.name.setText("Name:  "+ mDataset.get(position).getName()+"\nPhone: "+mDataset.get(position).getNumber()+"");
    }
    public void addItem(ChatListData dataObj, int index) {
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
}
