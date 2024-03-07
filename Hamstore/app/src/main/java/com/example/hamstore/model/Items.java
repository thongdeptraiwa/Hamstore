package com.example.hamstore.model;

import java.io.Serializable;

public class Items implements Serializable {
    private String id;
    private String ten;
    private String srcImg;
    private int gia;
    private String mieu_ta;

    public Items() {
    }

    public Items(String ten, String srcImg) {
        this.ten = ten;
        this.srcImg = srcImg;
    }


    public Items(String id, String ten, String srcImg, int gia, String mieu_ta) {
        this.id = id;
        this.ten = ten;
        this.srcImg = srcImg;
        this.gia = gia;
        this.mieu_ta = mieu_ta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
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
