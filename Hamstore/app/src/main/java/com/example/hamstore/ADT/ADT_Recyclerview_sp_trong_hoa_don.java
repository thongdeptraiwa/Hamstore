package com.example.hamstore.ADT;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.R;
import com.example.hamstore.model.Items;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class ADT_Recyclerview_sp_trong_hoa_don extends RecyclerView.Adapter<ADT_Recyclerview_sp_trong_hoa_don.ViewHolder>{
    private Context c;
    private ArrayList<Items> ds = new ArrayList<>();

    public ADT_Recyclerview_sp_trong_hoa_don(Context c, ArrayList<Items> ds) {
        this.c = c;
        this.ds = ds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //lấy LayoutInflater
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        //ánh xạ view
        View view = inf.inflate(R.layout.items_sp_trong_hoa_don,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        //hiển thị

        //name
        holder.tv_ten_dai.setText(ds.get(i).getTen_dai());

        //so luong
        holder.tv_so_luong.setText(String.valueOf(ds.get(i).getSo_luong()));

        //gia
        NumberFormat formatter = new DecimalFormat("#,###");
        int myNumber = ds.get(i).getGia();
        String formattedNumber = formatter.format(myNumber);
        holder.tv_gia.setText(String.valueOf(formattedNumber +"đ"));

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ten_dai,tv_so_luong,tv_gia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_dai = itemView.findViewById(R.id.tv_ten_dai);
            tv_so_luong = itemView.findViewById(R.id.tv_so_luong);
            tv_gia = itemView.findViewById(R.id.tv_gia);
        }
    }
}
