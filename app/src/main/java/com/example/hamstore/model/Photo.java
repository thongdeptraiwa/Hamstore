package com.example.hamstore.model;

import java.io.Serializable;

public class Photo implements Serializable{
    public Photo(int resourceId) {
        this.resourceId = resourceId;
    }

    private int resourceId;

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
