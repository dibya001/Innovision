package in.ac.nitrkl.innovisionr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;

public class ShowEventActivity extends AppCompatActivity {

    TextView id;
    String uid,result;
    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mDataset = {"29 degrees", "Seahawks 24 - 27 Bengals",
            "Flash missing, vanishes in crisis", "Half Life 3 announced"};
    private int mDatasetTypes[] = {11,15,15,15,15,15,15,15,15}; //view types


    ImageView image;
    TextView tvtitle;
    ArrayList<String>eventdetails;
    String passedtitle;
    TextView  jud,ven,rul,cod,des;
    String title,description,rules,date,venue,time,imagepath,coordinators,judging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);
        Toolbar tb= (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        eventdetails=new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(ShowEventActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CustomAdapter(this,eventdetails, mDatasetTypes);
        mRecyclerView.setAdapter(mAdapter);

        AlphaAnimatorAdapter animatorAdapter = new
                AlphaAnimatorAdapter(mAdapter,mRecyclerView);
        mRecyclerView.setAdapter(animatorAdapter);

        Intent intent=getIntent();
        Toast.makeText(this,intent.getStringExtra("id"), Toast.LENGTH_SHORT).show();
        //id.setText(intent.getStringExtra("id"));

        uid=intent.getStringExtra("id");
        fetchdata();
    }

    private void fetchdata() {

        String url = "http://192.168.43.103/inno_final/fetchevent.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        //Toast.makeText(ShowEvents.this, response, Toast.LENGTH_SHORT).show();
                        try {



                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj=jsonArray.getJSONObject(0);



                            eventdetails.add("http://innovision.nitrkl.ac.in/"+obj.getString("image_path"));
                            eventdetails.add(obj.getString("title"));

                            //Log.i("passeddata",passedtitle);

                            eventdetails.add(obj.getString("date"));
                            eventdetails.add(obj.getString("venue"));
                            eventdetails.add(obj.getString("time"));

                            eventdetails.add(obj.getString("description"));
                            eventdetails.add(obj.getString("rules"));
                            eventdetails.add(obj.getString("judging_criteria"));
                            eventdetails.add(obj.getString("coordinator"));

                            mAdapter.notifyDataSetChanged();

  /*                         Glide.with(ShowEvents.this).load("http://innovision.nitrkl.ac.in/"+obj.getString("image_path"))

                                   .diskCacheStrategy(DiskCacheStrategy.ALL)
                                   .into(image);
*/


                            ;

                            //   setAnimation(holder.card, position);

                       /*     tvtitle.setText(obj.getString("title"));
                            des.setText(obj.getString("description"));

                            rul.setText(obj.getString("rules"));
                            cod.setText(obj.getString("cooridnator"));

                            ven.setText(obj.getString("date")+obj.getString("venue")+obj.getString("time"));
                            jud.setText(obj.getString("judging_criteria"));

/*des
                           title=obj.getString("title");
                           description=obj.getString("description");
                           rules=obj.getString("rules");
                           judging=obj.getString("judging_criteria");
                           date=obj.getString("date");
                           venue=obj.getString("venue");
                            time=obj.getString("time");

                           tvtitle.append(response);
*/


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
                params.put("id", uid);

                return params;
            }
        };
        MySingleTon.getInstance(ShowEventActivity.this).addtoRequestQueue(stringRequest);

    }
}
