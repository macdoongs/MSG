package com.korchid.msg;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class Chatting {
    private String user;
    private String message;

    public Chatting(String user, String message){
        this.user = user;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
