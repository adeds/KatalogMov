package com.noes.adeyds.subsmovie2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.noes.adeyds.subsmovie2.fragment.NowPlaying;
import com.noes.adeyds.subsmovie2.fragment.Favorites;
import com.noes.adeyds.subsmovie2.fragment.Upcoming;

import java.util.ArrayList;

/**
 * Created by adeyds on 2/7/2018.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    int NUM_FRAGMENT = 3 ;
    private ArrayList<Fragment> list;
    public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list ;
    }

    @Override
    public Fragment getItem(int position) {
       return list.get(position);
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENT;
    }
}
