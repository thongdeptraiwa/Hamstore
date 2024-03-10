package com.example.hamstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hamstore.model.Items;
import com.example.hamstore.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AC_dang_nhap extends AppCompatActivity {
    Context c= this;

    Button btn_dang_nhap;
    TextView tv_dang_ki;
    TextInputEditText inputEdit_tai_khoan,inputEdit_mat_khau;
    //Realtiem
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private final String key_users = "Users",key_bundle_user = "User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_dang_nhap);

        //ánh xạ
        btn_dang_nhap = findViewById(R.id.btn_dang_nhap);
        tv_dang_ki = findViewById(R.id.tv_dang_ki);
        inputEdit_tai_khoan = findViewById(R.id.inputEdit_tai_khoan);
        inputEdit_mat_khau = findViewById(R.id.inputEdit_mat_khau);

        btn_dang_nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_user();
            }
        });

        tv_dang_ki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(c,AC_dang_ki.class));
            }
        });

    }
    private void check_user(){
        //check data
        myRef.child(key_users).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    User user = snapshot.getValue(User.class);
                    if(user.getTai_khoan().equals(inputEdit_tai_khoan.getText().toString()) && user.getMat_khau().equals(inputEdit_mat_khau.getText().toString())){
                        //dang nhap thanh cong
                        Toast.makeText(c, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(c,TrangChu.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(key_bundle_user,user);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                        return;
                    }

                }
                //dang nhap thất bại
                Toast.makeText(c, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });
    }

}