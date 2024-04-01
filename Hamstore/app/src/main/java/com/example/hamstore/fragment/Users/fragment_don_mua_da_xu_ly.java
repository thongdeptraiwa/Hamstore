package com.example.hamstore.fragment.Users;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hamstore.ADT.ADT_Recyclerview_sp_trong_hoa_don;
import com.example.hamstore.Activity.Users.TrangChu;
import com.example.hamstore.R;
import com.example.hamstore.model.Hoa_Don;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class fragment_don_mua_da_xu_ly extends Fragment {
    Context c;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference data = firebaseDatabase.getReference("Hóa đơn");
    private final String key_tai_khoan = "tai_khoan";
    String tai_khoan;
    public TrangChu trangChu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_list_khong_them,container,false);

        c = getActivity();



        //ánh xạ
        recyclerView=view.findViewById(R.id.recyclerView);
        trangChu = (TrangChu) getActivity();


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
                        .inflate(R.layout.items_don_mua, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, int position, Hoa_Don model) {

                if(model.getId_user().equals(trangChu.tai_khoan) && (model.getTrang_thai().equals("Hoàn thành") || model.getTrang_thai().equals("Hủy bỏ") || model.getTrang_thai().equals("Hoàn trả"))) {
                    //hien thi
                    holder.tv_id.setText("ID: "+model.getId());

                    //lấy SrcImg
                    if(model.getArr_items().get(0).getSrcImg().length()<40){
                        String imgName = model.getArr_items().get(0).getSrcImg();
                        //đổi string thành int (R.drawable.name)
                        int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
                        holder.img.setImageResource(imgId);
                    }else {
                        Glide.with(c).load(model.getArr_items().get(0).getSrcImg()).into(holder.img);
                    }

                    //tổng tiền
                    NumberFormat formatter = new DecimalFormat("#,###");
                    //int myNumber = ds.get(i).getGia();
                    int myNumber = model.getTong_tien();
                    String formattedNumber = formatter.format(myNumber);
                    holder.tv_tong_tien.setText(String.valueOf(formattedNumber +"đ"));

                    //trang thái
                    holder.tv_trang_thai.setText(model.getTrang_thai());
                    //Hủy và Hoàn trả đổi sang màu đỏ
                    if(model.getTrang_thai().equals("Hủy bỏ") || model.getTrang_thai().equals("Hoàn trả")){
                        holder.tv_trang_thai.setTextColor(Color.parseColor("#E53935"));
                    }

                    //nhấn item hiện dialog chi tiết
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_da_thanh_toan(model);
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
        TextView tv_id,tv_tong_tien,tv_trang_thai;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_tong_tien = itemView.findViewById(R.id.tv_tong_tien);
            tv_trang_thai = itemView.findViewById(R.id.tv_trang_thai);
            img = itemView.findViewById(R.id.img);
        }
    }

    private void dialog_da_thanh_toan(Hoa_Don hoaDon){

        //tạo dialog
        Dialog dialog = new Dialog((Activity)c);
        dialog.setContentView(R.layout.dialog_hoa_don_thoat);
        dialog.setCanceledOnTouchOutside(false);//nhấn ra ngoài ko tắc dialog
        dialog.show();

        //ánh xạ thông tin NV
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

        //đổi màu trạng thái
        if(hoaDon.getTrang_thai().equals("Hủy bỏ") || hoaDon.getTrang_thai().equals("Hoàn trả")){
            inputEdit_trang_thai.setTextColor(Color.parseColor("#E53935"));
        }



        //recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ADT_Recyclerview_sp_trong_hoa_don adtRecyclerviewSpTrongHoaDon = new ADT_Recyclerview_sp_trong_hoa_don(c,hoaDon.getArr_items());
        recyclerView.setAdapter(adtRecyclerviewSpTrongHoaDon);


        //nhấn
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

}
