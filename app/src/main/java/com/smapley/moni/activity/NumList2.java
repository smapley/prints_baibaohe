package com.smapley.moni.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.smapley.moni.R;
import com.smapley.moni.adapter.NumListAdapter;
import com.smapley.moni.http.params.AddJiangParams;
import com.smapley.moni.http.service.AddJiangService;
import com.smapley.moni.util.MyData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/11/15.
 */
public class NumList2 extends Activity  {

    private RecyclerView recyclerView;
    private NumListAdapter adapter;

    private String item0;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
    private String item6;

    private boolean qian;
    private boolean bai;
    private boolean shi;
    private boolean ge;

    private boolean qian2;
    private boolean bai2;
    private boolean shi2;
    private boolean ge2;

    private boolean dao1;
    private boolean dao2;
    private boolean dao3;
    private boolean dao4;

    private String[] item1s;
    private String[] item2s;
    private String[] item3s;
    private String[] item4s;
    private String[] item5s;
    private String[] item6s;


    private String base = "0123456789";

    private List<String> list_item = new ArrayList<>();
    private List<String> list_item_dao = new ArrayList<>();
    private List<String[]> list_items = new ArrayList<>();
    private List<Boolean> list_dao = new ArrayList<>();

    private List<String> list = new ArrayList<>();

    private final int UPDATA = 1;

    private TextView bottom_item1;
    private TextView bottom_item3;
    private View keybord;
    private TextView back;

    private ProgressDialog dialog;

