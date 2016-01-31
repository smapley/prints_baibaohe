package com.smapley.prints2.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.prints2.R;
import com.smapley.prints2.activity.Login;
import com.smapley.prints2.activity.SearchBTActivity;
import com.smapley.prints2.util.HttpUtils;
import com.smapley.prints2.util.MyData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smapley on 15/10/23.
 */
public class Set extends Fragment {


    private static final int LOGOUT = 2;
    private TextView tv_title2;

    private static final int GETVERSION = 1;
    private TextView item1;
    private TextView item2;
    private TextView item4;
    private TextView menu1;

    private ProgressDialog dialog;
    private String title = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        tv_title2 = (TextView) view.findViewById(R.id.title_item2);
        tv_title2.setText(title);

        item1 = (TextView) view.findViewById(R.id.set_item1);
        item2 = (TextView) view.findViewById(R.id.set_item2);
        item4 = (TextView) view.findViewById(R.id.set_item4);

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchBTActivity.class));
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("正在检查更新");
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mhandler.obtainMessage(GETVERSION, HttpUtils.updata(null, MyData.URL_GENGXIN)).sendToTarget();
                    }
                }).start();
            }
        });

        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示：");
                builder.setMessage("是否退出登录？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HashMap map = new HashMap();
                                map.put("user1",MyData.UserName1);
                                map.put("user2",MyData.UserName2);
                                mhandler.obtainMessage(LOGOUT,HttpUtils.updata(map,MyData.URL_Reg2)).sendToTarget();
                            }
                        }).start();

                    }
                });
                builder.create().show();

            }
        });
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case LOGOUT:
                        SharedPreferences sp_user = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp_user.edit();
                        editor.putBoolean("login1", false);
                        editor.putBoolean("login2", false);
                        editor.commit();
                        MyData.Login1=false;
                        MyData.Login2=false;
                        MyData.User=0;
                        startActivity(new Intent(getActivity(), Login.class));
                        getActivity().finish();
                        break;
                    case GETVERSION:
                        dialog.dismiss();
                        Map<String, String> result = JSON.parseObject(msg.obj.toString(), new TypeReference<Map<String, String>>() {
                        });
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        if (Float.parseFloat(getVersion()) < Float.parseFloat(result.get("banben").toString())) {
                            builder.setMessage("当前版本：" + getVersion() + "\n最新版本：" + result.get("banben").toString());
                            builder.setNegativeButton("取消", null);
                            builder.setPositiveButton("下载更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MyData.URL_XIAZAI));
                                    startActivity(intent);
                                }
                            });
                        } else {
                            builder.setMessage("当前已是最新版本:" + getVersion());
                            builder.setNegativeButton("取消", null);
                        }
                        builder.create().show();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void settitle(String title2) {
        this.title = title2;
        if (tv_title2 != null)
            tv_title2.setText(title2);
    }
}
