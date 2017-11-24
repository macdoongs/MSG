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
    @SerializedName("setting")
    @Expose
    private Boolean setting;
    @SerializedName("information")
    @Expose
    private Boolean information;

    
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

    public Boolean getSetting() {
        return setting;
    }

    public void setSetting(Boolean setting) {
        this.setting = setting;
    }

    public Boolean getInformation() {
        return information;
    }

    public void setInformation(Boolean information) {
        this.information = information;
    }
}
