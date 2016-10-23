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
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import in.ac.nitrkl.innovisionr.Timeline.TimelineActivity;



public class BookmarkActivity  extends AppCompatActivity  implements ConnectivityReceiver.ConnectivityReceiverListener {
    SQLiteDatabase db;
    SharedPreferences sp;
    ArrayList<String> arrayList;
    private BottomBar bottomBar;
    ListView l;
    String edate,etime,title;
    ArrayAdapter arrayAdapter;
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
            getSupportActionBar().setTitle("Bookmarks");


            Toast.makeText(BookmarkActivity.this,"You are not logged in",Toast.LENGTH_LONG).show();
        }
        else
        {
            setContentView(R.layout.activity_bookmark);
            Toolbar tb= (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(tb);
            getSupportActionBar().setTitle("Bookmarks");
            l= (ListView) findViewById(R.id.listView);
            arrayList=new ArrayList<>();


            arrayAdapter=new ArrayAdapter(BookmarkActivity.this,android.R.layout.simple_list_item_1,arrayList);
            fetchdata();
            l.setAdapter(arrayAdapter);



        }



    }

    private void fetchdata() {
        String bookmarkurl="http://192.168.43.103/android/bookmark.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,bookmarkurl,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        try {

                                JSONArray arr=new JSONArray(response);
                                for(int i=0;i<arr.length();i++)
                                {
                                    JSONObject obj=arr.getJSONObject(i);
                                    Log.i("eeee",obj.getString("title"));
                                    arrayList.add(obj.getString("title")+"\n"+obj.getString("date")+"\n"+obj.getString("time"));

                                    edate=obj.getString("date");
                                    title=obj.getString("title");
                                    etime=obj.getString("time");

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            setRemainder();
                                            arrayAdapter.notifyDataSetChanged();

                                        }
                                    });
                                }



                            Log.i("eeee",response.toString()+"aa");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {

                Toast.makeText(BookmarkActivity.this,"some error occured",Toast.LENGTH_LONG).show();
                Log.i("eeee",error.toString());
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("uid","179");
                Log.i("code",params.get("uid"));
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
        scheduleNotification(getNotification(title,"Starts In 30 mins",etime), startTime);


    }

    private void scheduleNotification(Notification notification, long delay) {

        notification.flags |= Notification.DEFAULT_SOUND;
        notification.flags |= Notification.DEFAULT_LIGHTS;
        notification.flags |= Notification.DEFAULT_VIBRATE;

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis =  delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String eventName, String date, String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(eventName);
        builder.setContentText(date+"\n"+content);
        builder.setSmallIcon(R.drawable.logo);



        return builder.build();
    }
}

