package in.ac.nitrkl.innovisionr.Timeline;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by pawan on 10/22/2016.
 */

public class TimelinePager extends FragmentStatePagerAdapter {

    int tabCount;

    //Constructor to the class
    public TimelinePager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                TimelineTab1 timelineTab1 = new TimelineTab1();
                return timelineTab1;
            case 1:
                TimelineTab2 timelineTab2 = new TimelineTab2();
                return timelineTab2;
            case 2:
                TimelineTab3 timelineTab3 = new TimelineTab3();
                return timelineTab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
