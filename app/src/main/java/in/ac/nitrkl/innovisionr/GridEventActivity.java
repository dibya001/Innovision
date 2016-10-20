package in.ac.nitrkl.innovisionr;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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

public class GridEventActivity extends AppCompatActivity {

    GridLayoutManager lLayout;
    String code;
    Toolbar tb;
    RecyclerView allEvents;
    ArrayList<EventDetails> eventDetails;
    RecyclerViewAdapter rcAdapter;
    SwipeRefreshLayout sp;
    String url = "http://192.168.43.103/inno_final/fetch_events.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridevent);
        tb= (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        eventDetails = new ArrayList<>();
        allEvents = (RecyclerView) findViewById(R.id.allEvents);
        sp= (SwipeRefreshLayout) findViewById(R.id.swipe);
        lLayout = new GridLayoutManager(GridEventActivity.this, 2);
        rcAdapter = new RecyclerViewAdapter(GridEventActivity.this, eventDetails);
        sp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventDetails.clear();
                rcAdapter.notifyDataSetChanged();
                fetch();
                makegrid();
                sp.setRefreshing(false);
            }
        });
        makegrid();

        fetch();





    }

    private void makegrid() {
        lLayout = new GridLayoutManager(GridEventActivity.this, 2);
        allEvents.setHasFixedSize(true);

        allEvents.setLayoutManager(lLayout);

        allEvents.setAdapter(rcAdapter);

        SlideInBottomAnimatorAdapter animatorAdapter = new
                SlideInBottomAnimatorAdapter(rcAdapter, allEvents);
        allEvents.setAdapter(animatorAdapter);
    }

    public void fetch(){
    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {

                @Override

                public void onResponse(String response) {
                    try {

                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject obj=jsonArray.getJSONObject(i);
                            EventDetails a=new EventDetails();
                            a.setId(Integer.parseInt(obj.getString("eid")));
                            a.setImage_path("http://innovision.nitrkl.ac.in/"+obj.getString("image_path"));
                            a.setName(obj.getString("title"));
                            eventDetails.add(a);
                            rcAdapter.notifyDataSetChanged();

                        }
                        Log.i("eee",response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(final VolleyError error) {


            Log.i("eeee", error.toString());
        }

    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("cat", "event");

            return params;
        }
    };

    MySingleTon.getInstance(GridEventActivity.this).addtoRequestQueue(stringRequest);
}
}
