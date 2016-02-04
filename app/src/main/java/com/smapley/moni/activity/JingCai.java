package com.smapley.moni.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.R;
import com.smapley.moni.adapter.PrintAdapter2;
import com.smapley.moni.adapter.TingyYaAdapter;
import com.smapley.moni.http.params.AddJiangParams;
import com.smapley.moni.http.params.GetJinParams;
import com.smapley.moni.http.service.AddJiangService;
import com.smapley.moni.http.service.GetJinService;
import com.smapley.moni.listview.SwipeMenu;
import com.smapley.moni.listview.SwipeMenuCreator;
import com.smapley.moni.listview.SwipeMenuItem;
import com.smapley.moni.listview.SwipeMenuListView;
import com.smapley.moni.util.MyData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JingCai extends Activity implements View.OnClickListener {


    private TextView tv_title1;
    private TextView tv_title2;
    private TextView tv_title3;

    private View keybord;
    private TextView back;
    private TextView numText;
    private RelativeLayout numLayout;
    private SwipeMenuListView listView;
    private ListView listView1;
    private TingyYaAdapter adapter1;
    private TextView numCart;
    private TextView keyitem1;
    private TextView keyitem2;
    private TextView keyitem3;
    private TextView keyitem5;
    private TextView keyitem6;
    private TextView keyitem7;
    private TextView keyitem8;
    private TextView keyitem9;
    private TextView keyitem10;
    private TextView keyitem11;
    private TextView keyitem12;
    private TextView keyitem13;
    private TextView keyitem15;
    private TextView tag;
    private TextView xiane;
    private final int GETDATA = -2;
    public static List<Map<String, String>> dataList = new ArrayList<>();
    private List<Map<String, String>> dataList1 = new ArrayList<>();
    private PrintAdapter2 adapter;
    private ProgressDialog dialog;
    private boolean jine = false;
    private int xian = 0;
    private int dao = 0;
    private static Map<String, String> baseMap = new HashMap<>();
    private SharedPreferences sp_user;

    private String title = "金币：";
    private String yyed = "";
    public String qishu = "";

    public static List<Map<String, String>> removeList = new ArrayList<>();
    private static TextView tingYa;

    private String onlyid;

    private String pei;
    private String gold;
    private String type;

    private TextView danzhu;
    private TextView peilv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingcai);
        sp_user = getSharedPreferences("user", Context.MODE_PRIVATE);

        onlyid = getIntent().getStringExtra("onlyid");
        pei = getIntent().getStringExtra("pei");
        gold = getIntent().getStringExtra("gold");
        type = getIntent().getStringExtra("type");

        dialog = new ProgressDialog(JingCai.this);
        dialog.setTitle("提示：");
        baseMap.put("number", "号码");
        baseMap.put("gold", "金额");
        baseMap.put("pei", "赔率");
        baseMap.put("hotstat", "0");
        baseMap.put("id", "0");
        dataList.clear();
        dataList.add(baseMap);
        initView();
        getJinService.load(new GetJinParams(MyData.UserName));
    }


    private void initView() {
        tingYa = (TextView) findViewById(R.id.print_tingya);

        tingYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tingYa.getText().equals(getString(R.string.delect))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JingCai.this);
                    builder.setTitle("提示：");
                    builder.setMessage("是否批量删除？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            removeList.remove(baseMap);
                            dataList.removeAll(removeList);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.create().show();

                } else if (tingYa.getText().equals(getString(R.string.tingya))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JingCai.this);
                    builder.setMessage("是否清空全部号码？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dataList1.clear();
                            dataList.clear();
                            dataList.add(baseMap);
                            adapter.notifyDataSetChanged();
                            adapter1.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.create().show();
                }
            }
        });


        danzhu = (TextView) findViewById(R.id.danzhu);
        peilv = (TextView) findViewById(R.id.peilv);

        peilv.setText("赔率："+pei);
        danzhu.setText("单注："+gold);


        tv_title1 = (TextView) findViewById(R.id.title_item1);
        tv_title2 = (TextView) findViewById(R.id.title_item2);
        tv_title3 = (TextView) findViewById(R.id.title_item3);
        listView1 = (ListView) findViewById(R.id.list1);
        //  tv_title1.setText("快选");
        tv_title2.setText(title + yyed);
        tv_title3.setText("开奖");
