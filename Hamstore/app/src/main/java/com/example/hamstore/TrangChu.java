package com.example.hamstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.hamstore.fragment.fragment_TrangChu;
import com.example.hamstore.fragment.fragment_TrangChu_2hang_Hamster;
import com.example.hamstore.fragment.fragment_TrangChu_2hang_PhuKien;
import com.example.hamstore.fragment.fragment_TrangChu_2hang_ThucAn;
import com.example.hamstore.model.Items;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class TrangChu extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private final String key_bundle_object = "object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu);

        //ánh xạ
        bottomNavigationView=findViewById(R.id.bottomNavigationView);


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
//                else if (item.getItemId() == R.id.mCart){
//                    //fragment = new fragment_caidat();
//                }
//                else if (item.getItemId() == R.id.mProfile){
//                    //fragment = new fragment_danhsachve();
//                }

                //nhúng fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();

                return true;
            }
        });
    }

    public void chuyen_fragment_2hang_hamster(){
        Fragment fragment = new fragment_TrangChu_2hang_Hamster();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
        //hiện bottomNavigationView
        hienBottomNavigationView(true);
    }
    public void chuyen_fragment_2hang_phukien(){
        Fragment fragment = new fragment_TrangChu_2hang_PhuKien();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
        //hiện bottomNavigationView
        hienBottomNavigationView(true);
    }
    public void chuyen_fragment_2hang_thucan(){
        Fragment fragment = new fragment_TrangChu_2hang_ThucAn();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
        //hiện bottomNavigationView
        hienBottomNavigationView(true);
    }
    public void chuyen_fragment_TrangChu(){
        Fragment fragment = new fragment_TrangChu();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
        //hiện bottomNavigationView
        hienBottomNavigationView(true);
    }
    public void chuyen_ac_chitiet(Items item){
        Intent intent = new Intent(TrangChu.this, AC_ChiTiet.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key_bundle_object,item);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void hienBottomNavigationView(boolean tf){
        if(tf == true){
            //hiện bottomNavigationView
            bottomNavigationView.setVisibility(View.VISIBLE);
        }else {
            //ẩn bottomNavigationView
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

}