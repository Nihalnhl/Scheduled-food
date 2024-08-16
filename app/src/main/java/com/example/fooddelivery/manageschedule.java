package com.example.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

public class manageschedule extends AppCompatActivity {

    ListView l1;
    Button b1;
    SharedPreferences sh;
    ArrayList<String> food,day,type,id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageschedule);
        l1=findViewById(R.id.schedule);
        b1=findViewById(R.id.button18);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),addtimeschedule.class);
                startActivity(i);
            }
        });


        String url1 ="http://"+sh.getString("ip", "") + ":5000/viewschedule";
        RequestQueue queue = Volley.newRequestQueue(manageschedule.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
//                                        Toast.makeText(manageschedule.this, response+"oooooooo", Toast.LENGTH_SHORT).show();

                    food= new ArrayList<>();
                    type=new ArrayList<>();

                    day= new ArrayList<>();
                    id= new ArrayList<>();




                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
//                        name.add(jo.getString("fname")+ "" +jo.getString("lname"));

                        food.add(jo.getString("food")+" - "+(jo.getString("restaurant")));

                        type.add(jo.getString("type"));
                        day.add(jo.getString("day"));
                        id.add(jo.getString("id"));

//


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new Custom_schedule(manageschedule.this,food,type,day,id));
//                    l1.setOnItemClickListener(view_notification.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(manageschedule.this, "err"+error, Toast.LENGTH_SHORT).show();
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
}