package com.noes.adeyds.favoritemovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.noes.adeyds.favoritemovies.DetailFavorites;
import com.noes.adeyds.favoritemovies.R;
import com.noes.adeyds.favoritemovies.entity.FavoriteModel;

import static com.noes.adeyds.favoritemovies.api.TMDB.POSTER_IMAGE;
import static com.noes.adeyds.favoritemovies.database.DBContract.CONTENT_URI;


/**
 * Created by adeyds on 3/13/2018.
 */

public class FavMovAdapter extends RecyclerView.Adapter<FavMovAdapter.Holder>{


    private Cursor list;

    public FavMovAdapter(Cursor items) {
        replaceAll(items);
    }

    public void replaceAll(Cursor items) {
        list = items;
        notifyDataSetChanged();
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
    holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        else return list.getCount();
    }

    private FavoriteModel getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new FavoriteModel(list);
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView tvJudul, tvDate, tvDesc;
        ImageView imgPoster;
        CardView kontainer;

        public Holder(View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvDesc = itemView.findViewById(R.id.tv_descc);
            tvDate = itemView.findViewById(R.id.tv_date);
            imgPoster = itemView.findViewById(R.id.img_poster);
            kontainer = itemView.findViewById(R.id.kontainer);
        }

        public void bind(final FavoriteModel item) {
        tvJudul.setText(item.getJudul());
        tvDate.setText(item.getDate());
        tvDesc.setText(item.getDesc());
            Glide.with(itemView.getContext())
                    .load(POSTER_IMAGE+ item.getPoster())
                    .into(imgPoster);
            kontainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailFavorites.class);
                    intent.setData(Uri.parse(CONTENT_URI + "/" + item.getId()));
                    itemView.getContext().startActivity(intent);
                }
            });



        }


    }


}