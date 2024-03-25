package com.example.hamstore.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hamstore.AC_dang_nhap;
import com.example.hamstore.AC_thanh_toan;
import com.example.hamstore.R;
import com.example.hamstore.model.Items;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class fragment_GioHang extends Fragment {
    Context c;
    RecyclerView recyclerView_gio_hang;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private final String key_tai_khoan = "tai_khoan";
    String tai_khoan;
    String gio_hang_tai_khoan;
    ImageView img_khung_rac_all;
    Button btn_Thanh_toan;
    //Tong tien
    int tong_tien = 0;
    TextView tv_tong_tien;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang,container,false);

        c = getActivity();

        tai_khoan = getArguments().getString(key_tai_khoan);

        //ánh xạ
        recyclerView_gio_hang=view.findViewById(R.id.recyclerView_gio_hang);
        img_khung_rac_all=view.findViewById(R.id.img_khung_rac_all);
        tv_tong_tien=view.findViewById(R.id.tv_tong_tien);
        btn_Thanh_toan=view.findViewById(R.id.btn_Thanh_toan);
        gio_hang_tai_khoan = "gio_hang_"+tai_khoan;
        myRef = firebaseDatabase.getReference("Giỏ hàng").child(gio_hang_tai_khoan);

        //nhấn rác hết
        img_khung_rac_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tạo dialog thông báo
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
                alertDialogBuilder.setTitle("Thông Báo!");
                alertDialogBuilder.setMessage("Bạn có chắc muốn xóa hết?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myRef.removeValue();
                    }
                });
                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //nhấn thanh toán
        btn_Thanh_toan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, AC_thanh_toan.class);
                intent.putExtra(key_tai_khoan,tai_khoan);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        tong_tien = 0;
        load_tong_tien();

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

                //checkbox
                if(model.getCheckbox() == 0){
                    holder.checkBox.setChecked(false);
                }else {
                    holder.checkBox.setChecked(true);
                }

                //tong tien
                if(model.getCheckbox() == 1){
                   tong_tien+= model.getGia() * model.getSo_luong();
                   load_tong_tien();
                }

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
                if(model.getSrcImg().length()<40){
                    String imgName = model.getSrcImg();
                    //đổi string thành int (R.drawable.name)
                    int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
                    holder.img.setImageResource(imgId);
                }else {
                    Glide.with(c).load(model.getSrcImg()).into(holder.img);
                }

                //so luong
                holder.tv_so_luong.setText(String.valueOf(model.getSo_luong()));

                //nhấn xóa
                holder.img_khung_rac.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //tạo dialog thông báo
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
                        alertDialogBuilder.setTitle("Thông Báo!");
                        alertDialogBuilder.setMessage("Bạn có chắc muốn xóa?");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myRef.child(model.getId()).removeValue();
                                //reset tổng tiền
                                reset_tong_tien();
                            }
                        });
                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });

                //nhấn +
                holder.img_tang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tang = model.getSo_luong() + 1;
                        myRef.child(model.getId()).child("so_luong").setValue(tang);
                        //reset tổng tiền
                        reset_tong_tien();
                    }
                });
                //nhấn -
                holder.img_giam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(model.getSo_luong() >1 ){
                            int giam = model.getSo_luong() - 1;
                            myRef.child(model.getId()).child("so_luong").setValue(giam);
                            //reset tổng tiền
                            reset_tong_tien();
                        }
                    }
                });
                //nhấn checkbox
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(model.getCheckbox() == 0){
                            myRef.child(model.getId()).child("checkbox").setValue(1);
                        }else {
                            myRef.child(model.getId()).child("checkbox").setValue(0);
                        }
                        //reset tổng tiền
                        reset_tong_tien();
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
    private void load_tong_tien(){

        //giá tổng tiền
        NumberFormat formatter = new DecimalFormat("#,###");
        //int myNumber = ds.get(i).getGia();
        int myNumber = tong_tien;
        String formattedNumber = formatter.format(myNumber);
        tv_tong_tien.setText(String.valueOf(formattedNumber +"đ"));

    }
    private void reset_tong_tien(){
        tong_tien = 0;
        load_tong_tien();
        onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        reset_tong_tien();
    }

}
