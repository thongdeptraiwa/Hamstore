package com.example.hamstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hamstore.model.Items;
import com.example.hamstore.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AC_dang_ki extends AppCompatActivity {

    Context c=this;
    ImageView img_back;
    Button btn_tao_tai_khoan;
    TextInputEditText inputEdit_tai_khoan,inputEdit_mat_khau_1,inputEdit_mat_khau_2;
    //Realtiem
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private final String key_users = "Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_dang_ki);

        //ánh xạ
        img_back = findViewById(R.id.img_back);
        btn_tao_tai_khoan = findViewById(R.id.btn_tao_tai_khoan);
        inputEdit_tai_khoan = findViewById(R.id.inputEdit_tai_khoan);
        inputEdit_mat_khau_1 = findViewById(R.id.inputEdit_mat_khau_1);
        inputEdit_mat_khau_2 = findViewById(R.id.inputEdit_mat_khau_2);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_tao_tai_khoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check null
                if(inputEdit_tai_khoan.getText().toString().isEmpty()){
                    Toast.makeText(c, "Chưa nhập tài khoản!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputEdit_mat_khau_1.getText().toString().isEmpty()){
                    Toast.makeText(c, "Chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputEdit_mat_khau_2.getText().toString().isEmpty()){
                    Toast.makeText(c, "Chưa nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //check 2 mật khẩu
                if(!inputEdit_mat_khau_1.getText().toString().equals(inputEdit_mat_khau_2.getText().toString())){
                    Toast.makeText(c, "Nhập lại mật khẩu không giống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                tao_user(inputEdit_tai_khoan.getText().toString(),inputEdit_mat_khau_1.getText().toString());


                //tạo thành công
                Toast.makeText(c, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(c,AC_dang_nhap.class));
                finish();
            }
        });


    }

    private void tao_user(String tai_khoan,String mat_khau){


        //User(tai_khoan,
        //     mat_khau,
        //     gmail,
        //     ho_ten,
        //     ngay_sinh,
        //     dia_chi,
        //     role) // 0: user - 1: admin
        myRef.child(key_users).child(tai_khoan).setValue(new User(tai_khoan,mat_khau,"null","null","null","null",0))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(c, "Add user thanh cong", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(c, "Add user thai bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}