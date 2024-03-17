package com.example.hamstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.hamstore.ADT.ADT_Recyclerview;
import com.example.hamstore.ADT.ADT_Recyclerview_loai_hamster;
import com.example.hamstore.ADT.PhotoAdapter;
import com.example.hamstore.R;
import com.example.hamstore.TrangChu;
import com.example.hamstore.model.Items;
import com.example.hamstore.model.Loai_Hamster;
import com.example.hamstore.model.Photo;
import com.example.hamstore.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class fragment_TrangChu extends Fragment {
    TrangChu trangChu;
    Context c;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference data_loai_hamster,data_phu_kien,data_thuc_an;
    DatabaseReference myRef = firebaseDatabase.getReference();
    private final String key_hamster_winter_white = "Hamster Winter White",
            key_hamster_robo = "Hamster Robo",
            key_hamster_bear = "Hamster Bear",
            key_hamster_campbell = "Hamster Campbell",
            key_loai_hamster = "Loại Hamster",
            key_phuKien = "Phụ kiện",
            key_thucAn = "Thức ăn",
            key_users = "Users";
    ADT_Recyclerview phuKien_recyclerview,thucAn_recyclerview;
    ADT_Recyclerview_loai_hamster adt_loai_hamster;
    ArrayList<Loai_Hamster> ds_loai_hamster = new ArrayList<>();
    ArrayList<Items> ds_phukien = new ArrayList<>();
    ArrayList<Items> ds_thucAn = new ArrayList<>();
    RecyclerView recyclerView_Loai_Hamster,recyclerView_PhuKien,recyclerView_ThucAn;
    ImageView img_Loai_Hamster,img_PhuKien,img_ThucAn;

    //slider
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    //auto
    private Handler handler;
    private Runnable runnable;
    private boolean isUserSwiping = false;

    //4 img
    RoundedImageView rdi_ban_chay,rdi_hamster,rdi_phu_kien,rdi_thuc_an;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu,container,false);
        c = getActivity();

        //ánh xạ
        trangChu = (TrangChu) getActivity();
        recyclerView_Loai_Hamster = view.findViewById(R.id.recyclerView_Loai_Hamster);
        recyclerView_PhuKien = view.findViewById(R.id.recyclerView_PhuKien);
        recyclerView_ThucAn = view.findViewById(R.id.recyclerView_ThucAn);
        img_Loai_Hamster = view.findViewById(R.id.img_Loai_Hamster);
        img_PhuKien = view.findViewById(R.id.img_PhuKien);
        img_ThucAn = view.findViewById(R.id.img_ThucAn);
        rdi_ban_chay = view.findViewById(R.id.rdi_ban_chay);
        rdi_hamster = view.findViewById(R.id.rdi_hamster);
        rdi_phu_kien = view.findViewById(R.id.rdi_phu_kien);
        rdi_thuc_an = view.findViewById(R.id.rdi_thuc_an);

        //ánh xạ slider
        viewPager = view.findViewById(R.id.ViewPager);
        circleIndicator = view.findViewById(R.id.circle_indicator);
        //slider
        photoAdapter = new PhotoAdapter(c, getListPhoto());
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());



        //chuyển fragment 2 hàng
        //nhấn >
        img_Loai_Hamster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_2hang_loai_hamster();
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
        //nhấn btn
        rdi_hamster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_2hang_loai_hamster();
            }
        });
        rdi_phu_kien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_2hang_phukien();
            }
        });
        rdi_thuc_an.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangChu.chuyen_fragment_2hang_thucan();
            }
        });

        //tắc auto slider trc khi add
        //add_Items();

        read_data();
        capNhatLayout();

        auto_slider();

        return view;
    }
    public void capNhatLayout(){

        //recyclerView loai hamster
        LinearLayoutManager linearLayoutManager_Hamster = new LinearLayoutManager(c);
        linearLayoutManager_Hamster.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView_Loai_Hamster.setLayoutManager(linearLayoutManager_Hamster);
        adt_loai_hamster = new ADT_Recyclerview_loai_hamster(c,ds_loai_hamster);
        recyclerView_Loai_Hamster.setAdapter(adt_loai_hamster);

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

        data_loai_hamster = firebaseDatabase.getReference(key_loai_hamster);
        //Loại hamster
        data_loai_hamster.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ds_loai_hamster.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Loai_Hamster loai_hamster = snapshot.getValue(Loai_Hamster.class);
                    ds_loai_hamster.add(loai_hamster);
                }
                //cập nhật lại APT liên tục
                adt_loai_hamster.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });

        data_phu_kien = firebaseDatabase.getReference(key_phuKien);
        //phụ kiện
        data_phu_kien.addValueEventListener(new ValueEventListener() {
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

        data_thuc_an = firebaseDatabase.getReference(key_thucAn);
        //thức ăn
        data_thuc_an.addValueEventListener(new ValueEventListener() {
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

        //add_Items();
    }
    private void add_Items(){

        //admin
        String tai_khoan = "admin";
        String mat_khau = "123";

        //User(tai_khoan,
        //     mat_khau,
        //     gmail,
        //     ho_ten,
        //     ngay_sinh,
        //     dia_chi,
        //     role) // 0: user - 1: admin
        myRef.child(key_users).child(tai_khoan).setValue(new User(tai_khoan,mat_khau,"null","null","null","null",1))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(c, "Add admin thanh cong", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(c, "Add admin thai bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //loai hamster
        //loai winter white
        String id_loai_1 = myRef.child(key_loai_hamster).push().getKey();
        String ten_loai_1 = "Winter White";
        String img_loai_1 = "hamster_winter_white_bonglan";
        String khoang_gia_loai_1 = "50.000đ - 150.000đ";

        myRef.child(key_loai_hamster).child(id_loai_1).setValue(new Loai_Hamster(id_loai_1,
                        ten_loai_1,
                        img_loai_1,
                        khoang_gia_loai_1))
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

        //loai Robo
        String id_loai_2 = myRef.child(key_loai_hamster).push().getKey();
        String ten_loai_2 = "Hamster Robo";
        String img_loai_2 = "hamster_robo_isabel";
        String khoang_gia_loai_2 = "170.000đ - 400.000đ";

        myRef.child(key_loai_hamster).child(id_loai_2).setValue(new Loai_Hamster(id_loai_2,
                        ten_loai_2,
                        img_loai_2,
                        khoang_gia_loai_2))
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

        //loai Bear
        String id_loai_3 = myRef.child(key_loai_hamster).push().getKey();
        String ten_loai_3 = "Hamster Bear";
        String img_loai_3 = "hamster_bear_albino";
        String khoang_gia_loai_3 = "125.000đ - 150.000đ";

        myRef.child(key_loai_hamster).child(id_loai_3).setValue(new Loai_Hamster(id_loai_3,
                        ten_loai_3,
                        img_loai_3,
                        khoang_gia_loai_3))
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

        //loai Campbell
        String id_loai_4 = myRef.child(key_loai_hamster).push().getKey();
        String ten_loai_4 = "Hamster Campbell";
        String img_loai_4 = "hamster_campbell_albino";
        String khoang_gia_loai_4 = "80.000đ - 90.000đ";

        myRef.child(key_loai_hamster).child(id_loai_4).setValue(new Loai_Hamster(id_loai_4,
                        ten_loai_4,
                        img_loai_4,
                        khoang_gia_loai_4))
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


        //**** chung ****
        //value chung
        int so_luong = 1;
        int so_luong_trong_kho = 50;
        int so_luong_da_mua = 0;
        int luot_mua = 0;
        int tong_sao = 0;
        int so_lan_danh_gia = 0;
        //chung mieu ta
        String mieu_ta_winter = "Mô tả : Chuột Hamster Winter White là một dòng chuột phổ biến được nhiều người ưa thích. Với một bộ lông xù mượt mà với nhiều màu sắc khác nhau, bộ dạng nhỏ nhắn và cực thân thiện. Những chú chuột Hamster Winter White này sẽ chiếm trọn tình cảm của bạn ngay lần đầu gặp mặt đấy.";
        String mieu_ta_robo = "Mô tả : Chuột Hamster Robo (Roborovski dwarf hamster) có kích thước khá nhỏ. Tính cách hiền lành, hoà đồng và siêu đáng yêu. Rất tinh nghịch và hay làm nhiều trò ngộ nghĩnh. Thích hợp cho mọi lứa tuổi nuôi dưỡng. Có nhiều màu sắc để lựa chọn.";
        String mieu_ta_bear = "Mô tả : Chuột Hamster Bear hay Chuột Gấu là một cái tên nổi tiếng trong thế giới Chuột Hamster đa dạng. Có vẻ ngoài mập mạp đáng yêu cùng những hành động ngộ nghĩnh. Những chú Hamster Bear sẽ là người bạn nhỏ tuyệt vời trong nhà.";
        String mieu_ta_campbell = "Mô tả : Chuột Hamster Campbell (Campbell’s dwarf hamster) hay còn gọi là Chuột Sóc rất phổ biến và được nuôi nhiều nhất. Thân hình nhỏ nhắn, bộ lông mượt mà và những cử chỉ ngộ nghĩnh khiến Campbell trở thành những vật nuôi tuyệt vời trong nhà.\n";


        //hamster winter white
        //ww Bông Lan
        // top 2 (đã mua: 20)
        String id_winter_1 = myRef.child(key_hamster_winter_white).push().getKey();
        String ten_ngan_winter_1 = "WW Bông Lan";
        String ten_dai_winter_1 = "Hamster Winter White (Bông Lan)";
        String img_winter_1 = "hamster_winter_white_bonglan";
        int gia_winter_1 = 100000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_winter_white).child(id_winter_1).setValue(new Items(id_winter_1,
                        ten_ngan_winter_1,
                        ten_dai_winter_1,
                        img_winter_1,
                        gia_winter_1,
                        mieu_ta_winter,
                        so_luong,
                        so_luong_trong_kho,
                        20,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_winter_white))
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

        //hamster winter white
        //ww Sapphire
        // top 1 (đã mua: 25) (so_luong_trong_kho: 5 để test hết hàng)
        String id_winter_2 = myRef.child(key_hamster_winter_white).push().getKey();
        String ten_ngan_winter_2 = "WW Sapphire";
        String ten_dai_winter_2 = "Hamster Winter White (Sapphire)";
        String img_winter_2 = "hamster_winter_white_sapphire";
        int gia_winter_2 = 50000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_winter_white).child(id_winter_2).setValue(new Items(id_winter_2,
                        ten_ngan_winter_2,
                        ten_dai_winter_2,
                        img_winter_2,
                        gia_winter_2,
                        mieu_ta_winter,
                        so_luong,
                        5,
                        25,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_winter_white))
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

        //hamster winter white
        //ww Sóc
        String id_winter_3 = myRef.child(key_hamster_winter_white).push().getKey();
        String ten_ngan_winter_3 = "WW Sóc";
        String ten_dai_winter_3 = "Hamster Winter White (Sóc)";
        String img_winter_3 = "hamster_winter_white_soc";
        int gia_winter_3 = 100000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_winter_white).child(id_winter_3).setValue(new Items(id_winter_3,
                        ten_ngan_winter_3,
                        ten_dai_winter_3,
                        img_winter_3,
                        gia_winter_3,
                        mieu_ta_winter,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_winter_white))
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

        //hamster winter white
        //ww Trà Sữa
        String id_winter_4 = myRef.child(key_hamster_winter_white).push().getKey();
        String ten_ngan_winter_4 = "WW Trà Sữa";
        String ten_dai_winter_4 = "Hamster Winter White (Trà Sữa)";
        String img_winter_4 = "hamster_winter_white_trasua";
        int gia_winter_4 = 130000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_winter_white).child(id_winter_4).setValue(new Items(id_winter_4,
                        ten_ngan_winter_4,
                        ten_dai_winter_4,
                        img_winter_4,
                        gia_winter_4,
                        mieu_ta_winter,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_winter_white))
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

        //hamster winter white
        //ww Trắng Sóc
        String id_winter_5 = myRef.child(key_hamster_winter_white).push().getKey();
        String ten_ngan_winter_5 = "WW Trắng Sóc";
        String ten_dai_winter_5 = "Hamster Winter White (Trắng Sóc)";
        String img_winter_5 = "hamster_winter_white_trangsoc";
        int gia_winter_5 = 130000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_winter_white).child(id_winter_5).setValue(new Items(id_winter_5,
                        ten_ngan_winter_5,
                        ten_dai_winter_5,
                        img_winter_5,
                        gia_winter_5,
                        mieu_ta_winter,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_winter_white))
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

        //hamster winter white
        //ww Vàng Chanh
        String id_winter_6 = myRef.child(key_hamster_winter_white).push().getKey();
        String ten_ngan_winter_6 = "WW Vàng Chanh";
        String ten_dai_winter_6 = "Hamster Winter White (Vàng Chanh)";
        String img_winter_6 = "hamster_winter_white_vangchanh";
        int gia_winter_6 = 150000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_winter_white).child(id_winter_6).setValue(new Items(id_winter_6,
                        ten_ngan_winter_6,
                        ten_dai_winter_6,
                        img_winter_6,
                        gia_winter_6,
                        mieu_ta_winter,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_winter_white))
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

        //hamster Robo
        //Robo Isabel
        String id_robo_1 = myRef.child(key_hamster_robo).push().getKey();
        String ten_ngan_robo_1 = "Robo Isabel";
        String ten_dai_robo_1 = "Hamster Robo (Isabel)";
        String img_robo_1 = "hamster_robo_isabel";
        int gia_robo_1 = 180000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_robo).child(id_robo_1).setValue(new Items(id_robo_1,
                        ten_ngan_robo_1,
                        ten_dai_robo_1,
                        img_robo_1,
                        gia_robo_1,
                        mieu_ta_robo,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_robo))
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

        //hamster Robo
        //Robo Mặt Nâu
        String id_robo_2 = myRef.child(key_hamster_robo).push().getKey();
        String ten_ngan_robo_2 = "Robo Mặt Nâu";
        String ten_dai_robo_2 = "Hamster Robo (Mặt Nâu)";
        String img_robo_2 = "hamster_robo_matnau";
        int gia_robo_2 = 170000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_robo).child(id_robo_2).setValue(new Items(id_robo_2,
                        ten_ngan_robo_2,
                        ten_dai_robo_2,
                        img_robo_2,
                        gia_robo_2,
                        mieu_ta_robo,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_robo))
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

        //hamster Robo
        //Robo Bò Sữa
        String id_robo_3 = myRef.child(key_hamster_robo).push().getKey();
        String ten_ngan_robo_3 = "Robo Bò Sữa";
        String ten_dai_robo_3 = "Hamster Robo (Bò Sữa)";
        String img_robo_3 = "hamster_robo_bosua";
        int gia_robo_3 = 350000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_robo).child(id_robo_3).setValue(new Items(id_robo_3,
                        ten_ngan_robo_3,
                        ten_dai_robo_3,
                        img_robo_3,
                        gia_robo_3,
                        mieu_ta_robo,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_robo))
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

        //hamster Robo
        //Robo Platinum
        String id_robo_4 = myRef.child(key_hamster_robo).push().getKey();
        String ten_ngan_robo_4 = "Robo Platinum";
        String ten_dai_robo_4 = "Hamster Robo (Platinum)";
        String img_robo_4 = "hamster_robo_platinum";
        int gia_robo_4 = 400000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_robo).child(id_robo_4).setValue(new Items(id_robo_4,
                        ten_ngan_robo_4,
                        ten_dai_robo_4,
                        img_robo_4,
                        gia_robo_4,
                        mieu_ta_robo,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_robo))
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

        //hamster Bear
        //Bear Albino
        String id_bear_1 = myRef.child(key_hamster_bear).push().getKey();
        String ten_ngan_bear_1 = "Bear Albino";
        String ten_dai_bear_1 = "Hamster Bear (Albino)";
        String img_bear_1 = "hamster_bear_albino";
        int gia_bear_1 = 125000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_bear).child(id_bear_1).setValue(new Items(id_bear_1,
                        ten_ngan_bear_1,
                        ten_dai_bear_1,
                        img_bear_1,
                        gia_bear_1,
                        mieu_ta_bear,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_bear))
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

        //hamster Bear
        //Bear Kem
        String id_bear_2 = myRef.child(key_hamster_bear).push().getKey();
        String ten_ngan_bear_2 = "Bear Kem";
        String ten_dai_bear_2 = "Hamster Bear (Kem)";
        String img_bear_2 = "hamster_bear_kem";
        int gia_bear_2 = 125000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_bear).child(id_bear_2).setValue(new Items(id_bear_2,
                        ten_ngan_bear_2,
                        ten_dai_bear_2,
                        img_bear_2,
                        gia_bear_2,
                        mieu_ta_bear,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_bear))
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

        //hamster Bear
        //Bear Tam Thể
        String id_bear_3 = myRef.child(key_hamster_bear).push().getKey();
        String ten_ngan_bear_3 = "Bear Tam Thể";
        String ten_dai_bear_3 = "Hamster Bear (Tam Thể)";
        String img_bear_3 = "hamster_bear_tamthe";
        int gia_bear_3 = 125000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_bear).child(id_bear_3).setValue(new Items(id_bear_3,
                        ten_ngan_bear_3,
                        ten_dai_bear_3,
                        img_bear_3,
                        gia_bear_3,
                        mieu_ta_bear,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_bear))
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

        //hamster Bear
        //Bear Vàng
        String id_bear_4 = myRef.child(key_hamster_bear).push().getKey();
        String ten_ngan_bear_4 = "Bear Vàng";
        String ten_dai_bear_4 = "Hamster Bear (Vàng)";
        String img_bear_4 = "hamster_bear_vang";
        int gia_bear_4 = 150000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_bear).child(id_bear_4).setValue(new Items(id_bear_4,
                        ten_ngan_bear_4,
                        ten_dai_bear_4,
                        img_bear_4,
                        gia_bear_4,
                        mieu_ta_bear,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_bear))
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

        //hamster Bear
        //Bear Pied
        String id_bear_5 = myRef.child(key_hamster_bear).push().getKey();
        String ten_ngan_bear_5 = "Bear Pied";
        String ten_dai_bear_5 = "Hamster Bear (Pied)";
        String img_bear_5 = "hamster_bear_pied";
        int gia_bear_5 = 150000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_bear).child(id_bear_5).setValue(new Items(id_bear_5,
                        ten_ngan_bear_5,
                        ten_dai_bear_5,
                        img_bear_5,
                        gia_bear_5,
                        mieu_ta_bear,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_bear))
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

        //hamster Campbell
        //Campbell Albino
        String id_campbell_1 = myRef.child(key_hamster_campbell).push().getKey();
        String ten_ngan_campbell_1 = "Campbell Albino";
        String ten_dai_campbell_1 = "Hamster Campbell (Albino)";
        String img_campbell_1 = "hamster_campbell_albino";
        int gia_campbell_1 = 80000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_campbell).child(id_campbell_1).setValue(new Items(id_campbell_1,
                        ten_ngan_campbell_1,
                        ten_dai_campbell_1,
                        img_campbell_1,
                        gia_campbell_1,
                        mieu_ta_campbell,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_campbell))
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

        //hamster Campbell
        //Campbell Bông Lan
        String id_campbell_2 = myRef.child(key_hamster_campbell).push().getKey();
        String ten_ngan_campbell_2 = "Campbell Bông Lan";
        String ten_dai_campbell_2 = "Hamster Campbell (Bông Lan)";
        String img_campbell_2 = "hamster_campbell_bonglan";
        int gia_campbell_2 = 90000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_campbell).child(id_campbell_2).setValue(new Items(id_campbell_2,
                        ten_ngan_campbell_2,
                        ten_dai_campbell_2,
                        img_campbell_2,
                        gia_campbell_2,
                        mieu_ta_campbell,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_campbell))
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

        //hamster Campbell
        //Campbell Nâu
        String id_campbell_3 = myRef.child(key_hamster_campbell).push().getKey();
        String ten_ngan_campbell_3 = "Campbell Nâu";
        String ten_dai_campbell_3 = "Hamster Campbell (Nâu)";
        String img_campbell_3 = "hamster_campbell_nau";
        int gia_campbell_3 = 80000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_campbell).child(id_campbell_3).setValue(new Items(id_campbell_3,
                        ten_ngan_campbell_3,
                        ten_dai_campbell_3,
                        img_campbell_3,
                        gia_campbell_3,
                        mieu_ta_campbell,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_campbell))
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

        //hamster Campbell
        //Campbell Chocolate
        String id_campbell_4 = myRef.child(key_hamster_campbell).push().getKey();
        String ten_ngan_campbell_4 = "Campbell Chocolate";
        String ten_dai_campbell_4 = "Hamster Campbell (Chocolate)";
        String img_campbell_4 = "hamster_campbell_chocolate";
        int gia_campbell_4 = 90000;

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_hamster_campbell).child(id_campbell_4).setValue(new Items(id_campbell_4,
                        ten_ngan_campbell_4,
                        ten_dai_campbell_4,
                        img_campbell_4,
                        gia_campbell_4,
                        mieu_ta_campbell,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_hamster_campbell))
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

        //Phụ kiện
        //Lót Chuồng Chipsi 1kg
        String id_phu_kien_1 = myRef.child(key_phuKien).push().getKey();
        String ten_ngan_phu_kien_1 = "Chipsi";
        String ten_dai_phu_kien_1 = "Lót Chuồng Chipsi 1kg";
        String img_phu_kien_1 = "chipsi";
        int gia_phu_kien_1 = 60000;
        String mieu_ta_phu_kien_1 = "Mô tả : Sản phẩm Lót Chuồng Mùn Cưa Chipsi có khả năng thấm hút tốt, hương thơm dễ chịu, nguồn gốc tự nhiên an toàn với Hamster và các loài thú nhỏ như Bọ, Sóc, Nhím, Thỏ,… Luôn nằm trong TOP những sản phẩm lót chuồng được tin cậy và yêu thích nhất trên toàn cầu.";

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_phuKien).child(id_phu_kien_1).setValue(new Items(id_phu_kien_1,
                        ten_ngan_phu_kien_1,
                        ten_dai_phu_kien_1,
                        img_phu_kien_1,
                        gia_phu_kien_1,
                        mieu_ta_phu_kien_1,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_phuKien))
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

        //Phụ kiện
        //Lồng Hamster Deluxe
        String id_phu_kien_2 = myRef.child(key_phuKien).push().getKey();
        String ten_ngan_phu_kien_2 = "Lồng Deluxe";
        String ten_dai_phu_kien_2 = "Lồng Hamster Deluxe";
        String img_phu_kien_2 = "deluxe";
        int gia_phu_kien_2 = 500000;
        String mieu_ta_phu_kien_2 = "Mô tả : Mẫu Lồng Hamster Deluxe mới nhất được Hamstore Shop nhập về sẽ là một sự lựa chọn tuyệt vời cho các tín đồ nuôi Hamster. Lồng được thiết kế tỉ mỉ, độ hoàn thiện tốt, chất liệu cao cấp và có nhiều màu sắc cho các bạn thoải mái lựa chọn.";

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_phuKien).child(id_phu_kien_2).setValue(new Items(id_phu_kien_2,
                        ten_ngan_phu_kien_2,
                        ten_dai_phu_kien_2,
                        img_phu_kien_2,
                        gia_phu_kien_2,
                        mieu_ta_phu_kien_2,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_phuKien))
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

        //Phụ kiện
        //Cát Lót Chuồng
        // top 5 (đã mua: 5)
        String id_phu_kien_3 = myRef.child(key_phuKien).push().getKey();
        String ten_ngan_phu_kien_3 = "Cát";
        String ten_dai_phu_kien_3 = "Cát Lót Chuồng";
        String img_phu_kien_3 = "cat";
        int gia_phu_kien_3 = 15000;
        String mieu_ta_phu_kien_3 = "Mô tả : Cát hạt bi tròn là sản phẩm lót chuồng chuyên dụng dành cho chuột Hamster. Cát được sản xuất từ các loại khoáng trong tự nhiên nên rất an toàn, không gây ảnh hưởng tới sức khoẻ của chuột. Ngoài ra còn có tác dụng thấm hút, làm mát rất tốt.";

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_phuKien).child(id_phu_kien_3).setValue(new Items(id_phu_kien_3,
                        ten_ngan_phu_kien_3,
                        ten_dai_phu_kien_3,
                        img_phu_kien_3,
                        gia_phu_kien_3,
                        mieu_ta_phu_kien_3,
                        so_luong,
                        so_luong_trong_kho,
                        5,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_phuKien))
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

        //Phụ kiện
        //Cát Tắm
        String id_phu_kien_4 = myRef.child(key_phuKien).push().getKey();
        String ten_ngan_phu_kien_4 = "Cát Tắm";
        String ten_dai_phu_kien_4 = "Cát Tắm";
        String img_phu_kien_4 = "cat_tam";
        int gia_phu_kien_4 = 19000;
        String mieu_ta_phu_kien_4 = "Mô tả : Cát Joy là sản phẩm cực nổi tiếng và thông dụng dùng để lót chuồng cho các bé Hamster. Công dụng của cát Joy giúp hút ẩm siêu tốc, vón cục nhanh, ít bụi, khử mùi tốt và cực an toàn cho các bé chuột.";

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_phuKien).child(id_phu_kien_4).setValue(new Items(id_phu_kien_4,
                        ten_ngan_phu_kien_4,
                        ten_dai_phu_kien_4,
                        img_phu_kien_4,
                        gia_phu_kien_4,
                        mieu_ta_phu_kien_4,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_phuKien))
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

        //Phụ kiện
        //Nhà Tắm Bằng Nhựa
        String id_phu_kien_5 = myRef.child(key_phuKien).push().getKey();
        String ten_ngan_phu_kien_5 = "Nhà Tắm";
        String ten_dai_phu_kien_5 = "Nhà Tắm Bằng Nhựa";
        String img_phu_kien_5 = "nha_tam";
        int gia_phu_kien_5 = 40000;
        String mieu_ta_phu_kien_5 = "Mô tả : Sản phẩm nhà tắm gấu nhựa cho Hamster được thiết kế rộng rãi, thoải mái bằng chất liệu nhựa dẻo bền đẹp. Giúp các bé Hamster có thể dễ dàng tắm cát hằng ngày.";

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_phuKien).child(id_phu_kien_5).setValue(new Items(id_phu_kien_5,
                        ten_ngan_phu_kien_5,
                        ten_dai_phu_kien_5,
                        img_phu_kien_5,
                        gia_phu_kien_5,
                        mieu_ta_phu_kien_5,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_phuKien))
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

        //Thức ăn
        //Vi Hải Sản
        String id_thuc_an_1 = myRef.child(key_thucAn).push().getKey();
        String ten_ngan_thuc_an_1 = "Vi Hải Sản";
        String ten_dai_thuc_an_1 = "Thức Ăn Vi Hải Sản";
        String img_thuc_an_1 = "vi_hai_san";
        int gia_thuc_an_1 = 40000;
        String mieu_ta_thuc_an_1 = "Mô tả : Thức ăn Hamster hải sản tự trộn gồm nhiều thành phần như: Mè trắng (ruột) không vỏ, sâu khô, ruốc lạt, tôm khô, cá khô, bánh cá, thịt bò viên,… Mang đến hương vị mới lạ thơm ngon và giàu dinh dưỡng. Giúp các bé Hamster ăn nhiều hơn, lông đẹp hơn và mau lớn hơn.";

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_thucAn).child(id_thuc_an_1).setValue(new Items(id_thuc_an_1,
                        ten_ngan_thuc_an_1,
                        ten_dai_thuc_an_1,
                        img_thuc_an_1,
                        gia_thuc_an_1,
                        mieu_ta_thuc_an_1,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_thucAn))
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

        //Thức ăn
        //Ngũ Cốc
        //top 4 (đã mua: 10)
        String id_thuc_an_2 = myRef.child(key_thucAn).push().getKey();
        String ten_ngan_thuc_an_2 = "Ngũ Cốc";
        String ten_dai_thuc_an_2 = "Ngũ Cốc Chuyên Dụng";
        String img_thuc_an_2 = "ngu_coc";
        int gia_thuc_an_2 = 40000;
        String mieu_ta_thuc_an_2 = "Mô tả : Thức ăn Hamster Babies chuyên dùng cho các bé size nhỏ từ 4 – 5 tuần tuổi. Công thức trộn đặc biệt chỉ bao gồm các loại ngũ cốc nhiều dinh dưỡng & rất thơm ngon. Giúp các bé phát triển an toàn và nhanh chóng.";

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_thucAn).child(id_thuc_an_2).setValue(new Items(id_thuc_an_2,
                        ten_ngan_thuc_an_2,
                        ten_dai_thuc_an_2,
                        img_thuc_an_2,
                        gia_thuc_an_2,
                        mieu_ta_thuc_an_2,
                        so_luong,
                        so_luong_trong_kho,
                        10,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_thucAn))
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

        //Thức ăn
        //Thức Ăn Tổng Hợp
        String id_thuc_an_3 = myRef.child(key_thucAn).push().getKey();
        String ten_ngan_thuc_an_3 = "Tổng Hợp";
        String ten_dai_thuc_an_3 = "Thức Ăn Tổng Hợp";
        String img_thuc_an_3 = "tong_hop";
        int gia_thuc_an_3 = 30000;
        String mieu_ta_thuc_an_3 = "Mô tả : Thức ăn Hamster Tổng Hợp 40 thành phần dành riêng cho các bé chuột trưởng thành. Cung cấp một cách đầy đủ nhất lượng dinh dưỡng cần thiết cho các bé Hamster. Dùng làm món ăn chính hằng ngày.";

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_thucAn).child(id_thuc_an_3).setValue(new Items(id_thuc_an_3,
                        ten_ngan_thuc_an_3,
                        ten_dai_thuc_an_3,
                        img_thuc_an_3,
                        gia_thuc_an_3,
                        mieu_ta_thuc_an_3,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_thucAn))
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

        //Thức ăn
        //Thức Ăn Dinh Dưỡng
        // top 3 (đã mua: 15)
        String id_thuc_an_4 = myRef.child(key_thucAn).push().getKey();
        String ten_ngan_thuc_an_4 = "Dinh Dưỡng";
        String ten_dai_thuc_an_4 = "Thức Ăn Dinh Dưỡng";
        String img_thuc_an_4 = "dinh_duong";
        int gia_thuc_an_4 = 60000;
        String mieu_ta_thuc_an_4 = "Mô tả : Thức ăn dinh dưỡng chuyên dùng cho chuột Hamster đến từ thương hiệu Jessie nổi tiếng. Sản phẩm này sẽ mang đến một chế độ ăn tốt nhất dành cho chuột của bạn. Có hai vị trái cây và hải sản cho các bé Hamster đỡ ngán.";

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        myRef.child(key_thucAn).child(id_thuc_an_4).setValue(new Items(id_thuc_an_4,
                        ten_ngan_thuc_an_4,
                        ten_dai_thuc_an_4,
                        img_thuc_an_4,
                        gia_thuc_an_4,
                        mieu_ta_thuc_an_4,
                        so_luong,
                        so_luong_trong_kho,
                        15,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        0,
                        key_thucAn))
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
    //slider
    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.slider1));
        list.add(new Photo(R.drawable.slider2));
        list.add(new Photo(R.drawable.slider3));

        return list;
    }
    private void auto_slider(){

        // Khởi tạo handler
        handler = new Handler();

        // Khởi tạo runnable để tự động chuyển ảnh sau mỗi 2 giây
        runnable = new Runnable() {
            @Override
            public void run() {
                int position = viewPager.getCurrentItem();
                if (position == photoAdapter.getCount() - 1) {
                    position = 0;
                } else {
                    position++;
                }
                viewPager.setCurrentItem(position, true);
                handler.postDelayed(this, 2000); // Chạy lại runnable sau 2 giây
            }
        };

        // Bắt đầu tự động chuyển ảnh
        handler.postDelayed(runnable, 2000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // không cần xử lý ở đây
            }

            @Override
            public void onPageSelected(int position) {
                // không cần xử lý ở đây
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    // Người dùng bắt đầu lướt slider bằng tay
                    isUserSwiping = true;
                    // Hủy bỏ runnable để ngăn việc tự động chuyển ảnh trong khi người dùng đang lướt
                    handler.removeCallbacks(runnable);
                } else if (state == ViewPager.SCROLL_STATE_IDLE && isUserSwiping) {
                    // Người dùng kết thúc lướt slider bằng tay
                    isUserSwiping = false;
                    // Bắt đầu lại runnable để tiếp tục tự động chuyển ảnh sau một khoảng thời gian nhất định
                    handler.postDelayed(runnable, 2000);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy bỏ runnable khi Activity bị tắt
        handler.removeCallbacks(runnable);
    }

    //đang lỗi
    @Override
    public void onPause() {
        super.onPause();
        // Hủy bỏ runnable khi Activity bị tắt
        handler.removeCallbacks(runnable);
    }


}
