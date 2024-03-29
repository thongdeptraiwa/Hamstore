package com.example.hamstore.model;

import java.io.Serializable;

public class Items implements Serializable {
    private String id;
    private String ten_ngan;
    private String ten_dai;
    private String srcImg;
    private int gia;
    private String mieu_ta;
    private int so_luong;
    private int so_luong_trong_kho;
    private int so_luong_da_mua;
    private int luot_mua;
    private int tong_sao;
    private int so_lan_danh_gia;
    private int checkbox;
    private String loai;

    public Items() {
    }

    public Items(String ten_ngan, String srcImg) {
        this.ten_ngan = ten_ngan;
        this.srcImg = srcImg;
    }

    public Items(String id, String ten_ngan, String ten_dai, String srcImg, int gia, String mieu_ta, int so_luong, int so_luong_trong_kho, int so_luong_da_mua, int luot_mua, int tong_sao, int so_lan_danh_gia, int checkbox, String loai) {
        this.id = id;
        this.ten_ngan = ten_ngan;
        this.ten_dai = ten_dai;
        this.srcImg = srcImg;
        this.gia = gia;
        this.mieu_ta = mieu_ta;
        this.so_luong = so_luong;
        this.so_luong_trong_kho = so_luong_trong_kho;
        this.so_luong_da_mua = so_luong_da_mua;
        this.luot_mua = luot_mua;
        this.tong_sao = tong_sao;
        this.so_lan_danh_gia = so_lan_danh_gia;
        this.checkbox = checkbox;
        this.loai = loai;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(int checkbox) {
        this.checkbox = checkbox;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public int getSo_luong_trong_kho() {
        return so_luong_trong_kho;
    }

    public void setSo_luong_trong_kho(int so_luong_trong_kho) {
        this.so_luong_trong_kho = so_luong_trong_kho;
    }

    public int getSo_luong_da_mua() {
        return so_luong_da_mua;
    }

    public void setSo_luong_da_mua(int so_luong_da_mua) {
        this.so_luong_da_mua = so_luong_da_mua;
    }

    public String getTen_ngan() {
        return ten_ngan;
    }

    public void setTen_ngan(String ten_ngan) {
        this.ten_ngan = ten_ngan;
    }

    public String getTen_dai() {
        return ten_dai;
    }

    public void setTen_dai(String ten_dai) {
        this.ten_dai = ten_dai;
    }


    public int getLuot_mua() {
        return luot_mua;
    }

    public void setLuot_mua(int luot_mua) {
        this.luot_mua = luot_mua;
    }

    public int getTong_sao() {
        return tong_sao;
    }

    public void setTong_sao(int tong_sao) {
        this.tong_sao = tong_sao;
    }

    public int getSo_lan_danh_gia() {
        return so_lan_danh_gia;
    }

    public void setSo_lan_danh_gia(int so_lan_danh_gia) {
        this.so_lan_danh_gia = so_lan_danh_gia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(String srcImg) {
        this.srcImg = srcImg;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getMieu_ta() {
        return mieu_ta;
    }

    public void setMieu_ta(String mieu_ta) {
        this.mieu_ta = mieu_ta;
    }
}
