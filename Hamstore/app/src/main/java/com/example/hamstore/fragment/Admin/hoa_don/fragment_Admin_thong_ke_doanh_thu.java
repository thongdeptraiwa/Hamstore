package com.example.hamstore.fragment.Admin.hoa_don;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hamstore.ADT.ADT_Recyclerview_sp_trong_hoa_don;
import com.example.hamstore.Activity.Login.AC_dang_nhap;
import com.example.hamstore.Activity.Users.TrangChu;
import com.example.hamstore.R;
import com.example.hamstore.model.Hoa_Don;
import com.example.hamstore.model.User;
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
import java.util.Date;


public class fragment_Admin_thong_ke_doanh_thu extends Fragment {
    Context c;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference data = firebaseDatabase.getReference("Hóa đơn");
    Button btn_thong_ke;
    TextInputEditText inputEdit_tu,inputEdit_den;
    public SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    //Tong tien
    int tong_doanh_thu = 0;
    TextView tv_tong_doanh_thu;
    Boolean flat_tong_doanh_thu=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_thong_ke_doanh_thu,container,false);

        c = getActivity();

        //ánh xạ
        recyclerView=view.findViewById(R.id.recyclerView);
        btn_thong_ke=view.findViewById(R.id.btn_thong_ke);
        inputEdit_tu=view.findViewById(R.id.inputEdit_tu);
        inputEdit_den=view.findViewById(R.id.inputEdit_den);
        tv_tong_doanh_thu=view.findViewById(R.id.tv_tong_doanh_thu);


        //nhấn thống kê
        btn_thong_ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reset_tong_doanh_thu();

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
                    Date date_tu = format.parse(tu);
                    Date date_den = format.parse(den);
                    thong_ke_doanh_thu(date_tu,date_den);
                    //tổng doanh thu
                    flat_tong_doanh_thu=true;
                    check_doanh_thu(date_tu,date_den);

                } catch (ParseException e) {
                    //lỗi nhập
                    Toast.makeText(c, "lỗi nhập sai dạng dd/mm/yyyy", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }

    public void thong_ke_doanh_thu(Date tu,Date den) {

        FirebaseRecyclerOptions<Hoa_Don> options =
                new FirebaseRecyclerOptions.Builder<Hoa_Don>()
                        .setQuery(data, Hoa_Don.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Hoa_Don, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.items_admin_hoa_don, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, int position, Hoa_Don model) {

                //chỉ lấy hóa đơn đã thanh toán xong
                if(model.getTrang_thai().equals("Đã giao hàng")){

                    String time = model.getThoi_gian();
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

                            //hien thi
                            holder.tv_id.setText("ID: "+model.getId());
                            holder.tv_tai_khoan.setText("User: "+model.getId_user());
                            //tong tien
                            NumberFormat formatter = new DecimalFormat("#,###");
                            //int myNumber = ds.get(i).getGia();
                            int myNumber = model.getTong_tien();
                            String formattedNumber = formatter.format(myNumber);
                            holder.tv_gia.setText(String.valueOf(formattedNumber +"đ"));

                            //trang thái
                            holder.tv_trang_thai.setText(model.getTrang_thai());


                            //nhấn item hiện dialog chi tiết
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog_thoat(model);
                                }
                            });

                        }else {
                            holder.itemView.setVisibility(View.GONE);
                            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                        }



                    } catch (ParseException e) {
                        //lỗi data
                        Toast.makeText(c, "lỗi data!", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                }



            }

        };

        //recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tai_khoan,tv_id,tv_gia,tv_trang_thai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tai_khoan = itemView.findViewById(R.id.tv_tai_khoan);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tv_trang_thai = itemView.findViewById(R.id.tv_trang_thai);
        }
    }

    private void check_doanh_thu(Date tu,Date den){

        //check data
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    if(flat_tong_doanh_thu==true){

                        Hoa_Don hoa_don = snapshot.getValue(Hoa_Don.class);
                        if(hoa_don.getTrang_thai().equals("Đã giao hàng")){
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

                                    //Tổng doanh thu
                                    tong_doanh_thu+= hoa_don.getTong_tien();
                                    load_tong_doanh_thu();

                                }
                            } catch (ParseException e) {
                                //lỗi data
                                Toast.makeText(c, "lỗi data!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }

                //stop
                flat_tong_doanh_thu=false;
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });



    }

    private void dialog_thoat(Hoa_Don hoaDon){

        //tạo dialog
        Dialog dialog = new Dialog((Activity)c);
        dialog.setContentView(R.layout.dialog_hoa_don_thoat);
        dialog.setCanceledOnTouchOutside(false);//nhấn ra ngoài ko tắc dialog
        dialog.show();

        //ánh xạ thông tin NV
        Button btn_thoat = dialog.findViewById(R.id.btn_thoat);
        TextInputEditText inputEdit_id = dialog.findViewById(R.id.inputEdit_id);
        TextInputEditText inputEdit_nguoi_mua = dialog.findViewById(R.id.inputEdit_nguoi_mua);
        TextInputEditText inputEdit_thoi_gian = dialog.findViewById(R.id.inputEdit_thoi_gian);
        TextInputEditText inputEdit_sdt = dialog.findViewById(R.id.inputEdit_sdt);
        TextInputEditText inputEdit_dia_chi = dialog.findViewById(R.id.inputEdit_dia_chi);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        TextInputEditText inputEdit_tong_tien = dialog.findViewById(R.id.inputEdit_tong_tien);
        TextInputEditText inputEdit_trang_thai = dialog.findViewById(R.id.inputEdit_trang_thai);


        //text
        inputEdit_id.setText(hoaDon.getId());
        inputEdit_nguoi_mua.setText(hoaDon.getId_user());
        inputEdit_thoi_gian.setText(hoaDon.getThoi_gian());
        inputEdit_sdt.setText(String.valueOf(hoaDon.getSdt()));
        inputEdit_dia_chi.setText(String.valueOf(hoaDon.getDia_chi()));

        inputEdit_tong_tien.setText(String.valueOf(hoaDon.getTong_tien()));
        inputEdit_trang_thai.setText(String.valueOf(hoaDon.getTrang_thai()));

        //đổi màu trạng thái hủy bỏ
        if(inputEdit_trang_thai.getText().equals("Hủy bỏ")){
            inputEdit_trang_thai.setTextColor(Color.parseColor("#E53935"));
        }


        //recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ADT_Recyclerview_sp_trong_hoa_don adtRecyclerviewSpTrongHoaDon = new ADT_Recyclerview_sp_trong_hoa_don(c,hoaDon.getArr_items());
        recyclerView.setAdapter(adtRecyclerviewSpTrongHoaDon);


        //nhấn
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void load_tong_doanh_thu(){

        //giá tổng tiền
        NumberFormat formatter = new DecimalFormat("#,###");
        //int myNumber = ds.get(i).getGia();
        int myNumber = tong_doanh_thu;
        String formattedNumber = formatter.format(myNumber);
        tv_tong_doanh_thu.setText(String.valueOf(formattedNumber +"đ"));

    }
    private void reset_tong_doanh_thu(){
        tong_doanh_thu = 0;
        load_tong_doanh_thu();
    }

}
