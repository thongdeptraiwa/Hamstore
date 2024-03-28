package com.example.hamstore.fragment.Users;

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

import com.example.hamstore.ADT.ADT_Recyclerview_loai_hamster;
import com.example.hamstore.R;
import com.example.hamstore.Activity.Users.TrangChu;
import com.example.hamstore.model.Loai_Hamster;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_TrangChu_2hang_Loai_Hamster extends Fragment {
    TrangChu trangChu;
    Context c;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    private final String key_loai_hamster = "Loại Hamster";
    ArrayList<Loai_Hamster> ds = new ArrayList<>();
    ADT_Recyclerview_loai_hamster adt_recyclerview_loai_hamster;
    RecyclerView recyclerView_loai_hamster;
    ImageView img_back;
    TextView tv_title;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu_2hang,container,false);
        c = getActivity();
        //ánh xạ
        trangChu = (TrangChu) getActivity();
        recyclerView_loai_hamster=view.findViewById(R.id.recyclerView_2hang);
        img_back=view.findViewById(R.id.img_back);
        tv_title=view.findViewById(R.id.tv_title);

        //đổi title
        tv_title.setText("Loại Hamster");

        //onclick img back
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_TrangChu();
            }
        });

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
        recyclerView_loai_hamster.setLayoutManager(linearLayoutManager);
        adt_recyclerview_loai_hamster = new ADT_Recyclerview_loai_hamster(c,ds);
        recyclerView_loai_hamster.setAdapter(adt_recyclerview_loai_hamster);

    }
    private void read_data(){

        //Loại hamster
        data.child(key_loai_hamster).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ds.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Loai_Hamster loai_hamster = snapshot.getValue(Loai_Hamster.class);
                    ds.add(loai_hamster);
                }
                //cập nhật lại APT liên tục
                adt_recyclerview_loai_hamster.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });


    }
}
