package in.ac.nitrkl.innovisionr.Timeline;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import in.ac.nitrkl.innovisionr.MySingleTon;
import in.ac.nitrkl.innovisionr.R;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class TimelineTab1 extends android.support.v4.app.Fragment {

    LinearLayoutManager lLayout;
    RecyclerView allEvents;
    TimelineRecyclerViewAdapter rcAdapter;
    ArrayList<Timeline> list1;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.timeline_tab1, container, false);
        allEvents= (RecyclerView) v.findViewById(R.id.timelineTab1);

        list1=new ArrayList<>();
        sp=getActivity().getSharedPreferences("demo_file", Context.MODE_PRIVATE);
        edit=sp.edit();

        lLayout = new LinearLayoutManager(getActivity());

        //allEvents.setHasFixedSize(true);

        allEvents.setLayoutManager(lLayout);

        rcAdapter = new TimelineRecyclerViewAdapter(getActivity(), list1);


        allEvents.setAdapter(rcAdapter);




        func();

        /*if(list1.size()>TimelineActivity.size) {
            TimelineActivity.size = list1.size();
            rcAdapter.notifyDataSetChanged();
        }*/
        return v;
    }


    private void setcontent() {

        String url = "http://innovision.nitrkl.ac.in/android/fetch_all_events.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {

                        TimelineActivity.response=response;
                        edit.putString("taball",response);
                        edit.commit();
                        try {
                            json(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {

                TimelineActivity.response=sp.getString("taball","abc");
                func();

                Log.i("eeee", error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        MySingleTon.getInstance(getActivity()).addtoRequestQueue(stringRequest);


    }

    private void json(String response) throws JSONException {

        list1.clear();
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
                rcAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
        func();
    }

    private void func() {

        if(TimelineActivity.response.length()<10)
            setcontent();

        else
            try {
                json(TimelineActivity.response);
            } catch (JSONException e) {
                e.printStackTrace();
            }


    }
}