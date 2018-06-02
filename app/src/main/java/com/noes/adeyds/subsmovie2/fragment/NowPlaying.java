package com.noes.adeyds.subsmovie2.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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
public class NowPlaying extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movies>> {
private ListView listView;
MovieAdapter movieAdapter;
private ProgressBar progressBar;
private ArrayList<Movies> data;

    public NowPlaying() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        data  = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_now_playing,container,false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.lv_np);
        movieAdapter = new MovieAdapter(getContext());
        listView.setAdapter(movieAdapter);
        progressBar = view.findViewById(R.id.progress);
        if (data.isEmpty()){

            Bundle bundle = new Bundle();
            getLoaderManager().initLoader(1,bundle, this);

        }else{

            movieAdapter.setData(data);
            progressBar.setVisibility(View.GONE);
        }

//
//        if (savedInstanceState!=null){
//            Log.e("saved", "true");
//            data= savedInstanceState.getParcelableArrayList(KEY_INSTANCE);
//            movieAdapter.setData((ArrayList<Movies>) data);
//
//        }else {
//
//        }




    }

    @Override
    public Loader<ArrayList<Movies>> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new MyAsync(getActivity().getApplicationContext(), " ", "nowplaying");
        //return  null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movies>> loader, ArrayList<Movies> dataNew) {
        progressBar.setVisibility(View.GONE);
        data.addAll(dataNew);
    movieAdapter.setData(data);
    getLoaderManager().destroyLoader(1);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movies>> loader) {
    movieAdapter.setData(null);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("data","hide");
    }




}
