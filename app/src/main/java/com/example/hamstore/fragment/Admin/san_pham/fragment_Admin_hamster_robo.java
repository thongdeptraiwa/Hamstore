package com.example.hamstore.fragment.Admin.san_pham;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hamstore.R;
import com.example.hamstore.model.Items;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class fragment_Admin_hamster_robo extends Fragment {
    Context c;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference data = firebaseDatabase.getReference("Hamster Robo");
    //thêm
    FloatingActionButton floatAdd;
    int REQUEST_CODE_IMAGE = 1;
    //img chung cho 2 dialog
    public ImageView img;
    public Boolean check_img=false;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference("img_hamster_robo");
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
        View view = inflater.inflate(R.layout.fragment_admin_list,container,false);

        c = getActivity();

        //ánh xạ
        recyclerView=view.findViewById(R.id.recyclerView);
        floatAdd=view.findViewById(R.id.floatAdd);

        //nhấn thêm
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tạo dialog
                Dialog dialog = new Dialog((Activity)c);
                dialog.setContentView(R.layout.dialog_them_san_pham);
                dialog.setCanceledOnTouchOutside(false);//nhấn ra ngoài ko tắc dialog
                dialog.show();

                //ánh xạ thông tin NV
                Button btn_chup_anh = dialog.findViewById(R.id.btn_chup_anh);
                Button btn_vao_thu_vien = dialog.findViewById(R.id.btn_vao_thu_vien);
                Button btn_them = dialog.findViewById(R.id.btn_them);
                Button btn_huy = dialog.findViewById(R.id.btn_huy);
                img = dialog.findViewById(R.id.img);
                TextInputEditText inputEdit_ten_ngan = dialog.findViewById(R.id.inputEdit_ten_ngan);
                TextInputEditText inputEdit_ten_dai = dialog.findViewById(R.id.inputEdit_ten_dai);
                TextInputEditText inputEdit_gia = dialog.findViewById(R.id.inputEdit_gia);
                TextInputEditText inputEdit_so_luong_trong_kho = dialog.findViewById(R.id.inputEdit_so_luong_trong_kho);
                TextInputEditText inputEdit_mieu_ta = dialog.findViewById(R.id.inputEdit_mieu_ta);

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
                btn_them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
                                        String ten_ngan = inputEdit_ten_ngan.getText().toString().trim();
                                        String ten_dai = inputEdit_ten_dai.getText().toString().trim();
                                        int gia = Integer.parseInt(inputEdit_gia.getText().toString().trim());
                                        String mieu_ta = inputEdit_mieu_ta.getText().toString().trim();
                                        int so_luong_trong_kho = Integer.parseInt(inputEdit_so_luong_trong_kho.getText().toString().trim());
                                        them_sp(uri,ten_ngan,ten_dai,gia,mieu_ta,so_luong_trong_kho);
                                    }
                                });
                                //tắc dialog
                                dialog.dismiss();
                                check_img=false;
                            }
                        });



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
        });

        return view;
    }
    public void them_sp(Uri uri,String ten_ngan,String ten_dai,int gia,String mieu_ta,int so_luong_trong_kho){
        //img
        Log.d("link_img",uri.toString()+"");
        String link_img = uri.toString();


        String id = data.push().getKey();
        int so_luong = 1;
        int so_luong_da_mua = 0;
        int luot_mua = 0;
        int tong_sao = 0;
        int so_lan_danh_gia = 0;
        int checkbox = 0;
        String loai = "Hamster Robo";

        //Items(id,
        //      ten_ngan,
        //      ten_dai,
        //      img,
        //      gia,
        //      mieu_ta,
        //      so_luong,
        //      so_luong_trong_kho,
        //      so_luong_da_mua,
        //      luot_mua,
        //      tong_sao,
        //      so_lan_danh_gia,
        //      checkbox, //0: false 1:true
        //      loai)
        data.child(id).setValue(new Items(id,
                        ten_ngan,
                        ten_dai,
                        link_img,
                        gia,
                        mieu_ta,
                        so_luong,
                        so_luong_trong_kho,
                        so_luong_da_mua,
                        luot_mua,
                        tong_sao,
                        so_lan_danh_gia,
                        checkbox,
                        loai))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(c, "Add items thanh cong", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(c, "Add items thai bai", Toast.LENGTH_SHORT).show();
                        }
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


    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(data, Items.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Items, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.items_admin_san_pham, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, int position, Items model) {

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

                //lấy SrcImg
                if(model.getSrcImg().length()<40){
                    String imgName = model.getSrcImg();
                    //đổi string thành int (R.drawable.name)
                    int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
                    holder.img.setImageResource(imgId);
                }else {
                    Glide.with(c).load(model.getSrcImg()).into(holder.img);
                }

                //nhấn xóa
                holder.img_khung_rac.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_thong_bao_xoa(model);
                    }
                });
                //nhấn item xem chi tiết và chỉnh sửa
                holder.tv_ten_dai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_chinh_sua_chi_tiet(model);
                    }
                });
                holder.tv_gia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_chinh_sua_chi_tiet(model);
                    }
                });
                holder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_chinh_sua_chi_tiet(model);
                    }
                });

            }

        };

        //recyclerView giohang
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ten_dai,tv_gia;
        ImageView img,img_khung_rac;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten_dai = itemView.findViewById(R.id.tv_ten_dai);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            img = itemView.findViewById(R.id.img);
            img_khung_rac = itemView.findViewById(R.id.img_khung_rac);
        }
    }
    //dialog chỉnh sửa và chi tiết
    private void dialog_chinh_sua_chi_tiet(Items sp){


        //tạo dialog
        Dialog dialog = new Dialog((Activity)c);
        dialog.setContentView(R.layout.dialog_sua_san_pham);
        dialog.setCanceledOnTouchOutside(false);//nhấn ra ngoài ko tắc dialog
        dialog.show();

        //ánh xạ thông tin NV
        Button btn_chup_anh = dialog.findViewById(R.id.btn_chup_anh);
        Button btn_vao_thu_vien = dialog.findViewById(R.id.btn_vao_thu_vien);
        Button btn_chinh_sua = dialog.findViewById(R.id.btn_chinh_sua);
        Button btn_huy = dialog.findViewById(R.id.btn_huy);
        img = dialog.findViewById(R.id.img);
        TextInputEditText inputEdit_ten_ngan = dialog.findViewById(R.id.inputEdit_ten_ngan);
        TextInputEditText inputEdit_ten_dai = dialog.findViewById(R.id.inputEdit_ten_dai);
        TextInputEditText inputEdit_gia = dialog.findViewById(R.id.inputEdit_gia);
        TextInputEditText inputEdit_so_luong_trong_kho = dialog.findViewById(R.id.inputEdit_so_luong_trong_kho);
        TextInputEditText inputEdit_mieu_ta = dialog.findViewById(R.id.inputEdit_mieu_ta);


        //chi tiết
        //img
        if(sp.getSrcImg().length()<40){
            String imgName = sp.getSrcImg();
            //đổi string thành int (R.drawable.name)
            int imgId = c.getResources().getIdentifier(imgName, "drawable", c.getPackageName());
            img.setImageResource(imgId);
        }else {
            Glide.with(c).load(sp.getSrcImg()).into(img);

        }
        //text
        inputEdit_ten_ngan.setText(sp.getTen_ngan());
        inputEdit_ten_dai.setText(sp.getTen_dai());
        inputEdit_gia.setText(String.valueOf(sp.getGia()));
        inputEdit_so_luong_trong_kho.setText(String.valueOf(sp.getSo_luong_trong_kho()));
        inputEdit_mieu_ta.setText(sp.getMieu_ta());


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
        btn_chinh_sua.setOnClickListener(new View.OnClickListener() {
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
                                    src_img_tam(uri, sp.getId());
                                }
                            });

                        }
                    });

                }
                data.child(sp.getId()).child("ten_ngan").setValue(inputEdit_ten_ngan.getText().toString().trim());
                data.child(sp.getId()).child("ten_dai").setValue(inputEdit_ten_dai.getText().toString().trim());
                data.child(sp.getId()).child("gia").setValue(Integer.parseInt(inputEdit_gia.getText().toString().trim()));
                data.child(sp.getId()).child("so_luong_trong_kho").setValue(Integer.parseInt(inputEdit_so_luong_trong_kho.getText().toString().trim()));
                data.child(sp.getId()).child("mieu_ta").setValue(inputEdit_mieu_ta.getText().toString().trim());
                //tắc dialog
                dialog.dismiss();

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
    private void src_img_tam(Uri link_img,String id){
        //img
        Log.d("link_img",link_img.toString()+"");
        data.child(id).child("srcImg").setValue(link_img.toString());
        check_img=false;
    }
    private void dialog_thong_bao_xoa(Items sp){
        //tạo dialog thông báo đăng xuat
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
        alertDialogBuilder.setTitle("Thông Báo!");
        alertDialogBuilder.setMessage("Bạn có chắc muốn xóa không?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.child(sp.getId()).removeValue();
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
}
