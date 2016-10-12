package in.ac.nitrkl.innovisionr;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    private static final float INITIAL_ITEMS_COUNT = 1.5F;

    /**
     * Carousel container layout
     */
    private LinearLayout mCarouselContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mCarouselContainer = (LinearLayout) findViewById(R.id.carousel);

        // Compute the width of a carousel item based on the screen width and number of initial items.
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int imageWidth = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT);
        final int imageHeight= (int) (imageWidth*0.6);
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
            imageItem.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // Set the size of the image view to the previously computed value
            imageItem.setLayoutParams(new LinearLayout.LayoutParams(imageWidth,imageHeight));

            /// Add image view to the carousel container
            mCarouselContainer.addView(imageItem);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
