package com.example.hamstore.Activity.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.hamstore.R;

public class AC_Chao extends AppCompatActivity {
    Context c=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_chao);

        //2000 = 2 giay : tổng
        //1000 = 1 giây : thời gian lặp lại onTick()
        CountDownTimer timer = new CountDownTimer(2000,500) {
            @Override
            public void onTick(long l) {
                    //intifity Timer
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(c, AC_dang_nhap.class);
                startActivity(intent);
                finish();
            }
        };
        timer.start();

    }
}