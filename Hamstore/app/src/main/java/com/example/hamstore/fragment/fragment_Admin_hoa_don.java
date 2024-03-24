package com.example.hamstore.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.R;
import com.example.hamstore.model.Hoa_Don;
import com.example.hamstore.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class fragment_Admin_hoa_don extends Fragment {
    Context c;
    RecyclerView recyclerView;

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
        DatabaseReference data = firebaseDatabase.getReference("Hóa đơn");

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

                //hien thi
                holder.tv_id.setText("ID: "+model.getId());
                holder.tv_tai_khoan.setText("User: "+model.getId_user());
                //gia
                NumberFormat formatter = new DecimalFormat("#,###");
                //int myNumber = ds.get(i).getGia();
                int myNumber = model.getTong_tien();
                String formattedNumber = formatter.format(myNumber);
                holder.tv_gia.setText(String.valueOf(formattedNumber +"đ"));

                //trang thái
                holder.tv_trang_thai.setText(model.getTrang_thai());
                //chưa thanh toán đổi sang màu đỏ
                if(model.getTrang_thai().equals("Chưa thanh toán")){
                    holder.tv_trang_thai.setTextColor(Color.parseColor("#E53935"));
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

}
