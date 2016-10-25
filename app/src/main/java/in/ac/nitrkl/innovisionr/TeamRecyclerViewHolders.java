package in.ac.nitrkl.innovisionr;

        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;

/**
 * Created by pawan on 10/18/2016.
 */
public class TeamRecyclerViewHolders extends RecyclerView.ViewHolder {

    public TextView name,Phone;
    public ImageView Photo;


    public CardView card;
    public TeamRecyclerViewHolders(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.name);
        Photo = (ImageView)itemView.findViewById(R.id.photo);
        card= (CardView) itemView.findViewById(R.id.card_view);
        Phone= (TextView) itemView.findViewById(R.id.call);
    }


}