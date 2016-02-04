package com.smapley.moni.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lvrenyang.utils.DataUtils;
import com.smapley.moni.R;
import com.smapley.moni.adapter.DetailAdapter;
import com.smapley.moni.listview.SwipeMenu;
import com.smapley.moni.listview.SwipeMenuCreator;
import com.smapley.moni.listview.SwipeMenuItem;
import com.smapley.moni.listview.SwipeMenuListView;
import com.smapley.moni.print.Global;
import com.smapley.moni.print.WorkService;
import com.smapley.moni.util.HttpUtils;
import com.smapley.moni.util.MyData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2015/11/8.
 */
public class Detail extends Activity {

    private static final int PRINT = 5;
    private static final int ERROR = 7;
    private static final int DELECTS = 8;
    private SwipeMenuListView listView;

    private TextView re_print;

    private DetailAdapter adapter1;

    private final int GETDATA1 = 1;
    private final int TUIMA = 6;
    private final int UPDATA = 4;

    private static List<Map<String, String>> list1;
    private List<Map<String, String>> list1_now;


    private TextView page_up;
    private TextView page_num;
    private TextView page_down;

    private int now_item = 1;
    private int page_num1 = 1;

    public static Dialog dialog;
    private Map map;
    private String allidString;

    private String qishu;

    private static CheckBox checkBox;

