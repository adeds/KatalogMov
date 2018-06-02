package com.noes.adeyds.favoritemovies.asyncTask;

import android.os.AsyncTask;
import android.view.View;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.noes.adeyds.favoritemovies.DetailFavorites;
import com.noes.adeyds.favoritemovies.api.TMDB;


import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by adeyds on 2/8/2018.
 */

public class AsyncReview extends AsyncTask<String, String, String> {
    private String review;
    private String url ;
    public AsyncReview(String id) {

        url = TMDB.REV1+id+ TMDB.REV2;


    }

    @Override
    protected String doInBackground(String... strings) {


        SyncHttpClient client = new SyncHttpClient();
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
                    JSONObject movsend = list.getJSONObject(0);
                    review = movsend.getString("content");


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.getMessage();
            }
        });


        return review;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (review!=null) {
            DetailFavorites.dtlReview.setText(review);
        }else {
            DetailFavorites.dtlReview.setText(review);
            DetailFavorites.labelRev.setText(null);
        }
        DetailFavorites.mubeng.setVisibility(View.GONE);
    }
}
