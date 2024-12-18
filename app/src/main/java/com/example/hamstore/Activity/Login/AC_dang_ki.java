package com.example.hamstore.Activity.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hamstore.R;
import com.example.hamstore.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AC_dang_ki extends AppCompatActivity {
    ImageView img_back;
    Button btn_tao_tai_khoan;
    TextInputEditText inputEdit_tai_khoan,inputEdit_mat_khau_1,inputEdit_mat_khau_2;
    //Realtiem
    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    private final String key_users = "Users";
    boolean flat_user_trung = false;
    boolean flat_for = false;
    String mPhoneNumber;
    String sdt;
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

        //lấy sdt
        mPhoneNumber = getIntent().getStringExtra("sdt");
        //thêm 0 và xóa +84
        sdt = "0"+mPhoneNumber.substring(3);
        //Toast.makeText(this, sdt, Toast.LENGTH_SHORT).show();


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
                if(inputEdit_tai_khoan.getText().toString().trim().isEmpty()){
                    dialog_thong_bao("Bạn chưa nhập tài khoản!");
                    //Toast.makeText(AC_dang_ki.this, "Bạn chưa nhập tài khoản!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputEdit_mat_khau_1.getText().toString().trim().isEmpty()){
                    dialog_thong_bao("Bạn chưa nhập mật khẩu!");
                    //Toast.makeText(AC_dang_ki.this, "Bạn chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputEdit_mat_khau_2.getText().toString().trim().isEmpty()){
                    dialog_thong_bao("Bạn chưa nhập lại mật khẩu!");
                    //Toast.makeText(AC_dang_ki.this, "Bạn chưa nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //check 2 mật khẩu
                if(!inputEdit_mat_khau_1.getText().toString().trim().equals(inputEdit_mat_khau_2.getText().toString().trim())){
                    dialog_thong_bao("Nhập lại mật khẩu không giống!");
                    //Toast.makeText(AC_dang_ki.this, "Nhập lại mật khẩu không giống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                flat_for=true;
                flat_user_trung = true;
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
                                break;
                            }

                        }

                    }

                    if (flat_user_trung==true){
                        //tạo user thành công
                        tao_user(inputEdit_tai_khoan.getText().toString().trim(),inputEdit_mat_khau_1.getText().toString().trim());
                        flat_user_trung=false;
                        //tạo thành công
                        //Toast.makeText(AC_dang_ki.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        flat_for=false;
                        dialog_thong_bao_dang_ki_thanh_cong();

                    }else {
                        //đăng kí thất bại
                        dialog_thong_bao("Tài khoản này đã tồn tại!");
                        //Toast.makeText(AC_dang_ki.this, "Tài khoản này đã tồn tại!", Toast.LENGTH_SHORT).show();
                        flat_for=false;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });



    }

    private void tao_user(String tai_khoan,String mat_khau){


        //User(tai_khoan,
        //     mat_khau,
        //     img,
        //     ho_ten,
        //     gmail,
        //     sdt,
        //     dia_chi,
        //     role) // 0: khóa - 1: account thường - 2: gg - 3: admin
        data.child(key_users).child(tai_khoan).setValue(new User(tai_khoan,mat_khau,"img_avt","null","null",sdt,"null",1))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(AC_dang_ki.this, "Add user thanh cong", Toast.LENGTH_SHORT).show();
                        }else {
                            //Toast.makeText(AC_dang_ki.this, "Add user thai bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void dialog_thong_bao(String text){
        //tạo dialog thông báo
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Thông báo");
        alertDialogBuilder.setMessage(text);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void dialog_thong_bao_dang_ki_thanh_cong(){
        //tạo dialog thông báo
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Thông báo");
        alertDialogBuilder.setMessage("Đăng kí thành cong!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(AC_dang_ki.this,AC_dang_nhap.class));
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}