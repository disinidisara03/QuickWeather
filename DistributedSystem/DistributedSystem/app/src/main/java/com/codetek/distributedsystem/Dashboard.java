package com.codetek.distributedsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetek.distributedsystem.Adapters.PostListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Dashboard extends AppCompatActivity {


    ImageView dashboard_logout_button;

    RecyclerView home_recycler_view;

    ArrayList<JSONObject> dataList;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initProcess();
    }

    private void initProcess() {
        queue= Volley.newRequestQueue(Dashboard.this);;

        dataList=new ArrayList<>();
        home_recycler_view=findViewById(R.id.home_recycler_view);

        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.getPosts ) ,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject data=new JSONObject(response);
                                JSONArray postData= (JSONArray) data.get("data");
                                ArrayList<JSONObject> arr=new ArrayList();
                                for (int x=0;x<postData.length();x++){
                                    arr.add((JSONObject) postData.get(x));
                                }

                                PostListAdapter adapter = new PostListAdapter(Dashboard.this,arr);
                                home_recycler_view.setHasFixedSize(true);
                                home_recycler_view.setLayoutManager(new LinearLayoutManager(Dashboard.this));
                                home_recycler_view.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Dashboard.this, "Something wrong", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);
        }catch(Exception ex){
            ex.printStackTrace();
            Toast.makeText(Dashboard.this, "Something wrong", Toast.LENGTH_SHORT).show();
        }


        dashboard_logout_button=findViewById(R.id.dashboard_logout_button);
        dashboard_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this, "You've successfully logged out from your account.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Dashboard.this,Login.class));
            }
        });
;

    }


}