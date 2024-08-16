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

public class viewitems extends AppCompatActivity {
    ListView l1;
    EditText e1;
    Button b1;
    SharedPreferences sh;
    ArrayList<String> name,image,price,stock,category,pid;
    String url,sid="",pname,url1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewitems);
        l1=findViewById(R.id.productdetails);
        e1=findViewById(R.id.editTextTextPersonName16);
        b1=findViewById(R.id.button11);

        sid=getIntent().getStringExtra("sid");
//
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pname=e1.getText().toString();




                url1 ="http://"+sh.getString("ip", "") + ":5000/viewp_search";
                RequestQueue queue = Volley.newRequestQueue(viewitems.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);
//                    Toast.makeText(viewapprovedshop.this, "err"+response, Toast.LENGTH_SHORT).show();

                            name= new ArrayList<>();
                            price= new ArrayList<>();
                            stock= new ArrayList<>();
//                            quantity=new ArrayList<>();
                            category=new ArrayList<>();
                            image=new ArrayList<>();
                            pid=new ArrayList<>();


                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                name.add(jo.getString("name"));
                                price.add(jo.getString("price"));
                                stock.add(jo.getString("stock"));
//                                quantity.add(jo.getString("quantity"));
//                                unit.add(jo.getString("unit"));
                                category.add(jo.getString("category"));
//                                shop.add(jo.getString("shop"));
                                image.add(jo.getString("image"));
                                pid.add(jo.getString("pid"));
//                                offer.add(jo.getString("offer"));
//                                np.add(jo.getString("np"));


                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

                            l1.setAdapter(new Custom_viewitem(viewitems.this,name,image,category,price,stock,pid));

//                    l1.setOnItemClickListener(viewuser.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(viewitems.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("pname",pname);
                        params.put("pid",getIntent().getStringExtra("pid"));


                        return params;
                    }
                };
                queue.add(stringRequest);



            }
        });




        url ="http://"+sh.getString("ip", "") + ":5000/viewproductdetails";
        RequestQueue queue = Volley.newRequestQueue(viewitems.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
//                    Toast.makeText(viewproductdetails.this, "welcome", Toast.LENGTH_SHORT).show();

                    name= new ArrayList<>();
                    price= new ArrayList<>();
                    stock= new ArrayList<>();
//                    quantity=new ArrayList<>();
//                    unit=new ArrayList<>();
                    category=new ArrayList<>();
//                    shop=new ArrayList<>();
                    image=new ArrayList<>();
                    pid=new ArrayList<>();
//                    offer=new ArrayList<>();
//                    np=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        name.add(jo.getString("name"));
                        price.add(jo.getString("price"));
                        stock.add(jo.getString("stock"));
//                        quantity.add(jo.getString("quantity"));
//                        unit.add(jo.getString("unit"));
                        category.add(jo.getString("category"));
//                        shop.add(jo.getString("shop"));
                        image.add(jo.getString("image"));
                        pid.add(jo.getString("pid"));
//                        offer.add(jo.getString("offer"));
//                        np.add(jo.getString("np"));


                    }

//                     ArrayAdapter<String> ad=new ArrayAdapter<>(viewproductdetails.this,android.R.layout.simple_list_item_1,name);
//                    l1.setAdapter(ad);

                    l1.setAdapter(new Custom_viewitem(viewitems.this,name,image,category,price,stock,pid));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewitems.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("pid",getIntent().getStringExtra("pid"));

                return params;
            }
        };
        queue.add(stringRequest);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(getApplicationContext(), viewrestaurant.class);
        startActivity(ii);
    }
}