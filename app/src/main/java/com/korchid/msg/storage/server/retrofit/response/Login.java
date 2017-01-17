package com.korchid.msg.storage.server.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2017-01-17.
 */

public class Login {
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("check_id")
    @Expose
    private Boolean check_id;
    @SerializedName("login")
    @Expose
    private Boolean login;
    @SerializedName("user")
    @Expose
    private User user;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getCheck_id() {
        return check_id;
    }

    public void setCheck_id(Boolean check_id) {
        this.check_id = check_id;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
