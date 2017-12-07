package com.example.hirshagarwal.songle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by Hirsh Agarwal on 12/7/2017.
 */

public class ScoresAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;

    public ScoresAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public Fragment getItem(int position){

        switch (position){
            case 0:
                TabFragment1 tab1 = new TabFragment1();
                return tab1;
            case 1:
                TabFragment2 tab2 = new TabFragment2();
                return tab2;
            default:
                Log.e("Tab Fragment", "Index out of bounds");
                return null;
        }
    }

    public int getCount(){
        return mNumOfTabs;
    }
}
