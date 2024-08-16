package com.example.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Spinner;
import android.widget.Toast;

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

public class addtimeschedule extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner s1,s2,s3;
    Button b1;
    SharedPreferences sh;
    String url, food,type, day,url1;
    ArrayList<String> fid,foods;
    String array[]={"Breakfast","launch","Dinner"};
    String array1[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtimeschedule);
        s1=findViewById(R.id.spinner3);
        s2=findViewById(R.id.spinner4);
        b1=findViewById(R.id.button17);
        s3=findViewById(R.id.spinner5);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ArrayAdapter<String> ad=new ArrayAdapter<>(addtimeschedule.this,android.R.layout.simple_list_item_1,array);
        s2.setAdapter(ad);
//        s2.setOnItemSelectedListener(addtimeschedule.this);

        ArrayAdapter<String> ad2=new ArrayAdapter<>(addtimeschedule.this,android.R.layout.simple_list_item_1,array1);
        s3.setAdapter(ad2);
//        s3.setOnItemSelectedListener(addtimeschedule.this);




        url = "http://" + sh.getString("ip", "") + ":5000/viewitems";
        RequestQueue queue = Volley.newRequestQueue(addtimeschedule.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);
                    fid = new ArrayList<>();
                    foods = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        fid.add(jo.getString("id"));
                        foods.add(jo.getString("name")+"-"+(jo.getString("res")));


                    }

                    ArrayAdapter<String> ad = new ArrayAdapter<>(addtimeschedule.this, android.R.layout.simple_list_item_1, foods);
                    s1.setAdapter(ad);

                    s1.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) addtimeschedule.this);


                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(addtimeschedule.this, "err" + error, Toast.LENGTH_SHORT).show();
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



//                startActivity(i);

                    RequestQueue queue = Volley.newRequestQueue(addtimeschedule.this);
                    url1 = "http://" + sh.getString("ip", "") + ":5000/addschedule";

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


                                    Intent ik = new Intent(getApplicationContext(), manageschedule.class);
                                    startActivity(ik);
                                    Toast.makeText(addtimeschedule.this, "Schedule Added!!", Toast.LENGTH_SHORT).show();


                                } else {

                                    Toast.makeText(addtimeschedule.this, "Not Add!!", Toast.LENGTH_SHORT).show();

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
                            params.put("fid", food);
                            params.put("type",s2.getSelectedItem().toString());
                            params.put("day",s3.getSelectedItem().toString());



                            return params;
                        }

                    };
                    queue.add(stringRequest);



            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        food=fid.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}