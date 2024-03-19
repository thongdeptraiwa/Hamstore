package com.example.hamstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

public class AC_dang_nhap extends AppCompatActivity {
    Button btn_dang_nhap;
    TextView tv_dang_ki;
    TextInputEditText inputEdit_tai_khoan,inputEdit_mat_khau;
    //Realtiem
    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    private final String key_users = "Users",key_tai_khoan = "tai_khoan",key_admin = "Admin";
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
                check_user();
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
    private void check_user(){

        //check data
        data.child(key_users).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    User user = snapshot.getValue(User.class);
                    //dang nhap thanh cong
                    if(user.getTai_khoan().equals(inputEdit_tai_khoan.getText().toString()) && user.getMat_khau().equals(inputEdit_mat_khau.getText().toString())){
                        // check role
                        if(user.getRole() == 0){
                            Intent intent_user = new Intent(AC_dang_nhap.this,TrangChu.class);
                            intent_user.putExtra(key_tai_khoan,user.getTai_khoan());
                            startActivity(intent_user);
                            onDestroy();

                        }
                        if(user.getRole() == 1){
                            Intent intent_admin = new Intent(AC_dang_nhap.this,TrangChu_Admin.class);
                            startActivity(intent_admin);
                            onDestroy();

                        }
                        return;
                    }

                }
                //dang nhap thất bại
                Toast.makeText(AC_dang_nhap.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });

//        //cach 2 tot hon
//        data.child(key_users).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                User user = snapshot.getValue(User.class);
//                //dang nhap thanh cong
//                if(user.getTai_khoan().equals(inputEdit_tai_khoan.getText().toString()) && user.getMat_khau().equals(inputEdit_mat_khau.getText().toString())) {
//                    // check role
//                    if (user.getRole() == 0) {
//                        Intent intent_user = new Intent(AC_dang_nhap.this, TrangChu.class);
//                        intent_user.putExtra(key_tai_khoan, user.getTai_khoan());
//                        startActivity(intent_user);
//                        onDestroy();
//                        //finish();
//                    }
//                    if (user.getRole() == 1) {
//                        Intent intent_admin = new Intent(AC_dang_nhap.this, TrangChu_Admin.class);
//                        startActivity(intent_admin);
//                        onDestroy();
//                        //finish();
//                    }
//                    return;
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


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
                            FirebaseUser user = auth.getCurrentUser();

                            String tai_khoan = user.getUid();
                            String mat_khau = "123";
                            String gmail = user.getEmail();
                            String ho_ten = user.getDisplayName();
                            String ngay_sinh = "null";
                            String dia_chi = "null";
                            int role = 0;

                            //User(tai_khoan,
                            //     mat_khau,
                            //     gmail,
                            //     ho_ten,
                            //     ngay_sinh,
                            //     dia_chi,
                            //     role) // 0: user - 1: admin
                            data.child(key_users).child(user.getUid()).setValue(new User(tai_khoan,
                                    mat_khau,
                                    gmail,
                                    ho_ten,
                                    ngay_sinh,
                                    dia_chi,
                                    role));

                            Intent intent = new Intent(AC_dang_nhap.this,TrangChu.class);
                            intent.putExtra(key_tai_khoan,tai_khoan);
                            startActivity(intent);
                            onDestroy();
                        }else {
                            Toast.makeText(AC_dang_nhap.this, "Loi 2", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    // destroy để fix lại qua lại
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}