package com.example.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class login extends AppCompatActivity {
    EditText e1,e2;
    Button b1,b2;
    SharedPreferences sh;
    String url,uname,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=findViewById(R.id.editTextTextPersonName2);
        e2=findViewById(R.id.editTextTextPersonName3);
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button3);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),register.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = e1.getText().toString();
                password = e2.getText().toString();

                if (uname.equalsIgnoreCase("")) {
                    e1.setError("Enter Username");
                } else if (password.equalsIgnoreCase("")) {
                    e2.setError("Enter Password");
                } else {


//                Intent i=new Intent(getApplic
//
//                ationContext(),userhome.class);
//                startActivity(i);


                    RequestQueue queue = Volley.newRequestQueue(login.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/logincode";

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


                                    /////////////////////////////////////
                                    String lid = json.getString("id");
                                    String type = json.getString("type");
                                    SharedPreferences.Editor edp = sh.edit();
                                    edp.putString("lid", lid);
                                    edp.commit();
                                    if (type.equalsIgnoreCase("deliveryboy")) {
                                        /////////////////////////////////////
                                        Intent ik = new Intent(getApplicationContext(), agenthome.class);
                                        startActivity(ik);
                                        Toast.makeText(login.this, "welcome", Toast.LENGTH_SHORT).show();
                                    } else if (type.equalsIgnoreCase("user")) {
                                        Intent ik = new Intent(getApplicationContext(), userhome.class);
                                        startActivity(ik);
                                        Toast.makeText(login.this, "welcome", Toast.LENGTH_SHORT).show();


                                    } else {
                                        Toast.makeText(login.this, "Invalid Username or password", Toast.LENGTH_SHORT).show();

                                    }

                                } else {

                                    Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

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
                            params.put("uname", uname);
                            params.put("pswd", password);

                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }
            }
        });

    }
}