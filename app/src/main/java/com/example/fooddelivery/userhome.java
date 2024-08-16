package com.example.fooddelivery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class userhome extends AppCompatActivity {
    ImageView b1,b6,b7,b8,b9,b10,b11,b12;
    SharedPreferences sh;


    String url,pname,url1,oid,amt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        b1=findViewById(R.id.button2);
//        b2=findViewById(R.id.button9);
//        b3=findViewById(R.id.button15);
//        b4=findViewById(R.id.button16);
//        b5=findViewById(R.id.button17);
        b6=findViewById(R.id.button3);
        b7=findViewById(R.id.button4);
        b8=findViewById(R.id.button5);
        b9=findViewById(R.id.button6);
        b10=findViewById(R.id.timeschedule);
        b11=findViewById(R.id.button7);
        b12=findViewById(R.id.status);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),viewrestaurant.class);
                startActivity(i);

            }
        });
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(getApplicationContext(),nearestlocaion.class);
//                startActivity(i);
//
//
//            }
//        });

//        b5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(getApplicationContext(),addproduct_cart.class);
//                startActivity(i);
//
//            }
//        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url1 = "http://" + sh.getString("ip", "") + ":5000/viewaddtocart";
                RequestQueue queue = Volley.newRequestQueue(userhome.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {

                            JSONObject ar1 = new JSONObject(response);
                            String tp = ar1.getString("total");
                            SharedPreferences.Editor edp = sh.edit();
                            edp.putString("tott", tp);
                            edp.commit();
//                            Toast.makeText(userhome.this, "==================" + tp, Toast.LENGTH_SHORT).show();

                            if (tp.equalsIgnoreCase("0")) {
                                AlertDialog.Builder ald=new AlertDialog.Builder(userhome.this);
                                ald.setTitle("Alert!!!")
                                        .setPositiveButton("NO ITEMS IN THE CART.....", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {

                                            }
                                        });

                                AlertDialog al=ald.create();
                                al.show();

                            }
                            else
                            {
                                Intent i=new Intent(getApplicationContext(),viewmycart.class);
                                startActivity(i);
                            }


                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);


//                    l1.setOnItemClickListener(viewuser.this);

                        } catch(Exception e){
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Toast.makeText(userhome.this, "err" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("lid", sh.getString("lid",""));
//                        params.put("sid",sid);


                        return params;
                    }
                };
                queue.add(stringRequest);






            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),vieworderstatus.class);
                startActivity(i);


            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),shoprating.class);
                startActivity(i);

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),sendcomplaint.class);
                startActivity(i);

            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),manageschedule.class);
                startActivity(i);

            }
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Login1.class);
                startActivity(i);

            }
        });
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),viewschedulestatus.class);
                startActivity(i);

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(getApplicationContext(), userhome.class);
        startActivity(ii);
    }
}