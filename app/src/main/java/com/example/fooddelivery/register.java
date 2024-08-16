package com.example.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
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

public class register extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    Button b1;
    SharedPreferences sh;
    String url,name,place,post,pin,email,phone,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e1 = findViewById(R.id.editTextTextPersonName4);
        e2 = findViewById(R.id.editTextTextPersonName5);
        e3 = findViewById(R.id.editTextTextPersonName6);
        e4 = findViewById(R.id.editTextTextPersonName8);
        e5 = findViewById(R.id.editTextTextPersonName10);
        e6 = findViewById(R.id.editTextTextPersonName11);
        e7 = findViewById(R.id.editTextTextPersonName13);
        e8 = findViewById(R.id.editTextTextPersonName14);

        b1 = findViewById(R.id.button5);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = e1.getText().toString();
                place = e2.getText().toString();
                post = e3.getText().toString();
                pin = e4.getText().toString();
                email = e5.getText().toString();
                phone = e6.getText().toString();
                username = e7.getText().toString();
                password = e8.getText().toString();


                if (name.equalsIgnoreCase("")) {
                    e1.setError("Enter Full name");
                } else if (place.equalsIgnoreCase("")) {
                    e2.setError("Enter Your Place");
                } else if (post.equalsIgnoreCase("")) {
                    e3.setError("Enter Your Place");
                } else if (pin.equalsIgnoreCase("")) {
                    e4.setError("Enter your Pin");
                } else if (email.equalsIgnoreCase("")) {
                    e5.setError("Enter Your Email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    e5.setError("Enter Valid Email");
                    e5.requestFocus();
                } else if (phone.equalsIgnoreCase("")) {
                    e6.setError("Enter Your Phone No");
                } else if (phone.length() < 10) {
                    e6.setError("Minimum 10 nos required");
                    e6.requestFocus();
                } else if (username.equalsIgnoreCase("")) {
                    e7.setError("Enter Your username");
                } else if (password.equalsIgnoreCase("")) {
                    e8.setError("Enter Your password");
                } else {


                    RequestQueue queue = Volley.newRequestQueue(register.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/registration";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("success")) {


                                    /////////////////////////////////////
//                                String lid = json.getString("id");
//                                SharedPreferences.Editor edp = sh.edit();
//                                edp.putString("lid", lid);
//                                edp.commit();
                                    /////////////////////////////////////
                                    Intent ik = new Intent(getApplicationContext(), login.class);
                                    startActivity(ik);
                                    Toast.makeText(register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
//
//
                                } else {

                                    Toast.makeText(register.this, "not Registered", Toast.LENGTH_SHORT).show();

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
                            params.put("name", name);
                            params.put("post", post);
                            params.put("place", place);
                            params.put("pin", pin);
                            params.put("email", email);
//                        params.put("age", age);
                            params.put("phone", phone);
//                        params.put("adno", adno);
                            params.put("username", username);
                            params.put("password", password);
//                        params.put("type", type);

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(getApplicationContext(), Login1.class);
        startActivity(ii);
    }
}