package com.example.hamstore.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String id;
    private String tai_khoan;
    private String mat_khau;
    private ArrayList<Items> gio_hang;
    private String gmail;
    private String ho_ten;
    private String ngay_sinh;
    private String dia_chi;

    public User() {
    }

    public User(String id, String tai_khoan, String mat_khau, ArrayList<Items> gio_hang, String gmail, String ho_ten, String ngay_sinh, String dia_chi) {
        this.id = id;
        this.tai_khoan = tai_khoan;
        this.mat_khau = mat_khau;
        this.gio_hang = gio_hang;
        this.gmail = gmail;
        this.ho_ten = ho_ten;
        this.ngay_sinh = ngay_sinh;
        this.dia_chi = dia_chi;
    }

    public String getHo_ten() {
        return ho_ten;
    }

    public void setHo_ten(String ho_ten) {
        this.ho_ten = ho_ten;
    }

    public String getNgay_sinh() {
        return ngay_sinh;
    }

    public void setNgay_sinh(String ngay_sinh) {
        this.ngay_sinh = ngay_sinh;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<Items> getGio_hang() {
        return gio_hang;
    }

    public void setGio_hang(ArrayList<Items> gio_hang) {
        this.gio_hang = gio_hang;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
