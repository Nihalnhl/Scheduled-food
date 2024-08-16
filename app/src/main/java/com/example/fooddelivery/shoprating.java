package com.example.fooddelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class shoprating extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RatingBar e1;
    Spinner s1;
    Button b1;
    EditText e11;
    SharedPreferences sh;
    String url, rating,reviews, sid,url1;
    ArrayList<String> id, shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoprating);
        e1 = findViewById(R.id.ratingBar);
        e11 = findViewById(R.id.editTextTextPersonName12);
        s1 = findViewById(R.id.spinner);
        b1 = findViewById(R.id.button6);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        url = "http://" + sh.getString("ip", "") + ":5000/viewshopsendrating";
        RequestQueue queue = Volley.newRequestQueue(shoprating.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);
                    id = new ArrayList<>();
                    shop = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        id.add(jo.getString("id"));
                        shop.add(jo.getString("restaurant"));


                    }

                    ArrayAdapter<String> ad = new ArrayAdapter<>(shoprating.this, android.R.layout.simple_list_item_1, shop);
                    s1.setAdapter(ad);

                    s1.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) shoprating.this);


                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(shoprating.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(stringRequest);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rating = String.valueOf(e1.getRating());
                reviews=e11.getText().toString();

                 if (reviews.equalsIgnoreCase("")) {
                    e11.setError("enter your Reviews");
                }else if (rating.equalsIgnoreCase("0.0")) {
                    Toast.makeText(shoprating.this, "Please enter the rating", Toast.LENGTH_SHORT).show();
                } else {

//                startActivity(i);

                    RequestQueue queue = Volley.newRequestQueue(shoprating.this);
                    url1 = "http://" + sh.getString("ip", "") + ":5000/ratings";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("valid")) {


                                    Intent ik = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(ik);
                                    Toast.makeText(shoprating.this, "Rating Send!!", Toast.LENGTH_SHORT).show();


                                } else {

                                    Toast.makeText(shoprating.this, "Not Send!!", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("lid", sh.getString("lid", ""));
                            params.put("rating1", rating);
                            params.put("review", reviews);
                            params.put("sid", sid);



                            return params;
                        }

                    };
                    queue.add(stringRequest);


                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sid = id.get(i);
        Toast.makeText(getApplicationContext(), "iddddddddddd" + sid, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(getApplicationContext(), userhome.class);
        startActivity(ii);
    }
}