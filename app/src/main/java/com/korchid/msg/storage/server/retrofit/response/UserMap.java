package com.korchid.msg.storage.server.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac0314 on 2017-01-15.
 */

public class UserMap {
    @SerializedName("nickname_sn")
    @Expose
    private String nickname_sn;
    @SerializedName("phone_number_sn")
    @Expose
    private String phone_number_sn;
    @SerializedName("profile_ln")
    @Expose
    private String profile_ln;
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("topic_mn")
    @Expose
    private String topic_mn;


    public String getNickname_sn() {
        return nickname_sn;
    }

    public void setNickname_sn(String nickname_sn) {
        this.nickname_sn = nickname_sn;
    }

    public String getPhone_number_sn() {
        return phone_number_sn;
    }

    public void setPhone_number_sn(String phone_number_sn) {
        this.phone_number_sn = phone_number_sn;
    }

    public String getProfile_ln() {
        return profile_ln;
    }

    public void setProfile_ln(String profile_ln) {
        this.profile_ln = profile_ln;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTopic_mn() {
        return topic_mn;
    }

    public void setTopic_mn(String topic_mn) {
        this.topic_mn = topic_mn;
    }
}
