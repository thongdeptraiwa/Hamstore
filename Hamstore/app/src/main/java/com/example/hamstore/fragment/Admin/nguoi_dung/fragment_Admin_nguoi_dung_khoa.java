package com.example.hamstore.fragment.Admin.nguoi_dung;

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
import com.example.hamstore.R;
import com.example.hamstore.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class fragment_Admin_nguoi_dung_khoa extends Fragment {
    Context c;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference data = firebaseDatabase.getReference("Users");


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

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference data = firebaseDatabase.getReference("Users");
        //xóa gio hang của user bị xóa
        DatabaseReference data_gio_hang = firebaseDatabase.getReference();

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(data, User.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<User, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.items_admin_nguoi_dung, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, int position, User model) {

                //khóa
                if(model.getRole() == 0){
                    //hien thi
                    holder.tv_tai_khoan.setText(model.getTai_khoan());
                    holder.tv_trang_thai.setText("Khóa");
                    //đổi màu đỏ
                    holder.tv_trang_thai.setTextColor(Color.parseColor("#E53935"));
                    //xem chi tiết
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_chi_tiet(model);
                        }
                    });
                }else {
                    //ẩn holder
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
        TextView tv_tai_khoan,tv_trang_thai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tai_khoan = itemView.findViewById(R.id.tv_tai_khoan);
            tv_trang_thai = itemView.findViewById(R.id.tv_trang_thai);
        }
    }

    private void dialog_chi_tiet(User user){


        //tạo dialog
        Dialog dialog = new Dialog((Activity)c);
        dialog.setContentView(R.layout.dialog_chi_tiet_mo_khoa_nguoi_dung);
        dialog.setCanceledOnTouchOutside(false);//nhấn ra ngoài ko tắc dialog
        dialog.show();

        //ánh xạ thông tin NV
        Button btn_mo_khoa_tai_khoan = dialog.findViewById(R.id.btn_mo_khoa_tai_khoan);
        Button btn_huy = dialog.findViewById(R.id.btn_huy);
        ImageView img = dialog.findViewById(R.id.img);
        TextInputEditText inputEdit_tai_khoan = dialog.findViewById(R.id.inputEdit_tai_khoan);
        TextInputEditText inputEdit_ho_ten = dialog.findViewById(R.id.inputEdit_ho_ten);
        TextInputEditText inputEdit_gmail = dialog.findViewById(R.id.inputEdit_gmail);
        TextInputEditText inputEdit_sdt = dialog.findViewById(R.id.inputEdit_sdt);
        TextInputEditText inputEdit_dia_chi = dialog.findViewById(R.id.inputEdit_dia_chi);
        TextInputEditText inputEdit_trang_thai = dialog.findViewById(R.id.inputEdit_trang_thai);




        //chi tiết
        //img avt
        if(user.getImg().length()<40){

            String imgName = user.getImg();
            //đổi string thành int (R.drawable.name)
            int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
            Glide.with(c)
                    .load(imgId)
                    //bo tròn
                    .circleCrop()
                    .into(img);

        }else {
            Glide.with(c)
                    .load(user.getImg())
                    //bo tròn
                    .circleCrop()
                    .into(img);
        }


        //text
        inputEdit_tai_khoan.setText(user.getTai_khoan());
        inputEdit_ho_ten.setText(user.getHo_ten());
        inputEdit_gmail.setText(String.valueOf(user.getGmail()));
        inputEdit_sdt.setText(String.valueOf(user.getSdt()));
        inputEdit_dia_chi.setText(user.getDia_chi());
        inputEdit_trang_thai.setText("Khóa");


        //nhấn
        btn_mo_khoa_tai_khoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(user.getTai_khoan().length() > 25){
                    //gg
                    data.child(user.getTai_khoan()).child("role").setValue(2);
                }else {
                    //tài koản thường
                    data.child(user.getTai_khoan()).child("role").setValue(1);
                }

                dialog.dismiss();

            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

}
