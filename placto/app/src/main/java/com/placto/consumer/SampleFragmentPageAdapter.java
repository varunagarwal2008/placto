package com.placto.consumer;

import android.app.FragmentManager;
import android.content.Context;
import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;


/**
 * Created by User on 1/3/2017.
 */

public class SampleFragmentPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Tab1", "Search"};
    private Context context;
    public SampleFragmentPageAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }
    @Override
    public Fragment getItem(int position) {
        return Fragmentsearch.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
