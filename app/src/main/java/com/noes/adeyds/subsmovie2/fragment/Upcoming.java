package com.noes.adeyds.subsmovie2.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.noes.adeyds.subsmovie2.adapter.MovieAdapter;
import com.noes.adeyds.subsmovie2.model.Movies;
import com.noes.adeyds.subsmovie2.asyncTask.MyAsync;
import com.noes.adeyds.subsmovie2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Upcoming extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Movies>> {
    private ListView listView;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private ArrayList<Movies> data;

    public Upcoming() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        data  = new ArrayList<>();
        Log.e("onCreate", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_upcoming, container, false);


        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.lv_upc);
        progressBar = view.findViewById(R.id.progress);
        movieAdapter = new MovieAdapter(getContext());
        listView.setAdapter(movieAdapter);
        progressBar = view.findViewById(R.id.progress);
        if (data.isEmpty()) {

            Bundle bundle = new Bundle();
            getLoaderManager().initLoader(0, bundle, this);

        } else {
            Log.e("onViewUpc", data.get(0).getJudul());
            movieAdapter.setData(data);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<Movies>> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new MyAsync(getActivity().getApplicationContext(), " ", "upc");
        //return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Movies>> loader, ArrayList<Movies> dataNew) {
        progressBar.setVisibility(View.GONE);
        data.addAll(dataNew);
        movieAdapter.setData(data);
        getLoaderManager().destroyLoader(0);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Movies>> loader) {
movieAdapter.setData(null);
    }


}
