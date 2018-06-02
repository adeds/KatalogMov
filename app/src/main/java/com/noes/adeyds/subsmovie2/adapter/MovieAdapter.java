package com.noes.adeyds.subsmovie2.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.noes.adeyds.subsmovie2.api.TMDB;
import com.noes.adeyds.subsmovie2.converter.convertDate;
import com.noes.adeyds.subsmovie2.DetailActivity;
import com.noes.adeyds.subsmovie2.model.Movies;
import com.noes.adeyds.subsmovie2.R;

import java.util.ArrayList;

/**
 * Created by adeyds on 2/8/2018.
 */

public class MovieAdapter extends BaseAdapter {

    private ArrayList<Movies> arrayList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public MovieAdapter(Context c) {
        this.context = c;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setData(ArrayList<Movies> items) {
        arrayList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView= layoutInflater.inflate(R.layout.row_item, null);
            holder.tvJudul = convertView.findViewById(R.id.tv_judul);
            holder.tvDesc = convertView.findViewById(R.id.tv_descc);
            holder.tvDate = convertView.findViewById(R.id.tv_date);
            holder.imgPoster = convertView.findViewById(R.id.img_poster);
            holder.kontainer = convertView.findViewById(R.id.kontainer);
            convertView.setTag(holder);

        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.tvJudul.setText(arrayList.get(position).getJudul());


            holder.tvDate.setText(convertDate.convertDate(arrayList.get(position).getDate()));
            holder.tvDesc.setText(arrayList.get(position).getDesc());
            Glide.with(context).load(TMDB.POSTER_IMAGE+arrayList.get(position).getPoster()).thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imgPoster);
            holder.kontainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDetail = new Intent(v.getContext(), DetailActivity.class);
                    toDetail.putExtra("judul", arrayList.get(position).getJudul());
                    toDetail.putExtra("id", arrayList.get(position).getId());
                    toDetail.putExtra("popular", arrayList.get(position).getPopularity());
                    toDetail.putExtra("date", arrayList.get(position).getDate());
                    toDetail.putExtra("poster", arrayList.get(position).getPoster());
                    toDetail.putExtra("backdrop", arrayList.get(position).getBackdrop());
                    toDetail.putExtra("desc", arrayList.get(position).getDesc());
                    v.getContext().startActivity(toDetail);

                }
            });

        return convertView;
    }

    public static class ViewHolder {
        TextView tvJudul, tvDate, tvDesc;
        ImageView imgPoster;
        CardView kontainer;

    }
}
