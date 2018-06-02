package com.noes.adeyds.subsmovie2;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.noes.adeyds.subsmovie2.adapter.FragmentAdapter;
import com.noes.adeyds.subsmovie2.fragment.Favorites;
import com.noes.adeyds.subsmovie2.fragment.NowPlaying;
import com.noes.adeyds.subsmovie2.fragment.Upcoming;
import com.noes.adeyds.subsmovie2.notif.AlarmReceiver;
import com.noes.adeyds.subsmovie2.notif.SchedulerTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private AlarmReceiver alarmReceiver = new AlarmReceiver();
    private SchedulerTask schedulerTask;

    private Upcoming upcoming;
    private Favorites favorites;
    private NowPlaying nowPlaying;
    private int statusFrag = 0;
    private final String TAG_UPC = "UPCOMING";
    private final String TAG_NOWPLAYING = "NOW_PLAYING";
    private final String TAG_FAV = "FAVORITE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
        if (savedInstanceState==null){


            setFragment();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//




        alarmReceiver.setRepeatingAlarm(getApplicationContext(), alarmReceiver.TYPE_REPEATING, "07:00", getString(R.string.notif_title_daily));
        schedulerTask = new SchedulerTask(this);
        schedulerTask.createPeriodicTask();

    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (viewPager.getCurrentItem() == 0) {
//            getSupportFragmentManager().putFragment(outState, "NowPlaying", nowPlaying);
//        } else if (viewPager.getCurrentItem() == 1) {
//            getSupportFragmentManager().putFragment(outState, "Upcoming", upcoming);
//        } else if (viewPager.getCurrentItem() == 2) {
//            getSupportFragmentManager().putFragment(outState, "Favorites", favorites);
//        }
//    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//
//            nowPlaying = (NowPlaying) getSupportFragmentManager().getFragment(savedInstanceState, "NowPlaying");
//
//            upcoming = (Upcoming) getSupportFragmentManager().getFragment(savedInstanceState, "Upcoming");
//
//            favorites = (Favorites) getSupportFragmentManager().getFragment(savedInstanceState, "Favorites");
//
//         list = new ArrayList<>();
//        list.add(nowPlaying);
//        list.add(upcoming);
//        list.add(favorites);
//        viewPager = findViewById(R.id.vp_pager);
//        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), list));
//        viewPager.setOffscreenPageLimit(0);
//
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent = new Intent(getApplicationContext(), ResSearch.class);
                intent.putExtra(ResSearch.MOVIE_TITLE, query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_nowPlaying) {

            statusFrag = 0;

            //viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_upcoming) {
//            viewPager.setCurrentItem(1);

            statusFrag = 1;
        } else if (id == R.id.nav_search) {

            statusFrag = 2;
            //viewPager.setCurrentItem(2);
        }

        setFragment();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        upcoming = (Upcoming) fragmentManager.findFragmentByTag(TAG_UPC);
        nowPlaying = (NowPlaying) fragmentManager.findFragmentByTag(TAG_NOWPLAYING);
        favorites = (Favorites) fragmentManager.findFragmentByTag(TAG_FAV);
        Log.e("setFragment", String.valueOf(statusFrag));
        if (statusFrag == 1 && upcoming == null) {
            upcoming = new Upcoming();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, upcoming, TAG_UPC).commit();
        } else if (statusFrag == 0 && nowPlaying == null) {
            nowPlaying = new NowPlaying();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, nowPlaying, TAG_NOWPLAYING).commit();
        } else if (statusFrag == 2 && favorites == null) {
            favorites = new Favorites();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, favorites, TAG_FAV).commit();
        }

    }


}


