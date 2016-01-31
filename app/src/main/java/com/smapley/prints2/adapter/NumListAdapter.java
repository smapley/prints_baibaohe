package com.smapley.prints2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smapley.prints2.R;

import java.util.List;

/**
 * Created by smapley on 15/11/15.
 */
public class NumListAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private Context context;
    private List<String> list;

    public NumListAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_numlist_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((MyHolder)holder).setData(list.get(position));

    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }else {
            return list.size();
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.numlist_item_text);
        }

        public void setData(String data){
            textView.setText(data);
        }
    }
}
