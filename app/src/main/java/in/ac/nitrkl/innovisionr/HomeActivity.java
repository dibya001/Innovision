package in.ac.nitrkl.innovisionr;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import in.ac.nitrkl.innovisionr.Corousel.CirclePageIndicator;
import in.ac.nitrkl.innovisionr.Timeline.TimelineActivity;

public class HomeActivity extends AppCompatActivity  implements ConnectivityReceiver.ConnectivityReceiverListener, View.OnClickListener {

    private ImageView iv1,iv2,iv3,iv4,iv5,iv6;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    ImageView bm,tl,team;
    private static final float INITIAL_ITEMS_COUNT = 3F;

    SharedPreferences sp;
    /**
     * Carousel container layout
     */
    private LinearLayout mCarouselContainer;
    SQLiteDatabase db;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icon);

        sp = getSharedPreferences("demo_file", MODE_PRIVATE);
        edit = sp.edit();

        bm = (ImageView) findViewById(R.id.bookmark);
        tl = (ImageView) findViewById(R.id.timeline);
        team = (ImageView) findViewById(R.id.team);

        bm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this, BookmarkActivity.class);
                startActivity(i);
            }
        });
        tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, TimelineActivity.class);
                startActivity(i);
            }
        });
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, TeamActivity.class);
                startActivity(i);
            }
        });


        //   checkConnection();
       /* db=openOrCreateDatabase("bookmark",MODE_APPEND,null);
        if(sp.getString("table","no").equals("no"))
        {
            String q="create table if not exists bookmarktable (title varchar,date varchar,time varchar)";
            db.execSQL(q);
            edit.putString("table","yes");
            edit.commit();
        }*/

        // Compute the width of a carousel item based on the screen width and number of initial items.
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int imageWidth = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT);
        final int imageHeight = (int) imageWidth;

        ImageView iv1 = (ImageView) findViewById(R.id.iv1);
        ImageView iv2 = (ImageView) findViewById(R.id.iv2);
        ImageView iv3 = (ImageView) findViewById(R.id.iv3);
        ImageView iv4 = (ImageView) findViewById(R.id.iv4);
        ImageView iv5 = (ImageView) findViewById(R.id.iv5);
        ImageView iv6 = (ImageView) findViewById(R.id.iv6);

        iv1.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageHeight));
        iv2.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageHeight));
        iv3.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageHeight));
        iv4.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageHeight));
        iv5.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageHeight));
        iv6.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageHeight));

        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);
        iv6.setOnClickListener(this);


        showSnack(ConnectivityReceiver.isConnected());
        init();

        //mCarouselContainer = (LinearLayout) findViewById(R.id.carousel);
        // Get the array of puppy resources
        //final TypedArray puppyResourcesTypedArray = getResources().obtainTypedArray(R.array.puppies_array);
        // final TypedArray imageViewId = getResources().obtainTypedArray(R.array.click_id);
        // Populate the carousel with items
        // ImageView imageItem;

        //Log.d("lala",""+puppyResourcesTypedArray.length());
        /*for (int i = 0 ; i < puppyResourcesTypedArray.length() ; ++i) {
            // Create new ImageView
            imageItem = new ImageView(this);
            imageItem.setId(i+1);
            // Set the shadow background
            imageItem.setBackgroundResource(R.drawable.shadow);

            // Set the image view resource
            imageItem.setImageResource(puppyResourcesTypedArray.getResourceId(i, -1));
            imageItem.setScaleType(ImageView.ScaleType.FIT_XY);
            // Set the size of the image view to the previously computed value
            imageItem.setLayoutParams(new LinearLayout.LayoutParams(imageWidth,imageHeight));

            imageItem.setOnClickListener(click_listener);

            /// Add image view to the carousel container
            mCarouselContainer.addView(imageItem);
        }*/


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId){
                    case R.id.tab_home:

                        break;
                    case R.id.tab_timeline:
                        startActivity(new Intent(HomeActivity.this,TimelineActivity.class));
                        break;
                    case R.id.tab_bookmark:
                        startActivity(new Intent(HomeActivity.this,BookmarkActivity.class));
                        break;
                    case R.id.tab_team:
                        startActivity(new Intent(HomeActivity.this,TeamActivity.class));
                        break;
                }
            }
        });*/
    }

    private void init() {
        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(HomeActivity.this, ImagesArray));
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES = IMAGES.length;
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_register) {
            if(sp.getString("userid","-1").equals("-1"))
            {
                startActivity(new Intent(this,LoginActivity.class));

            }
            else
            {
                Toast.makeText(this,"You are already registered",Toast.LENGTH_LONG).show();
            }
            return true;
        }
        else if(id==R.id.map)
        {
            startActivity(new Intent(this,MapActivity.class));
        }

        return super.onOptionsItemSelected(item);
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
            AlertDialog.Builder ab=new AlertDialog.Builder(HomeActivity.this);
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

    @Override
    public void onClick(View v) {
        int id = (int) v.getId();
        switch (id) {
            case R.id.iv1:
                Intent i = new Intent(getApplicationContext(), GridEventActivity.class);
                i.putExtra("category", "event");
                startActivity(i);
                break;
            case R.id.iv2:
                Intent j = new Intent(getApplicationContext(), GridEventActivity.class);
                j.putExtra("category", "exhibition");
                startActivity(j);
                break;
            case R.id.iv3:
                Intent k = new Intent(getApplicationContext(), GridEventActivity.class);
                k.putExtra("category", "flagship");
                startActivity(k);
                break;
            case R.id.iv4:
                Intent l = new Intent(getApplicationContext(), GridEventActivity.class);
                l.putExtra("category", "workshop");
                startActivity(l);
                break;
            case R.id.iv5:
                Intent m = new Intent(getApplicationContext(), GridEventActivity.class);
                m.putExtra("category", "guest");
                startActivity(m);
                break;
            case R.id.iv6:
                Intent n = new Intent(getApplicationContext(), GridEventActivity.class);
                n.putExtra("category", "proshow");
                startActivity(n);
                break;
        }
    }
}
