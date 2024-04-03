package com.example.hamstore.ADT;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hamstore.Activity.Users.TrangChu;
import com.example.hamstore.R;
import com.example.hamstore.model.Items;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class ADT_Recyclerview_admin_thong_ke_san_pham extends RecyclerView.Adapter<ADT_Recyclerview_admin_thong_ke_san_pham.ViewHolder>{
    private Context c;
    private ArrayList<Items> ds = new ArrayList<>();


    public ADT_Recyclerview_admin_thong_ke_san_pham(Context c, ArrayList<Items> ds) {
        this.c = c;
        this.ds = ds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //lấy LayoutInflater
        LayoutInflater inf = ((Activity)c).getLayoutInflater();

        View view = inf.inflate(R.layout.items_admin_thong_ke_san_pham,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        //hiển thị

        //lấy SrcImg
        if(ds.get(i).getSrcImg().length()<40){
            String imgName = ds.get(i).getSrcImg();
            //đổi string thành int (R.drawable.name)
            int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
            holder.img.setImageResource(imgId);
        }else {
            Glide.with(c).load(ds.get(i).getSrcImg()).into(holder.img);
        }

        //name
        holder.tv_ten_dai.setText(ds.get(i).getTen_dai());


        //Đã đặt
        holder.tv_da_dat.setText("Đã đặt: "+ds.get(i).getSo_luong());

        //sl trong kho
        holder.tv_so_luong_trong_kho.setText("Số lượng trong kho: "+ds.get(i).getSo_luong_trong_kho());

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ten_dai,tv_da_dat,tv_so_luong_trong_kho;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_dai = itemView.findViewById(R.id.tv_ten_dai);
            tv_da_dat = itemView.findViewById(R.id.tv_da_dat);
            tv_so_luong_trong_kho = itemView.findViewById(R.id.tv_so_luong_trong_kho);
            img = itemView.findViewById(R.id.img);
        }
    }
}
