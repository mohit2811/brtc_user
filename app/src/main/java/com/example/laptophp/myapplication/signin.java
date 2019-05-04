package com.example.laptophp.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class signin extends AppCompatActivity {


    EditText username_et, password_et;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        username_et = (EditText) findViewById(R.id.editText1);
        password_et = (EditText) findViewById(R.id.editText2);
        login = (Button) findViewById(R.id.button);
    }

    public void cacc(View view) {
        Intent i = new Intent(signin.this, signup.class);
        startActivity(i);


    }

    public void login(View view) {

        String username = username_et.getText().toString();

        String password = password_et.getText().toString();

        if (username.equals("")) {
            Toast.makeText(signin.this, "please enter your usename", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("")) {
            Toast.makeText(signin.this, "please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }


        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(username , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {

                    Intent i = new Intent(signin.this,worki.class);
                    startActivity(i);


                }
                else {

                    Toast.makeText(signin.this , "invalid credentials" , Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void fgt(View view) {
        Intent i= new Intent(signin.this,forgot.class);
        startActivity(i);
    }
}
