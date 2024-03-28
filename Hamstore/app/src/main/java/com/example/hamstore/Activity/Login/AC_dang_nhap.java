package com.example.hamstore.Activity.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hamstore.Activity.Admin.TrangChu_Admin;
import com.example.hamstore.Activity.Users.TrangChu;
import com.example.hamstore.R;
import com.example.hamstore.model.Admin;
import com.example.hamstore.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

public class AC_dang_nhap extends AppCompatActivity {
    Button btn_dang_nhap;
    TextView tv_dang_ki,tv_quen_mat_khau;
    TextInputEditText inputEdit_tai_khoan,inputEdit_mat_khau;
    //Realtiem
    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    private final String key_users = "Users",key_tai_khoan = "tai_khoan",key_admin = "Admin";
    boolean flat_user_login_thanh_cong=false;
    boolean flat_admin_login_thanh_cong=false;
    boolean flat_gg_login_thanh_cong=false;
    //gg
    RoundedImageView rdi_gg;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    GoogleSignInClient mGoogleSignInClient;

    int RC_SIGN_IN = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_dang_nhap);

        //ánh xạ
        btn_dang_nhap = findViewById(R.id.btn_dang_nhap);
        tv_dang_ki = findViewById(R.id.tv_dang_ki);
        tv_quen_mat_khau = findViewById(R.id.tv_quen_mat_khau);
        inputEdit_tai_khoan = findViewById(R.id.inputEdit_tai_khoan);
        inputEdit_mat_khau = findViewById(R.id.inputEdit_mat_khau);
        rdi_gg = findViewById(R.id.rdi_gg);


        //gg
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.gg))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(AC_dang_nhap.this,gso);


        btn_dang_nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flat_admin_login_thanh_cong=true;
                check_admin();
            }
        });

        tv_quen_mat_khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEdit_tai_khoan.setText("");
                inputEdit_mat_khau.setText("");
                startActivity(new Intent(AC_dang_nhap.this,AC_quen_mat_khau.class));
            }
        });

        tv_dang_ki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEdit_tai_khoan.setText("");
                inputEdit_mat_khau.setText("");
                startActivity(new Intent(AC_dang_nhap.this,AC_dang_ki.class));
            }
        });

        rdi_gg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

    }
    private void check_admin(){

        //check data
        data.child("Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    if(flat_admin_login_thanh_cong==true){
                        Admin admin = snapshot.getValue(Admin.class);
                        //dang nhap thanh cong
                        if(admin.getTai_khoan().equals(inputEdit_tai_khoan.getText().toString().trim())
                                && admin.getMat_khau().equals(inputEdit_mat_khau.getText().toString().trim())){
                            //reset lại mật khẩu
                            inputEdit_mat_khau.setText("");

                            //admin
                            Intent intent_admin = new Intent(AC_dang_nhap.this, TrangChu_Admin.class);
                            startActivity(intent_admin);
                            flat_admin_login_thanh_cong=false;
                            return;
                        }

                    }


                }
                if (flat_admin_login_thanh_cong==true){
                    //dang nhap thất bại
                    flat_user_login_thanh_cong=true;
                    check_user();
                    flat_admin_login_thanh_cong=false;
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });



    }

    private void check_user(){

        //check data
        data.child(key_users).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    if(flat_user_login_thanh_cong==true){
                        User user = snapshot.getValue(User.class);
                        //dang nhap thanh cong
                        if(user.getTai_khoan().equals(inputEdit_tai_khoan.getText().toString().trim())
                                && user.getMat_khau().equals(inputEdit_mat_khau.getText().toString().trim())){
                            //reset lại mật khẩu
                            inputEdit_mat_khau.setText("");
                            // check role
                            if(user.getRole() != 0){
                                Intent intent_user = new Intent(AC_dang_nhap.this, TrangChu.class);
                                intent_user.putExtra(key_tai_khoan,user.getTai_khoan());
                                startActivity(intent_user);
                                flat_user_login_thanh_cong=false;

                            }else {
                                Toast.makeText(AC_dang_nhap.this, "Tài khoản đã bị khóa!", Toast.LENGTH_SHORT).show();
                                flat_user_login_thanh_cong=false;
                            }
                            return;
                        }

                    }


                }
                if (flat_user_login_thanh_cong==true){
                    //dang nhap thất bại
                    Toast.makeText(AC_dang_nhap.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                    flat_user_login_thanh_cong=false;
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });



    }

    private void googleSignIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            }catch (Exception e){
                Toast.makeText(AC_dang_nhap.this, "Loi 1", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser usergg = auth.getCurrentUser();

                            flat_gg_login_thanh_cong=true;
                            //check data
                            data.child(key_users).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                                        //có tài khoản trên firebasse rồi
                                        if(flat_gg_login_thanh_cong==true){
                                            User user = snapshot.getValue(User.class);

                                            if(user.getTai_khoan().equals(usergg.getUid())){
                                                // check role
                                                if(user.getRole() == 2){
                                                    Intent intent_user = new Intent(AC_dang_nhap.this, TrangChu.class);
                                                    intent_user.putExtra(key_tai_khoan,user.getTai_khoan());
                                                    startActivity(intent_user);
                                                    flat_gg_login_thanh_cong=false;

                                                }else {
                                                    Toast.makeText(AC_dang_nhap.this, "Tài khoản đã bị khóa!", Toast.LENGTH_SHORT).show();
                                                    flat_gg_login_thanh_cong=false;
                                                }
                                                return;
                                            }

                                        }


                                    }

                                    //chưa có tài khoản trên firebasse
                                    if (flat_gg_login_thanh_cong==true){

                                        String tai_khoan = usergg.getUid();
                                        String mat_khau = "123";
                                        String img = usergg.getPhotoUrl().toString();
                                        String gmail = usergg.getEmail();
                                        String ho_ten = usergg.getDisplayName();
                                        String ngay_sinh = "null";
                                        String dia_chi = "null";
                                        int role = 2;

                                        //User(tai_khoan,
                                        //     mat_khau,
                                        //     gmail,
                                        //     ho_ten,
                                        //     ngay_sinh,
                                        //     dia_chi,
                                        //     role) // 0: user - 1: admin
                                        data.child(key_users).child(usergg.getUid()).setValue(new User(tai_khoan,
                                                mat_khau,
                                                img,
                                                gmail,
                                                ho_ten,
                                                ngay_sinh,
                                                dia_chi,
                                                role));

                                        Intent intent = new Intent(AC_dang_nhap.this,TrangChu.class);
                                        intent.putExtra(key_tai_khoan,tai_khoan);
                                        startActivity(intent);

                                        flat_gg_login_thanh_cong=false;
                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w("DEUBG", "Failed read realtime", error.toException());
                                }
                            });





                        }else {
                            Toast.makeText(AC_dang_nhap.this, "Loi 2", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

}