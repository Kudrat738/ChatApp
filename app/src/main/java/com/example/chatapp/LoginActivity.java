package com.example.chatapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by 此文件打不开 on 2020/3/15.
 */

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText accountEditText;
   private EditText passwordEditText;
    private boolean rememberPass;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountEditText=(EditText) findViewById(R.id.account);
        passwordEditText=(EditText) findViewById(R.id.password);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
         FloatingActionButton login=(FloatingActionButton) findViewById(R.id.login);
        final FloatingActionButton remember=(FloatingActionButton) findViewById(R.id.remember);
        imageView=(ImageView)findViewById(R.id.remember_view);
        SharedPreferences preferences=getSharedPreferences("remember",MODE_PRIVATE);
        rememberPass=preferences.getBoolean("remember",false);

        remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rememberPass =!rememberPass;
                if(rememberPass){

                    imageView.setImageResource(R.drawable.secend);
                }else {
                    imageView.setImageResource(R.drawable.first);
                }

                SharedPreferences.Editor sava=getSharedPreferences("remember",MODE_PRIVATE).edit();
                sava.clear();
                sava.putBoolean("remember",rememberPass);
                sava.apply();

            }});

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account=accountEditText.getText().toString();
                String password=passwordEditText.getText().toString();

                if(account.equals("986238684")&&password.equals("123456")){//登录成功
                    editor = pref.edit();
                    if(rememberPass){
//储存数据到SharedPreferences

                        editor.putString("account",account);
                        editor.putString("password",password);
                    }
                    else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);


                }else{
                    Toast.makeText(LoginActivity.this,"invalid",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences=getSharedPreferences("remember",MODE_PRIVATE);
        rememberPass=preferences.getBoolean("remember",false);
        //判断是否需要记住账户密码
        if(rememberPass){
//从SharedPreferences中获取保存的账户及密码信息
            String account = pref.getString("account","");
            String password = pref.getString("password","");
//将账户和密码设置到文本框
            accountEditText.setText(account);
            passwordEditText.setText(password);
        }else {
            accountEditText.setText("");
            passwordEditText.setText("");
        }

    }

}
