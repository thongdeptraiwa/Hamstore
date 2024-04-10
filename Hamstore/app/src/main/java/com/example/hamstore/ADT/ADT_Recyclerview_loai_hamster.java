package com.example.hamstore.ADT;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hamstore.R;
import com.example.hamstore.Activity.Users.TrangChu;
import com.example.hamstore.model.Items;
import com.example.hamstore.model.Loai_Hamster;

import java.util.ArrayList;


public class ADT_Recyclerview_loai_hamster extends RecyclerView.Adapter<ADT_Recyclerview_loai_hamster.ViewHolder> implements Filterable {
    private Context c;
    private ArrayList<Loai_Hamster> ds = new ArrayList<>();
    private ArrayList<Loai_Hamster> dsSearch = new ArrayList<>();
    TrangChu trangChu;

    public ADT_Recyclerview_loai_hamster(Context c, ArrayList<Loai_Hamster> ds) {
        this.c = c;
        this.ds = ds;
        this.dsSearch = ds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //lấy LayoutInflater
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        //ánh xạ view
        trangChu = (TrangChu) ((Activity)c);
        View view = inf.inflate(R.layout.items_trangchu_loai_hamster,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        //hiển thị

        //gia
        holder.tv_Gia.setText(ds.get(i).getKhoang_gia());

        //name
        holder.tv_Ten.setText(ds.get(i).getTen_loai());

        //lấy SrcImg
        if(ds.get(i).getSrcImg().length()<40){
            String imgName = ds.get(i).getSrcImg();
            //đổi string thành int (R.drawable.name)
            int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
            holder.img.setImageResource(imgId);
        }else {
            Glide.with(c).load(ds.get(i).getSrcImg()).into(holder.img);
        }

        //nhấn item
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_2hang_hamster(ds.get(i).getTen_loai());
            }
        });
        holder.tv_Ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_2hang_hamster(ds.get(i).getTen_loai());
            }
        });
        holder.tv_Gia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_2hang_hamster(ds.get(i).getTen_loai());
            }
        });

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_Ten,tv_Gia;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Ten = itemView.findViewById(R.id.tv_Ten);
            tv_Gia = itemView.findViewById(R.id.tv_Gia);
            img = itemView.findViewById(R.id.img);
        }
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();

                if (strSearch.isEmpty()) {
                    ds = dsSearch;
                } else {
                    ArrayList<Loai_Hamster> dsTemp = new ArrayList<Loai_Hamster>();
                    for (Loai_Hamster loai_hamster : dsSearch) {
                        if (loai_hamster.getTen_loai().toLowerCase().contains(strSearch.toLowerCase())) {
                            dsTemp.add(loai_hamster);
                        }
                    }
                    ds = dsTemp;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = ds;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ds = (ArrayList<Loai_Hamster>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}