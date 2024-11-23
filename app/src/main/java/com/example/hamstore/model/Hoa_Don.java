package com.example.hamstore.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Hoa_Don implements Serializable {
    private String id;
    private String id_user;
    private String thoi_gian;
    private String sdt;
    private String dia_chi;
    private ArrayList<Items> arr_items = new ArrayList<>();
    private int tong_tien;
    private String trang_thai;



    public Hoa_Don() {
    }

    public Hoa_Don(String id, String id_user, String thoi_gian, String sdt, String dia_chi, ArrayList<Items> arr_items, int tong_tien, String trang_thai) {
        this.id = id;
        this.id_user = id_user;
        this.thoi_gian = thoi_gian;
        this.sdt = sdt;
        this.dia_chi = dia_chi;
        this.arr_items = arr_items;
        this.tong_tien = tong_tien;
        this.trang_thai = trang_thai;
    }

    public String getThoi_gian() {
        return thoi_gian;
    }

    public void setThoi_gian(String thoi_gian) {
        this.thoi_gian = thoi_gian;
    }

    public String getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(String trang_thai) {
        this.trang_thai = trang_thai;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public int getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(int tong_tien) {
        this.tong_tien = tong_tien;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Items> getArr_items() {
        return arr_items;
    }

    public void setArr_items(ArrayList<Items> arr_items) {
        this.arr_items = arr_items;
    }

}
