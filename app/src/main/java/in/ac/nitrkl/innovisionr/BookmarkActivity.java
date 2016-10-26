package in.ac.nitrkl.innovisionr;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

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
import java.util.Random;

import in.ac.nitrkl.innovisionr.Timeline.Timeline;
import in.ac.nitrkl.innovisionr.Timeline.TimelineActivity;
import in.ac.nitrkl.innovisionr.Timeline.TimelineRecyclerViewAdapter;



public class BookmarkActivity  extends AppCompatActivity  implements ConnectivityReceiver.ConnectivityReceiverListener {
    SQLiteDatabase db;
    SharedPreferences sp;
    ArrayList<Timeline> arrayList;
    RecyclerView l;
    ProgressBar pb;
    String edate,etime,title,eid;
    TimelineRecyclerViewAdapter arrayAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db=openOrCreateDatabase("bookmark",MODE_APPEND,null);
        sp=getSharedPreferences("demo_file",MODE_PRIVATE);
        showSnack(ConnectivityReceiver.isConnected());





        if(sp.getString("userid","-1").equals("-1"))
        {
            setContentView(R.layout.emptylayout);
            Toolbar tb= (Toolbar) findViewById(R.id.emptytoolbar);
            setSupportActionBar(tb);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_icon);
            getSupportActionBar().setTitle("Bookmarks");
            tb.setTitleTextColor(Color.WHITE);

            Toast.makeText(BookmarkActivity.this,"You are not logged in",Toast.LENGTH_LONG).show();
            finish();
        }
        else
        {
            setContentView(R.layout.activity_bookmark);
            Toolbar tb= (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(tb);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_icon);
            pb= (ProgressBar) findViewById(R.id.bookmarkprogress);
            getSupportActionBar().setTitle("Bookmarks");
            tb.setTitleTextColor(Color.WHITE);
            l= (RecyclerView) findViewById(R.id.listView);

            LinearLayoutManager lm=new LinearLayoutManager(this);
            l.setLayoutManager(lm);
            arrayList=new ArrayList<>();

            arrayAdapter = new TimelineRecyclerViewAdapter(BookmarkActivity.this, arrayList);

            fetchdata();
            l.setAdapter(arrayAdapter);



        }



    }

    private void fetchdata() {

        final String q="select E.eid,E.title,E.date,E.time,E.category  from cms_events E ,userreg R where R.userid='"+sp.getString("userid","-1") +"' and R.eid=E.eid ";
        String bookmarkurl="http://innovision.nitrkl.ac.in/android/common.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,bookmarkurl,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        try {

                            pb.setVisibility(View.GONE);

                            Log.i("res",response);
                            JSONArray arr=new JSONArray(response);
                            for(int i=0;i<arr.length();i++)
                            {
                                JSONObject obj=arr.getJSONObject(i);
                                Log.i("eeee",obj.getString("title"));

                                Timeline a=new Timeline();
                                a.setEid(obj.getString("eid"));
                                a.setEventName(obj.getString("title"));
                                a.setEventType(obj.getString("category"));
                                a.setTime(obj.getString("date")+"\n"+obj.getString("time"));

                                arrayList.add(a);
                                arrayAdapter.notifyDataSetChanged();
                            }



                            Log.i("eeee",response.toString()+"aa");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {

                Toast.makeText(BookmarkActivity.this,"server error",Toast.LENGTH_LONG).show();
                Log.i("eeee",error.toString());
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("query",q);
                Log.i("codeb",params.get("uid")+"aa");
                return params;
            }
        };

        MySingleTon.getInstance(BookmarkActivity.this).addtoRequestQueue(stringRequest);
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
            AlertDialog.Builder ab=new AlertDialog.Builder(BookmarkActivity.this);
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
    private void setRemainder() {

        String hour = "";
        String min="";

        if(etime.charAt(1)==':')
        {
            hour+=etime.charAt(0);
            min+=etime.charAt(2);
            min+=etime.charAt(3);

        }
        else
        {

            hour+=etime.charAt(0);
            hour+=etime.charAt(1);
            min+=etime.charAt(3);
            min+=etime.charAt(4);

        }

        int hr,minn;
        hr= Integer.parseInt(hour);


        if(etime.contains("PM")&& !etime.contains("AM"))
        {
            hr+=12;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016,10, Integer.parseInt(String.valueOf(edate.charAt(0))),hr, Integer.parseInt(min));
        long startTime = calendar.getTimeInMillis();
        startTime-=30*60*1000;

        //scheduleNotification(getNotification(title,"Starts In 30 mins",etime), startTime);

        setAlarm(Integer.parseInt(eid),title,etime,startTime);

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
        Toast.makeText(this, "Alarm set in " + time + " seconds",
                Toast.LENGTH_LONG).show();
    }

}
