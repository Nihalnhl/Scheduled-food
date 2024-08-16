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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class statusupdationofdelivery extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1;
    Button b1;
    SharedPreferences sh;
    String url,status="";
    String array[]={"On the way","Delivered","Will come"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusupdationofdelivery);
        s1=findViewById(R.id.spinner2);
        b1=findViewById(R.id.button24);



        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

         ArrayAdapter<String> ad=new ArrayAdapter<>(statusupdationofdelivery.this,android.R.layout.simple_list_item_1,array);
        s1.setAdapter(ad);
        s1.setOnItemSelectedListener(statusupdationofdelivery.this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



//                Intent i=new Intent(getApplicationContext(),home.class);
//                startActivity(i);

                RequestQueue queue = Volley.newRequestQueue(statusupdationofdelivery.this);
                url = "http://" + sh.getString("ip","") + ":5000/statusupdationofdelivery";
//                Toast.makeText(getApplicationContext(), "Error" + re, Toast.LENGTH_LONG).show();

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");

                            if (res.equalsIgnoreCase("valid")) {


//                                /////////////////////////////////////
//                                String lid = json.getString("id");
//                                SharedPreferences.Editor edp = sh.edit();
//                                edp.putString("lid", lid);
//                                edp.commit();
//                                /////////////////////////////////////
                                Intent ik = new Intent(getApplicationContext(), viewworkassigned.class);
                                startActivity(ik);
                                Toast.makeText(statusupdationofdelivery.this, "Status Updated!!", Toast.LENGTH_SHORT).show();


                            } else {

                                Toast.makeText(statusupdationofdelivery.this, "Not Update!!", Toast.LENGTH_SHORT).show();

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
                        params.put("lid", sh.getString("lid",""));
                        params.put("oid",getIntent().getStringExtra("oid"));
                        params.put("status", status);


                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        status=array[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(getApplicationContext(), agenthome.class);
        startActivity(ii);
    }
}