package com.noes.adeyds.favoritemovies.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.noes.adeyds.favoritemovies.R;
import com.noes.adeyds.favoritemovies.entity.FavoriteModel;

import java.util.concurrent.ExecutionException;

import static com.noes.adeyds.favoritemovies.api.TMDB.POSTER_IMAGE;
import static com.noes.adeyds.favoritemovies.database.DBContract.CONTENT_URI;


class StackRemoteViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private int mAppWidgetId;
    private Context context;
    private Cursor list;
    private TextView textView;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


    }

    public void onCreate() {
        list = context.getContentResolver().query(
            CONTENT_URI,
            null,
            null,
            null,
            null
    );



    }





    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
    Log.e("widgetCount",String.valueOf(list.getCount()));
        return list.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        FavoriteModel item = getItem(position);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .load(POSTER_IMAGE + item.getBackdrop())
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;



    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private FavoriteModel getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new FavoriteModel(list);
    }

}