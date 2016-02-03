package com.smapley.moni.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smapley.moni.R;
import com.smapley.moni.mode.GaiJiangMode;

import java.util.List;

/**
 * Created by hao on 2015/11/9.
 */
public class KaiJiangAdapter extends BaseAdapter {

    private List<GaiJiangMode> list;
    private LayoutInflater inflater;
    private Context context;

    public KaiJiangAdapter(Context context, List<GaiJiangMode> list) {
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
        final GaiJiangMode mode = list.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_kaijiang_item, null);
            viewHolder = new ViewHolder();
            viewHolder.jiangnum = (TextView) convertView.findViewById(R.id.list_kaijiang_item2);
            viewHolder.qishu = (TextView) convertView.findViewById(R.id.list_kaijiang_item1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String num = mode.getQishu();
        String nums="";
        for (int i = 0; i < num.length(); i++) {
            nums=nums+" "+num.substring(i,i+1);
        }
        viewHolder.jiangnum.setText(nums );
        viewHolder.qishu.setText(mode.getQishu()+ "期：");


        return convertView;
    }

    public class ViewHolder {
        TextView jiangnum;
        TextView qishu;
    }
}
