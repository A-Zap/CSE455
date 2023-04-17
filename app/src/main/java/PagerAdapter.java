import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.captain.schedit.MainActivity;
import com.example.captain.schedit.Tab1;
import com.example.captain.schedit.Tab2;
import com.example.captain.schedit.Tab3;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by saavf on 10/23/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int mNoOfTabs;

    private Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;
    private Context mContext;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs, Context context) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;

        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;
            case 2:
                Tab3 tab3 = new Tab3();
                return tab3;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            // record the fragment tag here.
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position, tag);
        }
        return obj;
    }


    public Fragment getFragment(int position) {
        String tag = mFragmentTags.get(position);
        if (tag == null)
            return null;
        return mFragmentManager.findFragmentByTag(tag);
    }

}