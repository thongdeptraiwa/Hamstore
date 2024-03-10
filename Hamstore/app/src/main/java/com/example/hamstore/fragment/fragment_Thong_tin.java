package com.example.hamstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.ADT.ADT_Recyclerview_2hang;
import com.example.hamstore.R;
import com.example.hamstore.TrangChu;
import com.example.hamstore.model.Items;
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
    TextView tv_ho_ten;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin,container,false);
        c = getActivity();
        //ánh xạ
        trangChu = (TrangChu) getActivity();
        tv_ho_ten = view.findViewById(R.id.tv_ho_ten);

        //lấy ho ten
        if(trangChu.user.getHo_ten().equals("null")){
            if(trangChu.user.getTai_khoan().equals("null")){
                //lay gmail
                tv_ho_ten.setText(trangChu.user.getGmail());
            }else {
                //lay tai khoan
                tv_ho_ten.setText(trangChu.user.getTai_khoan());
            }
        }else {
            //lay ho ten
            tv_ho_ten.setText(trangChu.user.getHo_ten());
        }

        return view;
    }

}
