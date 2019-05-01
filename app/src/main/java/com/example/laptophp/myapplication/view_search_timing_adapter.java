package com.example.laptophp.myapplication;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ghumman on 5/13/2017.
 */

public class view_search_timing_adapter extends RecyclerView.Adapter<view_search_timing_holder> {


    JSONArray jarr ;
    Activity a;

    public view_search_timing_adapter(JSONArray jarr , Activity a)
    {
        this.jarr = jarr;
        this.a = a;

    }
    @Override
    public view_search_timing_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new view_search_timing_holder(LayoutInflater.from(a).inflate(R.layout.search_cell, parent , false));

    }

    @Override
    public void onBindViewHolder(view_search_timing_holder holder, int position) {

        try {
            final JSONObject jobj = jarr.getJSONObject(position);

            holder.bus_id.setText(jobj.getString("bus_number"));
            holder.route_from.setText(jobj.getString("from_"));
            holder.route_to.setText(jobj.getString("to_"));
            holder.stop.setText(jobj.getString("stop_name"));
            holder.timing.setText(jobj.getString("timing"));



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jarr.length();
    }
}
