package com.smapley.prints2.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.prints2.R;
import com.smapley.prints2.util.HttpUtils;
import com.smapley.prints2.util.MyData;

import java.util.HashMap;

/**
 * Created by smapley on 15/10/26.
 */
public class Login extends Activity {
    private EditText log_et_username1;
    private EditText log_et_password1;
    private EditText log_et_username2;
    private EditText log_et_password2;

    private String log_st_usernmae1;
    private String log_st_password1;
    private String log_st_usernmae2;
    private String log_st_password2;

    private boolean isLogin1 = false;
    private boolean isLogin2 = false;
    private SharedPreferences sp_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initData();
        if (isLogin1 || isLogin2) {
            MyData.User = 1;
            MyData.UserName = MyData.UserName1;
            MyData.PassWord = MyData.PassWord1;
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
        initParams();


    }

    private void initData() {
        sp_user = getSharedPreferences("user", MODE_PRIVATE);

        isLogin1 = sp_user.getBoolean("login1", false);
        isLogin2 = sp_user.getBoolean("login2", false);
        MyData.Login1 = isLogin1;
        MyData.Login2 = isLogin2;

        log_st_usernmae1 = sp_user.getString("username1", "");
        log_st_password1 = sp_user.getString("password1", "");
        log_st_usernmae2 = sp_user.getString("username2", "");
        log_st_password2 = sp_user.getString("password2", "");
        MyData.UserName1 = log_st_usernmae1;
        MyData.UserName2 = log_st_usernmae2;
        MyData.PassWord1 = log_st_password1;
        MyData.PassWord2 = log_st_password2;
    }

    protected void initParams() {


        log_et_username1 = (EditText) findViewById(R.id.log_et_username1);
        log_et_password1 = (EditText) findViewById(R.id.log_et_password1);
        log_et_username2 = (EditText) findViewById(R.id.log_et_username2);
        log_et_password2 = (EditText) findViewById(R.id.log_et_password2);
        log_et_password1.setText(log_st_password1);
        log_et_username1.setText(log_st_usernmae1);
        log_et_password2.setText(log_st_password2);
        log_et_username2.setText(log_st_usernmae2);
    }


    private void doLogin() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("zhang", log_st_usernmae1);
                map.put("mi", log_st_password1);
                map.put("zhang1", log_st_usernmae2);
                map.put("mi1", log_st_password2);
                mhandler.obtainMessage(1, HttpUtils.updata(map, MyData.URL_reg)).sendToTarget();
            }
        }).start();
    }

    public Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case 1:
                        int result = JSON.parseObject(msg.obj.toString(), new TypeReference<Integer>() {
                        });
                        if (result > 0) {
                            SharedPreferences.Editor editor = sp_user.edit();
                            editor.putString("username1", log_st_usernmae1);
                            editor.putString("password1", log_st_password1);
                            editor.putBoolean("login1", true);
                            editor.putString("username2", log_st_usernmae2);
                            editor.putString("password2", log_st_password2);
                            editor.putBoolean("login2", true);
                            editor.commit();
                            MyData.UserName1 = log_st_usernmae1;
                            MyData.UserName2 = log_st_usernmae2;
                            MyData.PassWord1 = log_st_password1;
                            MyData.PassWord2 = log_st_password2;
                            MyData.Login1 = true;
                            MyData.Login2 = true;
                            MyData.User = 1;
                            MyData.UserName = MyData.UserName1;
                            MyData.PassWord = MyData.PassWord1;
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Login.this, "登陆失败！", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Login.this, "网络连接失败！", Toast.LENGTH_SHORT).show();

            }
        }
    };


    public void checkLogin(View view) {
        log_st_usernmae1 = log_et_username1.getText().toString();
        log_st_usernmae2 = log_et_username2.getText().toString();
        log_st_password1 = log_et_password1.getText().toString();
        log_st_password2 = log_et_password2.getText().toString();
        if (log_st_usernmae1 != null && !log_st_usernmae1.equals("")) {
            if (log_st_password1 != null && !log_st_password1.equals("")) {
                doLogin();
            } else {
                Toast.makeText(Login.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Login.this, "密码不能为空！", Toast.LENGTH_SHORT).show();

        }
    }
}
