package com.korchid.msg.setting.application;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class Setting {
    private int id;
    private String picture;
    private String title;

    public Setting (int id, String picture, String title){
        this.id = id;
        this.picture = picture;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
