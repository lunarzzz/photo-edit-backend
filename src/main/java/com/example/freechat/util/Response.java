package com.example.freechat.util;

public class Response {
    private Object data;
    private int code;
    private String msg;

    public Response(Object data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static Response ok() {
        return new Response(null, 200, null);
    }

    public Response withData(Object data) {
        this.data = data;
        return this;
    }

    public Response msg(String msg) {
        this.msg = msg;
        return this;
    }

    public static Response notFount(String msg) {
        return new Response(null, 404, null);
    }
}
