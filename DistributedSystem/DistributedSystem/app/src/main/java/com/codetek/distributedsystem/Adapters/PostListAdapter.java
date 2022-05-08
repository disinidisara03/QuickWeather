package com.codetek.distributedsystem.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetek.distributedsystem.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    ArrayList<JSONObject> dataList;
    Context context;

    public PostListAdapter(Context context, ArrayList<JSONObject> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView recordImageView;
        public TextView recordTitle,recordDescription;

        public ViewHolder(View view) {
            super(view);
            recordImageView = view.findViewById(R.id.recordImageView);
            recordTitle = view.findViewById(R.id.recordTitle);
            recordDescription = view.findViewById(R.id.recordDescription);
        }
    }

    @NonNull
    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_post_record, parent, false);
        PostListAdapter.ViewHolder viewHolder = new PostListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JSONObject obj = dataList.get(position);
        try {
            holder.recordTitle.setText(obj.getString("title"));
            holder.recordDescription.setText(obj.getString("description"));
            Picasso.get().load("http://10.0.2.2:8000/assets/posts/images/"+obj.getString("img_url")).into(holder.recordImageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Drawable LoadImageFromWebOperations(String url,String sourceName) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            System.out.println(is.read());
            Drawable d = Drawable.createFromStream(is, sourceName);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
