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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.ADT.ADT_Recyclerview_2hang;
import com.example.hamstore.R;
import com.example.hamstore.Activity.Users.TrangChu;
import com.example.hamstore.model.Items;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_TrangChu_2hang_ThucAn extends Fragment {
    TrangChu trangChu;
    Context c;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    private final String key_thucan = "Thức ăn";
    ArrayList<Items> ds = new ArrayList<>();
    ADT_Recyclerview_2hang adt_recyclerview_2hang;
    RecyclerView recyclerView_2hang;
    ImageView img_back;
    TextView tv_title;
    //Search
    SearchView searchView;
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
        searchView=view.findViewById(R.id.searchView);

        capNhatLayout();

        //đổi title
        tv_title.setText("Thức ăn");

        //onclick img back
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_TrangChu();
            }
        });

        //Search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adt_recyclerview_2hang.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adt_recyclerview_2hang.getFilter().filter(newText);
                return false;
            }
        });


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
        adt_recyclerview_2hang = new ADT_Recyclerview_2hang(c,ds);
        recyclerView_2hang.setAdapter(adt_recyclerview_2hang);

    }
    private void read_data(){

        //thuc ăn
        data.child(key_thucan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ds.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Items item_hamster = snapshot.getValue(Items.class);
                    ds.add(item_hamster);
                }
                //cập nhật lại APT liên tục
                adt_recyclerview_2hang.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });


    }
}
