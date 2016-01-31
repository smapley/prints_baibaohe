package com.smapley.moni.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.R;
import com.smapley.moni.http.params.AddZhuangParams;
import com.smapley.moni.http.service.AddZhuangService;
import com.smapley.moni.mode.ZhuangMode;
import com.smapley.moni.util.MyData;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by smapley on 16/1/30.
 */
@ContentView(R.layout.activity_addzhuang)
public class AddZhuang extends AppCompatActivity {

    @ViewInject(R.id.title)
    private EditText title;
    @ViewInject(R.id.spinner)
    private Spinner spinner;
    @ViewInject(R.id.money)
    private EditText money;
    @ViewInject(R.id.peilv)
    private EditText peilv;
    @ViewInject(R.id.password)
    private EditText password;

    private String type = "1";

    private AddZhuangService addZhuangService = new AddZhuangService() {
        @Override
        public void Succ(String data) {
            int result = JSON.parseObject(data, new TypeReference<Integer>() {
            });
            if (result > 0) {
                Toast.makeText(AddZhuang.this, "房间创建成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddZhuang.this, "房间创建失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = i + 1 + " ";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void publish(View view) {
        ZhuangMode mode = new ZhuangMode();
        mode.setUser1(MyData.UserName);
        mode.setMi(MyData.PassWord);
        mode.setBiaoti(title.getText().toString());
        mode.setType(type);
        mode.setDan(money.getText().toString());
        mode.setPei(peilv.getText().toString());
        mode.setFjmi(password.getText().toString());
        addZhuangService.load(new AddZhuangParams(mode));
    }

}
