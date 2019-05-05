package com.example.laptophp.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseAuth auth=FirebaseAuth.getInstance();

        Handler hn=new Handler();
        hn.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (auth.getCurrentUser() == null) {
                    Intent i = new Intent(MainActivity.this, signin.class);
                    startActivity(i);

                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                    finish();
                } else {
                    Intent i = new Intent(MainActivity.this, worki.class);
                    startActivity(i);
                }
            }
        },3000);
    }
}
