package com.example.fooddelivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewworkassigned extends AppCompatActivity  {
    ListView l1;
    SharedPreferences sh;
    ArrayList<String> user, phone, date, status, oid,latitude,longitude;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewworkassigned);
        l1 = findViewById(R.id.assignedwork);

//        b3=findViewById(R.id.button25);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        url = "http://" + sh.getString("ip", "") + ":5000/viewworkassigned";
        RequestQueue queue = Volley.newRequestQueue(viewworkassigned.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);

                    user = new ArrayList<>();
                    phone = new ArrayList<>();
                    date = new ArrayList<>();
                    status = new ArrayList<>();
                    oid = new ArrayList<>();
                    latitude = new ArrayList<>();
                    longitude = new ArrayList<>();



//
//
//                    photo=new ArrayList<>();

                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        user.add(jo.getString("user"));
                        phone.add(jo.getString("phone"));
                        oid.add(jo.getString("oid"));

                        date.add(jo.getString("date"));
                        status.add(jo.getString("status"));
                        latitude.add(jo.getString("latitude"));
                        longitude.add(jo.getString("longitude"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new Custom_viewworkassigned(viewworkassigned.this, user, phone, date, status, oid,latitude,longitude));
//                    l1.setOnItemClickListener(viewworkassigned.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewworkassigned.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };
        queue.add(stringRequest);




}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(getApplicationContext(), agenthome.class);
        startActivity(ii);
    }
}