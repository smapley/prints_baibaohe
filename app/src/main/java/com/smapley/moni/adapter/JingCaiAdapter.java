package com.smapley.moni.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smapley.moni.R;
import com.smapley.moni.mode.GetZhuangMode;

import java.util.List;

/**
 * Created by hao on 2015/11/9.
 */
public class JingCaiAdapter extends BaseAdapter {

    private List<GetZhuangMode> list;
    private LayoutInflater inflater;
    private Context context;

    public JingCaiAdapter(Context context, List<GetZhuangMode> list) {
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
        final GetZhuangMode mode = list.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_jingcai_item, null);
            viewHolder = new ViewHolder();
            viewHolder.biaoti = (TextView) convertView.findViewById(R.id.biaoti);
            viewHolder.jiami = (TextView) convertView.findViewById(R.id.jiami);
            viewHolder.danzhu = (TextView) convertView.findViewById(R.id.danzhu);
            viewHolder.peilv = (TextView) convertView.findViewById(R.id.peilv);
            viewHolder.yiya = (TextView) convertView.findViewById(R.id.yiya);
            viewHolder.bianhao = (TextView) convertView.findViewById(R.id.bianhao);
            viewHolder.type = (TextView) convertView.findViewById(R.id.type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.biaoti.setText(mode.getBiaoti());
        viewHolder.jiami.setText(mode.getQx() == 1 ? "密" : "");
        viewHolder.danzhu.setText(mode.getDan());
        viewHolder.peilv.setText(mode.getPei());
        viewHolder.yiya.setText(mode.getZhu());
        viewHolder.bianhao.setText(mode.getOnlyid());
        viewHolder.type.setText(mode.getType());


        return convertView;
    }

    public class ViewHolder {
        TextView biaoti;
        TextView jiami;
        TextView danzhu;
        TextView peilv;
        TextView yiya;
        TextView bianhao;
        TextView type;
    }
}
