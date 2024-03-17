package com.example.hamstore.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Hoa_Don implements Serializable {
    private String id;
    private ArrayList<Items> arr_items;

    public Hoa_Don() {
    }

    public Hoa_Don(String id, ArrayList<Items> arr_items) {
        this.id = id;
        this.arr_items = arr_items;
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
