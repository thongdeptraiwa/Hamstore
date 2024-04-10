package com.example.hamstore.Activity.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.example.hamstore.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;


import java.util.concurrent.TimeUnit;

public class AC_dang_ki_sdt extends AppCompatActivity {
    Context c=this;
    ImageView img_back;
    Button btn_gui_otp;
    TextInputEditText inputEdit_sdt;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_dang_ki_sdt);

        //ánh xạ
        img_back = findViewById(R.id.img_back);
        btn_gui_otp = findViewById(R.id.btn_gui_otp);
        inputEdit_sdt = findViewById(R.id.inputEdit_sdt);

        mAuth = FirebaseAuth.getInstance();

        //nhan
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_gui_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPhonenumber = inputEdit_sdt.getText().toString().trim();
                gui_otp(strPhonenumber);
                Toast.makeText(c, "Đang load", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void gui_otp(String strPhonenumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(strPhonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                //đi thẳng vào đăng kí
                                check_di_thang(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                //Failed
                                Toast.makeText(c, "Sai sdt hoặc hết lượt OTP", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                //chuyển qua OTP
                                chuyen_qua_ac_dang_ki_otp(strPhonenumber,verificationId);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void check_di_thang(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //thành công
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            //đi thẳng vào đăng kí
                            chuyen_ac_dang_ki(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                //lỗi
                                Toast.makeText(c, "Lỗi vào thẳng đăng kí", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void chuyen_ac_dang_ki(String strPhonenumber) {
        Intent intent = new Intent(c,AC_dang_ki.class);
        // truyen du lieu sang.
        intent.putExtra("number_phone",strPhonenumber);
        startActivity(intent);
    }

    private void chuyen_qua_ac_dang_ki_otp(String strPhonenumber, String verificationId) {
        Intent intent = new Intent(c, AC_dang_ki_otp.class);
        intent.putExtra("number_phone",strPhonenumber);
        intent.putExtra("verification_id",verificationId);
        startActivity(intent);
    }

}