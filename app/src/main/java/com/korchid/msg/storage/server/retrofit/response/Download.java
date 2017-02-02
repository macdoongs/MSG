package com.korchid.msg.storage.server.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac0314 on 2017-02-02.
 */

public class Download {
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("ext")
    @Expose
    private String ext;


}
