package com.example.hamstore;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hamstore.model.Items;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class AC_ChiTiet extends AppCompatActivity {
    Context c = this;
    private final String key_bundle_object = "object";
    Items item_bundle;
    ImageView img_back,img_sp;
    TextView tv_Ten,tv_Gia,tv_gioiThieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_chitiet);

        //ánh xạ
        img_back=findViewById(R.id.img_back);
        img_sp=findViewById(R.id.img_sp);
        tv_Ten=findViewById(R.id.tv_Ten);
        tv_Gia=findViewById(R.id.tv_Gia);
        tv_gioiThieu=findViewById(R.id.tv_gioiThieu);


        //lấy data
        item_bundle = (Items) getIntent().getExtras().get(key_bundle_object);

        //chuyen du lieu
        tv_Ten.setText(item_bundle.getTen_dai());
        tv_gioiThieu.setText(item_bundle.getMieu_ta());

        //gia
        NumberFormat formatter = new DecimalFormat("#,###");
        int myNumber = item_bundle.getGia();
        String formattedNumber = formatter.format(myNumber);
        tv_Gia.setText(String.valueOf(formattedNumber +" VND "));

        //img
        //lấy SrcImg
        String imgName =item_bundle.getSrcImg();
        //đổi string thành int (R.drawable.name)
        int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
        img_sp.setImageResource(imgId);


        //onclick img back
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}