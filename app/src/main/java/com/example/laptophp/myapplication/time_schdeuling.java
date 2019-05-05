package com.example.laptophp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.laptophp.myapplication.sampledata.createroute;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class time_schdeuling extends AppCompatActivity {

    Spinner route_spinner ;
    List<String> routes_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_schdeuling);

        routes_ids = new ArrayList<>();


        route_spinner = (Spinner) findViewById(R.id.route_spinner);

        get_routes();
    }

    public void submit_stop(View view) {

        Intent i = new Intent(time_schdeuling.this , Search_result.class);
        i.putExtra("route_id", routes_ids.get(route_spinner.getSelectedItemPosition()));
        startActivity(i);
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
                ArrayAdapter<String> data1 = new ArrayAdapter<String>(time_schdeuling.this, android.R.layout.simple_dropdown_item_1line, routes);

                route_spinner.setAdapter(data1);

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
