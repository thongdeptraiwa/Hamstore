package com.example.hamstore.fragment;

import android.content.Context;
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
import com.example.hamstore.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class fragment_Admin_nguoi_dung extends Fragment {
    Context c;
    RecyclerView recyclerView;
    String gio_hang = "gio_hang_";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_list,container,false);

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

                //hien thi
                holder.tv_tai_khoan.setText(model.getTai_khoan());

                //ẩn admin
                if(model.getRole() == 1){
                    holder.itemView.setVisibility(View.GONE);
                }
                //xóa
                holder.img_khung_rac.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.child(model.getTai_khoan()).removeValue();
                        //xóa gio hang
                        String ten_gio_hang = gio_hang+model.getTai_khoan();
                        data_gio_hang.child(ten_gio_hang).removeValue();
                    }
                });

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
        TextView tv_tai_khoan;
        ImageView img_khung_rac;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tai_khoan = itemView.findViewById(R.id.tv_tai_khoan);
            img_khung_rac = itemView.findViewById(R.id.img_khung_rac);
        }
    }

}
