package com.qualixam.adapter;

/**
 * Created by Akhtar on 09/02/16.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qualixam.modal.DataObject;
import com.qualixam.pinkhu.BrowserActivity;
import com.qualixam.pinkhu.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Random;

public class SecondRecyclerAdapter extends RecyclerView
        .Adapter<SecondRecyclerAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static ArrayList<DataObject> mDataset;
    private static Context context;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text1;
        ImageView image1;
        public DataObjectHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.text1);
            image1 =  (ImageView) itemView.findViewById(R.id.image1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("This","Here"+getAdapterPosition());
            Intent i = new Intent(context,BrowserActivity.class);
            i.putExtra("url",mDataset.get(getAdapterPosition()).getMimage());
            context.startActivity(i);
        }
    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public SecondRecyclerAdapter(ArrayList<DataObject> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_second, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        holder.text1.setText("Akhtar");
        Log.d("image","http://demo.digitaledgetech.in/admin/Slider/"+mDataset.get(position).getmText1());
        Picasso.with(context).load("http://demo.digitaledgetech.in/admin/Slider/"+mDataset.get(position).getmText1()).resize(150,75).centerCrop().into(holder.image1);
    }

    public void addItem(DataObject dataObj, int index) {
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
    public int getImage()
    {
        Random rand = new Random();
        int rndInt = rand.nextInt(5) + 1; // n = the number of images, that start at idx 1
        String imgName = "image" + rndInt;
        int id = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
        return id;
    }
    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
