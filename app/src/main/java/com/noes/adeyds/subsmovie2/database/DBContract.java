package com.noes.adeyds.subsmovie2.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.widget.BaseAdapter;

/**
 * Created by adeyds on 3/4/2018.
 */

public class DBContract {
    public static String TABLE_FAV = "favorit";
    public static final String AUTHORITY = "com.noes.adeyds.subsmovie2";

    public static final class FavKolom implements BaseColumns{
        public static String ID_MOVIE= "id_movies";
        public static String POP_KOLOM = "pop";
        public static String JUDUL_KOLOM = "judul";
        public static String DESC_KOLOM = "descr";
        public static String DATE_KOLOM = "releaseDate";
        public static String POSTER_KOLOM = "poster";
        public static String BACKDROP_KOLOM = "backdrop";
    }

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAV)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

}
