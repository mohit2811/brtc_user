package com.example.laptophp.myapplication;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.laptophp.myapplication.sampledata.createroute;
import com.example.laptophp.myapplication.sampledata.createuser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {

    EditText email, mobile, name;

    RadioButton male_radio, female_radio;
    String emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = (EditText) findViewById(R.id.email);
        mobile = (EditText) findViewById(R.id.mobile);
        name = (EditText) findViewById(R.id.name);
        male_radio = (RadioButton) findViewById(R.id.male_radio);
        female_radio = (RadioButton) findViewById(R.id.female_radio);

        get_basic_info();


    }

    public void get_basic_info() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        emails = auth.getCurrentUser().getEmail().replace(".","");


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("users").child(emails).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                createuser  data = dataSnapshot.getValue(createuser.class);
                System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv"+data.user_name+data.email+data.gender+data.mobile);
               /* name.setText(data.user_name);
                mobile.setText(data.mobile);
                email.setText(data.email);
                if (data.gender.equals("Male")) {
                    male_radio.setChecked(true);
                } else {
                    female_radio.setChecked(true);
                }
*/


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void update(View view) {
        String gender = null;
        if(name.getText().toString().equals("")) {

            Toast.makeText(Profile.this , "enter your name" , Toast.LENGTH_SHORT).show();
            return;

        }

        if(mobile.getText().toString().equals("")) {

            Toast.makeText(Profile.this , "enter your name" , Toast.LENGTH_SHORT).show();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){

            Toast.makeText(Profile.this, "enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (male_radio.isChecked()) {
           gender="male";
        }
        if (female_radio.isChecked()) {
            gender="female";
        }
        final ProgressDialog progress_bar = new ProgressDialog(Profile.this);
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("Create account");
        progress_bar.show();


        createuser data = new createuser ( email.getText().toString(),gender,mobile.getText().toString(),name.getText().toString());
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    database.getReference().child("user").child(email.getText().toString().replace(".","")).setValue(data);
                    Toast.makeText(Profile.this, "update success", Toast.LENGTH_SHORT).show();

            }


    public void close(View view) {

        finish();
    }

}