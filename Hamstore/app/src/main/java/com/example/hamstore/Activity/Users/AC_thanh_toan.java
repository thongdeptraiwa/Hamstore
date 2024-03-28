package com.example.hamstore.Activity.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hamstore.R;
import com.example.hamstore.model.Hoa_Don;
import com.example.hamstore.model.Items;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;

public class AC_thanh_toan extends AppCompatActivity {
    Context c = this;
    Button btn_momo,btn_thanh_toan_khi_nhan_hang;
    ImageView img_back;
    TextInputEditText inputEdit_sdt,inputEdit_dia_chi;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference data_gio_hang_tai_khoan,data_tang_sl_da_mua,data_hoa_don;
    private Boolean stop_remove = false;
    private final String key_tai_khoan = "tai_khoan";
    String tai_khoan;
    String gio_hang_tai_khoan;
    //Tong tien
    int tong_tien = 0;
    TextView tv_tong_tien;
    //ds sp thanh toan
    String id_hd;
    ArrayList<Items> ds_sp_thanh_toan = new ArrayList<>();
    //momo
    private String amount;
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Hamstore";
    private String merchantCode = "MOMOC2IC20220510";
    private String merchantNameLabel = "Nhà cung cấp Hamstore";
    private String description = "Mua hàng online";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_thanh_toan);
        //momo
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        //lấy tài khoản
        tai_khoan = getIntent().getStringExtra(key_tai_khoan);

        //ánh xạ
        btn_momo = findViewById(R.id.btn_momo);
        btn_thanh_toan_khi_nhan_hang = findViewById(R.id.btn_thanh_toan_khi_nhan_hang);
        img_back = findViewById(R.id.img_back);
        inputEdit_sdt = findViewById(R.id.inputEdit_sdt);
        inputEdit_dia_chi = findViewById(R.id.inputEdit_dia_chi);
        recyclerView = findViewById(R.id.recyclerView);
        tv_tong_tien = findViewById(R.id.tv_tong_tien);
        gio_hang_tai_khoan = "gio_hang_"+tai_khoan;
        data_gio_hang_tai_khoan = firebaseDatabase.getReference("Giỏ hàng").child(gio_hang_tai_khoan);
        data_tang_sl_da_mua = firebaseDatabase.getReference();
        data_hoa_don = firebaseDatabase.getReference("Hóa đơn");

        //tạo id HD
        id_hd = data_hoa_don.push().getKey();


        //nhấn thanh toán khi nhận hàng
        btn_thanh_toan_khi_nhan_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputEdit_sdt.getText().toString().equals("") || inputEdit_dia_chi.getText().toString().equals("")){
                    //tạo dialog thông báo
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
                    alertDialogBuilder.setTitle("Thông Báo!");
                    if(inputEdit_sdt.getText().toString().equals("")){
                        if(inputEdit_dia_chi.getText().toString().equals("")){
                            alertDialogBuilder.setMessage("Bạn chưa nhập sđt và địa chỉ!");
                        }else {
                            alertDialogBuilder.setMessage("Bạn chưa nhập sđt!");
                        }
                    }else {
                        alertDialogBuilder.setMessage("Bạn chưa nhập địa chỉ!");
                    }
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else {
                    stop_remove = true;
                    tao_hoa_don(id_hd,inputEdit_sdt.getText().toString(),inputEdit_dia_chi.getText().toString(),"Chưa thanh toán");
                    read_remove_data();
                    finish();
                }
            }
        });
        //nhấn thanh toán momo
        btn_momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPayment("HD001");
            }
        });
        //nhấn back
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        eventValue.put("fee", "0"); //Kiểu integer
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
                        //xóa item đã thanh toán
                        if(inputEdit_sdt.getText().toString().equals("") || inputEdit_dia_chi.getText().toString().equals("")){
                            //tạo dialog thông báo
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
                            alertDialogBuilder.setTitle("Thông Báo!");
                            if(inputEdit_sdt.getText().toString().equals("")){
                                if(inputEdit_dia_chi.getText().toString().equals("")){
                                    alertDialogBuilder.setMessage("Bạn chưa nhập sđt và địa chỉ!");
                                }else {
                                    alertDialogBuilder.setMessage("Bạn chưa nhập sđt!");
                                }
                            }else {
                                alertDialogBuilder.setMessage("Bạn chưa nhập địa chỉ!");
                            }
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }else {
                            stop_remove = true;
                            tao_hoa_don(id_hd,inputEdit_sdt.getText().toString(),inputEdit_dia_chi.getText().toString(),"Đã thanh toán");
                            read_remove_data();
                            finish();
                        }
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

    //ADT
    @Override
    public void onStart() {
        super.onStart();

        ds_sp_thanh_toan.clear();
        tong_tien = 0;
        load_tong_tien();

        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(data_gio_hang_tai_khoan, Items.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Items, AC_thanh_toan.ViewHolder>(options) {
            @Override
            public AC_thanh_toan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.items_thanh_toan, parent, false);

                return new AC_thanh_toan.ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(AC_thanh_toan.ViewHolder holder, int position, Items model) {

                //hiện item checkbox 1(true)
                if(model.getCheckbox() == 1){
                    //tính tổng tiền
                    tong_tien+= model.getGia() * model.getSo_luong();
                    //format tiền
                    load_tong_tien();

                    //hiển thị
                    //gia
                    NumberFormat formatter = new DecimalFormat("#,###");
                    //int myNumber = ds.get(i).getGia();
                    int myNumber = model.getGia();
                    String formattedNumber = formatter.format(myNumber);
                    holder.tv_gia.setText(String.valueOf(formattedNumber +"đ"));

                    //name
                    //holder.tv_ten_dai.setText(ds.get(i).getTen_dai());
                    holder.tv_ten_dai.setText(model.getTen_dai());

                    //so luong
                    holder.tv_so_luong.setText(String.valueOf(model.getSo_luong()));

                    //them vào ds
                    ds_sp_thanh_toan.add(model);

                }else {
                    holder.itemView.setVisibility(View.GONE);
                }

            }
        };

        //recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ten_dai,tv_gia,tv_so_luong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_dai = itemView.findViewById(R.id.tv_ten_dai);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tv_so_luong = itemView.findViewById(R.id.tv_so_luong);

        }
    }
    private void load_tong_tien(){

        //chuyền tổng tiền qua momo
        amount = String.valueOf(tong_tien);
        //giá tổng tiền
        NumberFormat formatter = new DecimalFormat("#,###");
        //int myNumber = ds.get(i).getGia();
        int myNumber = tong_tien;
        String formattedNumber = formatter.format(myNumber);
        tv_tong_tien.setText(String.valueOf(formattedNumber +"đ"));

    }
    private void reset_tong_tien(){
        tong_tien = 0;
        load_tong_tien();
        onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        reset_tong_tien();
    }

    //xóa item đã thanh toán
    private void read_remove_data() {

        data_gio_hang_tai_khoan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(stop_remove == true){
                        Items item = snapshot.getValue(Items.class);
                        if(item.getCheckbox() == 1){
                            //xóa trong giỏ hàng
                            data_gio_hang_tai_khoan.child(item.getId()).removeValue();
                            //tăng số lượng đã mua
                            int tang_sl_da_mua =  item.getSo_luong_da_mua() + item.getSo_luong();
                            data_tang_sl_da_mua.child(item.getLoai()).child(item.getId()).child("so_luong_da_mua").setValue(tang_sl_da_mua);
                            //giảm sl trong kho
                            int giam_sl_trong_kho = item.getSo_luong_trong_kho() - item.getSo_luong();
                            if(giam_sl_trong_kho < 0){
                                giam_sl_trong_kho = 0;
                            }
                            data_tang_sl_da_mua.child(item.getLoai()).child(item.getId()).child("so_luong_trong_kho").setValue(giam_sl_trong_kho);
                        }
                    }
                }
                stop_remove = false;

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });
    }
    private void tao_hoa_don(String id,String sdt,String dia_chi,String trang_thai){

        data_hoa_don.child(id).setValue(new Hoa_Don(id,tai_khoan,sdt,dia_chi,ds_sp_thanh_toan,tong_tien,trang_thai));

    }
}