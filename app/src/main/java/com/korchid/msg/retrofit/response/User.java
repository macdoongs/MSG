package com.korchid.msg.retrofit.response;

import com.google.api.client.util.DateTime;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * Created by mac on 2017-01-10.
 */

public class User {
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("phone_number_sn")
    @Expose
    private String phone_number_sn;
    @SerializedName("password_sn")
    @Expose
    private String password_sn;
    @SerializedName("login_token_ln")
    @Expose
    private String login_token_ln;
    @SerializedName("device_token_ln")
    @Expose
    private String device_token_ln;
    @SerializedName("create_dtm")
    @Expose
    private Timestamp create_dtm;
    @SerializedName("password_changed_dtm")
    @Expose
    private Timestamp password_changed_dtm;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPhone_number_sn() {
        return phone_number_sn;
    }

    public void setPhone_number_sn(String phone_number_sn) {
        this.phone_number_sn = phone_number_sn;
    }

    public String getPassword_sn() {
        return password_sn;
    }

    public void setPassword_sn(String password_sn) {
        this.password_sn = password_sn;
    }

    public String getLogin_token_ln() {
        return login_token_ln;
    }

    public void setLogin_token_ln(String login_token_ln) {
        this.login_token_ln = login_token_ln;
    }

    public String getDevice_token_ln() {
        return device_token_ln;
    }

    public void setDevice_token_ln(String device_token_ln) {
        this.device_token_ln = device_token_ln;
    }

    public Timestamp getCreate_dtm() {
        return create_dtm;
    }

    public void setCreate_dtm(Timestamp create_dtm) {
        this.create_dtm = create_dtm;
    }

    public Timestamp getPassword_changed_dtm() {
        return password_changed_dtm;
    }

    public void setPassword_changed_dtm(Timestamp password_changed_dtm) {
        this.password_changed_dtm = password_changed_dtm;
    }
}
