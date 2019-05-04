package com.example.laptophp.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.laptophp.myapplication.sampledata.createuser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class signup extends AppCompatActivity {


    EditText user_name_et , email_et , mobile_et , password_et ;
    RadioGroup gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        user_name_et = (EditText) findViewById(R.id.user_name_et);

        email_et = (EditText) findViewById(R.id.email_et);

        mobile_et = (EditText) findViewById(R.id.mobile_et);

        gender =findViewById(R.id.gender);

        password_et = (EditText) findViewById(R.id.password_et);


    }

    public void signup(View view) {

        final String user_name = user_name_et.getText().toString();

        final String email = email_et.getText().toString();

        final String mobile = mobile_et.getText().toString();

        String password = password_et.getText().toString();

        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";
        final String genders =((RadioButton)findViewById(gender.getCheckedRadioButtonId())).getText().toString();
        if(user_name.length() < 4 || !user_name.matches("[a-zA-Z ]+"))
        {

            Toast.makeText(signup.this, "name must be 4 character long and not contain any digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.contains("_"))
        {
            Toast.makeText(signup.this , "please enter valid email type" , Toast.LENGTH_SHORT).show();
            return;
        }
        if(mobile.length() < 10)
        {
            Toast.makeText(signup.this , "mobile must be 10 digit" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.matches(pattern) || password.length() < 8)
        {
            Toast.makeText(signup.this, "password must contain atleast one alphabet , digit , special character and length must be 8 character", Toast.LENGTH_SHORT).show();
            return;
        }



        FirebaseAuth f_auth = FirebaseAuth.getInstance();

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    Toast.makeText(signup.this, "done", Toast.LENGTH_SHORT).show();
                    createuser data = new createuser ( email,genders,mobile,user_name);
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    database.getReference().child("user").child(email.replace(".","")).setValue(data);


                } else {
                    Toast.makeText(signup.this, "error try again", Toast.LENGTH_SHORT).show();
                }
            }
        };

        f_auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);





    }


}
