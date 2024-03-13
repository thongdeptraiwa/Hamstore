package com.example.hamstore.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.ADT.ADT_Recyclerview_gio_hang;
import com.example.hamstore.R;
import com.example.hamstore.TrangChu;
import com.example.hamstore.model.Items;
import com.example.hamstore.model.Loai_Hamster;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class fragment_GioHang2 extends Fragment {
    Context c;
    RecyclerView recyclerView_gio_hang;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private final String key_tai_khoan = "tai_khoan";
    String tai_khoan;
    String gio_hang_tai_khoan;
    ImageView img_khung_rac_all,img_tang,img_giam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang,container,false);
        c = getActivity();

        tai_khoan = getArguments().getString(key_tai_khoan);

        //ánh xạ
        recyclerView_gio_hang=view.findViewById(R.id.recyclerView_gio_hang);
        img_khung_rac_all=view.findViewById(R.id.img_khung_rac_all);
        gio_hang_tai_khoan = "gio_hang_"+tai_khoan;
        myRef = firebaseDatabase.getReference(gio_hang_tai_khoan);

        //nhấn rác hết
        img_khung_rac_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.removeValue();
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(myRef, Items.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Items, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.items_gio_hang, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, int position, Items model) {

                //hiển thị
                //gia
                NumberFormat formatter = new DecimalFormat("#,###");
                //int myNumber = ds.get(i).getGia();
                int myNumber = model.getGia();
                String formattedNumber = formatter.format(myNumber);
                holder.tv_gia.setText(String.valueOf(formattedNumber +"đ"));

                //name
                //holder.tv_ten_dai.setText(ds.get(i).getTen_dai());
                holder.tv_ten_dai.setText(model.getTen_dai());

                //lấy SrcImg
                //String imgName = ds.get(i).getSrcImg();
                String imgName = model.getSrcImg();
                //đổi string thành int (R.drawable.name)
                int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
                holder.img.setImageResource(imgId);

                //so luong
                holder.tv_so_luong.setText(String.valueOf(model.getSo_luong()));

                //nhấn xóa
                holder.img_khung_rac.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myRef.child(model.getId()).removeValue();
                        Toast.makeText(c, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });

                //nhấn +
                holder.img_tang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tang = model.getSo_luong() + 1;
                        myRef.child(model.getId()).child("so_luong").setValue(tang);
                    }
                });
                //nhấn -
                holder.img_giam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(model.getSo_luong() >1 ){
                            int giam = model.getSo_luong() - 1;
                            myRef.child(model.getId()).child("so_luong").setValue(giam);
                        }
                    }
                });

            }
        };

        //recyclerView giohang
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_gio_hang.setLayoutManager(linearLayoutManager);
        //adt_recyclerview_gio_hang = new ADT_Recyclerview_gio_hang(c,ds_tam);
        recyclerView_gio_hang.setAdapter(adapter);
        adapter.startListening();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ten_dai,tv_gia,tv_so_luong;
        ImageView img,img_khung_rac,img_giam,img_tang;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_dai = itemView.findViewById(R.id.tv_ten_dai);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tv_so_luong = itemView.findViewById(R.id.tv_so_luong);
            img = itemView.findViewById(R.id.img);
            img_khung_rac = itemView.findViewById(R.id.img_khung_rac);
            img_giam = itemView.findViewById(R.id.img_giam);
            img_tang = itemView.findViewById(R.id.img_tang);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

}
