package com.smapley.moni.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.R;
import com.smapley.moni.http.params.GetDyszParams;
import com.smapley.moni.http.params.UpdateDyszParams;
import com.smapley.moni.http.service.GetDyszService;
import com.smapley.moni.http.service.UpdateDyszService;
import com.smapley.moni.util.MyData;
import com.smapley.moni.util.ThreadSleep;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 16/2/6.
 */
@ContentView(R.layout.activity_dayinji)
public class DaYinJi extends Activity {

    @ViewInject(R.id.title_item2)
    private TextView item2;
    @ViewInject(R.id.title_item3)
    private TextView item3;
    @ViewInject(R.id.dayinji_name)
    private EditText name;
    @ViewInject(R.id.dayinji_beizhu)
    private EditText beizhu;

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


    private List<TextView> jinList;
    private List<TextView> jinIcoList;
    private TextView jin_text;
    @ViewInject(R.id.print_keybord)
    private View print_keybord;
    private int NowPostion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        getDyszService.load(new GetDyszParams(MyData.UserName));
        item2.setText("资料设置");
        item3.setText("保存");
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDyszService.load(new UpdateDyszParams(MyData.UserName, MyData.PassWord,
                        name.getText().toString(), beizhu.getText().toString(),
                        jin1_text.getText().toString(), jin2_text.getText().toString(), jin3_text.getText().toString(),
                        jin4_text.getText().toString(), jin5_text.getText().toString(), jin6_text.getText().toString()));
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print_keybord.setVisibility(View.GONE);

            }
        });
        beizhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print_keybord.setVisibility(View.GONE);

            }
        });

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    print_keybord.setVisibility(View.GONE);
                }
            }
        });
        beizhu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    print_keybord.setVisibility(View.GONE);
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

    private GetDyszService getDyszService = new GetDyszService() {
        @Override
        public void Succ(String data) {
            Map<String,String> map = JSON.parseObject(data,new TypeReference<Map<String, String>>(){});
            name.setText(map.get("ming"));
            beizhu.setText(map.get("beizhu"));
            jin1_text.setText(map.get("erdpei"));
            jin2_text.setText(map.get("sandpei"));
            jin3_text.setText(map.get("sidpei"));
            jin4_text.setText(map.get("erxpei"));
            jin5_text.setText(map.get("sanxpei"));
            jin6_text.setText(map.get("sixpei"));
            yiya1.setText(map.get("erdfan"));
            yiya2.setText(map.get("sandfan"));
            yiya3.setText(map.get("sidfan"));
            yiya4.setText(map.get("erxfan"));
            yiya5.setText(map.get("sanxfan"));
            yiya6.setText(map.get("sixfan"));

        }
    };

    private UpdateDyszService updateDyszService = new UpdateDyszService() {
        @Override
        public void Succ(String data) {
            int result= JSON.parseObject(data,new TypeReference<Integer>(){});
            if(result>0){
                Toast.makeText(DaYinJi.this,"保存成功！",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(DaYinJi.this,"保存失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Event({R.id.back, R.id.jin1_layout, R.id.jin2_layout, R.id.jin3_layout, R.id.jin4_layout, R.id.jin5_layout, R.id.jin6_layout,
            R.id.key_item1, R.id.key_item2, R.id.key_item3, R.id.key_item5, R.id.key_item6, R.id.key_item7, R.id.key_item9,
            R.id.key_item10, R.id.key_item11, R.id.key_item8, R.id.key_item12, R.id.key_item13, R.id.key_item15})
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
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(name.getWindowToken(), 0);
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
            new ThreadSleep().sleep(500, new ThreadSleep.Callback() {
                @Override
                public void onCallback(ThreadSleep threadSleep, int number) {
                    print_keybord.setVisibility(View.VISIBLE);
                }
            });
        }

}
