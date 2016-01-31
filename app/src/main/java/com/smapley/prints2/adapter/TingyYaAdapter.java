package com.smapley.prints2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smapley.prints2.R;

import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2015/11/9.
 */
public class TingyYaAdapter extends BaseAdapter {

    private List<Map<String, String>> list;
    private LayoutInflater inflater;
    private Context context;

    public TingyYaAdapter(Context context, List<Map<String, String>> list) {
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
        final Map<String, String> map = list.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item1, null);
            viewHolder = new ViewHolder();
            viewHolder.num = (TextView) convertView.findViewById(R.id.list_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.num.setText(map.get("tingya"));
        try{
            if(map.get("yanse").toString().equals("1")){
                viewHolder.num.setTextColor(Color.RED);
            }else{
                viewHolder.num.setTextColor(Color.BLACK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    public class ViewHolder {
        TextView num;
    }
}
