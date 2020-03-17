package com.example.chatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private List<Msg> msgList=new ArrayList<>();
    private List<Msg> getList=new ArrayList<>();
    Gson gson=new Gson();
    private EditText editText;
    private Button button;
    private RecyclerView recyclerView;
    private  MsgAdapter adapter;
    private boolean Send=true;
    private JSONObject jsonObject = null;
    private Msg ldate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.input_text);
        button = (Button) findViewById(R.id.send);
        recyclerView = (RecyclerView) findViewById(R.id.msg_recycle_view);
        adapter = new MsgAdapter(msgList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        FloatingActionButton back=(FloatingActionButton) findViewById(R.id.back);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        SharedPreferences getlist=getSharedPreferences("list",MODE_PRIVATE);
        String chatdata=getlist.getString("listchat","");
        Gson getgson=new Gson();
        getList=getgson.fromJson(chatdata,new TypeToken<List<Msg>>(){}.getType());
        if(getList!=null){
        msgList.addAll(getList);}
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg;
                    if (Send) {
                        msg = new Msg(content, Msg.TYPE_SEND);
                    } else {
                        msg = new Msg(content, Msg.TYPE_RECEIVED);
                    }
                    //  Send = !Send;
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    recyclerView.scrollToPosition(msgList.size() - 1);
                    editText.setText("");
                    String url = "http://www.tuling123.com/openapi/api?key=11b9960eb1bc414281588b1ffd95d558" +
                            "&info=" + content;

                    // 调用封装好的类 返回数据
                    HttpRequestAndParse.sendhttpRequest(url, new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            //异常处理
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            // 获得服务器返回的json数据
                            String string = response.body().string();
                            try {

                                //json数据的解析
                                jsonObject = new JSONObject(string);

                                // 更新ui
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            ldate = new Msg(jsonObject.getString("text"), Msg.TYPE_RECEIVED);
                                            msgList.add(ldate);
                                            adapter.notifyItemInserted(msgList.size() - 1);
                                            recyclerView.scrollToPosition(msgList.size() - 1);
                                            adapter.notifyDataSetChanged();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    });

                }

            }});
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=gson.toJson(msgList);
                SharedPreferences.Editor savalist=getSharedPreferences("list",MODE_PRIVATE).edit();
                savalist.putString("listchat",data);
                savalist.apply();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                  startActivity(intent);

            }
        });}





}
