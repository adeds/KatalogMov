package com.noes.adeyds.subsmovie2.converter;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adeyds on 3/4/2018.
 */

public class convertDate {
    public static String convertDate(String old){
        String baru = null;
        Date date = null;
        SimpleDateFormat sdfOld = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d, yyyy");

        try {
        date = sdfOld.parse(old);
        }catch (ParseException e){
            Log.e("date/parse error",e.getMessage());
        }
        if (date!=null){
            baru = sdf.format(date).toString().trim();
        }

        return baru ;
    }

}
