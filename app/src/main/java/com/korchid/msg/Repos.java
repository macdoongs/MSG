package com.korchid.msg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2017-01-03.
 */

public class Repos {
    // 현재 사용하지 않음.

    @SerializedName("PhoneNumber")
    @Expose
    private String PhoneNumber;
    @SerializedName("Nickname")
    @Expose
    private String Nickname;
    @SerializedName("Sex")
    @Expose
    private String Sex;
    @SerializedName("Role")
    @Expose
    private String Role;
    @SerializedName("Birthday")
    @Expose
    private String Birthday;
    @SerializedName("Profile")
    @Expose
    private String Profile;
    @SerializedName("Enable")
    @Expose
    private int Enable;
    @SerializedName("Alert")
    @Expose
    private int Alert;
    @SerializedName("Weeknumber")
    @Expose
    private int Weeknumber;
    @SerializedName("SendTimes")
    @Expose
    private int SendTimes;

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }

    public int getEnable() {
        return Enable;
    }

    public void setEnable(int enable) {
        Enable = enable;
    }

    public int getAlert() {
        return Alert;
    }

    public void setAlert(int alert) {
        Alert = alert;
    }

    public int getWeeknumber() {
        return Weeknumber;
    }

    public void setWeeknumber(int weeknumber) {
        Weeknumber = weeknumber;
    }

    public int getSendTimes() {
        return SendTimes;
    }

    public void setSendTimes(int sendTimes) {
        SendTimes = sendTimes;
    }

    @Override
    public String toString(){
        return "PhoneNumber : " + PhoneNumber + ", Nickname : " + Nickname;
    }
}
