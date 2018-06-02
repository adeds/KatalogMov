package com.noes.adeyds.subsmovie2.fragment;


import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.noes.adeyds.subsmovie2.R;
import com.noes.adeyds.subsmovie2.adapter.FavAdapter;


import static com.noes.adeyds.subsmovie2.database.DBContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favorites extends Fragment {

    private RecyclerView rcView;
    private ProgressBar progressBar;
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIES";
    private Cursor list;
    private FavAdapter favAdapter;

    public Favorites() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.fragment_favorites, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcView = view.findViewById(R.id.rv_search);
        progressBar = view.findViewById(R.id.progress);
        favAdapter = new FavAdapter(getContext());
     //   favAdapter.setListNotes(list);
        rcView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcView.setAdapter(favAdapter);
        new LoadFavAsync().execute();
    }


    private class LoadFavAsync extends AsyncTask<Void, Void, Cursor>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {

            return getActivity().getContentResolver().query(CONTENT_URI,null,null,null,null);
           // return getContent
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            progressBar.setVisibility(View.GONE);

            list = cursor;
            favAdapter.setListNotes(list);
            Log.e("cursorGet", String.valueOf(cursor.getCount()));
            favAdapter.notifyDataSetChanged();
        }
    }
}
