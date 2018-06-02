package com.noes.adeyds.subsmovie2.asyncTask;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.noes.adeyds.subsmovie2.api.TMDB;
import com.noes.adeyds.subsmovie2.model.Movies;
import com.noes.adeyds.subsmovie2.prefs.AppPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by adeyds on 2/8/2018.
 */

public class MyAsync extends android.support.v4.content.AsyncTaskLoader<ArrayList<Movies>> {
    private ArrayList<Movies> mData = new ArrayList<>();
    private boolean mHasResult = false;
    private String url;
    private AppPreference appPreference ;
    // private static final String API_KEY = "54bf88dddf43e25d368a0eb606e92660";

    public MyAsync(Context context, String mKumlpulSearch, String access) {
        super(context);
        onContentChanged();
        appPreference = new AppPreference(context);
        Log.e("DATA",access);
        switch (access) {
            case "search":
                this.url = TMDB.SEARCH_API + mKumlpulSearch;
                break;
            case "nowplaying" :
                this.url = TMDB.NOW_PLAYING;
                break;
            case "upc" :
                this.url = TMDB.UP_COMING;
                break;
        }

    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        } else if (mHasResult) {
            deliverResult(mData);
        }

    }

    @Override
    public void deliverResult(ArrayList<Movies> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        Log.e("restar","reset");
        if (mHasResult) {
            //onReleaseResources(mData);
            mData = null;
            mHasResult = false;

        }
    }

    @Override
    public ArrayList<Movies> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        //final ArrayList<Movies> movies = new ArrayList<>();
        //String url = TMDB.SEARCH_API+cariFilm;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject respon = new JSONObject(result);
                    JSONArray list = respon.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movsend = list.getJSONObject(i);
                        Movies movies1 = new Movies(movsend);
                        mData.add(movies1);
                        if (url.equals(TMDB.UP_COMING) && i==0){
                        appPreference.setIdMov(movies1.getId());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.getMessage();


            }
        });

        return mData;
    }

    protected void onReleaseResources(ArrayList<Movies> data) {
        //nothing to do.
    }
}
