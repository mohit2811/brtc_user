package com.example.laptophp.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laptophp.myapplication.sampledata.createbuss;
import com.example.laptophp.myapplication.sampledata.createroute;
import com.example.laptophp.myapplication.sampledata.createstop;
import com.example.laptophp.myapplication.sampledata.createtime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search_result extends AppCompatActivity {


    ArrayList<createtime> time_list;
    RecyclerView time_recycler;
    String from_, to_, bus_num, stop_, stop_id, route_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        time_list = new ArrayList<>();
        stop_id = getIntent().getStringExtra("stop_id");
        route_id = getIntent().getStringExtra("route_id");
        time_recycler = findViewById(R.id.recycler_id);

        time_recycler.setLayoutManager(new LinearLayoutManager(Search_result.this, LinearLayoutManager.VERTICAL, false));
    }


    public void get_time() {

        FirebaseDatabase data = FirebaseDatabase.getInstance();
        System.out.println("rrrr");
        data.getReference().child("time").addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                time_list.clear();


                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    createtime details = data.getValue(createtime.class);
                    System.out.println("rrrrrr");
                    details.t_id = data.getKey();
                    time_list.add(details);
                    Adapter adapter = new Adapter();
                    time_recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        get_time();
    }

    public class view_holder extends RecyclerView.ViewHolder {


        TextView bid, from_id, to_id, stop_id, time_id;

        public view_holder(View itemView) {
            super(itemView);

            bid = itemView.findViewById(R.id.bus_id);
            from_id = itemView.findViewById(R.id.from_id);
            to_id = itemView.findViewById(R.id.to_id);
            stop_id = itemView.findViewById(R.id.stop_id);
            time_id = itemView.findViewById(R.id.timing_id);
        }
    }

    public class Adapter extends RecyclerView.Adapter<view_holder> {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {

            view_holder v = new Search_Stop.view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_cell, parent, false));

            return v;
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {


            final createtime data = time_list.get(position);
            if (data.routeidd.equals(route_id) && data.stop_id.equals(stop_id)) {
                get_route(data.routeidd);
                get_bus(data.bus_id);
                get_stop(data.stop_id);
                holder.bid.setText(bus_num);
                holder.from_id.setText(from_);
                holder.stop_id.setText(stop_);
                holder.to_id.setText(to_);
                holder.time_id.setText(data.time);

            }
        }

        @Override
        public int getItemCount() {
            return time_list.size();
        }
    }

    private void get_bus(String bus_id) {
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        data.getReference().child("bus").child(bus_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                createbuss details = dataSnapshot.getValue(createbuss.class);
                bus_num = details.b_num;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void get_stop(String stop_id) {
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        data.getReference().child("stop").child(stop_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                createstop details = dataSnapshot.getValue(createstop.class);
                stop_ = details.s_id;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void get_route(String routeidd) {
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        data.getReference().child("route").child(routeidd).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                createroute details = dataSnapshot.getValue(createroute.class);
                from_ = details.from_loc;
                to_ = details.to_loc;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
