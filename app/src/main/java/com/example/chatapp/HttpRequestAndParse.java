package com.example.chatapp;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 此文件打不开 on 2020/3/15.
 */

public class HttpRequestAndParse {
    public  static  void sendhttpRequest(String address,okhttp3.Callback callback) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
