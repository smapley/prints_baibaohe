package com.smapley.moni.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smapley.moni.R;
import com.smapley.moni.http.params.Reg1Params;
import com.smapley.moni.http.params.SendSMSParams;
import com.smapley.moni.http.service.Reg1Service;
import com.smapley.moni.http.service.SendSMSService;
import com.smapley.moni.util.MyData;
import com.smapley.moni.util.ThreadSleep;

/**
 * Created by smapley on 15/10/26.
 */
public class Login extends Activity {
    private EditText log_et_username;
    private EditText log_et_password;

    private String log_st_usernmae;
    private String log_st_password;

    private boolean isLogin = false;
    private SharedPreferences sp_user;

    private boolean canSend = true;
    private Button login_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initData();
        if (isLogin) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
        initParams();


    }

    private void initData() {
        sp_user = getSharedPreferences("user", MODE_PRIVATE);

        isLogin = sp_user.getBoolean("islogin", false);
        MyData.Login = isLogin;

        log_st_usernmae = sp_user.getString("user1", "");
        log_st_password = sp_user.getString("mi", "");
        MyData.UserName = log_st_usernmae;
        MyData.PassWord = log_st_password;
    }

    protected void initParams() {


        log_et_username = (EditText) findViewById(R.id.login_phone);
        log_et_password = (EditText) findViewById(R.id.login_key);
        login_send=(Button)findViewById(R.id.login_send);
        log_et_password.setText(log_st_password);
        log_et_username.setText(log_st_usernmae);
    }


    public void sendMess(View view){
        if (canSend) {
            log_st_usernmae = log_et_username.getText().toString();
            if (log_st_usernmae != null && log_st_usernmae.length() == 11) {
                sendSMSService.load(new SendSMSParams(log_st_usernmae));
            } else {
                Toast.makeText(Login.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkLogin(View view) {
        log_st_usernmae=log_et_username.getText().toString();
        if (log_st_usernmae != null && !log_st_usernmae.equals("")) {
            log_st_password = log_et_password.getText().toString();
            if (log_st_password != null && !log_st_password.equals("")) {
                reg1Service.load(new Reg1Params(log_st_usernmae, log_st_password));
            } else {
                Toast.makeText(Login.this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Login.this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();

        }
    }

    private SendSMSService sendSMSService=new SendSMSService() {
        @Override
        public void Succ() {
            canSend = false;
            new ThreadSleep().isLoop().sleep(1000, new ThreadSleep.Callback() {
                @Override
                public void onCallback(ThreadSleep threadSleep, int number) {
                    login_send.setText("已发送 (" + (60 - number) + ")");
                    if (number >= 60) {
                        threadSleep.stop();
                        canSend = true;
                        login_send.setText(R.string.login_send);
                    }
                }
            });
        }
    };
    private Reg1Service reg1Service = new Reg1Service() {
        @Override
        public void Succ() {
            afterLogin();
        }

        @Override
        public void Fail() {
            Toast.makeText(Login.this, "登陆失败！", Toast.LENGTH_SHORT).show();
        }
    };

    private void afterLogin() {
        SharedPreferences.Editor editor = sp_user.edit();
        editor.putString("user1", log_st_usernmae);
        editor.putString("mi", log_st_password);
        editor.putBoolean("islogin", true);
        editor.commit();
        MyData.UserName=log_st_usernmae;
        MyData.PassWord=log_st_password;
        MyData.Login=true;
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }
}
