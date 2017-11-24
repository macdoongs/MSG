package com.korchid.msg.storage.server.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac0314 on 2017-01-17.
 */

public class Invitation {
    @SerializedName("invitation")
    @Expose
    private Boolean invitation;
    @SerializedName("connection")
    @Expose
    private Boolean connection;

    public Boolean getInvitation() {
        return invitation;
    }

    public void setInvitation(Boolean invitation) {
        this.invitation = invitation;
    }

    public Boolean getConnection() {
        return connection;
    }

    public void setConnection(Boolean connection) {
        this.connection = connection;
    }
}
