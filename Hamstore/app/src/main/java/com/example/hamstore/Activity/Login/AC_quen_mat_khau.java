package com.example.hamstore.Activity.Login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hamstore.R;
import com.example.hamstore.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AC_quen_mat_khau extends AppCompatActivity {
    ImageView img_back;
    Button btn_lay_mat_khau;
    TextInputEditText inputEdit_tai_khoan;
    //Realtiem
    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    private final String key_users = "Users";
    boolean flat_user_trung = false;
    boolean flat_for = false;
    String mat_khau="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_quen_mat_khau);

        //ánh xạ
        img_back = findViewById(R.id.img_back);
        btn_lay_mat_khau = findViewById(R.id.btn_lay_mat_khau);
        inputEdit_tai_khoan = findViewById(R.id.inputEdit_tai_khoan);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_lay_mat_khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check null
                if(inputEdit_tai_khoan.getText().toString().trim().isEmpty()){
                    Toast.makeText(AC_quen_mat_khau.this, "Chưa nhập tài khoản!", Toast.LENGTH_SHORT).show();
                    return;
                }

                flat_for=true;
                flat_user_trung = true;
                mat_khau="";
                check_user_();

            }
        });


    }
    private void check_user_(){

        //check data
        data.child(key_users).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(flat_for==true){
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                        if(flat_user_trung==true){
                            User user = snapshot.getValue(User.class);
                            //tài khoản trùng
                            if(user.getTai_khoan().equals(inputEdit_tai_khoan.getText().toString().trim())){
                                flat_user_trung=false;
                                mat_khau=user.getMat_khau();
                                break;
                            }

                        }

                    }

                    if (flat_user_trung==true){
                        dialog_thong_bao_tai_khoan_khong_ton_tai();
                    }else {
                        //lấy mật khẩu thành công
                        dialog_thong_bao_lay_mat_khau(mat_khau);
                    }
                    flat_for=false;
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });

    }
    private void dialog_thong_bao_tai_khoan_khong_ton_tai(){
        //tạo dialog thông báo đăng xuat
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Thông Báo");
        alertDialogBuilder.setMessage("Tài khoản không tồn tại!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void dialog_thong_bao_lay_mat_khau(String mat_khau){
        //tạo dialog thông báo đăng xuat
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Lấy lại mật khẩu thành công");
        alertDialogBuilder.setMessage("Mật khẩu: "+mat_khau);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



}