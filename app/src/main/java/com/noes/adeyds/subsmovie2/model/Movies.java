package com.noes.adeyds.subsmovie2.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by adeyds on 2/8/2018.
 */

public class Movies  {
    private int id, popularity;
    private String judul;
    private String poster;
    private String desc;
    private String date;
    private String backdrop;



    public Movies(JSONObject object){
        try {
            int id = object.getInt("id");
            int pop= object.getInt("popularity");
            String judul = object.getString("title");
            String overview = object.getString("overview");
            String releaseDate = object.getString("release_date");
            String poster = object.getString("poster_path");
            String backdrop = object.getString("backdrop_path");

            this.id = id;
            this.popularity = pop;
            this.judul = judul;
            this.desc = overview;
            this.date = releaseDate;
            this.poster = poster;
            this.backdrop = backdrop;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Movies(Parcel in) {
        id = in.readInt();
        popularity = in.readInt();
        judul = in.readString();
        poster = in.readString();
        desc = in.readString();
        date = in.readString();
        backdrop = in.readString();
    }



    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }


    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


}
