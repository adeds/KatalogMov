/*
 * Created by omrobbie.
 * Copyright (c) 2018. All rights reserved.
 * Last modified 11/11/17 3:14 PM.
 */

package com.noes.adeyds.subsmovie2.notif;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.noes.adeyds.subsmovie2.DetailActivity;
import com.noes.adeyds.subsmovie2.R;
import com.noes.adeyds.subsmovie2.api.TMDB;
import com.noes.adeyds.subsmovie2.model.Movies;
import com.noes.adeyds.subsmovie2.prefs.AppPreference;


import org.json.JSONArray;
import org.json.JSONObject;


import cz.msebera.android.httpclient.Header;


public class SchedulerService extends GcmTaskService {

    public static String TAG_TASK_UPCOMING = "upcoming movies";
    private final int NOTIF_ID_UPC = 201;
    private AppPreference appPreference ;

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_UPCOMING)) {
            loadData();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }

        return result;
    }

    private void loadData() {
        SyncHttpClient client = new SyncHttpClient();
        client.get(TMDB.UP_COMING, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
                appPreference = new AppPreference(getApplicationContext());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject respon = new JSONObject(result);
                    JSONArray list = respon.getJSONArray("results");
                    JSONObject respon2 = list.getJSONObject(0);
                    int id = respon2.getInt("id");
                    String judul = respon2.getString("title");
                    Log.e("upcFirst", String.valueOf(id));
                    if (id != appPreference.getIdMov()) {

                        showNotification(getApplicationContext(), getResources().getString(R.string.notif_title),
                                getResources().getString(R.string.notif_title_new), NOTIF_ID_UPC, judul);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.e("errUPC", error.getMessage());
            loadFailed();
            }
        });
    }

    private void loadFailed() {
        Toast.makeText(this, "Gagal meload data", Toast.LENGTH_SHORT).show();
    }

    private void showNotification(Context context, String title, String message, int notifId, String item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("MovieNotif", item);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifi)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}
