package com.example.hamstore.ADT;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.R;
import com.example.hamstore.TrangChu;
import com.example.hamstore.model.Items;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ADT_Recyclerview_2hang extends RecyclerView.Adapter<ADT_Recyclerview_2hang.ViewHolder>{
    private Context c;
    private ArrayList<Items> ds = new ArrayList<Items>();
    TrangChu trangChu;

    public ADT_Recyclerview_2hang(Context c, ArrayList<Items> ds) {
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
        View view = inf.inflate(R.layout.items_trangchu_2hang,parent,false);
        //animation
        //Animation anim = AnimationUtils.loadAnimation((Activity)c,R.anim.anim_list);
        //view.startAnimation(anim);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        //hiển thị

        //gia
        NumberFormat formatter = new DecimalFormat("#,###");
        int myNumber = ds.get(i).getGia();
        String formattedNumber = formatter.format(myNumber);
        holder.tv_Gia.setText(String.valueOf(formattedNumber +"đ"));

        //name
        holder.tv_Ten.setText(ds.get(i).getTen_ngan());

        //lấy SrcImg
        String imgName = ds.get(i).getSrcImg();
        //đổi string thành int (R.drawable.name)
        int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
        holder.img.setImageResource(imgId);

        //Đã mua
        holder.tv_da_ban.setText("Đã bán: "+ds.get(i).getSo_luong_da_mua());

        //hết hàng
        if(ds.get(i).getSo_luong_trong_kho() > 0){
            holder.tv_het_hang.setVisibility(View.GONE);
        }

        //nhấn item
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Items item = (Items) ds.get(i);
                trangChu.chuyen_ac_chitiet(item);
            }
        });
        holder.tv_Ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Items item = (Items) ds.get(i);
                trangChu.chuyen_ac_chitiet(item);
            }
        });
        holder.tv_Gia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Items item = (Items) ds.get(i);
                trangChu.chuyen_ac_chitiet(item);
            }
        });
        //nhấn thêm -> chitiet
        holder.btn_them_vao_gio_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Items item = (Items) ds.get(i);
                trangChu.chuyen_ac_chitiet(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_Ten,tv_Gia,tv_da_ban,tv_het_hang;
        ImageView img;
        Button btn_them_vao_gio_hang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Ten = itemView.findViewById(R.id.tv_Ten);
            tv_Gia = itemView.findViewById(R.id.tv_Gia);
            tv_da_ban = itemView.findViewById(R.id.tv_da_ban);
            tv_het_hang = itemView.findViewById(R.id.tv_het_hang);
            img = itemView.findViewById(R.id.img);
            btn_them_vao_gio_hang = itemView.findViewById(R.id.btn_them_vao_gio_hang);
        }
    }

}
