package com.example.doan_thibanglaixe.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_thibanglaixe.Object.BienBao;
import com.example.doan_thibanglaixe.R;

import java.util.ArrayList;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

public class AdapterRecyclerViewBienBao extends RecyclerView.Adapter<AdapterRecyclerViewBienBao.RecyclerViewHolder> implements StickyHeaderAdapter<AdapterRecyclerViewBienBao.HeaderHolder> {
    List<BienBao> listBienBao = new ArrayList<BienBao>();
    Context context;
    private LayoutInflater mInflater;
    public AdapterRecyclerViewBienBao(Context context, List<BienBao> listBienBao) {
        this.listBienBao = listBienBao;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.layout_recyclerview_bienbao,parent,false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tv_noiDung.setText(listBienBao.get(position).getNoiDung());
        holder.image.setImageDrawable(getDrawable(context,position));
    }

    public Drawable getDrawable(Context context, int position){
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(listBienBao.get(position).getImage(), "drawable",
                context.getPackageName());
        Drawable drawable = resources.getDrawable(resourceId);
        return drawable;
    }

    @Override
    public int getItemCount() {
        return listBienBao.size();
    }

    @Override
    public long getHeaderId(int position) {
        return (long) listBienBao.get(position).getLoaiBien();
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.header_bienbao, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        String headerName = "";
        if (getHeaderId(position)==1){
            headerName = "BIỂN BÁO NGUY HIỂM";
        } else if (getHeaderId(position)==2){
            headerName = "BIỂN BÁO CẤM";
        } else if (getHeaderId(position)==3){
            headerName = "BIỂN BÁO HIỆU LỆNH";
        } else if (getHeaderId(position)==4){
            headerName = "BIỂN BÁO CHỈ DẪN";
        } else if (getHeaderId(position)==5){
            headerName = "BIỂN BÁO PHỤ";
        }
        viewholder.header.setText(headerName);
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tv_noiDung;
        ImageView image;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tv_noiDung = itemView.findViewById(R.id.tv_noiDung);
        }
    }
    static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);

            header = (TextView) itemView;
        }
    }
}
