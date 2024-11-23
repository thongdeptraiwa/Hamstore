package com.example.hamstore.model;

import java.io.Serializable;

public class Admin implements Serializable {

    private String tai_khoan;
    private String mat_khau;

    public Admin() {
    }

    public Admin(String tai_khoan, String mat_khau) {
        this.tai_khoan = tai_khoan;
        this.mat_khau = mat_khau;
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


}
