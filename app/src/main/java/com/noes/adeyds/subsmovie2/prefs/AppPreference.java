package com.noes.adeyds.subsmovie2.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by adeyds on 2/19/2018.
 */

public class AppPreference {
    SharedPreferences prefs;
    Context context;

    public AppPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setIdMov(int input){

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("ID_MOV", input);
        editor.commit();
    }

    public int getIdMov(){
        return prefs.getInt("ID_MOV", 0);
    }
}
