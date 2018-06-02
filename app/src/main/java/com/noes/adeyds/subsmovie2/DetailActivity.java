package com.noes.adeyds.subsmovie2;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
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
import com.noes.adeyds.subsmovie2.api.TMDB;
import com.noes.adeyds.subsmovie2.asyncTask.AsyncReview;
import com.noes.adeyds.subsmovie2.converter.convertDate;
import com.noes.adeyds.subsmovie2.database.FavHelper;
import com.noes.adeyds.subsmovie2.model.FavoriteModel;

import static android.provider.BaseColumns._ID;
import static com.noes.adeyds.subsmovie2.database.DBContract.CONTENT_URI;
import static com.noes.adeyds.subsmovie2.database.DBContract.FavKolom.BACKDROP_KOLOM;
import static com.noes.adeyds.subsmovie2.database.DBContract.FavKolom.DATE_KOLOM;
import static com.noes.adeyds.subsmovie2.database.DBContract.FavKolom.DESC_KOLOM;
import static com.noes.adeyds.subsmovie2.database.DBContract.FavKolom.ID_MOVIE;
import static com.noes.adeyds.subsmovie2.database.DBContract.FavKolom.JUDUL_KOLOM;
import static com.noes.adeyds.subsmovie2.database.DBContract.FavKolom.POP_KOLOM;
import static com.noes.adeyds.subsmovie2.database.DBContract.FavKolom.POSTER_KOLOM;

public class DetailActivity extends AppCompatActivity {
    public static TextView dtlReview, labelRev;
    public static ProgressBar mubeng;
    private TextView dtlJudul, dtlDate, dtlDesc, dtlPopular, tvFav;
    private ImageView imgPoster, imgBackdrop, icFav;
    private LinearLayout contFav;
    boolean isFav;
    AsyncReview gRev;

    private FavoriteModel model ;
    private FavHelper favHelper;

  int idKey ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dtlReview = findViewById(R.id.dtl_review);
        labelRev = findViewById(R.id.labelRev);
        mubeng = findViewById(R.id.progress);
        mubeng.setVisibility(View.VISIBLE);
        dtlJudul = findViewById(R.id.dtl_judul);
        dtlDate = findViewById(R.id.dtl_date);
        dtlDesc = findViewById(R.id.dtl_desc);
        dtlPopular = findViewById(R.id.dtl_popular);
        imgBackdrop = findViewById(R.id.dtl_backdrop);
        imgPoster = findViewById(R.id.dtl_poster);
        icFav = findViewById(R.id.ic_fav);
        tvFav = findViewById(R.id.tv_fav);
        contFav = findViewById(R.id.contFav);




        dtlJudul.setText(getIntent().getStringExtra("judul"));
        dtlDesc.setText(getIntent().getStringExtra("desc"));
        dtlPopular.setText(String.valueOf(getIntent().getIntExtra("popular", 0)));

        Glide.with(this).load(TMDB.POSTER_IMAGE + getIntent().getStringExtra("backdrop")).thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgBackdrop);
        Glide.with(this).load(TMDB.POSTER_IMAGE + getIntent().getStringExtra("poster")).thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPoster);


        dtlDate.setText(convertDate.convertDate(getIntent().getStringExtra("date")));
        gRev = new AsyncReview(String.valueOf(getIntent().getIntExtra("id", 0)));
        gRev.execute();

        load();
        contFav.setOnClickListener(new tapFav());

    }

    private void load() {
        favHelper = new FavHelper(getApplicationContext());
        favHelper.open();
        Cursor cursor = favHelper.queryByIdProvider(String.valueOf(getIntent().getIntExtra("id", 0)));
        Log.e("data", String.valueOf(cursor.getCount()));
        if (cursor.getCount() == 1 ){
//            while (cursor.moveToFirst()){
            cursor.moveToFirst();
                idKey = cursor.getInt(cursor.getColumnIndex(_ID));
                Log.e("_ID ", String.valueOf(idKey));
               // Log.e("cursor pos ",String.valueOf(cursor.getPosition()));

//            }
            icFav.setImageResource(R.drawable.ic_star);
            tvFav.setText(R.string.tapFav);
            isFav = true;
        }else {isFav = false;}
        favHelper.close();

    }


    private class tapFav implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (isFav){
                icFav.setImageResource(R.drawable.ic_star_border);
                tvFav.setText(R.string.untapFav);
                Snackbar.make(v, R.string.untapMsg, BaseTransientBottomBar.LENGTH_SHORT).show();
                isFav = false;
                delFav();
            }else{
                icFav.setImageResource(R.drawable.ic_star);
                tvFav.setText(R.string.tapFav);
                Snackbar.make(v, R.string.tapMsg, BaseTransientBottomBar.LENGTH_SHORT).show();
                isFav = true;
                save();
                load();
            }
        }
    }

    private void save() {
        ContentValues values = new ContentValues();
        values.put(ID_MOVIE, getIntent().getIntExtra("id", 0));
        values.put(JUDUL_KOLOM, getIntent().getStringExtra("judul"));
        values.put(DESC_KOLOM, getIntent().getStringExtra("desc"));
        values.put(POP_KOLOM, getIntent().getIntExtra("popular", 0));
        values.put(DATE_KOLOM, getIntent().getStringExtra("date"));
        values.put(POSTER_KOLOM, getIntent().getStringExtra("backdrop"));
        values.put(BACKDROP_KOLOM, getIntent().getStringExtra("poster"));

        getContentResolver().insert(CONTENT_URI,values);

        finish();
    }

    private void delFav() {
        Uri uri = Uri.parse(CONTENT_URI+"/"+idKey);
        String string[] = {String.valueOf(getIntent().getIntExtra("id", 0))};
        getContentResolver().delete(uri, null,null);

        load();
       // finish();
    }






}
