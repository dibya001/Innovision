package in.ac.nitrkl.innovisionr.Timeline;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.ac.nitrkl.innovisionr.R;

/**
 * Created by pawan on 10/18/2016.
 */
public class TimelineRecyclerViewHolders extends RecyclerView.ViewHolder {

    public TextView eventName,eventtype,time;
    public ImageView countryPhoto;


public CardView card;
    public TimelineRecyclerViewHolders(View itemView) {
        super(itemView);
        eventName = (TextView)itemView.findViewById(R.id.eventName);
        eventtype = (TextView) itemView.findViewById(R.id.eventType);
        card= (CardView) itemView.findViewById(R.id.card_view);
        time= (TextView) itemView.findViewById(R.id.time);

    }


}