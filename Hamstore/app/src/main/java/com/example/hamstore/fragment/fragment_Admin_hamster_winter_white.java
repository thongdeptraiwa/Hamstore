package com.example.hamstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.R;
import com.example.hamstore.model.Items;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class fragment_Admin_hamster_winter_white extends Fragment {
    Context c;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference data = firebaseDatabase.getReference("Hamster Winter White");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_list,container,false);

        c = getActivity();

        //ánh xạ
        recyclerView=view.findViewById(R.id.recyclerView);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(data, Items.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Items, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.items_admin_san_pham, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, int position, Items model) {

                //hiển thị
                //gia
                NumberFormat formatter = new DecimalFormat("#,###");
                //int myNumber = ds.get(i).getGia();
                int myNumber = model.getGia();
                String formattedNumber = formatter.format(myNumber);
                holder.tv_gia.setText(String.valueOf(formattedNumber +"đ"));

                //name
                //holder.tv_ten_dai.setText(ds.get(i).getTen_dai());
                holder.tv_ten_dai.setText(model.getTen_dai());

                //lấy SrcImg
                //String imgName = ds.get(i).getSrcImg();
                String imgName = model.getSrcImg();
                //đổi string thành int (R.drawable.name)
                int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
                holder.img.setImageResource(imgId);

                //nhấn xóa
                holder.img_khung_rac.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.child(model.getId()).removeValue();
                        Toast.makeText(c, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        };

        //recyclerView giohang
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ten_dai,tv_gia;
        ImageView img,img_khung_rac;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_dai = itemView.findViewById(R.id.tv_ten_dai);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            img = itemView.findViewById(R.id.img);
            img_khung_rac = itemView.findViewById(R.id.img_khung_rac);
        }
    }

}
