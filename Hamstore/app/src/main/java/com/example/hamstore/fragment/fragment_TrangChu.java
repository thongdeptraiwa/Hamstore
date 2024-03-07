package com.example.hamstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.ADT.ADT_Recyclerview;
import com.example.hamstore.R;
import com.example.hamstore.TrangChu;
import com.example.hamstore.model.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_TrangChu extends Fragment {
    TrangChu trangChu;
    Context c;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private final String key_hamster = "Hamster", key_phuKien = "Phụ kiện", key_thucAn = "Thức ăn";
    ADT_Recyclerview hamster_recyclerview,phuKien_recyclerview,thucAn_recyclerview;
    ArrayList<Items> ds_hamster = new ArrayList<>();
    ArrayList<Items> ds_phukien = new ArrayList<>();
    ArrayList<Items> ds_thucAn = new ArrayList<>();

    RecyclerView recyclerView_Hamster,recyclerView_PhuKien,recyclerView_ThucAn;
    ImageView img_Hamster,img_PhuKien,img_ThucAn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu,container,false);
        c = getActivity();

        //ánh xạ
        trangChu = (TrangChu) getActivity();
        recyclerView_Hamster = view.findViewById(R.id.recyclerView_Hamster);
        recyclerView_PhuKien = view.findViewById(R.id.recyclerView_PhuKien);
        recyclerView_ThucAn = view.findViewById(R.id.recyclerView_ThucAn);
        img_Hamster = view.findViewById(R.id.img_Hamster);
        img_PhuKien = view.findViewById(R.id.img_PhuKien);
        img_ThucAn = view.findViewById(R.id.img_ThucAn);


        //chỉnh kích thước img trong Edit vừa
//        Edit_Search.getViewTreeObserver()
//                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        Drawable img = c.getResources().getDrawable(
//                                R.drawable.ic_search_gray);
//                        img.setBounds(0, 0, img.getIntrinsicWidth() * Edit_Search.getMeasuredHeight() / img.getIntrinsicHeight(), Edit_Search.getMeasuredHeight());
//                        Edit_Search.setCompoundDrawables(img, null, null, null);
//                        Edit_Search.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    }
//                });

        //chuyển fragment 2 hàng
        img_Hamster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_2hang_hamster();
            }
        });
        img_PhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_2hang_phukien();
            }
        });
        img_ThucAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_2hang_thucan();
            }
        });

        capNhatLayout();

        return view;
    }
    public void capNhatLayout(){

        //read data
        read_data();

        //recyclerView hamster
        LinearLayoutManager linearLayoutManager_Hamster = new LinearLayoutManager(c);
        linearLayoutManager_Hamster.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView_Hamster.setLayoutManager(linearLayoutManager_Hamster);
        hamster_recyclerview = new ADT_Recyclerview(c,ds_hamster);
        recyclerView_Hamster.setAdapter(hamster_recyclerview);

        //recyclerView phụ kiện
        LinearLayoutManager linearLayoutManager_PhuKien = new LinearLayoutManager(c);
        linearLayoutManager_PhuKien.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView_PhuKien.setLayoutManager(linearLayoutManager_PhuKien);
        phuKien_recyclerview = new ADT_Recyclerview(c,ds_phukien);
        recyclerView_PhuKien.setAdapter(phuKien_recyclerview);

        //recyclerView phụ kiện
        LinearLayoutManager linearLayoutManager_ThucAn = new LinearLayoutManager(c);
        linearLayoutManager_ThucAn.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView_ThucAn.setLayoutManager(linearLayoutManager_ThucAn);
        thucAn_recyclerview = new ADT_Recyclerview(c,ds_thucAn);
        recyclerView_ThucAn.setAdapter(thucAn_recyclerview);

    }
    private void read_data(){

        //hamster
        myRef.child(key_hamster).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ds_hamster.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Items item_hamster = snapshot.getValue(Items.class);
                    ds_hamster.add(item_hamster);
                }
                //cập nhật lại APT liên tục
                hamster_recyclerview.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });

        //phụ kiện
        myRef.child(key_phuKien).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ds_phukien.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Items item_phukien = snapshot.getValue(Items.class);
                    ds_phukien.add(item_phukien);
                }
                //cập nhật lại APT liên tục
                phuKien_recyclerview.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });

        //thức ăn
        myRef.child(key_thucAn).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ds_thucAn.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Items item_thucan = snapshot.getValue(Items.class);
                    ds_thucAn.add(item_thucan);
                }
                //cập nhật lại APT liên tục
                thucAn_recyclerview.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });

    }
    private void add_Items(){

        String id = myRef.child(key_hamster).push().getKey();
        String ten = "Hamster 6";
        String img = "carrot";
        int gia = 100;
        String mieu_ta = "mieu ta .... 6";

        myRef.child(key_hamster).child(id).setValue(new Items(id,ten,img,gia,mieu_ta))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(c, "Add items thanh cong", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(c, "Add items thai bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
