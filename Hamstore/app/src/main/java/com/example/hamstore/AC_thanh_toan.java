package com.example.hamstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;

public class AC_thanh_toan extends AppCompatActivity {
    Context c = this;
    Button btn_momo;
    //momo
    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Hamstore";
    private String merchantCode = "SCB01";
    private String merchantNameLabel = "Nhà cung cấp Hamstore";
    private String description = "Mua hàng online";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_thanh_toan);
        //momo
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION

        //ánh xạ
        btn_momo = findViewById(R.id.btn_momo);

        //nhấn thanh toán momo
        btn_momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPayment("HD001");
            }
        });
    }
    //momo
    //Get token through MoMo app
    private void requestPayment(String id_don_hang) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

//        if (edAmount.getText().toString() != null && edAmount.getText().toString().trim().length() != 0)
//            amount = edAmount.getText().toString().trim();

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer giá
        eventValue.put("orderId", id_don_hang); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", id_don_hang); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", 0); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);


    }
    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Toast.makeText(c, "thành công!", Toast.LENGTH_SHORT).show();
                    //tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Toast.makeText(c, "thất bại!", Toast.LENGTH_SHORT).show();
                        //tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Toast.makeText(c, "thất bại!", Toast.LENGTH_SHORT).show();
                    //tvMessage.setText("message: " + message);
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Toast.makeText(c, "thất bại!", Toast.LENGTH_SHORT).show();
                    //tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                } else {
                    //TOKEN FAIL
                    Toast.makeText(c, "thất bại!", Toast.LENGTH_SHORT).show();
                    //tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                }
            } else {
                Toast.makeText(c, "thất bại!", Toast.LENGTH_SHORT).show();
                //tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
            }
        } else {
            Toast.makeText(c, "thất bại!", Toast.LENGTH_SHORT).show();
            //tvMessage.setText("message: " + this.getString(R.string.not_receive_info_err));
        }
    }
}