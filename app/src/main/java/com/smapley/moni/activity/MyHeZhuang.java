package com.smapley.moni.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.R;
import com.smapley.moni.http.params.AddHezhuangParams;
import com.smapley.moni.http.params.GetHezhuangParams;
import com.smapley.moni.http.service.AddHezhuangService;
import com.smapley.moni.http.service.GetHezhuangService;
import com.smapley.moni.util.MyData;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Map;

/**
 * Created by smapley on 16/2/3.
 */
@ContentView(R.layout.activity_myhezhuang)
public class MyHeZhuang extends Activity {

    @ViewInject(R.id.title_item1)
    private TextView item1;
    @ViewInject(R.id.title_item2)
    private TextView item2;
    @ViewInject(R.id.title_item3)
    private TextView item3;

    @ViewInject(R.id.jine1)
    private EditText jine1;
    @ViewInject(R.id.jine2)
    private EditText jine2;
    @ViewInject(R.id.jine3)
    private EditText jine3;
    @ViewInject(R.id.jine4)
    private EditText jine4;
    @ViewInject(R.id.jine5)
    private EditText jine5;
    @ViewInject(R.id.jine6)
    private EditText jine6;
    @ViewInject(R.id.peilv1)
    private TextView peilv1;
    @ViewInject(R.id.peilv2)
    private TextView peilv2;
    @ViewInject(R.id.peilv3)
    private TextView peilv3;
    @ViewInject(R.id.peilv4)
    private TextView peilv4;
    @ViewInject(R.id.peilv5)
    private TextView peilv5;
    @ViewInject(R.id.peilv6)
    private TextView peilv6;
    @ViewInject(R.id.yiya1)
    private TextView yiya1;
    @ViewInject(R.id.yiya2)
    private TextView yiya2;
    @ViewInject(R.id.yiya3)
    private TextView yiya3;
    @ViewInject(R.id.yiya4)
    private TextView yiya4;
    @ViewInject(R.id.yiya5)
    private TextView yiya5;
    @ViewInject(R.id.yiya6)
    private TextView yiya6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        getHezhuangService.load(new GetHezhuangParams(MyData.UserName, MyData.PassWord));
        item1.setText("明细");
        item2.setText("我的合庄");
        item3.setText("确定");

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyHeZhuang.this, Detail2.class));
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHezhuangService.load(new AddHezhuangParams(
                        MyData.UserName, MyData.PassWord,
                        jine1.getText().toString(), jine2.getText().toString(),
                        jine3.getText().toString(), jine4.getText().toString(),
                        jine5.getText().toString(), jine6.getText().toString()));
            }
        });
    }

    private AddHezhuangService addHezhuangService = new AddHezhuangService() {
        @Override
        public void Succ(String data) {
            int result=JSON.parseObject(data,new TypeReference<Integer>(){});
            if(result>0){
                Toast.makeText(MyHeZhuang.this,"保存成功！",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MyHeZhuang.this,"保存失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private GetHezhuangService getHezhuangService = new GetHezhuangService() {
        @Override
        public void Succ(String data) {
            Map<String, String> map = JSON.parseObject(data, new TypeReference<Map<String, String>>() {
            });
            jine1.setText(map.get("erdj"));
            jine2.setText(map.get("sandj"));
            jine3.setText(map.get("sidj"));
            jine4.setText(map.get("erxj"));
            jine5.setText(map.get("sanxj"));
            jine6.setText(map.get("sixj"));
            peilv1.setText(map.get("erdp"));
            peilv2.setText(map.get("sandp"));
            peilv3.setText(map.get("sidp"));
            peilv4.setText(map.get("erxp"));
            peilv5.setText(map.get("sanxp"));
            peilv6.setText(map.get("sixp"));
            yiya1.setText(map.get("erdya"));
            yiya2.setText(map.get("sandya"));
            yiya3.setText(map.get("sidya"));
            yiya4.setText(map.get("erxya"));
            yiya5.setText(map.get("sanxya"));
            yiya6.setText(map.get("sixya"));
        }
    };
}
