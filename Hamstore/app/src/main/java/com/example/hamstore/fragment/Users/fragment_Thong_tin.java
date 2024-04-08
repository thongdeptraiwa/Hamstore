package com.example.hamstore.fragment.Users;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.hamstore.Activity.Login.AC_dang_ki;
import com.example.hamstore.Activity.Login.AC_dang_nhap;
import com.example.hamstore.R;
import com.example.hamstore.Activity.Users.TrangChu;
import com.example.hamstore.model.Items;
import com.example.hamstore.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class fragment_Thong_tin extends Fragment {
    TrangChu trangChu;
    Context c;
    TextView tv_ho_ten,tv_dang_xuat,tv_doi_mat_khau,tv_thong_tin_ca_nhan;
    ImageView img_dang_xuat,img_avt,img_doi_mat_khau,img_thong_tin_ca_nhan;
    private final String key_tai_khoan = "tai_khoan";
    String tai_khoan;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference("Users");
    int REQUEST_CODE_IMAGE = 1;
    //img chung cho 2 dialog
    public ImageView img;
    public Boolean check_img=false;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference("Users");
    public User user_tong;
    //truy cập thư viện ảnh
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                if (result.getData() != null) {
                    Uri link_img = result.getData().getData();
                    Glide.with(c).load(link_img).into(img);
                    check_img=true;
                }
            }else {
                Toast.makeText(c, "thất bại vào thư viện!", Toast.LENGTH_SHORT).show();
            }
        }
    });


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin,container,false);
        c = getActivity();
        //ánh xạ
        trangChu = (TrangChu) getActivity();
        tv_ho_ten = view.findViewById(R.id.tv_ho_ten);
        tv_dang_xuat = view.findViewById(R.id.tv_dang_xuat);
        tv_doi_mat_khau = view.findViewById(R.id.tv_doi_mat_khau);
        tv_thong_tin_ca_nhan = view.findViewById(R.id.tv_thong_tin_ca_nhan);
        img_dang_xuat = view.findViewById(R.id.img_dang_xuat);
        img_avt = view.findViewById(R.id.img_avt);
        img_doi_mat_khau = view.findViewById(R.id.img_doi_mat_khau);
        img_thong_tin_ca_nhan = view.findViewById(R.id.img_thong_tin_ca_nhan);

        //lấy tài khoản từ activity
        tai_khoan = getArguments().getString(key_tai_khoan);


        read_data();

        //nhấn
        //đổi avt
        img_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_doi_avt(user_tong);
            }
        });
        //đăng xuất
        tv_dang_xuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_thong_bao_dang_xuat();
            }
        });
        img_dang_xuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_thong_bao_dang_xuat();
            }
        });
        //thông tin cá nhân
        tv_thong_tin_ca_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_thong_tin_ca_nhan(user_tong);
            }
        });
        img_thong_tin_ca_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_thong_tin_ca_nhan(user_tong);
            }
        });
        //doi mật khẩu
        tv_doi_mat_khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_doi_mat_khau(user_tong);
            }
        });
        img_doi_mat_khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_doi_mat_khau(user_tong);
            }
        });




        return view;
    }
    private void read_data(){
        //user
        data.child(tai_khoan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = (User) dataSnapshot.getValue(User.class);
                user_tong = user;

                //ten
                if(user.getHo_ten().equals("null")){
                    //lay tai_khoan
                    tv_ho_ten.setText(user.getTai_khoan());
                }else {
                    //lay ho ten
                    tv_ho_ten.setText(user.getHo_ten());
                }

                //img avt
                if(user.getImg().length()<40){

                    String imgName = user.getImg();
                    //đổi string thành int (R.drawable.name)
                    int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
                    Glide.with(c)
                            .load(imgId)
                            //bo tròn
                            .circleCrop()
                            .into(img_avt);

                }else {
                    Glide.with(c)
                            .load(user.getImg())
                            //bo tròn
                            .circleCrop()
                            .into(img_avt);
                }

                //tắc đổi mật khẩu vs tài khoản gg
                if(user.getRole() ==2){
                    img_doi_mat_khau.setVisibility(View.GONE);
                    tv_doi_mat_khau.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEUBG", "Failed read realtime", error.toException());
            }
        });
    }
    private void dialog_thong_bao_dang_xuat(){
        //tạo dialog thông báo đăng xuat
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
        alertDialogBuilder.setTitle("Thông Báo!");
        alertDialogBuilder.setMessage("Bạn có chắc muốn đăng xuất không?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(c, AC_dang_nhap.class));
                trangChu.dang_xuat();
            }
        });
        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void dialog_doi_avt(User user){


        //tạo dialog
        Dialog dialog = new Dialog((Activity)c);
        dialog.setContentView(R.layout.dialog_doi_avt);
        dialog.setCanceledOnTouchOutside(false);//nhấn ra ngoài ko tắc dialog
        dialog.show();

        //ánh xạ thông tin NV
        Button btn_chup_anh = dialog.findViewById(R.id.btn_chup_anh);
        Button btn_vao_thu_vien = dialog.findViewById(R.id.btn_vao_thu_vien);
        Button btn_luu = dialog.findViewById(R.id.btn_luu);
        Button btn_huy = dialog.findViewById(R.id.btn_huy);
        img = dialog.findViewById(R.id.img);


        //chi tiết
        //img avt
        if(user.getImg().length()<40){

            String imgName = user.getImg();
            //đổi string thành int (R.drawable.name)
            int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
            Glide.with(c)
                    .load(imgId)
                    //bo tròn
                    .circleCrop()
                    .into(img);

        }else {
            Glide.with(c)
                    .load(user.getImg())
                    //bo tròn
                    .circleCrop()
                    .into(img);
        }



        //nhấn
        btn_chup_anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);


            }
        });
        btn_vao_thu_vien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);


            }
        });

        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //chọn ảnh
                if(check_img==true){
                    //lưu ảnh lên storage

                    //tạo ten cho img
                    String ten_img = data.push().getKey();
                    StorageReference mountainsRef = storageRef.child(ten_img+".png");

                    // Get the data from an ImageView as bytes
                    img.setDrawingCacheEnabled(true);
                    img.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // thất bại
                            Toast.makeText(c, "Lỗi ko lưu đc ảnh!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // thành công
                            Toast.makeText(c, "Lưu ảnh thành công!", Toast.LENGTH_SHORT).show();
                            //Uri downloadUrl = mountainsRef.getDownloadUrl()
                            mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    src_img_tam(uri,user.getTai_khoan());
                                }
                            });

                        }
                    });

                    dialog.dismiss();
                    check_img=false;

                }



            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                check_img=false;
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);
            check_img=true;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void src_img_tam(Uri link_img,String tai_khoan){
        //img
        Log.d("link_img",link_img.toString()+"");
        data.child(tai_khoan).child("img").setValue(link_img.toString());
        check_img=false;
    }
    private void dialog_thong_tin_ca_nhan(User user){

        //tạo dialog
        Dialog dialog = new Dialog((Activity)c);
        dialog.setContentView(R.layout.dialog_thong_tin_ca_nhan);
        dialog.setCanceledOnTouchOutside(false);//nhấn ra ngoài ko tắc dialog
        dialog.show();

        //ánh xạ thông tin NV
        Button btn_chinh_sua = dialog.findViewById(R.id.btn_chinh_sua);
        Button btn_huy = dialog.findViewById(R.id.btn_huy);
        TextInputEditText inputEdit_tai_khoan = dialog.findViewById(R.id.inputEdit_tai_khoan);
        TextInputEditText inputEdit_ho_ten = dialog.findViewById(R.id.inputEdit_ho_ten);
        TextInputEditText inputEdit_gmail = dialog.findViewById(R.id.inputEdit_gmail);
        TextInputEditText inputEdit_sdt = dialog.findViewById(R.id.inputEdit_sdt);
        TextInputEditText inputEdit_dia_chi = dialog.findViewById(R.id.inputEdit_dia_chi);

        //text
        inputEdit_tai_khoan.setText(user.getTai_khoan());
        //check null ho ten
        if(user.getHo_ten().equals("null")){
            inputEdit_ho_ten.setText("");
        }else {
            inputEdit_ho_ten.setText(user.getHo_ten());
        }
        //check null gmail
        if(user.getGmail().equals("null")){
            inputEdit_gmail.setText("");
        }else {
            inputEdit_gmail.setText(user.getGmail());
        }
        //check null sdt
        if(user.getSdt().equals("null")){
            inputEdit_sdt.setText("");
        }else {
            inputEdit_sdt.setText(user.getSdt());
        }
        //check null dia chi
        if(user.getDia_chi().equals("null")){
            inputEdit_dia_chi.setText("");
        }else {
            inputEdit_dia_chi.setText(user.getDia_chi());
        }

        //nhấn
        btn_chinh_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.child(user.getTai_khoan()).child("ho_ten").setValue(inputEdit_ho_ten.getText().toString().trim());
                data.child(user.getTai_khoan()).child("gmail").setValue(inputEdit_gmail.getText().toString().trim());
                data.child(user.getTai_khoan()).child("sdt").setValue(inputEdit_sdt.getText().toString().trim());
                data.child(user.getTai_khoan()).child("dia_chi").setValue(inputEdit_dia_chi.getText().toString().trim());

                dialog.dismiss();
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void dialog_doi_mat_khau(User user){

        //tạo dialog
        Dialog dialog = new Dialog((Activity)c);
        dialog.setContentView(R.layout.dialog_doi_mat_khau);
        dialog.setCanceledOnTouchOutside(false);//nhấn ra ngoài ko tắc dialog
        dialog.show();

        //ánh xạ thông tin NV
        Button btn_doi_mat_khau = dialog.findViewById(R.id.btn_doi_mat_khau);
        Button btn_huy = dialog.findViewById(R.id.btn_huy);
        TextInputEditText inputEdit_mat_khau_cu = dialog.findViewById(R.id.inputEdit_mat_khau_cu);
        TextInputEditText inputEdit_mat_khau_moi_1 = dialog.findViewById(R.id.inputEdit_mat_khau_moi_1);
        TextInputEditText inputEdit_mat_khau_moi_2 = dialog.findViewById(R.id.inputEdit_mat_khau_moi_2);



        //nhấn
        btn_doi_mat_khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check null
                if(inputEdit_mat_khau_cu.getText().toString().trim().isEmpty()){
                    Toast.makeText(c, "Chưa nhập mật khẩu cũ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputEdit_mat_khau_moi_1.getText().toString().trim().isEmpty()){
                    Toast.makeText(c, "Chưa nhập mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputEdit_mat_khau_moi_2.getText().toString().trim().isEmpty()){
                    Toast.makeText(c, "Chưa nhập lại mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //sai mat khau cu
                if(!inputEdit_mat_khau_cu.getText().toString().trim().equals(user.getMat_khau())){
                    Toast.makeText(c, "Mật khẩu cũ sai!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //2 mật khẩu mới ko giống nhau
                if(!inputEdit_mat_khau_moi_1.getText().toString().trim().equals(inputEdit_mat_khau_moi_2.getText().toString().trim())){
                    Toast.makeText(c, "Mật khẩu mới không giống nhau!", Toast.LENGTH_SHORT).show();
                    return;
                }

                data.child(user.getTai_khoan()).child("mat_khau").setValue(inputEdit_mat_khau_moi_1.getText().toString().trim());

                dialog.dismiss();
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }



}
