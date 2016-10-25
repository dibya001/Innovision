package in.ac.nitrkl.innovisionr;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;

import static in.ac.nitrkl.innovisionr.R.drawable.a;

public class ShowEventActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    TextView id;
    String uid,result;
    private RecyclerView mRecyclerView;
    ProgressBar pb;
    private Context context;
    private PendingIntent mAlarmSender;
    private CustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mDataset = {"29 degrees", "Seahawks 24 - 27 Bengals",
            "Flash missing, vanishes in crisis", "Half Life 3 announced"};
    private int mDatasetTypes[] = {11,15,15,15,15,15,15,15,15}; //view types


    ImageView image;
    Boolean dload;
    FloatingActionButton fab;
    TextView tvtitle;
    ArrayList<String>eventdetails;
    String passedtitle;
    SharedPreferences sp;
    String category;
    TextView  jud,ven,rul,cod,des;
    String eventtime;
    String eid,title,description,rules,date,venue,time,imagepath,coordinators,judging;
    SQLiteDatabase db;
    SwipeRefreshLayout swipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);
        Toolbar tb= (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icon);
        tb.setTitleTextColor(Color.WHITE);

        sp=getSharedPreferences("demo_file",MODE_PRIVATE);
        pb= (ProgressBar) findViewById(R.id.showeventprogress);
        eventdetails=new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(ShowEventActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        db=openOrCreateDatabase("bookmark",MODE_APPEND,null);
        mAdapter = new CustomAdapter(this,eventdetails, mDatasetTypes);
        mRecyclerView.setAdapter(mAdapter);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        fetchdata();
        if(!swipe.isRefreshing()) {
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    fetchdata();

                    swipe.setRefreshing(true);

                }
            });
        }

        dload=false;
        AlphaAnimatorAdapter animatorAdapter = new
                AlphaAnimatorAdapter(mAdapter,mRecyclerView);
        mRecyclerView.setAdapter(animatorAdapter);

        Intent intent=getIntent();
        //Toast.makeText(this,intent.getStringExtra("id"), Toast.LENGTH_SHORT).show();
        //id.setText(intent.getStringExtra("id"));

        uid=intent.getStringExtra("id");
        category=intent.getStringExtra("category");

        //Toast.makeText(ShowEventActivity.this, time, Toast.LENGTH_SHORT).show();
      fab= (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.getString("userid","-1").equals("-1"))
                {
                    Toast.makeText(ShowEventActivity.this,"You are not logged in",Toast.LENGTH_LONG).show();

                }
                else {

                     eventreg();
                    setRemainder();

                }
            }



        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share, menu);
    /*    if(sp.getString("userid","-1").equals("-1"))
        {
            menu.getItem(R.id.action_register).setIcon(R.drawable.user);

        }
        else
        {
            menu.getItem(R.id.action_register).setIcon(R.drawable.profile);

        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);

            sendIntent.putExtra(Intent.EXTRA_TEXT,title+"\n\n"+ description + "\n" +"\nDate:"+date+"\nTime:"+time+"\nVenue:"+venue+ "\ninnovision.nitrkl.ac.in/details.php?EID=" + uid);
            sendIntent.setType("text/plain");
            if(title!=null)
                startActivity(Intent.createChooser(sendIntent, "Share Event"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setRemainder() {

        //Toast.makeText(ShowEventActivity.this, time, Toast.LENGTH_SHORT).show();
        String hour = "";
        String min="";

        if(time.charAt(1)==':')
        {
            hour+=time.charAt(0);
            min+=time.charAt(2);
            min+=time.charAt(3);

        }
        else
        {

            hour+=time.charAt(0);
            hour+=time.charAt(1);
            min+=time.charAt(3);
            min+=time.charAt(4);

        }

        int hr,minn;
        hr= Integer.parseInt(hour);


        if(time.contains("PM")&& !time.contains("AM"))
        {
            hr+=12;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016,10, Integer.parseInt(String.valueOf(date.charAt(0))),hr, Integer.parseInt(min));
        long startTime = calendar.getTimeInMillis();
        startTime-=30*60*1000;

        setAlarm(Integer.parseInt(eid),title,"starts in 30 mins ",startTime);
        //setAlarm(4545,title,"starts in 30 mins ",System.currentTimeMillis()+3000);

    }

    public void setAlarm(int id,String title,String Text,long time){
        Intent intent = new Intent(this, NotificationPublisher.class);

       intent.putExtra("title",title);
        intent.putExtra("text",Text);
        intent.putExtra("id",String.valueOf(id));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
       // Toast.makeText(this, "Alarm set in " + (time - System.currentTimeMillis()) + " seconds",
         //       Toast.LENGTH_LONG).show();
    }


    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            //   message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
        }


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


    private void eventreg() {

        String eventregurl="http://innovision.nitrkl.ac.in/android/eventreg.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, eventregurl,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {

                        dload=true;
                        Log.i("titns", response);
                        try {
                            JSONArray ob=new JSONArray(response);
                            JSONObject obj=ob.getJSONObject(0);
                            String code=obj.getString("code");
                            if (!code.equals("no")) {


                              /*  String date=obj.getString("date");
                                 time=obj.getString("time");
                                try{
                               String q="insert into bookmarktable values("+"\'"+code+"\'"+","+"\'"+date+"\'"+","+"\'"+time+"\'"+")";
                                db.execSQL(q);
                                }
                                catch (Exception e)
                                {

                                }*/
                                //
                                Toast.makeText(ShowEventActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();


                                //finish();
                            } else {
                                Toast.makeText(ShowEventActivity.this, "Already registered", Toast.LENGTH_LONG).show();

                            }
                            //Toast.makeText(ddctivity.this,code,Toast.LENGTH_LONG).show();






                        }
                        catch (JSONException e) {
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
                params.put("userid",sp.getString("userid","-1")) ;
                params.put("eventid", uid);
                // Log.i("code",params.get("email"));
                return params;
            }
        };

        MySingleTon.getInstance(ShowEventActivity.this).addtoRequestQueue(stringRequest);

    }




    private void fetchdata() {
        if(eventdetails.size()>0)
        {
            eventdetails.clear();
            mAdapter.notifyDataSetChanged();
        }

        String url = "http://innovision.nitrkl.ac.in/android/fetchevent.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        //Toast.makeText(ShowEvents.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            pb.setVisibility(View.GONE);
                            Log.i("res",response);


                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj=jsonArray.getJSONObject(0);

                            String path=obj.getString("image_path");
                            path=path.substring(14);


                            eventdetails.add(path);


                            eventdetails.add(obj.getString("title"));

                            //Log.i("passeddata",passedtitle);

                            eventdetails.add(obj.getString("date"));
                            eventdetails.add(obj.getString("venue"));
                            eventdetails.add(obj.getString("time"));

                            eventdetails.add(obj.getString("description"));
                            eventdetails.add(obj.getString("rules"));
                            eventdetails.add(obj.getString("judging_criteria"));
                            eventdetails.add(obj.getString("coordinator"));

                            eventtime=obj.getString("time");

                            mAdapter.notifyDataSetChanged();

                            if(category.equals("event"))
                                fab.setVisibility(View.VISIBLE);
                            eid=obj.getString("eid");
                            title=obj.getString("title");
                            description=obj.getString("description");
                            rules=obj.getString("rules");
                            judging=obj.getString("judging_criteria");
                            date=obj.getString("date");
                            venue=obj.getString("venue");
                            time=obj.getString("time");
                            imagepath=obj.getString("image_path");
                            Log.i("eee",time);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        swipe.setRefreshing(false);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {


                Log.i("eeee", error.toString());
                swipe.setRefreshing(false);
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