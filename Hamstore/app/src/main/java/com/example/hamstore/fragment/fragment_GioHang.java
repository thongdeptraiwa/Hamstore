package com.example.hamstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.ADT.ADT_Recyclerview_2hang;
import com.example.hamstore.ADT.ADT_Recyclerview_gio_hang;
import com.example.hamstore.ADT.ADT_Recyclerview_loai_hamster;
import com.example.hamstore.R;
import com.example.hamstore.TrangChu;
import com.example.hamstore.model.Items;
import com.example.hamstore.model.User;

import java.util.ArrayList;

public class fragment_GioHang extends Fragment {
    TrangChu trangChu;
    Context c;
    ArrayList<Items> ds_goc,ds_tam = new ArrayList<>();
    ADT_Recyclerview_gio_hang adt_recyclerview_gio_hang;
    RecyclerView recyclerView_gio_hang;

    public fragment_GioHang() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang,container,false);
        c = (TrangChu) getActivity();

        //ánh xạ
        trangChu = (TrangChu) getActivity();
        recyclerView_gio_hang=view.findViewById(R.id.recyclerView_gio_hang);

        capNhatLayout();

        return view;
    }
    public void capNhatLayout(){

//        //read data
//        ds_goc = trangChu.user.getGio_hang();
//        // ko lấy gio_hang thứ 0 của user
//        for (int i=0; i<ds_goc.size(); i++ ){
//            if (i!=0){
//                ds_tam.add(ds_goc.get(i));
//            }
//        }
//
//        //recyclerView giohang
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView_gio_hang.setLayoutManager(linearLayoutManager);
//        adt_recyclerview_gio_hang = new ADT_Recyclerview_gio_hang(c,ds_tam);
//        recyclerView_gio_hang.setAdapter(adt_recyclerview_gio_hang);

    }

}
