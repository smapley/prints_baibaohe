package com.smapley.moni.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.R;
import com.smapley.moni.activity.AddZhuang;
import com.smapley.moni.activity.JingCai;
import com.smapley.moni.adapter.JingCaiAdapter;
import com.smapley.moni.http.params.FangjianmimaParams;
import com.smapley.moni.http.service.FangjianmimaService;
import com.smapley.moni.http.service.GetZhuangService;
import com.smapley.moni.mode.GetZhuangMode;

import org.xutils.common.util.LogUtil;

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

    private GetZhuangMode item;

    private List<GetZhuangMode> listData=new ArrayList<>();
    private JingCaiAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date, container, false);
        initView(view);
        getZhuangService.load();
        return view;
    }

    public void getData(){
        getZhuangService.load();
    }

    private FangjianmimaService fangjianmimaService=new FangjianmimaService() {
        @Override
        public void Succ(String data) {
            int result= JSON.parseObject(data,new TypeReference<Integer>(){});
            if(result>0){
                Intent intent=new Intent(getActivity(), JingCai.class);
                intent.putExtra("onlyid",item.getOnlyid());
                intent.putExtra("pei",item.getPei());
                intent.putExtra("gold",item.getDan());
                intent.putExtra("type",item.getType());
                startActivity(intent);
            }else {
                Toast.makeText(getActivity(),"密码错误！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        adapter=new JingCaiAdapter(getActivity(),listData);
        listView.setAdapter(adapter);
        listView.setDivider(null);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(listData.get(i).getQx()==0){
                    Intent intent=new Intent(getActivity(), JingCai.class);
                    intent.putExtra("onlyid",listData.get(i).getOnlyid());
                    intent.putExtra("pei",listData.get(i).getPei());
                    intent.putExtra("gold",listData.get(i).getDan());
                    intent.putExtra("type",listData.get(i).getType());
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("房间密码：");
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view1 = inflater.inflate(R.layout.layout_inputtext, null, false);
                    final EditText editText = (EditText) view1.findViewById(R.id.editext);
                    builder.setView(view1);
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String num = editText.getText().toString();
                            if (num != null) {
                                fangjianmimaService.load(new FangjianmimaParams(num,listData.get(i).getOnlyid()));
                                item=listData.get(i);

                            }
                        }
                    });
                    builder.create().show();
                }
            }
        });

        title1=(TextView)view.findViewById(R.id.title_item1);
        title2=(TextView)view.findViewById(R.id.title_item2);
        title3=(TextView)view.findViewById(R.id.title_item3);

        title2.setText("竞猜");
        title3.setText("添加");
        title3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), AddZhuang.class), 20);
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

    @Override
    public void onResume() {
        super.onResume();
        getZhuangService.load();
        LogUtil.d("-----------");
    }


}
