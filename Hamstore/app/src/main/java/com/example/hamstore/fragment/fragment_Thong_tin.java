package com.example.hamstore.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.AC_dang_nhap;
import com.example.hamstore.ADT.ADT_Recyclerview_2hang;
import com.example.hamstore.R;
import com.example.hamstore.TrangChu;
import com.example.hamstore.model.Items;
import com.example.hamstore.model.Loai_Hamster;
import com.example.hamstore.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;

public class fragment_Thong_tin extends Fragment {
    TrangChu trangChu;
    Context c;
    TextView tv_ho_ten,tv_dang_xuat;
    ImageView img_dang_xuat;
    private final String key_tai_khoan = "tai_khoan";
    String tai_khoan;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin,container,false);
        c = getActivity();
        //ánh xạ
        trangChu = (TrangChu) getActivity();
        tv_ho_ten = view.findViewById(R.id.tv_ho_ten);
        tv_dang_xuat = view.findViewById(R.id.tv_dang_xuat);
        img_dang_xuat = view.findViewById(R.id.img_dang_xuat);

        //lấy tài khoản từ activity
        tai_khoan = getArguments().getString(key_tai_khoan);

        //nhấn đăng xuất
        tv_dang_xuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(c, AC_dang_nhap.class));
                trangChu.dang_xuat();
            }
        });
        img_dang_xuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(c, AC_dang_nhap.class));
                trangChu.dang_xuat();
            }
        });

        read_data();

        return view;
    }
    private void read_data(){
        //user
        myRef.child(tai_khoan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = (User) dataSnapshot.getValue(User.class);

                if(user.getHo_ten().equals("null")){
                    //lay tai_khoan
                    tv_ho_ten.setText(user.getTai_khoan());
                }else {
                    //lay ho ten
                    tv_ho_ten.setText(user.getHo_ten());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });
    }

}
