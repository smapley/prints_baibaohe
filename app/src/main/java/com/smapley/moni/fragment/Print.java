package com.smapley.moni.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.R;
import com.smapley.moni.activity.Detail;
import com.smapley.moni.activity.MainActivity;
import com.smapley.moni.adapter.PrintAdapter;
import com.smapley.moni.adapter.TingyYaAdapter;
import com.smapley.moni.listview.SwipeMenu;
import com.smapley.moni.listview.SwipeMenuCreator;
import com.smapley.moni.listview.SwipeMenuItem;
import com.smapley.moni.listview.SwipeMenuListView;
import com.smapley.moni.print.WorkService;
import com.smapley.moni.util.HttpUtils;
import com.smapley.moni.util.MyData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 15/10/23.
 */
public class Print extends Fragment implements View.OnClickListener {

    private static final int DELECT = 1;
    private static final int DELECTS = 2;
    private static final int ERROR = 3;
    private static final int CLEARN = 4;
    private static final int GETPEILV = 5;
    private TextView tv_title1;
    private TextView tv_title2;
    private TextView tv_title3;

    private View keybord;
    private TextView back;
    private TextView nowText;
    private TextView numText;
    private TextView jineText;
    private RelativeLayout numLayout;
    private RelativeLayout jinLayout;
    private SwipeMenuListView listView;
    private ListView listView1;
    private TingyYaAdapter adapter1;
    private TextView numCart;
    private TextView jinCart;
    private TextView keyitem1;
    private TextView keyitem2;
    private TextView keyitem3;
    private TextView keyitem4;
    private TextView keyitem5;
    private TextView keyitem6;
    private TextView keyitem7;
    private TextView keyitem8;
    private TextView keyitem9;
    private TextView keyitem10;
    private TextView keyitem11;
    private TextView keyitem12;
    private TextView keyitem13;
    private TextView keyitem14;
    private TextView keyitem15;
    private TextView tag;
    private TextView xiane;
    private final int UPDATA = -1;
    private final int GETDATA = -2;
    private String number;
    private String money;
    public static List<Map<String, String>> dataList = new ArrayList<>();
    private List<Map<String, String>> dataList1 = new ArrayList<>();
    private PrintAdapter adapter;
    private ProgressDialog dialog;
    private boolean jine = false;
    private int xian = 0;
    private int dao = 0;
    private static Map<String, String> baseMap = new HashMap<>();

    private String title = "金币：";
    private String yyed = "";
    public String qishu = "";

    private boolean hasPoint = false;

