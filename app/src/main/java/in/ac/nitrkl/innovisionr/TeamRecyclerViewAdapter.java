package in.ac.nitrkl.innovisionr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Phaser;

/**
 * Created by pawan on 10/18/2016.
 */
public class TeamRecyclerViewAdapter extends RecyclerView.Adapter<TeamRecyclerViewHolders> {


    ArrayList<Team> allEvents;
    Context context;

    public TeamRecyclerViewAdapter(Context mainActivity, ArrayList<Team> eventDetails) {
        this.allEvents=eventDetails;
        this.context=mainActivity;
    }

    @Override
    public TeamRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teamcardview, parent,false);
        //layoutView.setMinimumHeight(parent.getMeasuredHeight() / 4);
        TeamRecyclerViewHolders rcv = new TeamRecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(TeamRecyclerViewHolders holder, final int position) {
        holder.name.setText(allEvents.get(position).getNama());
        holder.Photo.setImageResource(allEvents.get(position).getPhoto());

        holder.Phone.setText(allEvents.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return this.allEvents.size();
    }
}