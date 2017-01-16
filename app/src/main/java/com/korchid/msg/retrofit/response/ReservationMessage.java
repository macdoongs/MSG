package com.korchid.msg.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac0314 on 2017-01-16.
 */

public class ReservationMessage {
    @SerializedName("reservation_message_id")
    @Expose
    private int reservation_message_id;
    @SerializedName("reservation_message_type_id")
    @Expose
    private int reservation_message_type_id;
    @SerializedName("content_txt")
    @Expose
    private String content_txt;


    public int getReservation_message_id() {
        return reservation_message_id;
    }

    public void setReservation_message_id(int reservation_message_id) {
        this.reservation_message_id = reservation_message_id;
    }

    public int getReservation_message_type_id() {
        return reservation_message_type_id;
    }

    public void setReservation_message_type_id(int reservation_message_type_id) {
        this.reservation_message_type_id = reservation_message_type_id;
    }

    public String getContent_txt() {
        return content_txt;
    }

    public void setContent_txt(String content_txt) {
        this.content_txt = content_txt;
    }
}
