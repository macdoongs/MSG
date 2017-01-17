package com.korchid.msg.storage.server.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac0314 on 2017-01-17.
 */

public class MappingData {
    @SerializedName("parentId")
    @Expose
    private String parentId;
    @SerializedName("childId")
    @Expose
    private String childId;
    @SerializedName("topic")
    @Expose
    private String topic;


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
