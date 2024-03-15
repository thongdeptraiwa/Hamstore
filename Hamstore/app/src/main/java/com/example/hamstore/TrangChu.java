package com.example.hamstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hamstore.fragment.fragment_GioHang2;
import com.example.hamstore.fragment.fragment_Thong_tin;
import com.example.hamstore.fragment.fragment_TrangChu;
import com.example.hamstore.fragment.fragment_TrangChu_2hang_Hamster;
import com.example.hamstore.fragment.fragment_TrangChu_2hang_Loai_Hamster;
import com.example.hamstore.fragment.fragment_TrangChu_2hang_PhuKien;
import com.example.hamstore.fragment.fragment_TrangChu_2hang_ThucAn;
import com.example.hamstore.model.Items;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrangChu extends AppCompatActivity {
    Context c= this;
    BottomNavigationView bottomNavigationView;
    private final String key_bundle_object = "object";

    //tai_khoan user
    private final String key_tai_khoan = "tai_khoan";
    public String tai_khoan;

    //loai hamster
    public String loai_hamster = "";

    //xóa sp giỏ hàng
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private final String key_users = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu);

        //ánh xạ
        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        //lấy data user
        tai_khoan = (String) getIntent().getStringExtra(key_tai_khoan);


        //fragment mặc định khi vào app
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fragment_TrangChu()).commit();



        //nhan bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new fragment_TrangChu();
                if(item.getItemId() == R.id.mTrangChu){
                    fragment = new fragment_TrangChu();
                }
                else if (item.getItemId() == R.id.mThongTin){
                    fragment = new fragment_Thong_tin();
                }
                else if (item.getItemId() == R.id.mGioHang){
                    fragment = new fragment_GioHang2();
                }

                Bundle bundle = new Bundle();
                bundle.putString(key_tai_khoan,tai_khoan);
                fragment.setArguments(bundle);
                //nhúng fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();

                return true;
            }
        });


    }

    public void chuyen_fragment_2hang_loai_hamster(){
        Fragment fragment = new fragment_TrangChu_2hang_Loai_Hamster();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_2hang_hamster(String loai){
        loai_hamster = loai;
        Fragment fragment = new fragment_TrangChu_2hang_Hamster();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_2hang_phukien(){
        Fragment fragment = new fragment_TrangChu_2hang_PhuKien();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_2hang_thucan(){
        Fragment fragment = new fragment_TrangChu_2hang_ThucAn();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_TrangChu(){
        Fragment fragment = new fragment_TrangChu();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_ac_chitiet(Items item){
        Intent intent = new Intent(TrangChu.this, AC_ChiTiet.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key_bundle_object,item);
        bundle.putString(key_tai_khoan,tai_khoan);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void xoa_1_sp_gio_hang(int index){

//        //do arr[0] bỏ qua nên index+1
//        int i = index + 1;
//        user.getGio_hang().remove(i);
//
//        //User(tai_khoan,
//        //     mat_khau,
//        //     arr_item,
//        //     gmail,
//        //     ho_ten,
//        //     ngay_sinh,
//        //     dia_chi,
//        //     role) // 0: user - 1: admin
//        myRef.child(key_users).child(user.getTai_khoan()).setValue(new User(user.getTai_khoan(),
//                        user.getMat_khau(),
//                        user.getGio_hang(),
//                        user.getGmail(),
//                        user.getHo_ten(),
//                        user.getNgay_sinh(),
//                        user.getDia_chi(),
//                        user.getRole()))
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(c, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(c, "Xóa thấi bại!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        //quay về giỏ hàng
        //chuyen_fragment_GioHang();

    }

    public void dang_xuat(){
        finish();
    }

}