package com.smapley.moni.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.smapley.moni.R;
import com.smapley.moni.activity.AddZhuang;
import com.smapley.moni.adapter.JingCaiAdapter;
import com.smapley.moni.http.service.GetZhuangService;
import com.smapley.moni.mode.GetZhuangMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2015/11/7.
 */
public class Data extends Fragment {

    private TextView title1;
    private TextView title2;
    private TextView title3;
    private ListView listView;

    private List<GetZhuangMode> listData=new ArrayList<>();
    private JingCaiAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date, container, false);
        initView(view);
        getZhuangService.load();
        return view;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        adapter=new JingCaiAdapter(getActivity(),listData);
        listView.setAdapter(adapter);

        title1=(TextView)view.findViewById(R.id.title_item1);
        title2=(TextView)view.findViewById(R.id.title_item2);
        title3=(TextView)view.findViewById(R.id.title_item3);

        title2.setText("竞猜");
        title3.setText("添加");
        title3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddZhuang.class));
            }
        });

    }

    private GetZhuangService getZhuangService=new GetZhuangService() {
        @Override
        public void Succ(List<GetZhuangMode> data) {
            if(data!=null&&!data.isEmpty()){
                listData.clear();
                listData.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }
    };


}
