package com.noes.adeyds.subsmovie2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adeyds on 3/4/2018.
 */


public class DBHelper extends SQLiteOpenHelper {

    public static int DB_VERSION = 1;
    public static String DB_NAME = "MovieCatalog";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table " + DBContract.TABLE_FAV + " (" +
                DBContract.FavKolom._ID+ " integer primary key autoincrement, " +
                DBContract.FavKolom.ID_MOVIE+ " integer, " +
                DBContract.FavKolom.JUDUL_KOLOM + " text not null, " +
                DBContract.FavKolom.DESC_KOLOM + " text not null, " +
                DBContract.FavKolom.POP_KOLOM + " text not null, " +
                DBContract.FavKolom.DATE_KOLOM + " text not null, " +
                DBContract.FavKolom.POSTER_KOLOM + " text not null, " +
                DBContract.FavKolom.BACKDROP_KOLOM + " text not null );";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.TABLE_FAV);
        onCreate(db);
    }
}

