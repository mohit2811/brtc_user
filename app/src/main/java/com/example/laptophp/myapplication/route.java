package com.example.laptophp.myapplication;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.laptophp.myapplication.sampledata.createroute;
import com.example.laptophp.myapplication.sampledata.createstop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class route extends AppCompatActivity {
    Spinner route_spinner , stop_spinner ;

    List<String> route_ids , stop_ids ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        route_spinner = (Spinner) findViewById(R.id.route_spinner);
        stop_spinner = (Spinner) findViewById(R.id.stop_spinner);
        route_ids = new ArrayList<>();
        stop_ids = new ArrayList<>();

        get_routes();
        route_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                get_stops(route_ids.get(route_spinner.getSelectedItemPosition()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







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
                    route_ids.add(details.r_id);
                }
                ArrayAdapter<String> data1 = new ArrayAdapter<String>(route.this, android.R.layout.simple_dropdown_item_1line, routes);

                route_spinner.setAdapter(data1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void get_stops(final String s) {

        final List<String> stops = new ArrayList<>();
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        data.getReference().child("stop").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    createstop details = data.getValue(createstop.class);

                    details.s_id = data.getKey();
                    if (details.routeidd.equals(s)) {
                        stops.add(details.S_name);
                        stop_ids.add(details.s_id);

                    }
                }
                ArrayAdapter<String> data1 = new ArrayAdapter<String>(route.this, android.R.layout.simple_dropdown_item_1line, stops);
                stop_spinner.setAdapter(data1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void search_stop(View view) {
        Intent i = new Intent(route.this , Search_Stop.class);
        i.putExtra("stop_id", stop_ids.get(stop_spinner.getSelectedItemPosition()));
        i.putExtra("route_id", route_ids.get(route_spinner.getSelectedItemPosition()));
        startActivity(i);

    }
}
