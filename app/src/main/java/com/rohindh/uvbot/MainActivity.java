package com.rohindh.uvbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private ImageView statusBtn,upBtn,downBtn,leftBtn,rightBtn;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private boolean status = false;
    private String url = "http://192.168.43.223:6538";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusBtn = findViewById(R.id.connection_statBtn);
        upBtn = findViewById(R.id.upBtn);
        downBtn = findViewById(R.id.downBtn);
        rightBtn = findViewById(R.id.rightBtn);
        leftBtn = findViewById(R.id.leftBtn);


        //checks the status of the raspberry pi
        final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                //call function
                checkstatus();
                ha.postDelayed(this, 7000);
            }
        }, 7000);

        upBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);




    }
    private void sendDirections(String direction){
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url+"/"+direction, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Response : sent" , Toast.LENGTH_LONG).show();//display the response on screen
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                statusBtn.setImageDrawable(getDrawable(R.drawable.redbtn));
                Log.i(TAG,"Error :" + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    private void checkstatus() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                statusBtn.setImageDrawable(getDrawable(R.drawable.greenbtn));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                statusBtn.setImageDrawable(getDrawable(R.drawable.redbtn));
                Log.i(TAG,"Error :" + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upBtn:
                sendDirections("up");
                break;
            case R.id.downBtn:
                sendDirections("down");
                break;
            case R.id.leftBtn:
                sendDirections("left");
                break;
            case R.id.rightBtn:
                sendDirections("right");
                break;
        }
    }
}




