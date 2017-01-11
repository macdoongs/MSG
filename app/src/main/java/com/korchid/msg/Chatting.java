package com.korchid.msg;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class Chatting {
    private String userName;
    private String message;

    public Chatting(String userName, String message){
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
