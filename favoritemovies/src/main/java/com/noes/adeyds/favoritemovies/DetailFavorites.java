package com.noes.adeyds.favoritemovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.noes.adeyds.favoritemovies.api.TMDB;
import com.noes.adeyds.favoritemovies.asyncTask.AsyncReview;
import com.noes.adeyds.favoritemovies.entity.FavoriteModel;

import static android.provider.BaseColumns._ID;
import static com.noes.adeyds.favoritemovies.api.TMDB.POSTER_IMAGE;
import static com.noes.adeyds.favoritemovies.database.DBContract.CONTENT_URI;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.BACKDROP_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.DATE_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.DESC_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.ID_MOVIE;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.JUDUL_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.POP_KOLOM;
import static com.noes.adeyds.favoritemovies.database.DBContract.FavKolom.POSTER_KOLOM;

public class DetailFavorites extends AppCompatActivity {
    public static TextView dtlReview, labelRev;
    public static ProgressBar mubeng;
    private TextView dtlJudul, dtlDate, dtlDesc, dtlPopular, tvFav;
    private ImageView imgPoster, imgBackdrop, icFav;
    private LinearLayout contFav;
    private FavoriteModel favoriteModel;

    AsyncReview gRev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorites);
        dtlReview =findViewById(R.id.dtl_review);
        labelRev = findViewById(R.id.labelRev);
        mubeng =findViewById(R.id.progress);
        mubeng.setVisibility(View.VISIBLE);
        dtlJudul =  findViewById(R.id.dtl_judul);
        dtlDate =  findViewById(R.id.dtl_date);
        dtlDesc =  findViewById(R.id.dtl_desc);
        dtlPopular = findViewById(R.id.dtl_popular);
        imgBackdrop = findViewById(R.id.dtl_backdrop);
        imgPoster =  findViewById(R.id.dtl_poster);
        icFav = findViewById(R.id.ic_fav);
        tvFav =  findViewById(R.id.tv_fav);
        contFav =  findViewById(R.id.contFav);

      loadData();
      storeData();


       // contFav.setOnClickListener(new tapFav());
        contFav.setVisibility(View.GONE);
    }

    private void storeData() {
        if (favoriteModel== null) return;

        Glide.with(this)
                .load(POSTER_IMAGE + favoriteModel.getPoster())
                .into(imgPoster);
        Glide.with(this)
                .load(POSTER_IMAGE + favoriteModel.getBackdrop())
                .into(imgPoster);
        dtlJudul.setText(favoriteModel.getJudul());
        dtlDate.setText(favoriteModel.getDate());
        dtlDesc.setText(favoriteModel.getDesc());
        dtlPopular.setText(String.valueOf(favoriteModel.getPopularity()));
        gRev = new AsyncReview(String.valueOf(favoriteModel.getId()));
        gRev.execute();



    }

    private void loadData() {
        Uri uri = getIntent().getData();
        if (uri == null) return;
        Cursor cursor = getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) favoriteModel = new FavoriteModel(cursor);
            cursor.close();
        }
    }

//    private class tapFav implements View.OnClickListener {
//            if (isFav){
//            icFav.setImageResource(R.drawable.ic_star_border);
//            tvFav.setText(R.string.untapFav);
//            isFav = false;
//           // delFav();
//        }else{
//            icFav.setImageResource(R.drawable.ic_star);
//            tvFav.setText(R.string.tapFav);
//            isFav = true;
//
//
//        }
//    }




}
