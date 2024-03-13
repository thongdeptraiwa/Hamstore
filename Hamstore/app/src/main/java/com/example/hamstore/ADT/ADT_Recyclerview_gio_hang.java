package com.example.hamstore.ADT;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.R;
import com.example.hamstore.TrangChu;
import com.example.hamstore.fragment.fragment_GioHang;
import com.example.hamstore.fragment.fragment_TrangChu;
import com.example.hamstore.model.Items;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class ADT_Recyclerview_gio_hang extends RecyclerView.Adapter<ADT_Recyclerview_gio_hang.ViewHolder>{
    private Context c;
    private ArrayList<Items> ds = new ArrayList<>();
    TrangChu trangChu;

    public ADT_Recyclerview_gio_hang(Context c, ArrayList<Items> ds) {
        this.c = c;
        this.ds = ds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //lấy LayoutInflater
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        //ánh xạ view
        trangChu = (TrangChu) ((Activity)c);
        View view = inf.inflate(R.layout.items_gio_hang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        //hiển thị
        //gia
        NumberFormat formatter = new DecimalFormat("#,###");
        int myNumber = ds.get(i).getGia();
        String formattedNumber = formatter.format(myNumber);
        holder.tv_gia.setText(String.valueOf(formattedNumber +"đ"));

        //name
        holder.tv_ten_dai.setText(ds.get(i).getTen_dai());

        //lấy SrcImg
        String imgName = ds.get(i).getSrcImg();
        //đổi string thành int (R.drawable.name)
        int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
        holder.img.setImageResource(imgId);

        //nhấn xóa
        holder.img_khung_rac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trangChu.chuyen_fragment_GioHang();
                trangChu.xoa_1_sp_gio_hang(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ten_dai,tv_gia,tv_so_luong;
        ImageView img,img_khung_rac,img_giam,img_tang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_dai = itemView.findViewById(R.id.tv_ten_dai);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tv_so_luong = itemView.findViewById(R.id.tv_so_luong);
            img = itemView.findViewById(R.id.img);
            img_khung_rac = itemView.findViewById(R.id.img_khung_rac);
            img_giam = itemView.findViewById(R.id.img_giam);
            img_tang = itemView.findViewById(R.id.img_tang);
        }
    }
}
