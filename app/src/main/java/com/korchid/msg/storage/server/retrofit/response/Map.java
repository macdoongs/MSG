package com.korchid.msg.storage.server.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac0314 on 2017-01-17.
 */

public class Map {
    @SerializedName("mapping")
    @Expose
    private Boolean mapping;
    @SerializedName("mappingData")
    @Expose
    private MappingData mappingData;

    public Boolean getMapping() {
        return mapping;
    }

    public void setMapping(Boolean mapping) {
        this.mapping = mapping;
    }

    public MappingData getMappingData() {
        return mappingData;
    }

    public void setMappingData(MappingData mappingData) {
        this.mappingData = mappingData;
    }
}
