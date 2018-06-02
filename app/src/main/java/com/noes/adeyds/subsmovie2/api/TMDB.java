package com.noes.adeyds.subsmovie2.api;

import com.noes.adeyds.subsmovie2.BuildConfig;

/**
 * Created by adeyds on 1/2/2018.
 */

public class TMDB {

    public static final String LIST_API= "https://api.themoviedb.org/3/movie/now_playing?api_key="+ BuildConfig.API_KEY +"&language=id-ID&page=1&region=ID";
    //title,overview,release_date
    public static final String POSTER_IMAGE="http://image.tmdb.org/t/p/w154";
    public static final String SEARCH_API="https://api.themoviedb.org/3/search/movie?api_key="+ BuildConfig.API_KEY +"&language=en-US&query=";
    public static final String NOW_PLAYING="https://api.themoviedb.org/3/movie/now_playing?api_key="+ BuildConfig.API_KEY +"&language=en-US&page=1";
    public static final String UP_COMING = "https://api.themoviedb.org/3/movie/upcoming?api_key="+ BuildConfig.API_KEY +"&language=en-US&page=1";
    public static final String REV1 = "https://api.themoviedb.org/3/movie/";
    public static final String REV2= "/reviews?api_key=54bf88dddf43e25d368a0eb606e92660&language=en-US&page=1";


}
