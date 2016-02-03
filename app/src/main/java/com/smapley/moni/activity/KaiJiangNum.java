package com.smapley.moni.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.smapley.moni.R;
import com.smapley.moni.adapter.KaiJiangAdapter;
import com.smapley.moni.http.service.GeiJiangjiluService;
import com.smapley.moni.mode.GaiJiangMode;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 16/2/3.
 */
@ContentView(R.layout.activity_kaijiangnum)
public class KaiJiangNum extends Activity {

    @ViewInject(R.id.title_item2)
    private TextView item2;
    @ViewInject(R.id.listView)
    private ListView listView;

    private List<GaiJiangMode> listData=new ArrayList<>();
    private KaiJiangAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        item2.setText("开奖号码");

        adapter=new KaiJiangAdapter(this,listData);
        listView.setAdapter(adapter);
        geiJiangjiluService.load();
    }

    private GeiJiangjiluService geiJiangjiluService =new GeiJiangjiluService() {
        @Override
        public void Succ(List<GaiJiangMode> data) {
            if(data!=null&&!data.isEmpty()){
                listData.clear();
                listData.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }
    };
}
