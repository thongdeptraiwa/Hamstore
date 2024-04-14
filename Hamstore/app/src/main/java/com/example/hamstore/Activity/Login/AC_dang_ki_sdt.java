package com.example.hamstore.Activity.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    FirebaseAuth mAuth;

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

                //check null
                if(inputEdit_sdt.getText().toString().trim().equals("")){
                    dialog_thong_bao_sdt_null();
                    return;
                }

                //chuyen 0.... thanh +84
                String strPhonenumber = "+84"+inputEdit_sdt.getText().toString().trim().substring(1);
                //Toast.makeText(c, strPhonenumber, Toast.LENGTH_SHORT).show();

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

    private void chuyen_ac_dang_ki(String strPhonenumber1) {
        Intent intent = new Intent(c,AC_dang_ki.class);
        // truyen du lieu sang.
        intent.putExtra("sdt",strPhonenumber1);
        startActivity(intent);
    }

    private void chuyen_qua_ac_dang_ki_otp(String strPhonenumber2, String verificationId) {
        Intent intent = new Intent(c, AC_dang_ki_otp.class);
        intent.putExtra("sdt",strPhonenumber2);
        intent.putExtra("otp",verificationId);
        startActivity(intent);
    }
    private void dialog_thong_bao_sdt_null(){
        //tạo dialog thông báo
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Thông báo");
        alertDialogBuilder.setMessage("Bạn chưa nhập số điện thoại!");
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