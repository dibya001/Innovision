package in.ac.nitrkl.innovisionr;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.gmariotti.recyclerview.adapter.SlideInBottomAnimatorAdapter;

public class GridEventActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    GridLayoutManager lLayout;
    String code;
    Toolbar tb;
    RecyclerView allEvents;
    ArrayList<EventDetails> eventDetails;
    RecyclerViewAdapter rcAdapter;
    SwipeRefreshLayout sp;
    ProgressBar pb;
    String category;
    String url = "http://innovision.nitrkl.ac.in/android/fetch_events.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridevent);

        tb = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icon);

        tb.setTitleTextColor(Color.WHITE);
        showSnack(ConnectivityReceiver.isConnected());
        pb= (ProgressBar) findViewById(R.id.gridprogress);
        eventDetails = new ArrayList<>();
        category=getIntent().getStringExtra("category");
        allEvents = (RecyclerView) findViewById(R.id.allEvents);
        sp = (SwipeRefreshLayout) findViewById(R.id.swipe);
        lLayout = new GridLayoutManager(GridEventActivity.this, 2);
        rcAdapter = new RecyclerViewAdapter(GridEventActivity.this, eventDetails,category);
        makegrid();
        fetch();

        if(!sp.isRefreshing()) {
            sp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    //makegrid();
                    fetch();

                    sp.setRefreshing(true);

                }
            });
        }






    }
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
            //Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
            // Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab=new AlertDialog.Builder(GridEventActivity.this);
            ab.setTitle("No Internet");
            ab.setMessage(message);
            ab.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog ad = ab.create();
            ad.show();
        }

       /* Snackbar snackbar = Snackbar
                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }



    private void makegrid() {
        lLayout = new GridLayoutManager(GridEventActivity.this, 2);
       // allEvents.setHasFixedSize(true);

        allEvents.setLayoutManager(lLayout);

        allEvents.setAdapter(rcAdapter);

        SlideInBottomAnimatorAdapter animatorAdapter = new
                SlideInBottomAnimatorAdapter(rcAdapter, allEvents);
        allEvents.setAdapter(animatorAdapter);
    }

    public void fetch(){
        eventDetails.clear();
        rcAdapter.notifyDataSetChanged();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        try {
                            pb.setVisibility(View.GONE);
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject obj=jsonArray.getJSONObject(i);
                                EventDetails a=new EventDetails();
                                a.setId(Integer.parseInt(obj.getString("eid")));
                                String path=obj.getString("image_path");
                                path=path.substring(14);
                                Log.i("path",path);
                                path="http://innovision.nitrkl.ac.in/android/images/"+path ;
                                a.setImage_path(path);
                                a.setName(obj.getString("title"));
                                eventDetails.add(a);


                            }
                            rcAdapter.notifyDataSetChanged();
                            Log.i("eee",response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        sp.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {


                Log.i("eeee", error.toString());
                sp.setRefreshing(false);

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cat", category);
                Log.i("eeee",category);
                return params;
            }
        };

        MySingleTon.getInstance(GridEventActivity.this).addtoRequestQueue(stringRequest);
    }
}
