package com.noes.adeyds.favoritemovies;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.noes.adeyds.favoritemovies.adapter.FavMovAdapter;

import static android.provider.BaseColumns._ID;
import static com.noes.adeyds.favoritemovies.database.DBContract.CONTENT_URI;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.BACKDROP_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.DATE_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.DESC_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.ID_MOVIE;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.JUDUL_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.POP_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.POSTER_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.getColumnInt;
import static com.noes.adeyds.favoritemovies.database.DBContract.getColumnString;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvFav;
    private FavMovAdapter favMovAdapter;
    private Cursor list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favMovAdapter = new FavMovAdapter(list);

        rvFav = findViewById(R.id.rv_fav);

        rvFav.setLayoutManager(new LinearLayoutManager(this));
        rvFav.addItemDecoration(new DividerItemDecoration(rvFav.getContext(), DividerItemDecoration.VERTICAL));
        rvFav.setAdapter(favMovAdapter);
        new load().execute();

    }

    private class load extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            list = cursor;
            Log.e("favCursor", String.valueOf(cursor.getCount()));
            favMovAdapter.replaceAll(list);
        }
    }
}