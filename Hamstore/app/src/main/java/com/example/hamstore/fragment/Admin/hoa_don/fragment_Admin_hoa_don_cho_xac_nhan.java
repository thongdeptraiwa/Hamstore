package com.example.hamstore.fragment.Admin.hoa_don;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.ADT.ADT_Recyclerview_sp_trong_hoa_don;
import com.example.hamstore.R;
import com.example.hamstore.model.Hoa_Don;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class fragment_Admin_hoa_don_cho_xac_nhan extends Fragment {
    Context c;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference data = firebaseDatabase.getReference("Hóa đơn");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_list_khong_them,container,false);

        c = getActivity();

        //ánh xạ
        recyclerView=view.findViewById(R.id.recyclerView);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Hoa_Don> options =
                new FirebaseRecyclerOptions.Builder<Hoa_Don>()
                        .setQuery(data, Hoa_Don.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Hoa_Don, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.items_admin_hoa_don, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, int position, Hoa_Don model) {

                if(model.getTrang_thai().equals("Chờ xác nhận")) {
                    //hien thi
                    holder.tv_id.setText("ID: "+model.getId());
                    holder.tv_tai_khoan.setText("User: "+model.getId_user());
                    //tong tien
                    NumberFormat formatter = new DecimalFormat("#,###");
                    //int myNumber = ds.get(i).getGia();
                    int myNumber = model.getTong_tien();
                    String formattedNumber = formatter.format(myNumber);
                    holder.tv_gia.setText(String.valueOf(formattedNumber +"đ"));

                    //trang thái
                    holder.tv_trang_thai.setText(model.getTrang_thai());
                    //Hủy và Hoàn trả đổi sang màu đỏ
                    if(model.getTrang_thai().equals("Hủy bỏ")){
                        holder.tv_trang_thai.setTextColor(Color.parseColor("#E53935"));
                    }

                    //nhấn item hiện dialog chi tiết
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_xanh(model);
                        }
                    });
                }else {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                }


            }

        };

        //recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tai_khoan,tv_id,tv_gia,tv_trang_thai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tai_khoan = itemView.findViewById(R.id.tv_tai_khoan);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tv_trang_thai = itemView.findViewById(R.id.tv_trang_thai);
        }
    }

    private void dialog_xanh(Hoa_Don hoaDon){

        //tạo dialog
        Dialog dialog = new Dialog((Activity)c);
        dialog.setContentView(R.layout.dialog_hoa_don_xanh);
        dialog.setCanceledOnTouchOutside(false);//nhấn ra ngoài ko tắc dialog
        dialog.show();

        //ánh xạ thông tin NV
        Button btn_xanh = dialog.findViewById(R.id.btn_xanh);
        Button btn_thoat = dialog.findViewById(R.id.btn_thoat);
        TextInputEditText inputEdit_id = dialog.findViewById(R.id.inputEdit_id);
        TextInputEditText inputEdit_nguoi_mua = dialog.findViewById(R.id.inputEdit_nguoi_mua);
        TextInputEditText inputEdit_thoi_gian = dialog.findViewById(R.id.inputEdit_thoi_gian);
        TextInputEditText inputEdit_sdt = dialog.findViewById(R.id.inputEdit_sdt);
        TextInputEditText inputEdit_dia_chi = dialog.findViewById(R.id.inputEdit_dia_chi);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        TextInputEditText inputEdit_tong_tien = dialog.findViewById(R.id.inputEdit_tong_tien);
        TextInputEditText inputEdit_trang_thai = dialog.findViewById(R.id.inputEdit_trang_thai);


        //text
        inputEdit_id.setText(hoaDon.getId());
        inputEdit_nguoi_mua.setText(hoaDon.getId_user());
        inputEdit_thoi_gian.setText(hoaDon.getThoi_gian());
        inputEdit_sdt.setText(String.valueOf(hoaDon.getSdt()));
        inputEdit_dia_chi.setText(String.valueOf(hoaDon.getDia_chi()));

        inputEdit_tong_tien.setText(String.valueOf(hoaDon.getTong_tien()));
        inputEdit_trang_thai.setText(String.valueOf(hoaDon.getTrang_thai()));

        //đổi text btn_xanh
        btn_xanh.setText("Đã xác nhận");


        //recyclerView các sp trong hóa đơn
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ADT_Recyclerview_sp_trong_hoa_don adtRecyclerviewSpTrongHoaDon = new ADT_Recyclerview_sp_trong_hoa_don(c,hoaDon.getArr_items());
        recyclerView.setAdapter(adtRecyclerviewSpTrongHoaDon);


        //nhấn
        btn_xanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.child(hoaDon.getId()).child("trang_thai").setValue("Đã xác nhận");
                dialog.dismiss();
            }
        });
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

}
