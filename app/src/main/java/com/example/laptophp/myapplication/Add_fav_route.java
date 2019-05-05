package com.example.laptophp.myapplication;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.laptophp.myapplication.sampledata.createfav;
import com.example.laptophp.myapplication.sampledata.createroute;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Add_fav_route extends AppCompatActivity {


    Spinner route_spinner;
    List<String> routes_ids;
    List<String> routes_fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fav_route);

        routes_ids = new ArrayList<>();
        routes_fav = new ArrayList<>();

        route_spinner = (Spinner) findViewById(R.id.route_spinner);


        get_fav_routes();
    }

    public void add_fav(View view) {

        String route_id = routes_ids.get(route_spinner.getSelectedItemPosition());
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
System.out.println(route_id+routes_fav.toString()+"vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        if (routes_fav.contains(route_id)) {
            Toast.makeText(Add_fav_route.this, "Already Added in favorites", Toast.LENGTH_SHORT).show();
        } else {
            createfav data = new createfav(route_id,email);
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.getReference().child("favorite").child(email.replace(".","")).push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {

                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {

                        Toast.makeText(Add_fav_route.this , "Favorite Added" , Toast.LENGTH_SHORT).show();

                    }
                    else {

                        Toast.makeText(Add_fav_route.this , "Error" , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void get_routes() {
        final List<String> routes = new ArrayList<>();
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        data.getReference().child("route").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    createroute details = data.getValue(createroute.class);
                    routes.add(details.from_loc + " - to - " + details.to_loc);
                    details.r_id = data.getKey();
                    routes_ids.add(details.r_id);

                }
                ArrayAdapter<String> data1 = new ArrayAdapter<String>(Add_fav_route.this, android.R.layout.simple_dropdown_item_1line, routes);
                route_spinner.setAdapter(data1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void get_fav_routes() {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().replace(".", "");
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        data.getReference().child("favorite").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                routes_fav.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    createfav details = data.getValue(createfav.class);
                    routes_fav.add(details.routeidd);
                }

                get_routes();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void close(View view) {
        finish();
    }
}
