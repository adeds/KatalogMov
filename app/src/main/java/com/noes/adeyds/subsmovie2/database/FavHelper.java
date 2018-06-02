package com.noes.adeyds.subsmovie2.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.noes.adeyds.subsmovie2.database.DBContract.FavKolom.ID_MOVIE;
import static com.noes.adeyds.subsmovie2.database.DBContract.TABLE_FAV;

/**
 * Created by adeyds on 3/4/2018.
 */

public class FavHelper {

    private static String DATABASE_TABLE = TABLE_FAV;
    private Context context;
    private DBHelper dataBaseHelper;
    private SQLiteDatabase database;

public FavHelper(Context context){
    this.context =context;
    }

    public FavHelper open() throws SQLException{
        dataBaseHelper = new DBHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dataBaseHelper.close();
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , ID_MOVIE + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public  int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}