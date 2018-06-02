package com.noes.adeyds.subsmovie2.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.noes.adeyds.subsmovie2.CustomOnItemClickListener;
import com.noes.adeyds.subsmovie2.DetailActivity;
import com.noes.adeyds.subsmovie2.R;
import com.noes.adeyds.subsmovie2.api.TMDB;
import com.noes.adeyds.subsmovie2.converter.convertDate;
import com.noes.adeyds.subsmovie2.model.FavoriteModel;


public class FavAdapter extends RecyclerView.Adapter<FavAdapter.Holder>{


    private Context context;
    private Cursor listFav;

    public FavAdapter(Context context) {
        this.context = context;
    }

    public void setListNotes(Cursor listFav) {
        this.listFav = listFav;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final FavoriteModel model= getItem(position);
        holder.tvJudul.setText(model.getJudul());
        holder.tvDate.setText(convertDate.convertDate(model.getDate()));
        holder.tvDesc.setText(model.getDesc());
        Glide.with(context).load(TMDB.POSTER_IMAGE+model.getPoster()).thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgPoster);

        holder.kontainer.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent toDetail = new Intent(context, DetailActivity.class);
                toDetail.putExtra("judul", model.getJudul());
                toDetail.putExtra("id", model.getId());
                toDetail.putExtra("popular", model.getPopularity());
                toDetail.putExtra("date", model.getDate());
                toDetail.putExtra("poster", model.getPoster());
                toDetail.putExtra("backdrop", model.getBackdrop());
                toDetail.putExtra("desc", model.getDesc());
                context.startActivity(toDetail);
            }
        }));


    }

    @Override
    public int getItemCount() {
        if (listFav == null) return 0;
        return listFav.getCount();
    }
    private FavoriteModel getItem(int position){
        if (!listFav.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        Log.e("posss", position+"");
        return new FavoriteModel(listFav);
    }


    class Holder extends RecyclerView.ViewHolder {
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
    }
}
