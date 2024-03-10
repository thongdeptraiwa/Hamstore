package com.example.hamstore.model;

import java.io.Serializable;

public class Loai_Hamster implements Serializable {
    private String id;
    private String ten_loai;
    private String srcImg;
    private String khoang_gia;

    public Loai_Hamster() {
    }

    public Loai_Hamster(String id, String ten_loai, String srcImg, String khoang_gia) {
        this.id = id;
        this.ten_loai = ten_loai;
        this.srcImg = srcImg;
        this.khoang_gia = khoang_gia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen_loai() {
        return ten_loai;
    }

    public void setTen_loai(String ten_loai) {
        this.ten_loai = ten_loai;
    }

    public String getSrcImg() {
        return srcImg;
    }

    public void setSrcImg(String srcImg) {
        this.srcImg = srcImg;
    }

    public String getKhoang_gia() {
        return khoang_gia;
    }

    public void setKhoang_gia(String khoang_gia) {
        this.khoang_gia = khoang_gia;
    }
}
