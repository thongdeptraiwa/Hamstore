package com.example.hamstore.fragment.Admin.hoa_don;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.ADT.ADT_Recyclerview;
import com.example.hamstore.ADT.ADT_Recyclerview_admin_thong_ke_san_pham;
import com.example.hamstore.ADT.ADT_Recyclerview_loai_hamster;
import com.example.hamstore.ADT.ADT_Recyclerview_sp_trong_hoa_don;
import com.example.hamstore.R;
import com.example.hamstore.model.Hoa_Don;
import com.example.hamstore.model.Items;
import com.example.hamstore.model.Loai_Hamster;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class fragment_Admin_thong_ke_san_pham extends Fragment {
    Context c;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference data = firebaseDatabase.getReference("Hóa đơn");
    Button btn_thong_ke;
    TextInputEditText inputEdit_tu,inputEdit_den;
    public SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    ADT_Recyclerview_admin_thong_ke_san_pham adt_recyclerview_admin_thong_ke_san_pham;
    Boolean flat_read_data=false;
    ArrayList<Items> ds= new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_thong_ke_san_pham,container,false);

        c = getActivity();

        //ánh xạ
        recyclerView=view.findViewById(R.id.recyclerView);
        btn_thong_ke=view.findViewById(R.id.btn_thong_ke);
        inputEdit_tu=view.findViewById(R.id.inputEdit_tu);
        inputEdit_den=view.findViewById(R.id.inputEdit_den);


        //nhấn thống kê
        btn_thong_ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tu = inputEdit_tu.getText().toString().trim();
                String den = inputEdit_den.getText().toString().trim();

                //check
                if(tu.equals("")){
                    Toast.makeText(c, "Bạn chưa nhập từ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(den.equals("")){
                    Toast.makeText(c, "Bạn chưa nhập đến", Toast.LENGTH_SHORT).show();
                    return;
                }

                //đủ đk
                try {
                    //reset lại ds
                    ds.clear();

                    Date date_tu = format.parse(tu);
                    Date date_den = format.parse(den);

                    flat_read_data=true;
                    check_doanh_thu(date_tu,date_den);

                } catch (ParseException e) {
                    //lỗi nhập
                    Toast.makeText(c, "lỗi nhập sai dạng dd/mm/yyyy", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }

    private void check_doanh_thu(Date tu,Date den){

        //check data
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    if(flat_read_data==true){

                        Hoa_Don hoa_don = snapshot.getValue(Hoa_Don.class);
                        //lấy hết hóa đơn trừ "Hủy bỏ"
                        if(!hoa_don.getTrang_thai().equals("Hủy bỏ")){
                            String time = hoa_don.getThoi_gian();
                            //tách thoi_gian thành 2
                            //[0] từ kí tự đầu đến " "
                            //[1] từ " " đến cuối
                            String[] tach_thoi_gian = time.split(" ");

                            try {
                                Date date_hoa_don = format.parse(tach_thoi_gian[1]);

                                //so sánh date
                                //date_hoa_don.after(tu) là date_hoa_don > tu (Boolean)
                                //date_hoa_don.before(den) là date_hoa_don < den (Boolean)
                                if(date_hoa_don.after(tu) && date_hoa_don.before(den)){

                                    if(!ds.isEmpty()){
                                        //ds đã có items
                                        for (Items item_hoa_don:hoa_don.getArr_items()){
                                            boolean flat_trung=false;
                                            for (Items item_ds:ds){
                                                //trùng
                                                if(item_ds.getId().equals(item_hoa_don.getId())){
                                                    flat_trung=true;
                                                    //tăng sl
                                                    int sl_moi = item_ds.getSo_luong() + item_hoa_don.getSo_luong();;
                                                    item_ds.setSo_luong(sl_moi);
                                                }
                                            }
                                            //nếu ko trùng
                                            if(flat_trung==false){
                                                ds.add(item_hoa_don);
                                            }
                                        }

                                    }else {
                                        //ds rỗng
                                        for (Items item:hoa_don.getArr_items()){
                                            ds.add(item);
                                        }
                                    }

                                }
                            } catch (ParseException e) {
                                //lỗi data
                                Toast.makeText(c, "lỗi data!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }

                //recyclerView
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                adt_recyclerview_admin_thong_ke_san_pham = new ADT_Recyclerview_admin_thong_ke_san_pham(c,ds);
                recyclerView.setAdapter(adt_recyclerview_admin_thong_ke_san_pham);
                adt_recyclerview_admin_thong_ke_san_pham.notifyDataSetChanged();

                //stop
                flat_read_data=false;

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });



    }


}
