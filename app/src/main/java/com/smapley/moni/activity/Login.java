package com.smapley.moni.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.R;
import com.smapley.moni.http.params.Reg1Params;
import com.smapley.moni.http.params.RegzhuceParams;
import com.smapley.moni.http.service.Reg1Service;
import com.smapley.moni.http.service.RegzhuceService;
import com.smapley.moni.util.MyData;

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
        log_et_password.setText(log_st_password);
        log_et_username.setText(log_st_usernmae);
    }


    public void checkLogin(View view) {
        log_st_usernmae = log_et_username.getText().toString();
        if (log_st_usernmae != null && !log_st_usernmae.equals("")) {
            log_st_password = log_et_password.getText().toString();
            if (log_st_password != null && !log_st_password.equals("")) {
                reg1Service.load(new Reg1Params(log_st_usernmae, log_st_password));
            } else {
                Toast.makeText(Login.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Login.this, "账号不能为空！", Toast.LENGTH_SHORT).show();

        }
    }

    private Reg1Service reg1Service = new Reg1Service() {
        @Override
        public void Succ(String data) {
            int result = JSON.parseObject(data, new TypeReference<Integer>() {
            });
            switch (result) {
                case 1:
                    afterLogin();
                    break;
                case -2:
                    AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                    builder.setTitle("请核对注册信息：");
                    builder.setMessage("账号：" + log_st_usernmae + "\n密码：" + log_st_password);
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            regzhuceService.load(new RegzhuceParams(log_st_usernmae, log_st_password));
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();
                    break;
                default:
                    Toast.makeText(Login.this, "登陆失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void afterLogin() {
        SharedPreferences.Editor editor = sp_user.edit();
        editor.putString("user1", log_st_usernmae);
        editor.putString("mi", log_st_password);
        editor.putBoolean("islogin", true);
        editor.commit();
        MyData.UserName = log_st_usernmae;
        MyData.PassWord = log_st_password;
        MyData.Login = true;
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }

    private RegzhuceService regzhuceService=new RegzhuceService() {
        @Override
        public void Succ(String data) {
            int result=JSON.parseObject(data,new TypeReference<Integer>(){});
            if(result>0){
                afterLogin();
            }else{
                Toast.makeText(Login.this, "注册失败！", Toast.LENGTH_SHORT).show();

            }
        }
    };
}
