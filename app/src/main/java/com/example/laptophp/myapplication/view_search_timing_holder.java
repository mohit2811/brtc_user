package com.example.laptophp.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ghumman on 5/13/2017.
 */

public class view_search_timing_holder extends RecyclerView.ViewHolder {

    TextView bus_id , route_from , route_to , stop , timing ;



    public view_search_timing_holder(View itemView) {
        super(itemView);

        bus_id = (TextView) itemView.findViewById(R.id.bus_id);
        route_from = (TextView) itemView.findViewById(R.id.from_id);
        route_to = (TextView) itemView.findViewById(R.id.to_id);
        stop = (TextView) itemView.findViewById(R.id.stop_id);
        timing = (TextView) itemView.findViewById(R.id.timing_id);



    }
}
