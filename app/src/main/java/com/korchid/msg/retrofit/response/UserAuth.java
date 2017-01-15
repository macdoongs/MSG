package com.korchid.msg.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2017-01-11.
 */

public class UserAuth {
    @SerializedName("password_sn")
    @Expose
    private String password_sn;

    public String getPassword_sn() {
        return password_sn;
    }

    public void setPassword_sn(String password_sn) {
        this.password_sn = password_sn;
    }
}
