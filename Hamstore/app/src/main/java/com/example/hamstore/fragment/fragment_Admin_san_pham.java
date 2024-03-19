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


public class fragment_Admin_san_pham extends Fragment {
    Context c;
    TrangChu_Admin trangChuAdmin;
    Button btn_loai_hamster,btn_phu_kien,btn_thuc_an;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_san_pham,container,false);

        c = getActivity();

        //ánh xạ
        trangChuAdmin = (TrangChu_Admin) getActivity();
        btn_loai_hamster=view.findViewById(R.id.btn_loai_hamster);
        btn_phu_kien=view.findViewById(R.id.btn_phu_kien);
        btn_thuc_an=view.findViewById(R.id.btn_thuc_an);

        //nhấn
        btn_loai_hamster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChuAdmin.chuyen_fragment_admin_loai_hamster();
            }
        });
        btn_phu_kien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChuAdmin.chuyen_fragment_admin_phu_kien();
            }
        });
        btn_thuc_an.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChuAdmin.chuyen_fragment_admin_thuc_an();
            }
        });

        return view;
    }


}
