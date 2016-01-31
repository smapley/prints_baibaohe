package com.smapley.prints2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.prints2.R;
import com.smapley.prints2.util.HttpUtils;
import com.smapley.prints2.util.MyData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 15/10/23.
 */
public class DuiJiang extends Activity {

    private final int GETDATA=1;
    private final int DUiJIANG=2;
    private String user1;
    private String allid;
    private TextView qishu;
    private TextView shijian;
    private ListView listView;
    private TextView duijiang;
    private SimpleAdapter adapter;
    private List<Map<String,String>> list = new ArrayList<>();
    private boolean CanDuiJiang=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duijiang);
        initView();
        initData();
        getData();

    }

    private void initView(){
        qishu=(TextView)findViewById(R.id.duijiang_qishu);
        shijian=(TextView)findViewById(R.id.duijiang_shijian);
        duijiang=(TextView)findViewById(R.id.duijiang_duijiang);
        listView=(ListView)findViewById(R.id.duijiang_listview);
        duijiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CanDuiJiang){
                    doDuiJiang();
                }
            }
        });
        adapter=new SimpleAdapter(DuiJiang.this,list, R.layout.list_item,
                new String[]{"number","gold","pei"},
                new int[]{R.id.list_item1,R.id.list_item2, R.id.list_item3});
        listView.setAdapter(adapter);
    }

    private void doDuiJiang(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("allid",allid);
                map.put("user1",user1);
                mhandler.obtainMessage(DUiJIANG, HttpUtils.updata(map, MyData.URL_updateZt2)).sendToTarget();
            }
        }).start();

    }

    private void initData(){
        allid=getIntent().getStringExtra("allid");
        user1=getIntent().getStringExtra("user1");

    }

    private void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("allid",allid);
                map.put("user1",user1);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_getSoudj)).sendToTarget();

            }
        }).start();
    }

    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try{
                switch (msg.what){
                    case GETDATA:
                        List<Map<String,String>> resultList= JSON.parseObject(msg.obj.toString(), new TypeReference<List<Map<String, String>>>() {
                        });
                        if(resultList!=null&&!resultList.isEmpty()){
                            qishu.setText(resultList.get(0).get("qishu").toString());
                            shijian.setText(resultList.get(0).get("tm").toString());
                            if(Integer.parseInt(resultList.get(0).get("zt").toString())==1){
                                CanDuiJiang=true;
                                duijiang.setText("兑奖");
                            }else {
                                CanDuiJiang=false;
                                duijiang.setText("已兑奖");
                            }
                            list.clear();
                            list.addAll(resultList);
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case DUiJIANG:
                        Map map= JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if(Integer.parseInt(map.get("newid").toString())>0){
                            Toast.makeText(DuiJiang.this, "兑奖成功", Toast.LENGTH_SHORT).show();
                            getData();
                        }else {
                            Toast.makeText(DuiJiang.this, "兑奖失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }catch (Exception e){
                Toast.makeText(DuiJiang.this, "网络连接失败。。。", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
