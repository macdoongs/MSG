package com.korchid.msg.storage.server.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2017-01-17.
 */

public class Signup {
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("duplicate")
    @Expose
    private Boolean duplicate;
    @SerializedName("signup")
    @Expose
    private Boolean signup;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(Boolean duplicate) {
        this.duplicate = duplicate;
    }

    public Boolean getSignup() {
        return signup;
    }

    public void setSignup(Boolean signup) {
        this.signup = signup;
    }
}
