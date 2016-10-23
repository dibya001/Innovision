package in.ac.nitrkl.innovisionr.Timeline;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.ac.nitrkl.innovisionr.*;
import in.ac.nitrkl.innovisionr.R;

//Implementing the interface OnTabSelectedListener to our TimelineActivity
//This interface would help in swiping views
public class TimelineActivity extends AppCompatActivity {

    //This is our tablayout
    private TabLayout tabLayout;
    TextView share;
    public static ArrayList<Timeline> list1, list2, list3;

    //This is our viewPager
    private ViewPager viewPager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        share= (TextView) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "AndroidSolved");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Now Learn Android with AndroidSolved clicke here to visit https://androidsolved.wordpress.com/ ");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        setcontent();
        //Initializing viewPager

        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TimelineTab1(), "4th Nov");
        adapter.addFragment(new TimelineTab2(), "5th Nov");
        adapter.addFragment(new TimelineTab3(), "6th Nov");

        viewPager.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void setcontent() {

        String url = "http://192.168.43.103/android/fetch_all_events.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        //Toast.makeText(ShowEvents.this, response, Toast.LENGTH_SHORT).show();
                        try {


                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = jsonArray.getJSONObject(0);

                            for(int i=0;i<jsonArray.length();i++)
                            {
                                Timeline t=new Timeline();
                                obj = jsonArray.getJSONObject(i);
                               t.setEventName(obj.getString("title"));
                                t.setEventType(obj.getString("category"));
                                t.setTime(obj.getString("time"));
                                t.setEid(obj.getString("eid"));
                              //  list1.add(t); list2.add(t); list3.add(t);

                                String tmp=obj.getString("date");
                                if(tmp.charAt(0)=='4')
                                {
                                    list1.add(t);
                                    if(tmp.contains("5"))
                                        list2.add(t);
                                    if(tmp.contains("6"))
                                    {
                                        list2.add(t);
                                        list3.add(t);

                                    }
                                }
                                else if(tmp.charAt(0)=='5')
                                {
                                    list2.add(t);
                                    if(tmp.contains("6"))
                                    {
                                        list3.add(t);

                                    }
                                }
                                else if(tmp.charAt(0)=='6')
                                {
                                    list3.add(t);
                                }
                                else
                                    list1.add(t);




                            }


                            Log.i("eee", response);
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

                return params;
            }
        };
        MySingleTon.getInstance(TimelineActivity.this).addtoRequestQueue(stringRequest);


    }
}