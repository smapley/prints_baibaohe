package com.smapley.moni.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
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

    @ViewInject(R.id.jin1_text)
    private TextView jin1_text;
    @ViewInject(R.id.jin1_ico)
    private TextView jin1_ico;
    @ViewInject(R.id.jin2_text)
    private TextView jin2_text;
    @ViewInject(R.id.jin2_ico)
    private TextView jin2_ico;
    @ViewInject(R.id.jin3_text)
    private TextView jin3_text;
    @ViewInject(R.id.jin3_ico)
    private TextView jin3_ico;
    @ViewInject(R.id.jin4_text)
    private TextView jin4_text;
    @ViewInject(R.id.jin4_ico)
    private TextView jin4_ico;
    @ViewInject(R.id.jin5_text)
    private TextView jin5_text;
    @ViewInject(R.id.jin5_ico)
    private TextView jin5_ico;
    @ViewInject(R.id.jin6_text)
    private TextView jin6_text;
    @ViewInject(R.id.jin6_ico)
    private TextView jin6_ico;

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

    @ViewInject(R.id.kai)
    private TextView kai;
    @ViewInject(R.id.guan)
    private TextView guan;

    private List<TextView> jinList;
    private List<TextView> jinIcoList;
    private TextView jin_text;

    @ViewInject(R.id.print_keybord)
    private View print_keybord;
    private int NowPostion = 0;
    private int zt=0;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        dialog=new ProgressDialog(MyHeZhuang.this);


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
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyHeZhuang.this);
                    float num = 0;
                    float num1 = Float.parseFloat(jin1_text.getText().toString());
                    float num2 = Float.parseFloat(jin2_text.getText().toString());
                    float num3 = Float.parseFloat(jin3_text.getText().toString());
                    float num4 = Float.parseFloat(jin4_text.getText().toString());
                    float num5 = Float.parseFloat(jin5_text.getText().toString());
                    float num6 = Float.parseFloat(jin6_text.getText().toString());
                    float num1s = Float.parseFloat(peilv1.getText().toString());
                    float num2s = Float.parseFloat(peilv2.getText().toString());
                    float num3s = Float.parseFloat(peilv3.getText().toString());
                    float num4s = Float.parseFloat(peilv4.getText().toString());
                    float num5s = Float.parseFloat(peilv5.getText().toString());
                    float num6s = Float.parseFloat(peilv6.getText().toString());
                    num = num1 * num1s * 6 + num2 * num2s * 4 + num3 * num3s + num4 * num4s * 4 + num5 * num5s * 5 + num6 * num6s;

                    builder.setTitle("本次操作需要扣除" + num + "金币");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.show();
                            addHezhuangService.load(new AddHezhuangParams(
                                    MyData.UserName, MyData.PassWord,zt+"",
                                    jin1_text.getText().toString(), jin2_text.getText().toString(),
                                    jin3_text.getText().toString(), jin4_text.getText().toString(),
                                    jin5_text.getText().toString(), jin6_text.getText().toString()));
                        }
                    });
                    builder.create().show();
                } catch (Exception e) {
                    Toast.makeText(MyHeZhuang.this, "请输入正确的数据！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        jinList = new ArrayList<>();
        jinIcoList = new ArrayList<>();

        jinList.add(jin1_text);
        jinList.add(jin2_text);
        jinList.add(jin3_text);
        jinList.add(jin4_text);
        jinList.add(jin5_text);
        jinList.add(jin6_text);

        jinIcoList.add(jin1_ico);
        jinIcoList.add(jin2_ico);
        jinIcoList.add(jin3_ico);
        jinIcoList.add(jin4_ico);
        jinIcoList.add(jin5_ico);
        jinIcoList.add(jin6_ico);

    }

    @Event({R.id.back, R.id.jin1_layout, R.id.jin2_layout, R.id.jin3_layout, R.id.jin4_layout, R.id.jin5_layout, R.id.jin6_layout,
            R.id.key_item1, R.id.key_item2, R.id.key_item3, R.id.key_item5, R.id.key_item6, R.id.key_item7, R.id.key_item9,
            R.id.key_item10, R.id.key_item11, R.id.key_item8, R.id.key_item12, R.id.key_item13, R.id.key_item15,
            R.id.kai, R.id.guan})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                print_keybord.setVisibility(View.GONE);
                break;
            case R.id.jin1_layout:
                editItem(0);
                break;
            case R.id.jin2_layout:
                editItem(1);
                break;
            case R.id.jin3_layout:
                editItem(2);
                break;
            case R.id.jin4_layout:
                editItem(3);
                break;
            case R.id.jin5_layout:
                editItem(4);
                break;
            case R.id.jin6_layout:
                editItem(5);
                break;
            case R.id.kai:
                kai.setBackgroundColor(Color.BLUE);
                guan.setBackgroundColor(Color.WHITE);
                zt=1;
                break;
            case R.id.guan:
                guan.setBackgroundColor(Color.BLUE);
                kai.setBackgroundColor(Color.WHITE);
                zt=0;
                break;

            case R.id.key_item8:

                break;
            case R.id.key_item12:
                if (NowPostion <= 4)
                    editItem(NowPostion + 1);
                break;

            case R.id.key_item15:
                String data = jin_text.getText().toString();
                if (data != null && !data.equals("")) {
                    if (data.length() > 0 || data.equals("0")) {
                        if (data.indexOf(".") == -1) {
                            jin_text.setText(jin_text.getText() + ((TextView) view).getText().toString());
                        }
                    }
                }
                break;

            default:
                if (jin_text.getText().toString().equals("0")) {
                    jin_text.setText(((TextView) view).getText().toString());
                } else {
                    jin_text.setText(jin_text.getText() + ((TextView) view).getText().toString());
                }
                break;
        }
    }

    private void editItem(int position) {
        for (int i = 0; i < 6; i++) {
            if (position == i) {
                jinIcoList.get(i).setVisibility(View.VISIBLE);
                jin_text = jinList.get(i);
                jin_text.setText("");
                NowPostion = i;
            } else {
                jinIcoList.get(i).setVisibility(View.GONE);
            }
        }
        print_keybord.setVisibility(View.VISIBLE);

    }

    private AddHezhuangService addHezhuangService = new AddHezhuangService() {
        @Override
        public void Succ(String data) {
            dialog.dismiss();
            int result = JSON.parseObject(data, new TypeReference<Integer>() {
            });
            if (result > 0) {
                Toast.makeText(MyHeZhuang.this, "保存成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MyHeZhuang.this, "保存失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private GetHezhuangService getHezhuangService = new GetHezhuangService() {
        @Override
        public void Succ(String data) {
            try {
                Map<String, String> map = JSON.parseObject(data, new TypeReference<Map<String, String>>() {
                });
                jin1_text.setText(map.get("erdj"));
                jin2_text.setText(map.get("sandj"));
                jin3_text.setText(map.get("sidj"));
                jin4_text.setText(map.get("erxj"));
                jin5_text.setText(map.get("sanxj"));
                jin6_text.setText(map.get("sixj"));
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

                if (Integer.parseInt(map.get("zt").toString()) == 0) {
                    kai.setBackgroundColor(Color.WHITE);
                    guan.setBackgroundColor(Color.BLUE);
                } else {
                    kai.setBackgroundColor(Color.BLUE);
                    guan.setBackgroundColor(Color.WHITE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
