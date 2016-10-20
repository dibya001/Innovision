package in.ac.nitrkl.innovisionr;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;


/**
 * Created by pawan on 10/18/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {


    ArrayList<EventDetails> eventDetails;
    Context context;
    int lastPosition=-1;

    public RecyclerViewAdapter(Context mainActivity, ArrayList<EventDetails> eventDetails) {
        this.eventDetails = eventDetails;
        this.context = mainActivity;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, parent, false);
        //layoutView.setMinimumHeight(parent.getMeasuredHeight() / 2);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, int position) {
        holder.countryName.setText(eventDetails.get(position).getName());
     /*   Picasso.with(context)
                .load(eventDetails.get(position).getImage_path())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.countryPhoto);

*/            Glide.with(context).load(eventDetails.get(position).getImage_path())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.pb.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.pb.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.countryPhoto);
      setAnimation(holder.card, position);

    }

    private void setAnimation(CardView card, int position) {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            card.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return this.eventDetails.size();

    }

    public void start(final int pos) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(context, ShowEventActivity.class);

                i.putExtra("id", eventDetails.get(pos).getId() + "");
                //Toast.makeText(context,eventDetails.get(holder.getAdapterPosition()).getId(),Toast.LENGTH_LONG).show();
                //Toast.makeText(context,allEvents.get(holder.getAdapterPosition()).getId(),Toast.LENGTH_LONG).show();
                Log.i("eee", eventDetails.get(pos).getId() + "");
                context.startActivity(i);
            }
        }, 500);


    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView countryName;
        public ImageView countryPhoto;
        public ProgressBar pb;

        public CardView card;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
            pb= (ProgressBar) itemView.findViewById(R.id.progress);
            card = (CardView) itemView.findViewById(R.id.card_view);
            card.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            start(getAdapterPosition());
        }


    }
}
