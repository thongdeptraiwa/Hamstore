package com.example.hamstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hamstore.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class test_login extends AppCompatActivity {
    Button btngg;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    private final String key_users = "Users";
    FirebaseAuth auth = FirebaseAuth.getInstance();

    GoogleSignInClient mGoogleSignInClient;

    int RC_SIGN_IN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login);

        //anns xạ
        btngg= findViewById(R.id.btn);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.gg))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(test_login.this,gso);

        btngg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
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
                Toast.makeText(test_login.this, "Loi 1", Toast.LENGTH_SHORT).show();
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

                            String tai_khoan = user.getEmail();
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

                            Intent i = new Intent(test_login.this,AC_Chao.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(test_login.this, "Loi 2", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}