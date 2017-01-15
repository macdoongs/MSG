package com.korchid.msg.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac0314 on 2017-01-11.
 */

public class Res {
    // TODO Modify Res to contain object

    @SerializedName("fieldCount")
    @Expose
    private int fieldCount;
    @SerializedName("affectedRows")
    @Expose
    private int affectedRows;
    @SerializedName("insertId")
    @Expose
    private int insertId;
    @SerializedName("serverStatus")
    @Expose
    private int serverStatus;
    @SerializedName("warningCount")
    @Expose
    private int warningCount;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("protocol41")
    @Expose
    private Boolean protocol41;
    @SerializedName("changedRows")
    @Expose
    private int changedRows;

    public int getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    public int getAffectedRows() {
        return affectedRows;
    }

    public void setAffectedRows(int affectedRows) {
        this.affectedRows = affectedRows;
    }

    public int getInsertId() {
        return insertId;
    }

    public void setInsertId(int insertId) {
        this.insertId = insertId;
    }

    public int getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(int serverStatus) {
        this.serverStatus = serverStatus;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getProtocol41() {
        return protocol41;
    }

    public void setProtocol41(Boolean protocol41) {
        this.protocol41 = protocol41;
    }

    public int getChangedRows() {
        return changedRows;
    }

    public void setChangedRows(int changedRows) {
        this.changedRows = changedRows;
    }
}
