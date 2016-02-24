package com.smapley.moni.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smapley.moni.R;

import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2015/11/9.
 */
public class ZhangdanHZAdapter extends BaseAdapter {

    private List<Map<String,String>> list;
    private LayoutInflater inflater;
    private Context context;

    public ZhangdanHZAdapter(Context context, List<Map<String, String>> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Map<String,String> map= list.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_zhangdan_item, null);
            viewHolder = new ViewHolder();
            viewHolder.item2 = (TextView) convertView.findViewById(R.id.list_zhangdan_item2);
            viewHolder.item3 = (TextView) convertView.findViewById(R.id.list_zhangdan_item3);
            viewHolder.item4 = (TextView) convertView.findViewById(R.id.list_zhangdan_item4);
            viewHolder.item5 = (TextView) convertView.findViewById(R.id.list_zhangdan_item5);
            viewHolder.item6 = (TextView) convertView.findViewById(R.id.list_zhangdan_item6);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.item2.setText(map.get("yajin"));
        viewHolder.item3.setText(map.get("chibi"));
        viewHolder.item4.setText(map.get("zhongjiang"));
        viewHolder.item5.setText(map.get("yingkui"));
        if(map.get("yanse").toString().equals("1")){
            viewHolder.item6.setTextColor(Color.RED);
        }else{
            viewHolder.item6.setTextColor(Color.BLUE);
        }
        viewHolder.item6.setText(map.get("zonghe"));


        return convertView;
    }

    public class ViewHolder {
        TextView item2;
        TextView item3;
        TextView item4;
        TextView item5;
        TextView item6;
    }
}
