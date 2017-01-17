package com.korchid.msg.storage.server.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac0314 on 2017-01-18.
 */

public class DeviceToken {
    @SerializedName("register")
    @Expose
    private Boolean register;

    public Boolean getRegister() {
        return register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }
}
