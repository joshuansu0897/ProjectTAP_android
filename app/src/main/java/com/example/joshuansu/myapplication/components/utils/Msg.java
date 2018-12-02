package com.example.joshuansu.myapplication.components.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Msg {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private String error;

    public Msg() {
    }

    public Msg(String msg, String error) {
        this.msg = msg;
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
