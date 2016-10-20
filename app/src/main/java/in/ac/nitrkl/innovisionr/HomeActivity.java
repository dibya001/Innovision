package in.ac.nitrkl.innovisionr;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import in.ac.nitrkl.innovisionr.Corousel.CirclePageIndicator;
public class HomeActivity extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.five};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    private static final float INITIAL_ITEMS_COUNT = 1.5F;

    /**
     * Carousel container layout
     */
    private LinearLayout mCarouselContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        mCarouselContainer = (LinearLayout) findViewById(R.id.carousel);

        // Compute the width of a carousel item based on the screen width and number of initial items.
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int imageWidth = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT);
        final int imageHeight= (int) (imageWidth*0.6);
        // Get the array of puppy resources
        final TypedArray puppyResourcesTypedArray = getResources().obtainTypedArray(R.array.puppies_array);
       // final TypedArray imageViewId = getResources().obtainTypedArray(R.array.click_id);
        // Populate the carousel with items
        ImageView imageItem;

        View.OnClickListener click_listener = new View.OnClickListener() {
            public void onClick(View v) {
                // do something when the button is clicked
                int id = (int) v.getId();
                switch (id) {
                    case 1:
                        Intent i=  new Intent(getApplicationContext(),GridEventActivity.class);
                        i.putExtra("category","event");
                        startActivity(i);
                        break;
                    case 2:
                        Intent j=  new Intent(getApplicationContext(),GridEventActivity.class);
                        j.putExtra("category","event");
                        startActivity(j);
                        break;
                    case 3:
                        Intent k=  new Intent(getApplicationContext(),GridEventActivity.class);
                        k.putExtra("category","event");
                        startActivity(k);
                        break;
                    case 4:
                        Intent l=  new Intent(getApplicationContext(),GridEventActivity.class);
                        l.putExtra("category","event");
                        startActivity(l);
                        break;
                }

            }
        };
        for (int i = 0 ; i < puppyResourcesTypedArray.length() ; ++i) {
            // Create new ImageView
            imageItem = new ImageView(this);
            imageItem.setId(i+1);
            // Set the shadow background
            imageItem.setBackgroundResource(R.drawable.shadow);

            // Set the image view resource
            imageItem.setImageResource(puppyResourcesTypedArray.getResourceId(i, -1));
            imageItem.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // Set the size of the image view to the previously computed value
            imageItem.setLayoutParams(new LinearLayout.LayoutParams(imageWidth,imageHeight));

            imageItem.setOnClickListener(click_listener);

            /// Add image view to the carousel container
            mCarouselContainer.addView(imageItem);
        }


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favorites) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                }
            }
        });


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
   /* @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Compute the width of a carousel item based on the screen width and number of initial items.
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int imageWidth = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT);

        // Get the array of puppy resources
        final TypedArray puppyResourcesTypedArray = getResources().obtainTypedArray(R.array.puppies_array);

        // Populate the carousel with items
        ImageView imageItem;
        for (int i = 0 ; i < puppyResourcesTypedArray.length() ; ++i) {
            // Create new ImageView
            imageItem = new ImageView(this);

            // Set the shadow background
            imageItem.setBackgroundResource(R.drawable.shadow);

            // Set the image view resource
            imageItem.setImageResource(puppyResourcesTypedArray.getResourceId(i, -1));

            // Set the size of the image view to the previously computed value
            imageItem.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageWidth));

            /// Add image view to the carousel container
            mCarouselContainer.addView(imageItem);
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
