package com.smapley.moni.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.R;
import com.smapley.moni.adapter.ZhangdanAdapter;
import com.smapley.moni.adapter.ZhangdanHZAdapter;
import com.smapley.moni.http.params.GetZhangdanHZParams;
import com.smapley.moni.http.params.GetZhangdanParams;
import com.smapley.moni.http.service.GetZhangdanHZService;
import com.smapley.moni.http.service.GetZhangdanService;
import com.smapley.moni.mode.GaiJiangMode;
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
@ContentView(R.layout.activity_kaijiangnum)
public class KaiJiangNum extends Activity {

    @ViewInject(R.id.detail_item1)
    private TextView item1;
    @ViewInject(R.id.detail_item2)
    private TextView item2;
    @ViewInject(R.id.layout1)
    private LinearLayout layout1;
    @ViewInject(R.id.layout2)
    private LinearLayout layout2;
    @ViewInject(R.id.listview1)
    private ListView listView1;
    @ViewInject(R.id.listview2)
    private ListView listView2;


    private List<GaiJiangMode> listData = new ArrayList<>();
    private ZhangdanAdapter adapter1;
    private ZhangdanHZAdapter adapter2;

    private  List<Map<String, String>> data1=new ArrayList<>();
    private  List<Map<String, String>> data2=new ArrayList<>();


    private GetZhangdanService getZhangdanService = new GetZhangdanService() {
        @Override
        public void Succ(String data) {
            data1.clear();
            data1.addAll( JSON.parseObject(data, new TypeReference<List<Map<String, String>>>() {
            }));
            adapter1.notifyDataSetChanged();
        }
    };

    private GetZhangdanHZService getZhangdanHZService = new GetZhangdanHZService() {
        @Override
        public void Succ(String data) {
            data2.clear();
            data2.addAll(JSON.parseObject(data, new TypeReference<List<Map<String, String>>>() {
            }));
            adapter2.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        adapter1=new ZhangdanAdapter(this,data1);
        adapter2=new ZhangdanHZAdapter(this,data2);
        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);

        getZhangdanService.load(new GetZhangdanParams(MyData.UserName));
        getZhangdanHZService.load(new GetZhangdanHZParams(MyData.UserName));
    }

    @Event({R.id.detail_item1, R.id.detail_item2})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_item1:
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.INVISIBLE);
                item1.setTextColor(getResources().getColor(R.color.blue));
                item2.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.detail_item2:
                layout1.setVisibility(View.INVISIBLE);
                layout2.setVisibility(View.VISIBLE);
                item1.setTextColor(getResources().getColor(R.color.black));
                item2.setTextColor(getResources().getColor(R.color.blue));
                break;
        }
    }

}
