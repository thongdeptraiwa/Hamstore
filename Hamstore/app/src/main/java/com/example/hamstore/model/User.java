package com.example.hamstore.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String tai_khoan;
    private String mat_khau;
    private String img;
    private String ho_ten;
    private String gmail;
    private String sdt;
    private String dia_chi;
    private int role;

    public User() {
    }

    public User(String tai_khoan, String mat_khau, String img, String ho_ten, String gmail, String sdt, String dia_chi, int role) {
        this.tai_khoan = tai_khoan;
        this.mat_khau = mat_khau;
        this.img = img;
        this.ho_ten = ho_ten;
        this.gmail = gmail;
        this.sdt = sdt;
        this.dia_chi = dia_chi;
        this.role = role;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getHo_ten() {
        return ho_ten;
    }

    public void setHo_ten(String ho_ten) {
        this.ho_ten = ho_ten;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }


    public String getTai_khoan() {
        return tai_khoan;
    }

    public void setTai_khoan(String tai_khoan) {
        this.tai_khoan = tai_khoan;
    }

    public String getMat_khau() {
        return mat_khau;
    }

    public void setMat_khau(String mat_khau) {
        this.mat_khau = mat_khau;
    }


    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