    public static List<Map> removeList = new ArrayList<>();
    private TextView delect;
    private TextView item2;
    private TextView item3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        qishu = getIntent().getStringExtra("qishu");


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_item1);
        builder.setMessage(R.string.dialog_item2);
        dialog = builder.create();

        initView();
        getData(GETDATA1);
    }

    private void upData() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
        builder.setTitle("提示：");
        builder.setMessage("是否重新打印上一张？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogs, int which) {
                dialogs.dismiss();
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap map = new HashMap();
                        map.put("user1", MyData.UserName);
                        mhandler.obtainMessage(UPDATA, HttpUtils.updata(map, MyData.URL_GETJILU2)).sendToTarget();
                    }
                }).start();
            }
        });
        builder.create().show();


    }


    public static void check(boolean b) {
        if (b) {
            removeList.clear();
            removeList.addAll(list1);
        } else {
            if (removeList.size() == list1.size())
                removeList.clear();
        }
    }

    public static void check(Map map, boolean b) {
        if (b) {
            removeList.remove(map);
            removeList.add(map);
        } else {
            removeList.remove(map);
            checkBox.setChecked(false);
        }
    }

    private void initView() {

        item2=(TextView)findViewById(R.id.title_item2);
        item2.setText("明细");
        checkBox = (CheckBox) findViewById(R.id.details_check);
        delect = (TextView) findViewById(R.id.title_item1);
        delect.setText("退码");

        delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
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

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                check(b);
                adapter1.notifyDataSetChanged();
            }
        });

        re_print = (TextView) findViewById(R.id.title_item3);
        re_print.setText("重打");
        re_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WorkService.workThread.isConnected()) {
                    upData();
                } else {
                    Toast.makeText(Detail.this, "未连接到打印机！", Toast.LENGTH_SHORT).show();

                }

            }
        });



        listView = (SwipeMenuListView) findViewById(R.id.detail_list);
        listView.setDivider(null);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(Detail.this);
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
                Map map = null;
                if (now_item == 1) {
                    map = list1_now.get(position);
                    final String id = map.get("id").toString();
                    final String biaoshi = map.get("biaoshi").toString();
                    switch (index) {
                        case 0:
                            AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
                            builder.setTitle("提示：");
                            builder.setMessage("确定退码？");
                            builder.setNegativeButton("取消", null);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HashMap map = new HashMap();
                                            map.put("id", id);
                                            map.put("biaoshi", biaoshi);
                                            map.put("user1", MyData.UserName);
                                            map.put("mi", MyData.PassWord);
                                            mhandler.obtainMessage(TUIMA, HttpUtils.updata(map, MyData.URL_TUIMA1)).sendToTarget();
                                        }
                                    }).start();
                                }
                            });
                            builder.create().show();


                            break;
                    }
                }


                return false;
            }
        });

        page_down = (TextView) findViewById(R.id.page_down);
        page_num = (TextView) findViewById(R.id.page_num);
        page_up = (TextView) findViewById(R.id.page_up);
        page_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (now_item == 1) {
                    if (page_num1 * 100 < list1.size()) {
                        if (page_num1 * 100 + 100 < list1.size()) {
                            list1_now = list1.subList(page_num1 * 100, page_num1 * 100 + 100);
                        } else {
                            list1_now = list1.subList(page_num1 * 100, list1.size());
                        }
                        page_num1++;
                        page_num.setText(page_num1 + "");
                        adapter1 = new DetailAdapter(Detail.this, list1_now);
                        listView.setAdapter(adapter1);
                    }
                }
            }
        });

        page_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (now_item == 1) {
                    if (page_num1 > 1) {
                        page_num1--;
                        list1_now = list1.subList(page_num1 * 100 - 100, page_num1 * 100);
                        page_num.setText(page_num1 + "");
                        adapter1 = new DetailAdapter(Detail.this, list1_now);
                        listView.setAdapter(adapter1);
                    }
                }
            }
        });

    }

    private void getData(final int num) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("user1", MyData.UserName);
                String url = null;
                if (num == GETDATA1) {
                    url = MyData.URL_GETMINGXI;
                }
                mhandler.obtainMessage(num, HttpUtils.updata(map, url)).sendToTarget();
            }
        }).start();

    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case GETDATA1:
                        list1 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Map<String, String>>>() {
                        });
                        for (int i = 0; i < list1.size(); i++) {
                            switch (Integer.parseInt(list1.get(i).get("zt").toString())) {
                                case 0:
                                    list1.get(i).put("zt", "未打印");

                                    break;
                                case 1:
                                    list1.get(i).put("zt", "已打印");
                                    break;
                                case 9:
                                    list1.get(i).put("zt", "已退码");
                                    break;
                            }
                        }
                        if (list1.size() > 100) {
                            list1_now = list1.subList(0, 100);
                        } else {
                            list1_now = list1;
                        }
                        adapter1 = new DetailAdapter(Detail.this, list1_now);
                        listView.setAdapter(adapter1);

                        break;

                    case TUIMA:
                        int result = JSON.parseObject(msg.obj.toString(), new TypeReference<Integer>() {
                        });
                        switch (result) {
                            case 1:
                                Toast.makeText(Detail.this, "退码成功！", Toast.LENGTH_SHORT).show();
                                getData(now_item);
                                break;

                            case -2:
                                Toast.makeText(Detail.this, "退码已过期！", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(Detail.this, "退码失败！", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        break;
                    case UPDATA:
                        dialog.dismiss();

                        map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map.get("count").toString()) > 0) {

                            mhandler.obtainMessage(PRINT).sendToTarget();

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
                            builder.setTitle(R.string.dialog_item1);
                            builder.setMessage(R.string.dialog_item3);
                            builder.setPositiveButton(R.string.dialog_item7, null);
                            dialog = builder.create();
                            dialog.show();
                        }
                        break;

                    case PRINT:
                        AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
                        builder.setTitle(R.string.dialog_item1);
                        builder.setMessage(R.string.dialog_item4);
                        dialog = builder.create();
                        dialog.show();
                        allidString = map.get("allid").toString();
                        String allid = "编号：" + allidString;
                        String riqi = "日期：" + map.get("riqi").toString();
                        String name = "会员：" + map.get("ming").toString();
                        String qihao = "第" + qishu + "期，3天内有效！！";
                        String allnum = " 笔数 " + map.get("count") + "  总金额 " + map.get("allgold") + "元";
                        String lin = " ";
                        String lin2 = "————————————————————————————————";
                        String dataString = map.get("beizhu").toString();
                        List<Map<String, String>> list = JSON.parseObject(map.get("result").toString(), new TypeReference<List<Map<String, String>>>() {
                        });
                        String number = "号码";
                        String gold = "金额";
                        String pei = "1元赔率";

                        byte[] setHT = {0x1b, 0x44, 0x01, 0x0e, 0x16, 0x00,
                                0x1b, 0x61, 0x00,
                                0x1b, 0x39, 0x01,
                                0x1b, 0x21, 0x28,
                                0x1b, 0x33, 0x30};

                        byte[] setHT0 = {0x1b, 0x44, 0x04, 0x0d, 0x17, 0x00,
                                0x1b, 0x61, 0x00,
                                0x1b, 0x39, 0x01,
                                0x1b, 0x21, 0x08,
                                0x1b, 0x33, 0x35};

                        byte[] text1 = new byte[]{
                                //初始化打印机
                                0x1b, 0x40,
                                // 对齐方式
                                0x1b, 0x61, 0x01,
                                // 打印方式
                                0x1b, 0x21, 0x08,
                                0x1b, 0x39, 0x01,
                                // 行间距
                                0x1b, 0x33, 0x30};
                        byte[] text0 = new byte[]{0x1b, 0x40,
                                0x1b, 0x61, 0x00,
                                0x1b, 0x21, 0x08,
                                0x1b, 0x39, 0x01,
                                0x1b, 0x33, 0x20};
                        byte[] text4 = new byte[]{0x1b, 0x40,
                                0x1b, 0x61, 0x00,
                                0x1b, 0x21, 0x08,
                                0x1b, 0x39, 0x01,
                                0x1b, 0x33, 0x10};
                        byte[] text3 = new byte[]{0x1b, 0x40,
                                0x1b, 0x61, 0x01,
                                0x1b, 0x21, 0x08,
                                0x1b, 0x39, 0x01,
                                0x1b, 0x33, 0x10};
                        byte[] HT = {0x09};
                        byte[] LF = {0x0d, 0x0a};

                        int num = 19;
                        int loops = 6;
                        int size = num + list.size() * loops + 18;
                        byte[][] databuf = new byte[size][];


                        databuf[0] = text0;
                        databuf[1] = riqi.getBytes();
                        databuf[2] = LF;
                        databuf[3] = name.getBytes("UTF-8");
                        databuf[4] = LF;
                        databuf[5] = allid.getBytes();
                        databuf[6] = LF;
                        databuf[7] = dataString.getBytes();
                        databuf[8] = LF;
                        databuf[9] = text3;
                        databuf[10] = lin2.getBytes();
                        databuf[11] = LF;
                        databuf[12] = setHT0;
                        databuf[13] = number.getBytes();
                        databuf[14] = HT;
                        databuf[15] = gold.getBytes();
                        databuf[16] = HT;
                        databuf[17] = pei.getBytes();
                        databuf[18] = LF;
                        databuf[19] = setHT;


                        for (int i = 0; i < list.size(); i++) {

                            databuf[i * loops + num + 1] = list.get(i).get("number").getBytes();
                            databuf[i * loops + num + 2] = HT;
                            databuf[i * loops + num + 3] = list.get(i).get("gold").getBytes();
                            databuf[i * loops + num + 4] = HT;
                            String data = list.get(i).get("pei");
                            databuf[i * loops + num + 5] = data.getBytes();
                            databuf[i * loops + num + 6] = LF;
                        }

                        databuf[size - 18] = LF;
                        databuf[size - 17] = text4;
                        databuf[size - 16] = LF;
                        databuf[size - 15] = allnum.getBytes();
                        databuf[size - 14] = LF;
                        databuf[size - 13] = text3;
                        databuf[size - 12] = lin2.getBytes();
                        databuf[size - 11] = LF;
                        databuf[size - 10] = qihao.getBytes();
                        databuf[size - 9] = LF;
                        databuf[size - 8] = lin.getBytes();
                        databuf[size - 7] = LF;
                        databuf[size - 6] = lin.getBytes();
                        databuf[size - 5] = LF;
                        databuf[size - 4] = lin.getBytes();
                        databuf[size - 3] = LF;
                        databuf[size - 2] = lin.getBytes();
                        databuf[size - 1] = LF;


                        byte[] buf = DataUtils.byteArraysToBytes(databuf);

                        if (WorkService.workThread.isConnected()) {
                            Bundle data = new Bundle();
                            data.putByteArray(Global.BYTESPARA1, buf);
                            data.putInt(Global.INTPARA1, 0);
                            data.putInt(Global.INTPARA2, buf.length);
                            WorkService.workThread.handleCmd(Global.CMD_POS_WRITE, data);
                        } else {
                            dialog.dismiss();
                            builder.setMessage(R.string.dialog_item10);
                            builder.setNegativeButton(R.string.dialog_item5, null);
                            dialog = builder.create();
                            dialog.show();
                            Toast.makeText(Detail.this, Global.toast_notconnect, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case DELECTS:
                        checkBox.setChecked(false);
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
                            Toast.makeText(Detail.this, "退码成功！", Toast.LENGTH_SHORT).show();
                            getData(GETDATA1);
                        } else {
                            Toast.makeText(Detail.this, "退码失败！", Toast.LENGTH_SHORT).show();

                        }

                        break;
                    case ERROR:
                        checkBox.setChecked(false);
                        removeList.clear();
                        adapter1.notifyDataSetChanged();
                        Toast.makeText(Detail.this, "没有码可以退！", Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Detail.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