    private String onlyid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numlist2);

        onlyid=getIntent().getStringExtra("onlyid");

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在下注。。。");

        initView();
        initData();
        create0();
    }

    private void create0() {
        int daos = 0;
        for (int i = 0; i < list_dao.size(); i++) {
            if (list_dao.get(i) == true) {
                daos++;
                list_item_dao.add(list_item.get(i));
            }
        }

        if (daos > 1) {
            create01(list_item_dao, 0);
        } else {
            create1();
            create2();
            setData(list);
        }

    }

    private void create01(List<String> listdata, int num) {
        for (int i = 0; i < listdata.size(); i++) {
            while (list_dao.get(num) == false) {
                num++;
            }
            list_item.set(num, listdata.get(i));
            if (num < 3 && listdata.size() > 1) {
                List<String> listdatas = new ArrayList<>();
                listdatas.addAll(listdata);
                listdatas.remove(i);
                create01(listdatas, num + 1);
            } else {
                create1();
                create2();
                setData(list);
            }
        }
    }

    private void create4(String data1, String data2, String data3, String data4) {
        if (!qian2 && !bai2 && !shi2 && !ge2) {
            list.add(data1 + data2 + data3 + data4);
        } else {
            int num = 0;

            if (qian2) {
                num = Integer.parseInt(data1);
            }
            if (bai2) {
                num += Integer.parseInt(data2);
            }
            if (shi2) {
                num += Integer.parseInt(data3);
            }
            if (ge2) {
                num += Integer.parseInt(data4);
            }
            for (int i = 0; i < item6s.length; i++) {
                if (num % 10 == Integer.parseInt(item6s[i])) {
                    list.add(data1 + data2 + data3 + data4);
                }
            }
        }
    }

    private void create3(String data1, String data2, String data3, String data4) {

        if (!qian && !bai && !shi && !ge) {
            list.add(data1 + data2 + data3 + data4);
        } else {
            int num = 0;

            if (qian) {
                num = Integer.parseInt(data1);
            }
            if (bai) {
                num += Integer.parseInt(data2);
            }
            if (shi) {
                num += Integer.parseInt(data3);
            }
            if (ge) {
                num += Integer.parseInt(data4);
            }

            for (int i = 0; i < item5s.length; i++) {
                if (num % 10 == Integer.parseInt(item5s[i])) {
                    create4(data1, data2, data3, data4);
                }
            }
        }
    }

    private void create2() {
        String data1 = "";
        String data2 = "";
        String data3 = "";
        String data4 = "";
        for (int i = 0; i < item1s.length; i++) {
            data1 = item1s[i];
            for (int j = 0; j < item2s.length; j++) {
                data2 = item2s[j];
                for (int z = 0; z < item3s.length; z++) {
                    data3 = item3s[z];
                    for (int y = 0; y < item4s.length; y++) {
                        data4 = item4s[y];
                        create3(data1, data2, data3, data4);
                    }
                }
            }
        }
    }

    private void create1() {

        item1s = new String[list_item.get(0).length()];
        item2s = new String[list_item.get(1).length()];
        item3s = new String[list_item.get(2).length()];
        item4s = new String[list_item.get(3).length()];
        item5s = new String[list_item.get(4).length()];
        item6s = new String[list_item.get(5).length()];
        list_items.clear();
        list_items.add(item1s);
        list_items.add(item2s);
        list_items.add(item3s);
        list_items.add(item4s);
        list_items.add(item5s);
        list_items.add(item6s);

        for (int i = 0; i < list_item.size(); i++) {
            for (int j = 0; j < list_item.get(i).length(); j++) {
                list_items.get(i)[j] = list_item.get(i).substring(j, j + 1);
            }
        }
    }

    private void initData() {
        Intent intent = getIntent();
        item1 = intent.getStringExtra("item1").length() == 0 ? base : intent.getStringExtra("item1");
        item2 = intent.getStringExtra("item2").length() == 0 ? base : intent.getStringExtra("item2");
        item3 = intent.getStringExtra("item3").length() == 0 ? base : intent.getStringExtra("item3");
        item4 = intent.getStringExtra("item4").length() == 0 ? base : intent.getStringExtra("item4");
        item5 = intent.getStringExtra("item5").length() == 0 ? base : intent.getStringExtra("item5");
        item6 = intent.getStringExtra("item6").length() == 0 ? base : intent.getStringExtra("item6");


        list_item.add(item1);
        list_item.add(item2);
        list_item.add(item3);
        list_item.add(item4);
        list_item.add(item5);
        list_item.add(item6);

        qian = intent.getBooleanExtra("qian", false);
        bai = intent.getBooleanExtra("bai", false);
        shi = intent.getBooleanExtra("shi", false);
        ge = intent.getBooleanExtra("ge", false);

        qian2 = intent.getBooleanExtra("qian2", false);
        bai2 = intent.getBooleanExtra("bai2", false);
        shi2 = intent.getBooleanExtra("shi2", false);
        ge2 = intent.getBooleanExtra("ge2", false);

        dao1 = intent.getBooleanExtra("dao1", false);
        dao2 = intent.getBooleanExtra("dao2", false);
        dao3 = intent.getBooleanExtra("dao3", false);
        dao4 = intent.getBooleanExtra("dao4", false);

        list_dao.add(dao1);
        list_dao.add(dao2);
        list_dao.add(dao3);
        list_dao.add(dao4);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.numlist_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));




        bottom_item1 = (TextView) findViewById(R.id.chose_bottom_item1);
        bottom_item3 = (TextView) findViewById(R.id.chose_bottom_item3);
        keybord = findViewById(R.id.print_keybord);
        back = (TextView) findViewById(R.id.back);

        bottom_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NumList2.this);
                builder.setTitle("提示：");
                builder.setMessage("是否确定下注？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        String number = "";
                        for (int i = 0; i < list.size(); i++) {
                            number = number + list.get(i) + ",";
                        }
                        number = number.substring(0, number.length() - 1);
                        addJiangService.load(new AddJiangParams(MyData.UserName,MyData.PassWord,onlyid,number));
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keybord.setVisibility(View.GONE);
            }
        });
    }

    private AddJiangService addJiangService=new AddJiangService() {
        @Override
        public void Succ(String data) {

        }
    };

    private void setData(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).equals(list.get(j))) {
                    list.remove(j);
                    j--;
                }
            }
        }
        adapter = new NumListAdapter(this, list);
        recyclerView.setAdapter(adapter);

        bottom_item1.setText("笔数：" + list.size());
    }


}
