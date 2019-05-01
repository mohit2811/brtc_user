package com.example.laptophp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class forgot extends AppCompatActivity {

    EditText mobile ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

    mobile = (EditText) findViewById(R.id.fgt);

}

    public void verify(View view) {

        if(!Patterns.EMAIL_ADDRESS.matcher(mobile.getText().toString()).matches())
        {
            Toast.makeText(forgot.this , "enter valid email",Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(forgot.this , "Hum kya kren pehle yaad rkhna tha password!!!!!!!",Toast.LENGTH_SHORT).show();

    }


}
