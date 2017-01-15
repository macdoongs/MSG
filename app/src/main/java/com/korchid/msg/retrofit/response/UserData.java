package com.korchid.msg.retrofit.response;

import com.google.api.client.util.DateTime;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by mac on 2017-01-11.
 */

public class UserData {
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("nickname_sn")
    @Expose
    private String nickname_sn;
    @SerializedName("sex_sn")
    @Expose
    private String sex_sn;
    @SerializedName("birthday_dt")
    @Expose
    private Date birthday_dt;
    @SerializedName("profile_ln")
    @Expose
    private String profile_ln;
    @SerializedName("role_name_sn")
    @Expose
    private String role_name_sn;
    @SerializedName("message_alert")
    @Expose
    private int message_alert;
    @SerializedName("reserve_enable")
    @Expose
    private int reserve_enable;
    @SerializedName("reserve_alert")
    @Expose
    private int reserve_alert;
    @SerializedName("week_number")
    @Expose
    private int week_number;
    @SerializedName("reserve_number")
    @Expose
    private int reserve_number;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname_sn() {
        return nickname_sn;
    }

    public void setNickname_sn(String nickname_sn) {
        this.nickname_sn = nickname_sn;
    }

    public String getSex_sn() {
        return sex_sn;
    }

    public void setSex_sn(String sex_sn) {
        this.sex_sn = sex_sn;
    }

    public Date getBirthday_dt() {
        return birthday_dt;
    }

    public void setBirthday_dt(Date birthday_dt) {
        this.birthday_dt = birthday_dt;
    }

    public String getProfile_ln() {
        return profile_ln;
    }

    public void setProfile_ln(String profile_ln) {
        this.profile_ln = profile_ln;
    }

    public String getRole_name_sn() {
        return role_name_sn;
    }

    public void setRole_name_sn(String role_name_sn) {
        this.role_name_sn = role_name_sn;
    }

    public int getMessage_alert() {
        return message_alert;
    }

    public void setMessage_alert(int message_alert) {
        this.message_alert = message_alert;
    }

    public int getReserve_enable() {
        return reserve_enable;
    }

    public void setReserve_enable(int reserve_enable) {
        this.reserve_enable = reserve_enable;
    }

    public int getReserve_alert() {
        return reserve_alert;
    }

    public void setReserve_alert(int reserve_alert) {
        this.reserve_alert = reserve_alert;
    }

    public int getWeek_number() {
        return week_number;
    }

    public void setWeek_number(int week_number) {
        this.week_number = week_number;
    }

    public int getReserve_number() {
        return reserve_number;
    }

    public void setReserve_number(int reserve_number) {
        this.reserve_number = reserve_number;
    }
}
