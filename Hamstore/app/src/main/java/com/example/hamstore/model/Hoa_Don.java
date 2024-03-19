package com.example.hamstore.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Hoa_Don implements Serializable {
    private String id;
    private String id_user;
    private String sdt;
    private String dia_chi;
    private ArrayList<Items> arr_items = new ArrayList<>();
    private int tong_tien;

    public Hoa_Don() {
    }

    public Hoa_Don(String id, String id_user, String sdt, String dia_chi, ArrayList<Items> arr_items, int tong_tien) {
        this.id = id;
        this.id_user = id_user;
        this.sdt = sdt;
        this.dia_chi = dia_chi;
        this.arr_items = arr_items;
        this.tong_tien = tong_tien;
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
