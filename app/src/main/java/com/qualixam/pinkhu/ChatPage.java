package com.qualixam.pinkhu;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qualixam.constant.dumpclass;
import com.qualixam.modal.ChatListData;
import com.qualixam.modal.MessageData;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class ChatPage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    String json_response;
    JSONArray array;
    ArrayList results;
    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager1;
    SwipeRefreshLayout swipeRefreshLayout;
    static String owner_id = "";
    static String other_id="";
    static String owner_name="";
    static String other_name="";
    Intent intent;
    String message = "";
    EditText etMessage;
    Button btnSend;
    SharedPreferences prefs;
    int REQUEST_CAMERA = 0, FILE_SELECT_CODE = 1;
    String path = "",selectedImagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        prefs = getSharedPreferences("Pinkhu", Context.MODE_PRIVATE);
        intent = getIntent();

        other_id = intent.getStringExtra("registerid");
        other_name = intent.getStringExtra("name");

        owner_id = prefs.getString("id","");
        owner_name = prefs.getString("Name","");

        Log.d("owner_name: "+owner_name ,"other_name: "+other_name);
        setTitle("" + other_name);
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

        btnSend = (Button) findViewById(R.id.send);
        etMessage = (EditText) findViewById(R.id.message);

        mRecyclerView1 = (RecyclerView) findViewById(R.id.my_recycler_view1);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager1 = new GridLayoutManager(ChatPage.this, 1);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        new AllMessages().execute();
                                    }
                                }
        );
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = etMessage.getText().toString();
                new SendMessage().execute();
                dumpclass.hideKeyboard(ChatPage.this);
                etMessage.setText("");
            }
        });
    }
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new AllMessages().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh) {
            onRefresh();
            return true;
        }
        if(id==R.id.attach)
        {
            selectImage();
        }
        return super.onOptionsItemSelected(item);
    }

    public class AllMessages extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            results = new ArrayList<ChatListData>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                json_response = dumpclass.getAllMessages(owner_id,other_id);
                array = new JSONArray(json_response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String message_from = object.getString("message_from");
                    String message_to = object.getString("message_to");
                    String message = object.getString("message");
                    String date_time = object.getString("date_time");
                    String type = object.getString("type");
                    MessageData data = new MessageData(message_from,message_to,message,type,date_time);
                    results.add(i, data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swipeRefreshLayout.setRefreshing(false);
            mAdapter = new MessageAdapter(results,ChatPage.this);
            mRecyclerView1.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }
    public class SendMessage extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                json_response = dumpclass.insertChatMessage(owner_id,other_id,message,"text");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onRefresh();
        }
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static class MessageAdapter extends RecyclerView
            .Adapter<MessageAdapter
            .DataObjectHolder> {
        public String LOG_TAG = "MyRecyclerViewAdapter";
        public ArrayList<MessageData> mDataset;
        Context context;
        public MyClickListener myClickListener;
        ViewGroup viewGroup;
        public class DataObjectHolder extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {
            TextView name,name_other,name_other_image,name_image;
            TextView message,message_other;
            ImageView message_own_image,message_other_image;
            LinearLayout own,other,other_image,own_image;
            CardView card;
            public DataObjectHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                message = (TextView) itemView.findViewById(R.id.message);
                name_other = (TextView) itemView.findViewById(R.id.name_other);
                message_other = (TextView) itemView.findViewById(R.id.message_other);
                own = (LinearLayout) itemView.findViewById(R.id.own);
                other = (LinearLayout) itemView.findViewById(R.id.other);

                name_other_image = (TextView) itemView.findViewById(R.id.name_other_image);
                message_own_image = (ImageView) itemView.findViewById(R.id.message_own_image);
                name_image = (TextView) itemView.findViewById(R.id.name_image);
                message_other_image = (ImageView) itemView.findViewById(R.id.message_other_image);
                own_image = (LinearLayout) itemView.findViewById(R.id.own_image);
                other_image = (LinearLayout) itemView.findViewById(R.id.other_image);
                Log.i(LOG_TAG, "Adding Listener");
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                /*Intent detailIntent = new Intent(context, DetailsPage.class);
                int position = getAdapterPosition();
                detailIntent.putExtra("registerid",mDataset.get(position).getRegisterid());
                detailIntent.putExtra("name", mDataset.get(position).getName());
                context.startActivity(detailIntent);*/
            }
        }

        public void setOnItemClickListener(MyClickListener myClickListener) {
            this.myClickListener = myClickListener;
        }

        public MessageAdapter(ArrayList<MessageData> myDataset, Context context) {
            mDataset = myDataset;
            this.context = context;
        }

        @Override
        public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item, parent, false);
            DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
            return dataObjectHolder;
        }

        @Override
        public void onBindViewHolder(DataObjectHolder holder, final int position) {
            if(mDataset.get(position).getType().equals("image"))
            {
                if(mDataset.get(position).getMessage_from().equals(owner_id))
                {
                    holder.name.setVisibility(View.GONE);
                    holder.message.setVisibility(View.GONE);
                    holder.name_other.setVisibility(View.GONE);
                    holder.message_other.setVisibility(View.GONE);

                    holder.name_image.setVisibility(View.VISIBLE);
                    holder.message_own_image.setVisibility(View.VISIBLE);
                    holder.name_other_image.setVisibility(View.GONE);
                    holder.message_other_image.setVisibility(View.GONE);

                    holder.other.setVisibility(View.GONE);
                    holder.own.setVisibility(View.GONE);

                    holder.other_image.setVisibility(View.GONE);
                    holder.own_image.setVisibility(View.VISIBLE);

                    holder.name_image.setText(""+ owner_name);
                    String url = mDataset.get(position).getMessage();
                    url = url.replace(" ","%20");
                    Picasso.with(context).load(url).into(holder.message_own_image);

                }
                else{
                    holder.name.setVisibility(View.GONE);
                    holder.message.setVisibility(View.GONE);
                    holder.name_other.setVisibility(View.GONE);
                    holder.message_other.setVisibility(View.GONE);

                    holder.name_image.setVisibility(View.GONE);
                    holder.message_own_image.setVisibility(View.GONE);
                    holder.name_other_image.setVisibility(View.VISIBLE);
                    holder.message_other_image.setVisibility(View.VISIBLE);

                    holder.other.setVisibility(View.GONE);
                    holder.own.setVisibility(View.GONE);

                    holder.other_image.setVisibility(View.VISIBLE);
                    holder.own_image.setVisibility(View.GONE);

                    holder.name_other_image.setText(""+ other_name);
                    String url = mDataset.get(position).getMessage();
                    url = url.replace(" ","%20");
                    Picasso.with(context).load(url).into(holder.message_other_image);
                    //loadimage
                }
            }
            else{
                if(mDataset.get(position).getMessage_from().equals(owner_id))
                {
                    holder.name.setVisibility(View.VISIBLE);
                    holder.message.setVisibility(View.VISIBLE);
                    holder.name_other.setVisibility(View.GONE);
                    holder.message_other.setVisibility(View.GONE);

                    holder.name_image.setVisibility(View.GONE);
                    holder.message_own_image.setVisibility(View.GONE);
                    holder.name_other_image.setVisibility(View.GONE);
                    holder.message_other_image.setVisibility(View.GONE);

                    holder.other.setVisibility(View.GONE);
                    holder.own.setVisibility(View.VISIBLE);

                    holder.other_image.setVisibility(View.GONE);
                    holder.own_image.setVisibility(View.GONE);

                    holder.name.setText(""+ owner_name);
                    holder.message.setText(""+ mDataset.get(position).getMessage());

                }
                else{
                    holder.name.setVisibility(View.GONE);
                    holder.message.setVisibility(View.GONE);
                    holder.name_other.setVisibility(View.VISIBLE);
                    holder.message_other.setVisibility(View.VISIBLE);

                    holder.name_image.setVisibility(View.GONE);
                    holder.message_own_image.setVisibility(View.GONE);
                    holder.name_other_image.setVisibility(View.GONE);
                    holder.message_other_image.setVisibility(View.GONE);

                    holder.other.setVisibility(View.VISIBLE);
                    holder.own.setVisibility(View.GONE);

                    holder.other_image.setVisibility(View.GONE);
                    holder.own_image.setVisibility(View.GONE);

                    holder.name_other.setText(""+ other_name);
                    holder.message_other.setText(""+ mDataset.get(position).getMessage());
                }
            }

        }
        public void addItem(MessageData dataObj, int index) {
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
    private String imgPath;
    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ChatPage.this);
        builder.setTitle("Add File");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                    try {
                        startActivityForResult(
                                Intent.createChooser(intent, "Select a File to Upload"),
                                FILE_SELECT_CODE);
                    } catch (android.content.ActivityNotFoundException ex) {
                        // Potentially direct the user to the Market with a Dialog
                        Toast.makeText(ChatPage.this, "Please install a File Manager.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FILE_SELECT_CODE)
            {
                Uri uri = data.getData();
                Log.d("TAG", "File Uri: " + uri.toString());
                // Get the path

                path = getPath(this, uri);
                Log.d("TAG", "File Path: " + path);
                String filename = path.substring(path.lastIndexOf("/") + 1);
                new PostNotification().execute();
            }
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    public String getImagePath() {
        return imgPath;
    }
    private void onCaptureImageResult(Intent data) {
        selectedImagePath = getImagePath();
        File destination = new File(selectedImagePath);
        path = destination.getAbsolutePath();

        new PostNotification().execute();
    }
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public class PostNotification extends AsyncTask<String, String, String> {

        protected void onPreExecute() {

            dumpclass.startprogress("Please Wait", "Uploading", ChatPage.this, false);
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                json_response =dumpclass.doFileUpload(owner_id,other_id,path,"image");
            } catch (NullPointerException e) {
                dumpclass.dismissprogress();
                e.printStackTrace();
            } catch (Exception e) {
                dumpclass.dismissprogress();
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String lenghtOfFile) {
            try{
                dumpclass.dismissprogress();
                onRefresh();
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

}
