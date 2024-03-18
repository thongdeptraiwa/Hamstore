package com.example.hamstore.fragment;

import android.content.Context;
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
import java.util.Collections;
import java.util.Comparator;

public class fragment_TrangChu_2hang_ban_chay extends Fragment {
    TrangChu trangChu;
    Context c;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    ArrayList<Items> ds_tong = new ArrayList<>();
    ArrayList<Items> ds_top_5 = new ArrayList<>();
    ADT_Recyclerview_2hang adt_recyclerview_2hang;
    RecyclerView recyclerView_2hang;
    ImageView img_back;
    TextView tv_title;
    Boolean check_winter = false;
    Boolean check_robo = false;
    Boolean check_bear = false;
    Boolean check_campbell = false;
    Boolean check_phu_kien = false;
    Boolean check_thuc_an = false;

    private final String key_hamster_winter_white = "Hamster Winter White",
            key_hamster_robo = "Hamster Robo",
            key_hamster_bear = "Hamster Bear",
            key_hamster_campbell = "Hamster Campbell",
            key_phuKien = "Phụ kiện",
            key_thucAn = "Thức ăn";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu_2hang,container,false);
        c = getActivity();
        //ánh xạ
        trangChu = (TrangChu) getActivity();
        recyclerView_2hang=view.findViewById(R.id.recyclerView_2hang);
        img_back=view.findViewById(R.id.img_back);
        tv_title=view.findViewById(R.id.tv_title);

        //đổi title
        tv_title.setText("Top 5");

        //onclick img back
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_TrangChu();
            }
        });

        //cập nhật Layout
        capNhatLayout();

        return view;
    }
    public void capNhatLayout(){

        //read data
        read_data();

        //recycleview Grid
        GridLayoutManager linearLayoutManager = new GridLayoutManager(c,
                2,
                GridLayoutManager.VERTICAL,
                false
        );
        recyclerView_2hang.setLayoutManager(linearLayoutManager);
        adt_recyclerview_2hang = new ADT_Recyclerview_2hang(c,ds_top_5);
        recyclerView_2hang.setAdapter(adt_recyclerview_2hang);

    }
    private void read_data(){

        //reset
        ds_tong.clear();

        //lấy hết item trong data
        //hamster_winter_white
        data.child(key_hamster_winter_white).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Items item_hamster = snapshot.getValue(Items.class);
                    ds_tong.add(item_hamster);
                }
                check_winter=true;
                top_5();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });
        //hamster_robo
        data.child(key_hamster_robo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Items item_hamster = snapshot.getValue(Items.class);
                    ds_tong.add(item_hamster);
                }
                check_robo=true;
                top_5();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });
        //hamster_bear
        data.child(key_hamster_bear).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Items item_hamster = snapshot.getValue(Items.class);
                    ds_tong.add(item_hamster);
                }
                check_bear=true;
                top_5();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });
        //hamster_campbell
        data.child(key_hamster_campbell).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Items item_hamster = snapshot.getValue(Items.class);
                    ds_tong.add(item_hamster);
                }
                check_campbell=true;
                top_5();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });
        //phuKien
        data.child(key_phuKien).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Items item_hamster = snapshot.getValue(Items.class);
                    ds_tong.add(item_hamster);
                }
                check_phu_kien=true;
                top_5();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });
        //thucAn
        data.child(key_thucAn).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Items item_hamster = snapshot.getValue(Items.class);
                    ds_tong.add(item_hamster);
                }
                check_thuc_an=true;
                top_5();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });


    }
    private void top_5(){

        if(check_winter==true && check_robo==true
                && check_bear==true && check_campbell==true
                && check_phu_kien==true && check_thuc_an==true){

            //reset
            ds_top_5.clear();

            //sắp xếp ds_tong từ lớn đến nhỏ (đã bán)
            Collections.sort(ds_tong, new Comparator<Items>() {
                @Override
                public int compare(Items item1, Items item2) {
                    if (item1.getSo_luong_da_mua() < item2.getSo_luong_da_mua()) {
                        return 1;
                    } else {
                        if (item1.getSo_luong_da_mua() == item2.getSo_luong_da_mua()) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                }
            });

            //lấy top5
            for(int i=0; i <5; i++){
                ds_top_5.add(ds_tong.get(i));
                //cập nhật lại APT liên tục
                adt_recyclerview_2hang.notifyDataSetChanged();
            }
            //reset lại check
            check_winter = false;
            check_robo = false;
            check_bear = false;
            check_campbell = false;
            check_phu_kien = false;
            check_thuc_an = false;
        }

    }

}
