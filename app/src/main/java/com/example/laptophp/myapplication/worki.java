  package com.example.laptophp.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptophp.myapplication.sampledata.createbuss;
import com.example.laptophp.myapplication.sampledata.createfav;
import com.example.laptophp.myapplication.sampledata.createroute;
import com.example.laptophp.myapplication.sampledata.createstop;
import com.example.laptophp.myapplication.sampledata.createtime;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

  public class worki extends AppCompatActivity {
    ImageView menu;
    DrawerLayout drawerLayout;
      ArrayList<createtime> time_list;
      ArrayList<String> routes_fav,f_id;
      RecyclerView time_recycler;
      String from_, to_, bus_num, stop_;
      ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_worki);
        menu=(ImageView)findViewById(R.id.menu);
            drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        time_list = new ArrayList<>();
        routes_fav = new ArrayList<String>();
        f_id = new ArrayList<String>();
        pd=new ProgressDialog(this);
        pd.setTitle("Wait!!!");
        pd.setMessage("Loading!!!!!!");
        pd.show();
        time_recycler = (RecyclerView) findViewById(R.id.recycler_id);
        time_recycler.setLayoutManager(new LinearLayoutManager(worki.this , LinearLayoutManager.VERTICAL,false));


    }


    public void open_time_scheduling(View view) {
        Intent i= new Intent(worki.this,time_schdeuling.class);
        startActivity(i);

    }

    public void open_routing(View view) {
        Intent i= new Intent(worki.this,route.class);
        startActivity(i);
    }

    public void open_feedback(View view) {
        Intent i= new Intent(worki.this,feedback.class);
        startActivity(i);
    }

    public void open_real_time_tracking(View view) {
        Intent i= new Intent(worki.this,tracking.class);
        startActivity(i);
    }

    public void open_contact_us(View view) {
        Intent i= new Intent(worki.this,contact.class);
        startActivity(i);
    }

    public void menu(View view) {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void open_profile(View view) {

        Intent i= new Intent(worki.this, Profile.class);
        startActivity(i);
    }

    public void add_fav(View view) {

        Intent i= new Intent(worki.this,Add_fav_route.class);
        startActivity(i);

    }
      @Override
      protected void onResume() {
          super.onResume();
          get_fav_routes();

      }
    public void get_time() {

        FirebaseDatabase data = FirebaseDatabase.getInstance();
        System.out.println(routes_fav.toString()+f_id.toString()+"vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv"+"rrrr");
        data.getReference().child("Time").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                time_list.clear();
                pd.hide();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    createtime details = data.getValue(createtime.class);
                    System.out.println("rrrcccccccccccccccccccccccccccccccccccccccccccrrr");
                    if (routes_fav.contains(details.routeidd)) {
                        details.t_id = data.getKey();
                       details.f_id= f_id.get(routes_fav.indexOf(details.routeidd));
                        time_list.add(details);
                    }
                        Adapter adapter = new Adapter();
                        time_recycler.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    public class view_holder extends RecyclerView.ViewHolder {

        Button del;
        TextView bid, from_id, to_id, stop_id, time_id;

        public view_holder(View itemView) {
            super(itemView);

            bid = itemView.findViewById(R.id.bus_id);
            from_id = itemView.findViewById(R.id.from_id);
            to_id = itemView.findViewById(R.id.to_id);
            stop_id = itemView.findViewById(R.id.stop_id);
            time_id = itemView.findViewById(R.id.timing_id);
            del = itemView.findViewById(R.id.delete_time);
        }
    }

    public class Adapter extends RecyclerView.Adapter<view_holder> {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {

            view_holder v = new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.timing_cell, parent, false));

            return v;
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {

            final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().replace(".", "");
            final createtime data = time_list.get(position);
                get_routes(data.routeidd, holder.from_id, holder.to_id);
                get_bus(data.bus_id, holder.bid);
                get_stop(data.stop_id, holder.stop_id);
                holder.time_id.setText(data.time);
                holder.del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference().child("favorite").child(email).child(data.f_id);
                        mPostReference.removeValue();
                    }
                });
            }

        @Override
        public int getItemCount() {
            return time_list.size();
        }
    }

    private void get_bus(String bus_id, final TextView bid) {
        System.out.println(bus_id+"vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        data.getReference().child("bus").child(bus_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                createbuss details = dataSnapshot.getValue(createbuss.class);
                bid.setText(details.b_num);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void get_stop(final String stop_id, final TextView stopId) {
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        data.getReference().child("stop").child(stop_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                createstop details = dataSnapshot.getValue(createstop.class);
                stopId.setText(details.S_name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void get_routes(String routeidd, final TextView floc, final TextView tloc) {
        FirebaseDatabase data = FirebaseDatabase.getInstance();
        data.getReference().child("route").child(routeidd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                createroute details=dataSnapshot.getValue(createroute.class);
                floc.setText(details.from_loc);
                tloc.setText(details.to_loc);
                System.out.println(from_+to_+"mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
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
                  for (DataSnapshot data : dataSnapshot.getChildren()) {
                      createfav details = data.getValue(createfav.class);
                      routes_fav.add(details.routeidd);
                      details.f_id = data.getKey();
                      f_id.add(details.f_id);
                      System.out.println("1111111111111111111111111111111111111111111111111111111111111111111111111"+routes_fav.toString());
                      get_time();
                  }
              }
              @Override
              public void onCancelled(DatabaseError databaseError) {

              }
          });
      }

}
