package com.example.hamstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hamstore.R;
import com.example.hamstore.TrangChu_Admin;


public class fragment_Admin_loai_hamster extends Fragment {
    Context c;
    TrangChu_Admin trangChuAdmin;
    Button btn_winter_white,btn_robo,btn_bear,btn_campbell;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_loai_hamster,container,false);

        c = getActivity();

        //ánh xạ
        trangChuAdmin = (TrangChu_Admin) getActivity();
        btn_winter_white=view.findViewById(R.id.btn_winter_white);
        btn_robo=view.findViewById(R.id.btn_robo);
        btn_bear=view.findViewById(R.id.btn_bear);
        btn_campbell=view.findViewById(R.id.btn_campbell);

        //nhấn
        btn_winter_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChuAdmin.chuyen_fragment_admin_hamster_winter_white();
            }
        });
        btn_robo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChuAdmin.chuyen_fragment_admin_hamster_robo();
            }
        });
        btn_bear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChuAdmin.chuyen_fragment_admin_hamster_bear();
            }
        });
        btn_campbell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChuAdmin.chuyen_fragment_admin_hamster_campbell();
            }
        });


        return view;
    }


}
