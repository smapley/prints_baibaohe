package com.smapley.prints2.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.prints2.R;
import com.smapley.prints2.util.HttpUtils;
import com.smapley.prints2.util.MyData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2015/11/7.
 */
public class Data extends Fragment implements OnClickListener ,View.OnFocusChangeListener{

    private TextView tv_title2;
    private TextView tv_title3;

    private final int GETDATA = 1;
    private final int UPDATA = 2;

    private TextView item11;
    private TextView item12;
    private EditText item13;
    private TextView item21;
    private TextView item22;
    private EditText item23;
    private TextView item31;
    private TextView item32;
    private EditText item33;
    private TextView item41;
    private TextView item42;
    private EditText item43;
    private TextView item51;
    private TextView item52;
    private EditText item53;
    private TextView item61;
    private TextView item62;
    private EditText item63;

    private TextView item1;
    private TextView item2;
    private TextView item3;
    private TextView item4;
    private TextView item5;

    private TextView mode1;
    private TextView mode2;

    private boolean mode1_state = false;
    private boolean mode2_state = false;

    private ScrollView scrollView;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date, container, false);

        dialog=new ProgressDialog(getActivity());
        dialog.setMessage("正在上传信息。。。");

        initView(view);
        getData();
        return view;
    }

    private void initView(View view) {

        tv_title2=(TextView)view.findViewById(R.id.title_item2);
        tv_title3=(TextView)view.findViewById(R.id.title_item3);
        tv_title3.setText("保存");
        tv_title3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                builder.setTitle("提示：");
                builder.setMessage("赔率填写错误会导致回水变为0，需再次修改");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        upData();
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        item11 = (TextView) view.findViewById(R.id.data_tv_item11);
        item12 = (TextView) view.findViewById(R.id.data_tv_item12);
        item13 = (EditText) view.findViewById(R.id.data_ev_item13);
        item21 = (TextView) view.findViewById(R.id.data_tv_item21);
        item22 = (TextView) view.findViewById(R.id.data_tv_item22);
        item23 = (EditText) view.findViewById(R.id.data_ev_item23);
        item31 = (TextView) view.findViewById(R.id.data_tv_item31);
        item32 = (TextView) view.findViewById(R.id.data_tv_item32);
        item33 = (EditText) view.findViewById(R.id.data_ev_item33);
        item41 = (TextView) view.findViewById(R.id.data_tv_item41);
        item42 = (TextView) view.findViewById(R.id.data_tv_item42);
        item43 = (EditText) view.findViewById(R.id.data_ev_item43);
        item51 = (TextView) view.findViewById(R.id.data_tv_item51);
        item52 = (TextView) view.findViewById(R.id.data_tv_item52);
        item53 = (EditText) view.findViewById(R.id.data_ev_item53);
        item61 = (TextView) view.findViewById(R.id.data_tv_item61);
        item62 = (TextView) view.findViewById(R.id.data_tv_item62);
        item63 = (EditText) view.findViewById(R.id.data_ev_item63);

        mode1 = (TextView) view.findViewById(R.id.data_tv_mode1);
        mode2 = (TextView) view.findViewById(R.id.data_tv_mode2);

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);


        item13.setOnClickListener(this);
        item23.setOnClickListener(this);
        item33.setOnClickListener(this);
        item43.setOnClickListener(this);
        item53.setOnClickListener(this);
        item63.setOnClickListener(this);

        item13.setOnFocusChangeListener(this);
        item23.setOnFocusChangeListener(this);
        item33.setOnFocusChangeListener(this);
        item43.setOnFocusChangeListener(this);
        item53.setOnFocusChangeListener(this);
        item63.setOnFocusChangeListener(this);

        mode1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                choseMode(1);
            }
        });
        mode2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                choseMode(0);
            }
        });

        item1 = (TextView) view.findViewById(R.id.data_tv_item1);
        item2 = (TextView) view.findViewById(R.id.data_tv_item2);
        item3 = (TextView) view.findViewById(R.id.data_tv_item3);
        item4 = (TextView) view.findViewById(R.id.data_tv_item4);
        item5 = (TextView) view.findViewById(R.id.data_tv_item5);

    }

    private void choseMode(int i) {
        switch (i) {
            case 1:
                mode1_state = true;
                mode2_state = false;
                mode2.setBackgroundResource(R.drawable.textview_circle);
                mode1.setBackgroundResource(R.drawable.textview_circle2);
                break;
            case 0:
                mode2_state = true;
                mode1_state = false;
                mode1.setBackgroundResource(R.drawable.textview_circle);
                mode2.setBackgroundResource(R.drawable.textview_circle2);
                break;
        }
    }

    public void upData() {
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("user1", MyData.UserName);
                map.put("erd", item13.getText().toString());
                map.put("sand", item23.getText().toString());
                map.put("sid", item33.getText().toString());
                map.put("erx", item43.getText().toString());
                map.put("sanx", item53.getText().toString());
                map.put("six", item63.getText().toString());
                map.put("luma", mode1_state ? 1 : 0);
                mhandler.obtainMessage(UPDATA, HttpUtils.updata(map, MyData.URL_UPDATAZILIAO)).sendToTarget();
            }
        }).start();
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("user1", MyData.UserName);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETZILIAO)).sendToTarget();
            }
        }).start();
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case GETDATA:
                        // item11.setText(JSON.parseObject(msg.obj.toString(),new TypeReference<String>(){}));

                        Map<String, Object> result1 = JSON.parseObject(msg.obj.toString(), new TypeReference<Map<String, Object>>() {
                        });
                        item1.setText(result1.get("yyed2").toString());
                        item2.setText(result1.get("shui").toString());
                        item3.setText(result1.get("jiang").toString());
                        item4.setText(result1.get("yk").toString());
                        item5.setText(result1.get("yk2").toString());
                        tv_title2.setText(result1.get("murl").toString());
                        Map<String, List<String>> result = JSON.parseObject(result1.get("new").toString(), new TypeReference<Map<String, List<String>>>() {
                        });
                        item11.setText(result.get("2").get(0) == null ? "" : result.get("2").get(0));
                        item12.setText(result.get("2").get(1) == null ? "" : result.get("2").get(1));
                        item13.setText(result.get("2").get(2) == null ? "" : result.get("2").get(2));
                        item21.setText(result.get("3").get(0) == null ? "" : result.get("3").get(0));
                        item22.setText(result.get("3").get(1) == null ? "" : result.get("3").get(1));
                        item23.setText(result.get("3").get(2) == null ? "" : result.get("3").get(2));
                        item31.setText(result.get("4").get(0) == null ? "" : result.get("4").get(0));
                        item32.setText(result.get("4").get(1) == null ? "" : result.get("4").get(1));
                        item33.setText(result.get("4").get(2) == null ? "" : result.get("4").get(2));
                        item41.setText(result.get("5").get(0) == null ? "" : result.get("5").get(0));
                        item42.setText(result.get("5").get(1) == null ? "" : result.get("5").get(1));
                        item43.setText(result.get("5").get(2) == null ? "" : result.get("5").get(2));
                        item51.setText(result.get("6").get(0) == null ? "" : result.get("6").get(0));
                        item52.setText(result.get("6").get(1) == null ? "" : result.get("6").get(1));
                        item53.setText(result.get("6").get(2) == null ? "" : result.get("6").get(2));
                        item61.setText(result.get("7").get(0) == null ? "" : result.get("7").get(0));
                        item62.setText(result.get("7").get(1) == null ? "" : result.get("7").get(1));
                        item63.setText(result.get("7").get(2) == null ? "" : result.get("7").get(2));

                        choseMode(Integer.parseInt(result1.get("luma").toString()));
                        break;
                    case UPDATA:
                        dialog.dismiss();
                        int result2 = JSON.parseObject(msg.obj.toString(), new TypeReference<Integer>() {
                        });
                        if (result2 >= 0) {
                            getData();
                            Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "保存失败！", Toast.LENGTH_SHORT).show();

                        }
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            ((EditText) v).setText("");
            switch (v.getId()) {
                case R.id.data_ev_item13:
                    scrollView.scrollTo(0, 150);

                    break;
                case R.id.data_ev_item23:
                    scrollView.scrollTo(0, 200);

                    break;
                case R.id.data_ev_item33:
                    scrollView.scrollTo(0, 250);

                    break;
                case R.id.data_ev_item43:
                    scrollView.scrollTo(0, 300);

                    break;
                case R.id.data_ev_item53:
                    scrollView.scrollTo(0, 350);

                    break;
                case R.id.data_ev_item63:
                    scrollView.scrollTo(0, 400);

                    break;
            }
        }
    }
    @Override
    public void onClick(View view) {
        ((EditText) view).setText("");
        switch (view.getId()) {
            case R.id.data_ev_item13:
                scrollView.scrollTo(0, 150);

                break;
            case R.id.data_ev_item23:
                scrollView.scrollTo(0, 200);

                break;
            case R.id.data_ev_item33:
                scrollView.scrollTo(0, 250);

                break;
            case R.id.data_ev_item43:
                scrollView.scrollTo(0, 300);

                break;
            case R.id.data_ev_item53:
                scrollView.scrollTo(0, 350);

                break;
            case R.id.data_ev_item63:
                scrollView.scrollTo(0, 400);

                break;
        }
    }
}
