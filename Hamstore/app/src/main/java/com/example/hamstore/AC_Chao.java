package com.example.hamstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class AC_Chao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_chao);

        //2000 = 2 giay : tổng
        //1000 = 1 giây : thời gian lặp lại onTick()
        CountDownTimer timer = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(AC_Chao.this, TrangChu.class);
                startActivity(intent);
                finish();
            }
        };
        timer.start();

    }
}