package com.smapley.moni.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smapley.moni.R;
import com.smapley.moni.mode.GetZhuangJMode;

import java.util.List;

/**
 * Created by hao on 2015/11/9.
 */
public class MyJingCaiAdapter extends BaseAdapter {

    private List<GetZhuangJMode> list;
    private LayoutInflater inflater;
    private Context context;

    public MyJingCaiAdapter(Context context, List<GetZhuangJMode> list) {
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
        final GetZhuangJMode mode = list.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_myjingcai_item, null);
            viewHolder = new ViewHolder();
            viewHolder.biaoti = (TextView) convertView.findViewById(R.id.biaoti);
            viewHolder.jiami = (TextView) convertView.findViewById(R.id.jiami);
            viewHolder.danzhu = (TextView) convertView.findViewById(R.id.danzhu);
            viewHolder.peilv = (TextView) convertView.findViewById(R.id.peilv);
            viewHolder.yiya = (TextView) convertView.findViewById(R.id.yiya);
            viewHolder.bianhao = (TextView) convertView.findViewById(R.id.bianhao);
            viewHolder.type = (TextView) convertView.findViewById(R.id.type);
            viewHolder.zhuangtai = (TextView) convertView.findViewById(R.id.zhuangtai);
            viewHolder.zhongjiangzhe = (TextView) convertView.findViewById(R.id.zhongjiangzhe);
            viewHolder.kaijiang = (TextView) convertView.findViewById(R.id.kaijiang);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.biaoti.setText(mode.getBiaoti());
        if (mode.getMima() != null)
            viewHolder.jiami.setText(mode.getMima());
        viewHolder.danzhu.setText("单注" + mode.getDan() + "元");
        viewHolder.peilv.setText("赔率 1：" + mode.getPei());
        viewHolder.yiya.setText("已押" + mode.getZhu() + "注");
        viewHolder.bianhao.setText("编号：" + mode.getOnlyid());
        viewHolder.kaijiang.setText("开奖号码：" + mode.getJiang());
        viewHolder.type.setText(mode.getType());
        viewHolder.zhuangtai.setText(mode.getZt() == 0 ? "未中" : "已中");
        if (mode.getWinner() != null) {
            viewHolder.zhongjiangzhe.setText(mode.getWinner());
        }


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
        TextView zhuangtai;
        TextView zhongjiangzhe;
        TextView kaijiang;
    }
}
