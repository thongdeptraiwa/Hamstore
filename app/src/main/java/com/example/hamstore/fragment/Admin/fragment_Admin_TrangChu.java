package com.example.hamstore.fragment.Admin;

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
import com.example.hamstore.Activity.Admin.TrangChu_Admin;


public class fragment_Admin_TrangChu extends Fragment {
    Context c;
    TrangChu_Admin trangChuAdmin;
    Button btn_san_pham,btn_nguoi_dung,btn_hoa_don,btn_thong_ke;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_trang_chu,container,false);

        c = getActivity();

        //ánh xạ
        trangChuAdmin = (TrangChu_Admin) getActivity();
        btn_san_pham=view.findViewById(R.id.btn_san_pham);
        btn_nguoi_dung=view.findViewById(R.id.btn_nguoi_dung);
        btn_hoa_don=view.findViewById(R.id.btn_hoa_don);
        btn_thong_ke=view.findViewById(R.id.btn_thong_ke);

        //nhấn
        btn_san_pham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChuAdmin.chuyen_fragment_admin_tablayout_san_pham();
            }
        });
        btn_nguoi_dung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChuAdmin.chuyen_fragment_admin_tablayout_nguoi_dung();
            }
        });
        btn_hoa_don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChuAdmin.chuyen_fragment_admin_tablayout_hoa_don();
            }
        });
        btn_thong_ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChuAdmin.chuyen_fragment_admin_tablayout_thong_ke();
            }
        });

        return view;
    }


}