    public static List<Map<String, String>> removeList = new ArrayList<>();
    private static TextView tingYa;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.print, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("提示：");
        baseMap.put("number", "号码");
        baseMap.put("gold", "金额");
        baseMap.put("pei", "赔率");
        baseMap.put("hotstat", "0");
        baseMap.put("id", "0");
        dataList.clear();
        dataList.add(baseMap);
        initView(view);
        getData();
        return view;
    }

    private void initView(View view) {
        tingYa = (TextView) view.findViewById(R.id.print_tingya);

        tingYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tingYa.getText().equals(getString(R.string.delect))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("提示：");
                    builder.setMessage("是否批量删除？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HashMap map = new HashMap();
                                    List<Map> datalist = new ArrayList<Map>();
                                    datalist.addAll(removeList);
                                    datalist.remove(baseMap);
                                    if (datalist.isEmpty()) {
                                        mhandler.obtainMessage(ERROR).sendToTarget();
                                    } else {
                                        String data = "";
                                        for (Map map1 : datalist) {
                                            data = data + map1.get("id").toString() + "," + map1.get("biaoshi").toString() + ",";
                                        }
                                        map.put("tuima", data.substring(0, data.length() - 1));
                                        map.put("user1", MyData.UserName);
                                        map.put("mi", MyData.PassWord);
                                        mhandler.obtainMessage(DELECTS, HttpUtils.updata(map, MyData.URL_TUIMA3)).sendToTarget();
                                    }
                                }

                            }).start();
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.create().show();

                } else if (tingYa.getText().equals(getString(R.string.tingya))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("是否清空失败的号码？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HashMap map = new HashMap();
                                    map.put("user1", MyData.UserName);
                                    mhandler.obtainMessage(CLEARN, HttpUtils.updata(map, MyData.URL_DELTINGYA)).sendToTarget();
                                }
                            }).start();
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.create().show();
                }
            }
        });


        tv_title1 = (TextView) view.findViewById(R.id.title_item1);
        tv_title2 = (TextView) view.findViewById(R.id.title_item2);
        tv_title3 = (TextView) view.findViewById(R.id.title_item3);
        listView1 = (ListView) view.findViewById(R.id.list1);
        tv_title1.setText("明细");
        tv_title2.setText(title + yyed);
        tv_title3.setText("打印");
        tv_title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Detail.class);
                intent.putExtra("qishu", qishu);
                startActivity(intent);
            }
        });
        tv_title3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WorkService.workThread.isConnected()) {
                    ((MainActivity) getActivity()).mhandler.obtainMessage(11).sendToTarget();
                } else {
                    ((MainActivity) getActivity()).connectBT();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (!WorkService.workThread.isConnected()) {

                            }
                            ((MainActivity) getActivity()).mhandler.obtainMessage(11).sendToTarget();
                        }
                    }).start();
                }
            }
        });

        back = (TextView) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keybord.setVisibility(View.GONE);
                ((MainActivity) getActivity()).bottom.setVisibility(View.VISIBLE);

            }
        });
        numLayout = (RelativeLayout) view.findViewById(R.id.table_item1_layout);
        jinLayout = (RelativeLayout) view.findViewById(R.id.table_item2_layout);
        numCart = (TextView) view.findViewById(R.id.table_item1_clo);
        jinCart = (TextView) view.findViewById(R.id.table_item2_clo);
        keybord = view.findViewById(R.id.print_keybord);
        numText = (TextView) view.findViewById(R.id.num_text);
        jineText = (TextView) view.findViewById(R.id.jine_text);
        numLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNumText("");
            }
        });
        jinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goJinText(true);
                jineText.setText("");
            }
        });

        keyitem1 = (TextView) view.findViewById(R.id.key_item1);
        keyitem2 = (TextView) view.findViewById(R.id.key_item2);
        keyitem3 = (TextView) view.findViewById(R.id.key_item3);
        keyitem4 = (TextView) view.findViewById(R.id.key_item4);
        keyitem5 = (TextView) view.findViewById(R.id.key_item5);
        keyitem6 = (TextView) view.findViewById(R.id.key_item6);
        keyitem7 = (TextView) view.findViewById(R.id.key_item7);
        keyitem8 = (TextView) view.findViewById(R.id.key_item8);
        keyitem9 = (TextView) view.findViewById(R.id.key_item9);
        keyitem10 = (TextView) view.findViewById(R.id.key_item10);
        keyitem11 = (TextView) view.findViewById(R.id.key_item11);
        keyitem12 = (TextView) view.findViewById(R.id.key_item12);
        keyitem13 = (TextView) view.findViewById(R.id.key_item13);
        keyitem14 = (TextView) view.findViewById(R.id.key_item14);
        keyitem15 = (TextView) view.findViewById(R.id.key_item15);
        tag = (TextView) view.findViewById(R.id.text_tag);
        xiane=(TextView)view.findViewById(R.id.text_xiane);

        keyitem1.setOnClickListener(this);
        keyitem2.setOnClickListener(this);
        keyitem3.setOnClickListener(this);
        keyitem4.setOnClickListener(this);
        keyitem5.setOnClickListener(this);
        keyitem6.setOnClickListener(this);
        keyitem7.setOnClickListener(this);
        keyitem8.setOnClickListener(this);
        keyitem9.setOnClickListener(this);
        keyitem10.setOnClickListener(this);
        keyitem11.setOnClickListener(this);
        keyitem12.setOnClickListener(this);
        keyitem13.setOnClickListener(this);
        keyitem14.setOnClickListener(this);
        keyitem15.setOnClickListener(this);


        listView = (SwipeMenuListView) view.findViewById(R.id.listView);
        listView.setDivider(null);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);

        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                if (position != 0) {
                    final Map item = dataList.get(position);
                    switch (index) {
                        case 0:
                            dialog.setMessage("正在撤销投注。。。");
                            dialog.show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HashMap map = new HashMap();
                                    map.put("id", item.get("id").toString());
                                    map.put("biaoshi", item.get("biaoshi").toString());
                                    map.put("user1", MyData.UserName);
                                    map.put("mi", MyData.PassWord);
                                    mhandler.obtainMessage(DELECT, HttpUtils.updata(map, MyData.URL_TUIMA1)).sendToTarget();
                                }
                            }).start();


                            break;
                    }

                }
                return false;
            }
        });

        adapter = new PrintAdapter(getActivity(), dataList);
        listView.setDivider(null);
        listView.setAdapter(adapter);

        adapter1 = new TingyYaAdapter(getActivity(), dataList1);
        listView1.setAdapter(adapter1);
    }

    private void goJinText(boolean tag) {
        //网络连接
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("user1", MyData.UserName);
                map.put("number", numText.getText().toString());
                mhandler.obtainMessage(GETPEILV, HttpUtils.updata(map, MyData.URL_GETPEILV)).sendToTarget();
            }
        }).start();

        hasPoint = false;
        nowText = jineText;
        jine = tag;
        keybord.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).bottom.setVisibility(View.GONE);
        keybord.setBackgroundResource(R.color.back2);
        numCart.setVisibility(View.GONE);
        jinCart.setVisibility(View.VISIBLE);
        listView.smoothScrollToPosition(adapter.getCount() - 1);
        listView1.smoothScrollToPosition(adapter.getCount() - 1);


    }

    private void goNumText(String text) {

        if (tag.getText().length() != 1)
            tag.setText("");
        xiane.setText("");
        hasPoint = false;
        nowText = numText;
        nowText.setText(text);
        keybord.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).bottom.setVisibility(View.GONE);
        keybord.setBackgroundResource(R.color.bac1);
        numCart.setVisibility(View.VISIBLE);
        jinCart.setVisibility(View.GONE);
        listView.smoothScrollToPosition(adapter.getCount() - 1);
        listView1.smoothScrollToPosition(adapter.getCount() - 1);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.key_item4:
                if (xian == 0) {
                    tag.setText("现");
                    xian = 1;
                    dao = 0;
                } else {
                    tag.setText("");
                    xian = 0;
                }

                break;
            case R.id.key_item8:
                if (dao == 0) {
                    tag.setText("倒");
                    xian = 0;
                    dao = 1;
                } else {
                    tag.setText("");
                    dao = 0;
                }
                break;
            case R.id.key_item14:

                if (nowText == numText) {
                    if (nowText.getText().length() < 3) {
                        nowText.setText(nowText.getText().toString() + ((TextView) view).getText().toString());
                    } else {
                        nowText.setText(nowText.getText().toString() + ((TextView) view).getText().toString());
                        goJinText(true);
                    }
                }

                break;


            case R.id.key_item12:
                upData();
                goNumText("");
                xian = 0;
                dao = 0;
                tag.setText("");

                break;

            case R.id.key_item15:
                if (nowText == jineText && !jine && !hasPoint) {
                    nowText.setText(nowText.getText().toString() + ((TextView) view).getText().toString());
                    hasPoint = true;
                }
                break;

            default:
                if (nowText == numText && nowText.getText().length() < 3 || nowText == jineText) {
                    if (jine && nowText == jineText) {
                        nowText.setBackgroundColor(getResources().getColor(R.color.back3));
                        nowText.setText(((TextView) view).getText().toString());
                        jine = false;
                    } else {
                        nowText.setText(nowText.getText().toString() + ((TextView) view).getText().toString());
                    }
                } else if (nowText.length() == 3) {
                    nowText.setText(nowText.getText().toString() + ((TextView) view).getText().toString());
                    goJinText(true);
                }
                break;


        }

    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("user1", MyData.UserName);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETJILU1)).sendToTarget();
            }
        }).start();


    }

    private void upData() {
        number = numText.getText().toString();
        money = jineText.getText().toString();
        final int sizixian = xian;
        final int zhuan = dao;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                String peilv = tag.getText().toString();
                if (peilv.length() < 2)
                    peilv = "0";
                map.put("peilv", peilv);
                map.put("number", number);
                map.put("money", money);
                map.put("user1", MyData.UserName);
                map.put("sizixian", sizixian);
                map.put("zhuan", zhuan);
                map.put("mi", MyData.PassWord);
                mhandler.obtainMessage(UPDATA, HttpUtils.updata(map, MyData.URL_INDEX1)).sendToTarget();
            }
        }).start();
    }

    public Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                dialog.dismiss();
                switch (msg.what) {
                    case GETDATA:
                        Map map1 = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        try {
                            List<Map<String, String>> list2 = JSON.parseObject(map1.get("res").toString(), new TypeReference<List<Map<String, String>>>() {
                            });
                            dataList1.clear();
                            dataList1.addAll(list2);
                            adapter1.notifyDataSetChanged();
                            listView1.smoothScrollToPosition(adapter1.getCount() - 1);
                        } catch (Exception e) {

                        }
                        if (Integer.parseInt(map1.get("count").toString()) > 0) {
                            qishu = map1.get("qishu").toString();
                            yyed = map1.get("yyed1").toString();
                            tv_title2.setText(title + yyed);
                            List<Map<String, String>> list = JSON.parseObject(map1.get("result").toString(), new TypeReference<List<Map<String, String>>>() {
                            });
                            dataList.clear();
                            dataList.add(baseMap);
                            dataList.addAll(list);
                            adapter.notifyDataSetChanged();
                            listView.smoothScrollToPosition(adapter.getCount() - 1);


                        } else if (Integer.parseInt(map1.get("count").toString()) == 0) {
                            dataList.clear();
                            dataList.add(baseMap);
                            adapter.notifyDataSetChanged();
                            yyed = map1.get("yyed1").toString();
                            tv_title2.setText(title + yyed);
                            qishu = map1.get("qishu").toString();
                        }


                        break;
                    case UPDATA:

                        Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        getData();
                        if (Integer.parseInt(map.get("count").toString()) > 0) {
                            List<Map> list = JSON.parseObject(map.get("result").toString(), new TypeReference<List<Map>>() {
                            });
                            for (int i = 0; i < list.size(); i++) {
                                Map resultmap = list.get(i);
                                Map dataMap = new HashMap();
                                dataMap.put("count", map.get("count").toString());
                                dataMap.put("allgold", map.get("allgold").toString());
                                dataMap.put("allid", map.get("allid").toString());
                                dataMap.put("number", resultmap.get("number").toString());
                                dataMap.put("gold", resultmap.get("gold").toString());
                                dataMap.put("pei", resultmap.get("pei").toString());
                                dataMap.put("id", resultmap.get("id").toString());
                                dataMap.put("hotstat", "0");
                                dataList.add(dataMap);
                            }
                            adapter.notifyDataSetChanged();
                            listView.smoothScrollToPosition(adapter.getCount() - 1);


                        }
                        List<Map<String, String>> list1 = JSON.parseObject(map.get("disresult").toString(), new TypeReference<List<Map<String, String>>>() {
                        });
                        String result = "";
                        for (int i = 0; i < list1.size(); i++) {
                            result = result + list1.get(i).get("number").toString() + "\n";
                        }
                        if (result != null && !result.equals("")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("下注失败的号码：").setMessage(result)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                        }


                        break;
                    case DELECT:
                        tingYa.setText(R.string.tingya);
                        tingYa.setTextColor(Color.BLACK);
                        removeList.clear();
                        int result1 = JSON.parseObject(msg.obj.toString(), new TypeReference<Integer>() {
                        });
                        switch (result1) {
                            case 1:
                                Toast.makeText(getActivity(), "退码成功！", Toast.LENGTH_SHORT).show();
                                getData();
                                break;

                            case -2:
                                Toast.makeText(getActivity(), "退码已过期！", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getActivity(), "退码失败！", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        break;
                    case DELECTS:
                        tingYa.setText(R.string.tingya);
                        tingYa.setTextColor(Color.BLACK);
                        removeList.clear();
                        String result2 = JSON.parseObject(msg.obj.toString(), new TypeReference<String>() {
                        });
                        boolean isSucc = false;
                        for (int i = 0; i < result2.length(); i++) {
                            String data = result2.substring(i, i + 1);
                            if (data.equals("1")) {
                                isSucc = true;
                            }
                        }
                        if (isSucc) {
                            Toast.makeText(getActivity(), "退码成功！", Toast.LENGTH_SHORT).show();
                            getData();
                        } else {
                            Toast.makeText(getActivity(), "退码失败！", Toast.LENGTH_SHORT).show();

                        }

                        break;
                    case ERROR:
                        tingYa.setText(R.string.tingya);
                        tingYa.setTextColor(Color.BLACK);
                        removeList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "没有码可以退！", Toast.LENGTH_SHORT).show();
                        break;
                    case CLEARN:
                        Map map2 = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map2.get("count").toString()) > 0) {
                            getData();
                        }
                        break;
                    case GETPEILV:
                        Map map3 = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (tag.getText().length() != 1)
                            tag.setText(map3.get("peilv").toString());
                        xiane.setText(map3.get("xiane").toString());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public static void check(boolean b) {
        if (b) {
            removeList.clear();
            for (Map<String, String> map : dataList) {
                removeList.add(map);
            }
        } else {
            for (Map<String, String> map : removeList) {
                if (map.equals(baseMap)) {
                    removeList.clear();
                    break;
                }
            }
        }
        if (removeList.size() > 0) {
            tingYa.setText(R.string.delect);
            tingYa.setTextColor(Color.BLUE);
        } else {
            tingYa.setText(R.string.tingya);
            tingYa.setTextColor(Color.BLACK);
        }
    }

    public static void check(Map<String, String> map, boolean b) {
        if (b) {
            removeList.remove(map);
            removeList.add(map);
        } else {
            removeList.remove(baseMap);
            removeList.remove(map);
        }
        if (removeList.size() > 0) {
            tingYa.setText(R.string.delect);
            tingYa.setTextColor(Color.BLUE);
        } else {
            tingYa.setText(R.string.tingya);
            tingYa.setTextColor(Color.BLACK);
        }
    }
}
