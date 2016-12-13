package com.korchid.msg;

/**
 * Created by Mac on 2016-12-13.
 */

public class MessageSetting extends Setting {
    private static final String TAG = "MessageSetting";

    public enum Type {POLITE, IMPOLITE, IN_PERSON}

    private Type type;


    public MessageSetting(int id, String picture, Type type, String title) {
        super(id, picture, title);

        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public String getPicture() {
        return super.getPicture();
    }

    @Override
    public void setPicture(String picture) {
        super.setPicture(picture);
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }
}
