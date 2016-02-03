package com.smapley.moni.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.smapley.moni.R;
import com.smapley.moni.adapter.MyJingCaiAdapter;
import com.smapley.moni.http.params.GetzhuangJParams;
import com.smapley.moni.http.service.GetZhuangJService;
import com.smapley.moni.mode.GetZhuangJMode;
import com.smapley.moni.util.MyData;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 16/2/3.
 */
@ContentView(R.layout.activity_myjingcai)
public class MyJingCai extends Activity {

    @ViewInject(R.id.title_item2)
    private TextView title2;
    @ViewInject(R.id.title_item3)
    private TextView title3;
    @ViewInject(R.id.listView)
    private ListView listView;

    private List<GetZhuangJMode> listData=new ArrayList<>();
    private MyJingCaiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        adapter=new MyJingCaiAdapter(this,listData);
        listView.setAdapter(adapter);
        listView.setDivider(null);

        title2.setText("我的竞猜");
        getZhuangJService.load(new GetzhuangJParams(MyData.UserName,MyData.PassWord));
    }
    private GetZhuangJService getZhuangJService=new GetZhuangJService() {
        @Override
        public void Succ(List<GetZhuangJMode> data) {
            if(data!=null&&!data.isEmpty()){
                listData.clear();
                listData.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }
    };

}
