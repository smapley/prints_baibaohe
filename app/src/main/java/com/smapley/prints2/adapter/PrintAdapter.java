package com.smapley.prints2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smapley.prints2.R;
import com.smapley.prints2.fragment.Print;

import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2015/11/9.
 */
public class PrintAdapter extends BaseAdapter {

    private List<Map<String, String>> list;
    private LayoutInflater inflater;
    private Context context;

    public PrintAdapter(Context context, List<Map<String, String>> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Map<String, String> map = list.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.layout=(LinearLayout)convertView.findViewById(R.id.layout);
            viewHolder.num = (CheckBox) convertView.findViewById(R.id.list_item1);
            viewHolder.gold = (TextView) convertView.findViewById(R.id.list_item2);
            viewHolder.pei = (TextView) convertView.findViewById(R.id.list_item3);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(map.get("hotstat").toString().equals("1")){
            viewHolder.layout.setBackgroundColor(Color.YELLOW);
        }else{
            viewHolder.layout.setBackgroundColor(Color.WHITE);
        }
        boolean check=false;
        for(Map map1:Print.removeList){
            if(map1.equals(map)){
                check=true;
            }
        }
        if(position==0){
            viewHolder.num.setTextColor(Color.BLACK);
            viewHolder.gold.setTextColor(Color.BLACK);
            viewHolder.pei.setTextColor(Color.BLACK);
        }else{
            viewHolder.num.setTextColor(context.getResources().getColor(R.color.green));
            viewHolder.gold.setTextColor(Color.RED);
        }
        viewHolder.num.setChecked(check);
        viewHolder.num.setText(map.get("number"));
        viewHolder.gold.setText(map.get("gold"));
        viewHolder.pei.setText(map.get("pei"));

        viewHolder.num.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(position==0){
                    Print.check(b);
                    notifyDataSetChanged();
                }else{
                    Print.check(map,b);
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        LinearLayout layout;
        CheckBox num;
        TextView gold;
        TextView pei;
    }
}
