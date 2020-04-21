package com.example.entity;

public class EventBean {
    private String code;
    private String msg;

    public EventBean(String msg) {
        this.msg = msg;
    }

    public EventBean(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
