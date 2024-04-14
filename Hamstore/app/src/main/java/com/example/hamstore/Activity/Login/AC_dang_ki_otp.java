package com.example.hamstore.Activity.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hamstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AC_dang_ki_otp extends AppCompatActivity {
    Context c=this;
    ImageView img_back;
    TextInputEditText inputEdit_otp;
    Button btn_xac_nhan;
    TextView tv_gui_lai_otp;
    String mPhoneNumber;
    String mVertificationId;
    FirebaseAuth mAuth;
    PhoneAuthProvider.ForceResendingToken mForce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_dang_ki_otp);

        //ánh xạ
        img_back = findViewById(R.id.img_back);
        inputEdit_otp = findViewById(R.id.inputEdit_otp);
        btn_xac_nhan = findViewById(R.id.btn_xac_nhan);
        tv_gui_lai_otp = findViewById(R.id.tv_gui_lai_otp);

        mAuth = FirebaseAuth.getInstance();

        //lấy sdt vs ma OTP
        mPhoneNumber = getIntent().getStringExtra("sdt_sdt");
        mVertificationId = getIntent().getStringExtra("otp_sdt");

        //nhan
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_xac_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp_user_nhap = inputEdit_otp.getText().toString().trim();
                //check null
                if(otp_user_nhap.equals("")){
                    dialog_thong_bao_otp_null();
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVertificationId, otp_user_nhap);
                check_otp(credential);
                Toast.makeText(c, "Đang load", Toast.LENGTH_SHORT).show();
            }
        });
        tv_gui_lai_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gui_lai_otp();
            }
        });



    }
    private void gui_lai_otp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)
                        .setForceResendingToken(mForce)// (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                check_otp(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(AC_dang_ki_otp.this, "Hết lượt OTP!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationId, forceResendingToken);
                                //đổi lại mã OTP
                                mVertificationId = verificationId;
                                mForce = forceResendingToken;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void check_otp(PhoneAuthCredential credential) {
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
                                Toast.makeText(AC_dang_ki_otp.this, "Lỗi OTP!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void chuyen_ac_dang_ki(String phoneNumber) {
        //Toast.makeText(AC_dang_ki_otp.this, "Lỗi dang ki", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,AC_dang_ki.class);
        // truyen du lieu sang.
        intent.putExtra("sdt",phoneNumber);
        startActivity(intent);
    }
    private void dialog_thong_bao_otp_null(){
        //tạo dialog thông báo
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Thông báo");
        alertDialogBuilder.setMessage("Bạn chưa nhập mã OTP!");
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