package in.ac.nitrkl.innovisionr.Timeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import in.ac.nitrkl.innovisionr.R;

/**
 * Created by pawan on 10/18/2016.
 */
public class TimelineRecyclerViewAdapter extends RecyclerView.Adapter<TimelineRecyclerViewHolders> {


    ArrayList<Timeline> allEvents;
Context context;

    public TimelineRecyclerViewAdapter(Context mainActivity, ArrayList<Timeline> eventDetails) {
        this.allEvents=eventDetails;
        this.context=mainActivity;
    }

    @Override
    public TimelineRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_card_view_list, parent,false);
       layoutView.setMinimumHeight(parent.getMeasuredHeight() / 7);
        TimelineRecyclerViewHolders rcv = new TimelineRecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(TimelineRecyclerViewHolders holder, final int position) {
        holder.eventName.setText(allEvents.get(position).getEventName());

        holder.eventtype.setText(allEvents.get(position).getEventType());

        holder.time.setText(allEvents.get(position).getTime());
       holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, allEvents.get(position).getEid()+"", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return this.allEvents.size();
    }
}