//        tv_title1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(JingCai.this, KuaiXuan.class);
//                intent.putExtra("onlyid", onlyid);
//                startActivity(intent);
//            }
//        });
        tv_title3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shuzu = null;
                for (Map map : dataList) {
                    if (!map.equals(baseMap)) {
                        if (shuzu == null) {
                            shuzu = map.get("number").toString();
                        } else {
                            shuzu = shuzu + "," + map.get("number").toString();
                        }
                    }
                }
                if (shuzu != null) {
                    addJiangService.load(new AddJiangParams(MyData.UserName, MyData.PassWord, onlyid, shuzu));
                } else {
                    Toast.makeText(JingCai.this, "您还没有选择号码！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keybord.setVisibility(View.GONE);

            }
        });
        numLayout = (RelativeLayout) findViewById(R.id.table_item1_layout);
        numCart = (TextView) findViewById(R.id.table_item1_clo);
        keybord = findViewById(R.id.print_keybord);
        numText = (TextView) findViewById(R.id.num_text);
        numLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNumText();
            }
        });

        keyitem1 = (TextView) findViewById(R.id.key_item1);
        keyitem2 = (TextView) findViewById(R.id.key_item2);
        keyitem3 = (TextView) findViewById(R.id.key_item3);
        keyitem5 = (TextView) findViewById(R.id.key_item5);
        keyitem6 = (TextView) findViewById(R.id.key_item6);
        keyitem7 = (TextView) findViewById(R.id.key_item7);
        keyitem8 = (TextView) findViewById(R.id.key_item8);
        keyitem9 = (TextView) findViewById(R.id.key_item9);
        keyitem10 = (TextView) findViewById(R.id.key_item10);
        keyitem11 = (TextView) findViewById(R.id.key_item11);
        keyitem12 = (TextView) findViewById(R.id.key_item12);
        keyitem13 = (TextView) findViewById(R.id.key_item13);
        keyitem15 = (TextView) findViewById(R.id.key_item15);
        tag = (TextView) findViewById(R.id.text_tag);
        xiane = (TextView) findViewById(R.id.text_xiane);

        keyitem1.setOnClickListener(this);
        keyitem2.setOnClickListener(JingCai.this);
        keyitem3.setOnClickListener(this);
        keyitem5.setOnClickListener(this);
        keyitem6.setOnClickListener(this);
        keyitem7.setOnClickListener(this);
        keyitem8.setOnClickListener(this);
        keyitem9.setOnClickListener(this);
        keyitem10.setOnClickListener(this);
        keyitem11.setOnClickListener(this);
        keyitem12.setOnClickListener(this);
        keyitem13.setOnClickListener(this);
        keyitem15.setOnClickListener(this);


        listView = (SwipeMenuListView) findViewById(R.id.listView);
        listView.setDivider(null);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(JingCai.this);
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
                            dataList.remove(item);
                            adapter.notifyDataSetChanged();
                            break;
                    }

                }
                return false;
            }
        });

        adapter = new PrintAdapter2(this, dataList);
        listView.setDivider(null);
        listView.setAdapter(adapter);

        adapter1 = new TingyYaAdapter(this, dataList1);
        listView1.setAdapter(adapter1);
    }


    private void goNumText() {

        if (tag.getText().length() != 1)
            tag.setText("");
        xiane.setText("");
        numText.setText("");
        keybord.setVisibility(View.VISIBLE);
        keybord.setBackgroundResource(R.color.bac1);
        numCart.setVisibility(View.VISIBLE);
        listView.smoothScrollToPosition(adapter.getCount() - 1);
        listView1.smoothScrollToPosition(adapter.getCount() - 1);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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


            case R.id.key_item12:
                if (numText.getText().toString().length() == Integer.parseInt(type)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("number", numText.getText().toString());
                    map.put("tingya", numText.getText().toString());
                    map.put("gold", gold);
                    map.put("hotstat", "0");
                    map.put("pei", "1:" + pei);
                    boolean isHase = false;
                    for (Map map1 : dataList) {
                        if (map1.equals(map)) {
                            isHase = true;
                            break;
                        }
                    }
                    if (isHase) {
                        dataList1.add(map);
                    } else {
                        dataList.add(map);
                    }
                    adapter.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();
                    goNumText();
                    xian = 0;
                    dao = 0;
                    tag.setText("");
                } else {
                    Toast.makeText(JingCai.this, "类型不符合！", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.key_item15:

                break;

            default:
                if (numText.getText().length() < Integer.parseInt(type)) {
                    numText.setText(numText.getText().toString() + ((TextView) view).getText().toString());

                }
                break;


        }

    }

    private AddJiangService addJiangService = new AddJiangService() {
        @Override
        public void Succ(String data) {
            getJinService.load(new GetJinParams(MyData.UserName));
            int result = JSON.parseObject(data, new TypeReference<Integer>() {
            });
            if (result > 0) {
                Toast.makeText(JingCai.this, "恭喜您，中奖啦！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(JingCai.this, "很遗憾，就差一点点！", Toast.LENGTH_SHORT).show();
            }
            dataList.clear();
            dataList.add(baseMap);
            adapter.notifyDataSetChanged();
            dataList1.clear();
            adapter1.notifyDataSetChanged();
        }
    };

    private GetJinService getJinService=new GetJinService() {
        @Override
        public void Succ(String data) {
            Map map=JSON.parseObject(data,new TypeReference<Map>(){});
            tv_title2.setText("金币："+map.get("gold"));
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
