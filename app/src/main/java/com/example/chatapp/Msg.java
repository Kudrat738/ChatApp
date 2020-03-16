package com.example.chatapp;

/**
 * Created by 此文件打不开 on 2020/3/15.
 */

public class Msg {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SEND=1;
    private String content;
    private int Type;
    public Msg(String content,int Type) {
        this.content=content;
        this.Type=Type;
    }
    public String getContent() {

        return content;
    }
    public int getType() {

        return Type;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setFlag(int Type) {
        this.Type = Type;
    }
}
