package com.example.hamstore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hamstore.model.Items;
import com.example.hamstore.model.Loai_Hamster;
import com.example.hamstore.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AC_ChiTiet extends AppCompatActivity {
    Context c = this;
    private final String key_bundle_object = "object";
    private final String key_tai_khoan = "tai_khoan";
    Items item_bundle;
    ImageView img_back,img_sp;
    TextView tv_Ten,tv_Gia,tv_gioiThieu,tv_da_ban,tv_het_hang;

    //thêm gio hang của user
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference data_chitiet;
    DatabaseReference data_gio_hang = firebaseDatabase.getReference("Giỏ hàng");
    private Boolean stop_remove = false;
    private Boolean check_trung = false;
    Button btn_them_vao_gio_hang;
    String tai_khoan;
    ArrayList<Items> arr_gio_hang = new ArrayList<>();
    String gio_hang_tai_khoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_chitiet);

        //ánh xạ
        img_back=findViewById(R.id.img_back);
        img_sp=findViewById(R.id.img_sp);
        tv_Ten=findViewById(R.id.tv_Ten);
        tv_Gia=findViewById(R.id.tv_Gia);
        tv_da_ban=findViewById(R.id.tv_da_ban);
        tv_het_hang=findViewById(R.id.tv_het_hang);
        tv_gioiThieu=findViewById(R.id.tv_gioiThieu);
        btn_them_vao_gio_hang=findViewById(R.id.btn_them_vao_gio_hang);

        //lấy data
        item_bundle = (Items) getIntent().getExtras().get(key_bundle_object);

        //lấy data user
        tai_khoan = (String) getIntent().getExtras().getString(key_tai_khoan);
        data_chitiet = firebaseDatabase.getReference("Users").child(tai_khoan);
        //tên giỏ hàng
        gio_hang_tai_khoan = "gio_hang_"+tai_khoan;

        //chuyen du lieu
        tv_Ten.setText(item_bundle.getTen_dai());
        tv_gioiThieu.setText(item_bundle.getMieu_ta());

        //gia
        NumberFormat formatter = new DecimalFormat("#,###");
        int myNumber = item_bundle.getGia();
        String formattedNumber = formatter.format(myNumber);
        tv_Gia.setText(String.valueOf(formattedNumber +" VND "));

        //img
        //lấy SrcImg
        if(item_bundle.getSrcImg().length()<40){
            String imgName =item_bundle.getSrcImg();
            //đổi string thành int (R.drawable.name)
            int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
            img_sp.setImageResource(imgId);
        }else {
            Glide.with(c).load(item_bundle.getSrcImg()).into(img_sp);
        }

        //đã bán
        tv_da_ban.setText("Đã bán: "+item_bundle.getSo_luong_da_mua());

        //ẩn hiện tv hết hàng
        if(item_bundle.getSo_luong_trong_kho() > 0){
            tv_het_hang.setVisibility(View.GONE);
        }

        //nhấn img back
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //nhấn btn them
        btn_them_vao_gio_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item_bundle.getSo_luong_trong_kho() > 0){
                    //tạo dialog thông báo
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
                    alertDialogBuilder.setTitle("Thông Báo!");
                    alertDialogBuilder.setMessage("Thêm vào giỏ hàng thành công");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stop_remove = true;
                            them_vao_gio_hang();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else {
                    //tạo dialog thông báo
                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(c);
                    alertDialogBuilder1.setTitle("Thông Báo!");
                    alertDialogBuilder1.setMessage("Sản phẩm đã hết hàng!");
                    alertDialogBuilder1.setCancelable(false);
                    alertDialogBuilder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog1 = alertDialogBuilder1.create();
                    alertDialog1.show();
                }

            }
        });


    }
    private void them_vao_gio_hang(){

        data_gio_hang.child(gio_hang_tai_khoan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(stop_remove == true){
                        Items item = snapshot.getValue(Items.class);
                        if(item.getId().equals(item_bundle.getId())){
                            check_trung = true;
                            int tang_sl = item.getSo_luong() +1;
                            data_gio_hang.child(gio_hang_tai_khoan).child(item_bundle.getId()).child("so_luong").setValue(tang_sl);
                        }
                    }
                }
                stop_remove = false;

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });

        //nếu item chưa có trong giỏ hàng
        if(check_trung == false){
            //add item vào giỏ hàng
            data_gio_hang.child(gio_hang_tai_khoan).child(item_bundle.getId()).setValue(item_bundle);
        }
        //reset lại boolean check_trung
        check_trung = false;


    }

}