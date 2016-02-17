package com.smapley.moni.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.R;
import com.smapley.moni.http.params.ReggaimiParams;
import com.smapley.moni.http.service.ReggaimiService;
import com.smapley.moni.util.MyData;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by smapley on 16/2/17.
 */
@ContentView(R.layout.activity_gaimi)
public class Gaimi extends Activity {

    @ViewInject(R.id.oldmi)
    private EditText oldmi;
    @ViewInject(R.id.newmi1)
    private EditText newmi1;
    @ViewInject(R.id.newmi2)
    private EditText newmi2;

    private ReggaimiService reggaimiService = new ReggaimiService() {
        @Override
        public void Succ(String data) {
            int result= JSON.parseObject(data,new TypeReference<Integer>(){});
            if(result>0){
                Toast.makeText(Gaimi.this, "修改密码成功，请重新登陆！", Toast.LENGTH_SHORT).show();
                SharedPreferences sp_user = Gaimi.this.getSharedPreferences("user", Gaimi.this.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp_user.edit();
                editor.putBoolean("islogin", false);
                editor.commit();
                MyData.Login = false;
                startActivity(new Intent(Gaimi.this, Login.class));
                MainActivity.stop();
                Gaimi.this.finish();
            }else {
                Toast.makeText(Gaimi.this, "修改密码失败！", Toast.LENGTH_SHORT).show();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    public void checkGaimi(View view) {
        if (!oldmi.getText().equals("") && !newmi1.getText().equals("") && !newmi2.getText().equals("")) {
            reggaimiService.load(new ReggaimiParams(MyData.UserName, oldmi.getText().toString(), newmi1.getText().toString(), newmi2.getText().toString()));
        } else {
            Toast.makeText(Gaimi.this, "请完善信息！", Toast.LENGTH_SHORT).show();
        }
    }

}
