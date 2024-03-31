package com.example.hamstore.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hamstore.Activity.Login.AC_dang_nhap;
import com.example.hamstore.R;
import com.example.hamstore.fragment.Admin.TabLayout.fragment_Admin_TabLayout_hoa_don;
import com.example.hamstore.fragment.Admin.TabLayout.fragment_Admin_TabLayout_nguoi_dung;
import com.example.hamstore.fragment.Admin.TabLayout.fragment_Admin_TabLayout_san_pham;
import com.example.hamstore.fragment.Admin.fragment_Admin_TrangChu;
import com.google.android.material.navigation.NavigationView;

public class TrangChu_Admin extends AppCompatActivity {
    Context c=this;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu_admin);

        //ánh xạ
        drawerLayout=findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navigationView);

        //menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);

        //fragment mặc định khi phải app
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fragment_Admin_TrangChu()).commit();

        //nhan navigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = new fragment_Admin_TrangChu();

                if(item.getItemId() == R.id.mTrangChu){
                    fragment = new fragment_Admin_TrangChu();

                }else if (item.getItemId() == R.id.mSanPham){
                    fragment = new fragment_Admin_TabLayout_san_pham();

                }else if (item.getItemId() == R.id.mNguoiDung){
                    fragment = new fragment_Admin_TabLayout_nguoi_dung();

                }else if (item.getItemId() == R.id.mHoaDon){
                    fragment = new fragment_Admin_TabLayout_hoa_don();

                }else if (item.getItemId() == R.id.mDangXuat) {
                    dialog_thong_bao_dang_xuat();

                }

                //đổi title
                getSupportActionBar().setTitle(item.getTitle());

                //nhúng fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
                //tắc drawer khi nhan xong
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //android.R.id.home là icon 3 que (tên mặc định của hệ thống)
        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    public void chuyen_fragment_admin_tablayout_nguoi_dung(){
        Fragment fragment = new fragment_Admin_TabLayout_nguoi_dung();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_admin_tablayout_hoa_don(){
        Fragment fragment = new fragment_Admin_TabLayout_hoa_don();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_admin_tablayout_san_pham(){
        Fragment fragment = new fragment_Admin_TabLayout_san_pham();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }


    private void dialog_thong_bao_dang_xuat(){
        //tạo dialog thông báo đăng xuat
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
        alertDialogBuilder.setTitle("Thông Báo!");
        alertDialogBuilder.setMessage("Bạn có chắc muốn đăng xuất không?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(c, AC_dang_nhap.class));
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}