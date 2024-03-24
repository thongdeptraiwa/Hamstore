package com.example.hamstore;

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
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hamstore.fragment.fragment_Admin_TrangChu;
import com.example.hamstore.fragment.fragment_Admin_hamster_bear;
import com.example.hamstore.fragment.fragment_Admin_hamster_campbell;
import com.example.hamstore.fragment.fragment_Admin_hamster_robo;
import com.example.hamstore.fragment.fragment_Admin_hamster_winter_white;
import com.example.hamstore.fragment.fragment_Admin_hoa_don;
import com.example.hamstore.fragment.fragment_Admin_loai_hamster;
import com.example.hamstore.fragment.fragment_Admin_nguoi_dung;
import com.example.hamstore.fragment.fragment_Admin_phu_kien;
import com.example.hamstore.fragment.fragment_Admin_san_pham;
import com.example.hamstore.fragment.fragment_Admin_thuc_an;
import com.example.hamstore.fragment.fragment_TrangChu_2hang_ThucAn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

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
                    fragment = new fragment_Admin_san_pham();
                }else if (item.getItemId() == R.id.mNguoiDung){
                    fragment = new fragment_Admin_nguoi_dung();
                }else if (item.getItemId() == R.id.mHoaDon){
                    fragment = new fragment_Admin_hoa_don();
                }else if (item.getItemId() == R.id.mDangXuat) {
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

    public void chuyen_fragment_admin_nguoi_dung(){
        Fragment fragment = new fragment_Admin_nguoi_dung();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_admin_hoa_don(){
        Fragment fragment = new fragment_Admin_hoa_don();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_admin_san_pham(){
        Fragment fragment = new fragment_Admin_san_pham();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_admin_loai_hamster(){
        Fragment fragment = new fragment_Admin_loai_hamster();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_admin_hamster_winter_white(){
        Fragment fragment = new fragment_Admin_hamster_winter_white();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_admin_hamster_robo(){
        Fragment fragment = new fragment_Admin_hamster_robo();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_admin_hamster_bear(){
        Fragment fragment = new fragment_Admin_hamster_bear();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_admin_hamster_campbell(){
        Fragment fragment = new fragment_Admin_hamster_campbell();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_admin_phu_kien(){
        Fragment fragment = new fragment_Admin_phu_kien();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void chuyen_fragment_admin_thuc_an(){
        Fragment fragment = new fragment_Admin_thuc_an();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }

}