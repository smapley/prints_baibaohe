package com.smapley.moni.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.R;
import com.smapley.moni.adapter.DetailAdapter2;
import com.smapley.moni.listview.SwipeMenuListView;
import com.smapley.moni.util.HttpUtils;
import com.smapley.moni.util.MyData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2015/11/8.
 */
public class Detail3 extends Activity {

    private SwipeMenuListView listView;


    private DetailAdapter2 adapter1;

    private final int GETDATA1 = 1;

    private static List<Map<String, String>> list1;
    private List<Map<String, String>> list1_now;


    private TextView page_up;
    private TextView page_num;
    private TextView page_down;

    private int now_item = 1;
    private int page_num1 = 1;

    public static Dialog dialog;

    public TextView item2;

    private String zhang;
    private String aaa="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail2);

        zhang=getIntent().getStringExtra("zhang");
        aaa=getIntent().getStringExtra("aaa");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_item1);
        builder.setMessage(R.string.dialog_item2);
        dialog = builder.create();

        initView();
        getData(GETDATA1);
    }



    private void initView() {

        item2=(TextView)findViewById(R.id.title_item2);
        item2.setText("明细");

        listView = (SwipeMenuListView) findViewById(R.id.detail_list);
        listView.setDivider(null);

        page_down = (TextView) findViewById(R.id.page_down);
        page_num = (TextView) findViewById(R.id.page_num);
        page_up = (TextView) findViewById(R.id.page_up);
        page_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (now_item == 1) {
                    if (page_num1 * 100 < list1.size()) {
                        if (page_num1 * 100 + 100 < list1.size()) {
                            list1_now = list1.subList(page_num1 * 100, page_num1 * 100 + 100);
                        } else {
                            list1_now = list1.subList(page_num1 * 100, list1.size());
                        }
                        page_num1++;
                        page_num.setText(page_num1 + "");
                        adapter1 = new DetailAdapter2(Detail3.this, list1_now);
                        listView.setAdapter(adapter1);
                    }
                }
            }
        });

        page_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (now_item == 1) {
                    if (page_num1 > 1) {
                        page_num1--;
                        list1_now = list1.subList(page_num1 * 100 - 100, page_num1 * 100);
                        page_num.setText(page_num1 + "");
                        adapter1 = new DetailAdapter2(Detail3.this, list1_now);
                        listView.setAdapter(adapter1);
                    }
                }
            }
        });


    }

    private void getData(final int num) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("zhang", zhang);
                map.put("aaa",aaa);
                String url = null;
                if (num == GETDATA1) {
                    url = MyData.URL_getMingxi2;
                }
                mhandler.obtainMessage(num, HttpUtils.updata(map, url)).sendToTarget();
            }
        }).start();

    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case GETDATA1:
                        Map<String ,String> map0 = JSON.parseObject(msg.obj.toString(),new TypeReference<Map<String, String>>(){});
                        item2.setText("共"+map0.get("allgold"));
                        list1 = JSON.parseObject(map0.get("result"), new TypeReference<List<Map<String, String>>>() {
                        });
                        for (int i = 0; i < list1.size(); i++) {
                            switch (Integer.parseInt(list1.get(i).get("zt").toString())) {
                                case 0:
                                    list1.get(i).put("zt", "未打印");

                                    break;
                                case 1:
                                    list1.get(i).put("zt", "已打印");
                                    break;
                                case 9:
                                    list1.get(i).put("zt", "已退码");
                                    break;
                            }
                        }
                        if (list1.size() > 100) {
                            list1_now = list1.subList(0, 100);
                        } else {
                            list1_now = list1;
                        }
                        adapter1 = new DetailAdapter2(Detail3.this, list1_now);
                        listView.setAdapter(adapter1);

                        break;






                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Detail3.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };


}
