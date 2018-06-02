package com.noes.adeyds.favoritemovies.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.BACKDROP_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.DATE_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.DESC_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.ID_MOVIE;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.JUDUL_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.POP_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.POSTER_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.getColumnInt;
import static com.noes.adeyds.favoritemovies.database.DBContract.getColumnString;


/**
 * Created by adeyds on 3/4/2018.
 */

public class FavoriteModel implements Parcelable {
    private int id, popularity;
    private String judul;
    private String poster;
    private String desc;
    private String date;
    private String backdrop;

    public static final Creator<FavoriteModel> CREATOR = new Creator<FavoriteModel>() {
        @Override
        public FavoriteModel createFromParcel(Parcel in) {
            return new FavoriteModel(in);
        }

        @Override
        public FavoriteModel[] newArray(int size) {
            return new FavoriteModel[size];
        }
    };

    public FavoriteModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.popularity);
        dest.writeString(this.judul);
        dest.writeString(this.poster);
        dest.writeString(this.desc);
        dest.writeString(this.date);
        dest.writeString(this.backdrop);

    }

    protected FavoriteModel(Parcel in){
        this.id = in.readInt();
        this.popularity= in.readInt();
        this.judul= in.readString();
        this.poster= in.readString();
        this.desc= in.readString();
        this.date= in.readString();
        this.backdrop= in.readString();
    }

    public FavoriteModel(Cursor cursor){
        this.id = getColumnInt(cursor, ID_MOVIE);
        this.popularity= getColumnInt(cursor, POP_KOLOM);
        this.judul=getColumnString(cursor, JUDUL_KOLOM);
        this.poster= getColumnString(cursor, POSTER_KOLOM);
        this.desc= getColumnString(cursor, DESC_KOLOM);
        this.date= getColumnString(cursor, DATE_KOLOM);
        this.backdrop= getColumnString(cursor, BACKDROP_KOLOM);
    }
}
