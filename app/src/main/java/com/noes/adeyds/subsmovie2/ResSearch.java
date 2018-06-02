package com.noes.adeyds.subsmovie2;

import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.noes.adeyds.subsmovie2.adapter.MovieAdapter;
import com.noes.adeyds.subsmovie2.asyncTask.MyAsync;
import com.noes.adeyds.subsmovie2.model.Movies;

import java.util.ArrayList;

public class ResSearch extends AppCompatActivity implements
        android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Movies>> {

    public static final String MOVIE_TITLE = "movie_title";
    private ListView listView;
    MovieAdapter movieAdapter;
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIES";
    String movie_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_search);
        movieAdapter = new MovieAdapter(this);
        movieAdapter.notifyDataSetChanged();
        listView = findViewById(R.id.lv_search);
        listView.setAdapter(movieAdapter);
        movie_title = getIntent().getStringExtra(MOVIE_TITLE);
        Log.e("onCreate", movie_title);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE,movie_title);
        getSupportLoaderManager().initLoader(2, bundle,this);
    }


    @Override
    public Loader<ArrayList<Movies>> onCreateLoader(int id, Bundle args) {
        Log.e("onLoader", movie_title);
        return new MyAsync(this, movie_title, "search");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movies>> loader, ArrayList<Movies> data) {
        movieAdapter.setData(data);
        getLoaderManager().destroyLoader(2);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movies>> loader) {
        movieAdapter.setData(null);
    }
}
