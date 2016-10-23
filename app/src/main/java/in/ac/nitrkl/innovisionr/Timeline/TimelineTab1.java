package in.ac.nitrkl.innovisionr.Timeline;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.ac.nitrkl.innovisionr.R;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class TimelineTab1 extends android.support.v4.app.Fragment {

    LinearLayoutManager lLayout;
    RecyclerView allEvents;
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View v=inflater.inflate(R.layout.timeline_tab1, container, false);
       allEvents= (RecyclerView) v.findViewById(R.id.timelineTab1);


        lLayout = new LinearLayoutManager(getActivity());

        //allEvents.setHasFixedSize(true);

        allEvents.setLayoutManager(lLayout);

        TimelineRecyclerViewAdapter rcAdapter = new TimelineRecyclerViewAdapter(getActivity(), TimelineActivity.list1);


        allEvents.setAdapter(rcAdapter);

        return v;
    }
}