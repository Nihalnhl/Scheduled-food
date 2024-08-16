package com.example.fooddelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class viewrestaurant extends AppCompatActivity {
    ListView l1;
    EditText e1;
    Button b1;
    SharedPreferences sh;
    ArrayList<String> name,address,phone,email,image,latitude,rid,longitude;
    String url,sname,url1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewapprovedshop);
        l1=findViewById(R.id.approvedshop);
        e1=findViewById(R.id.editTextTextPersonName9);
        b1=findViewById(R.id.button10);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sname=e1.getText().toString();




                url1 ="http://"+sh.getString("ip", "") + ":5000/viewrest_search";
                RequestQueue queue = Volley.newRequestQueue(viewrestaurant.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);
//                    Toast.makeText(viewrestaurant.this, "err"+response, Toast.LENGTH_SHORT).show();

                            name= new ArrayList<>();
                            address= new ArrayList<>();
//                            post= new ArrayList<>();
//                            pin= new ArrayList<>();
                            phone= new ArrayList<>();
                            email= new ArrayList<>();
                            rid= new ArrayList<>();
                            image= new ArrayList<>();
                            latitude= new ArrayList<>();
                            longitude= new ArrayList<>();


                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                name.add(jo.getString("name"));
                                address.add(jo.getString("place")+" "+jo.getString("post")+" "+jo.getString("pin"));
//                                post.add(jo.getString("post"));
//                                pin.add(jo.getString("pin"));
                                phone.add(jo.getString("phone"));
                                email.add(jo.getString("email"));
                                rid.add(jo.getString("id"));
                                image.add(jo.getString("image"));
                                latitude.add(jo.getString("latitude"));
                                longitude.add(jo.getString("longitude"));


                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

                            l1.setAdapter(new Custom_viewrestaurant(viewrestaurant.this,name,image,address,phone,email,latitude,longitude,rid));
//                    l1.setOnItemClickListener(viewuser.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(viewrestaurant.this, "No Shops"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("shopname",sname);

                        return params;
                    }
                };
                queue.add(stringRequest);



            }
        });

        url ="http://"+sh.getString("ip", "") + ":5000/viewrestaurant";
        RequestQueue queue = Volley.newRequestQueue(viewrestaurant.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
//                    Toast.makeText(viewrestaurant.this, "err"+response, Toast.LENGTH_SHORT).show();

                    name= new ArrayList<>();
                    address= new ArrayList<>();
//                            post= new ArrayList<>();
//                            pin= new ArrayList<>();
                    phone= new ArrayList<>();
                    email= new ArrayList<>();
                    rid= new ArrayList<>();
                    image= new ArrayList<>();
                    latitude= new ArrayList<>();
                    longitude= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        name.add(jo.getString("name"));
                        address.add(jo.getString("place")+" "+jo.getString("post")+" "+jo.getString("pin"));
//                                post.add(jo.getString("post"));
//                                pin.add(jo.getString("pin"));
                        phone.add(jo.getString("phone"));
                        email.add(jo.getString("email"));
                        rid.add(jo.getString("id"));
                        image.add(jo.getString("image"));
                        latitude.add(jo.getString("latitude"));
                        longitude.add(jo.getString("longitude"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new Custom_viewrestaurant(viewrestaurant.this,name,image,address,phone,email,latitude,longitude,rid));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewrestaurant.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("latitude",LocationService.lati);
                params.put("longitude",LocationService.logi);


                return params;
            }
        };
        queue.add(stringRequest);


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(getApplicationContext(), userhome.class);
        startActivity(ii);
    }
}