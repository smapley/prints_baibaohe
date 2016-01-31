package com.smapley.prints2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smapley.prints2.R;
import com.smapley.prints2.activity.MainActivity;
import com.smapley.prints2.activity.NumList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2015/11/6.
 */
public class Chose extends Fragment implements View.OnClickListener {

    private TextView tv_title2;
    private TextView tv_title3;

    private TextView item1;
    private TextView item2;
    private TextView item3;
    private TextView item4;
    private TextView item5;
    private TextView item6;

    private TextView qian;
    private TextView bai;
    private TextView shi;
    private TextView ge;

    private TextView qian2;
    private TextView bai2;
    private TextView shi2;
    private TextView ge2;

    private TextView dao1;
    private TextView dao2;
    private TextView dao3;
    private TextView dao4;

    private View item1_layout;
    private View item2_layout;
    private View item3_layout;
    private View item4_layout;
    private View item5_layout;
    private View item6_layout;

    private TextView item1_clo;
    private TextView item2_clo;
    private TextView item3_clo;
    private TextView item4_clo;
    private TextView item5_clo;
    private TextView item6_clo;

    private LinearLayout keybord;

    private View back;

    private List<TextView> list_clo;
    private List<TextView> list_item;

    private TextView now_text;

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


    private boolean qian_state = false;
    private boolean bai_state = false;
    private boolean shi_state = false;
    private boolean ge_state = false;

    private boolean qian2_state = false;
    private boolean bai2_state = false;
    private boolean shi2_state = false;
    private boolean ge2_state = false;

    private boolean dao1_state = false;
    private boolean dao2_state = false;
    private boolean dao3_state = false;
    private boolean dao4_state = false;


    private int now_position = 0;
    private String title="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chose, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        tv_title2 = (TextView) view.findViewById(R.id.title_item2);
        tv_title3=(TextView)view.findViewById(R.id.title_item3);
        tv_title3.setText("生成");
        tv_title3.setOnClickListener(this);
        tv_title2.setText(title);
        item1 = (TextView) view.findViewById(R.id.chose_item1);
        item2 = (TextView) view.findViewById(R.id.chose_item2);
        item3 = (TextView) view.findViewById(R.id.chose_item3);
        item4 = (TextView) view.findViewById(R.id.chose_item4);
        item5 = (TextView) view.findViewById(R.id.chose_item5);
        item6 = (TextView) view.findViewById(R.id.chose_item6);

        list_item = new ArrayList<>();
        list_item.add(item1);
        list_item.add(item2);
        list_item.add(item3);
        list_item.add(item4);
        list_item.add(item5);
        list_item.add(item6);

        qian = (TextView) view.findViewById(R.id.chose_qian);
        bai = (TextView) view.findViewById(R.id.chose_bai);
        shi = (TextView) view.findViewById(R.id.chose_shi);
        ge = (TextView) view.findViewById(R.id.chose_ge);

        qian2 = (TextView) view.findViewById(R.id.chose_qian2);
        bai2 = (TextView) view.findViewById(R.id.chose_bai2);
        shi2 = (TextView) view.findViewById(R.id.chose_shi2);
        ge2 = (TextView) view.findViewById(R.id.chose_ge2);

        dao1 = (TextView) view.findViewById(R.id.chose_dao1);
        dao2 = (TextView) view.findViewById(R.id.chose_dao2);
        dao3 = (TextView) view.findViewById(R.id.chose_dao3);
        dao4 = (TextView) view.findViewById(R.id.chose_dao4);

        keybord = (LinearLayout) view.findViewById(R.id.chose_keybord);

        back = view.findViewById(R.id.chose_back);

        item1_clo = (TextView) view.findViewById(R.id.chose_item1_clo);
        item2_clo = (TextView) view.findViewById(R.id.chose_item2_clo);
        item3_clo = (TextView) view.findViewById(R.id.chose_item3_clo);
        item4_clo = (TextView) view.findViewById(R.id.chose_item4_clo);
        item5_clo = (TextView) view.findViewById(R.id.chose_item5_clo);
        item6_clo = (TextView) view.findViewById(R.id.chose_item6_clo);

        list_clo = new ArrayList<>();
        list_clo.add(item1_clo);
        list_clo.add(item2_clo);
        list_clo.add(item3_clo);
        list_clo.add(item4_clo);
        list_clo.add(item5_clo);
        list_clo.add(item6_clo);

        item1_layout = view.findViewById(R.id.chose_item1_layout);
        item2_layout = view.findViewById(R.id.chose_item2_layout);
        item3_layout = view.findViewById(R.id.chose_item3_layout);
        item4_layout = view.findViewById(R.id.chose_item4_layout);
        item5_layout = view.findViewById(R.id.chose_item5_layout);
        item6_layout = view.findViewById(R.id.chose_item6_layout);

        item1_layout.setOnClickListener(this);
        item2_layout.setOnClickListener(this);
        item3_layout.setOnClickListener(this);
        item4_layout.setOnClickListener(this);
        item5_layout.setOnClickListener(this);
        item6_layout.setOnClickListener(this);

        qian.setOnClickListener(this);
        bai.setOnClickListener(this);
        shi.setOnClickListener(this);
        ge.setOnClickListener(this);

        qian2.setOnClickListener(this);
        bai2.setOnClickListener(this);
        shi2.setOnClickListener(this);
        ge2.setOnClickListener(this);

        dao1.setOnClickListener(this);
        dao2.setOnClickListener(this);
        dao3.setOnClickListener(this);
        dao4.setOnClickListener(this);


        back.setOnClickListener(this);

        initKeybord(view);

    }

    private void initKeybord(View view) {
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

        keyitem1.setOnClickListener(this);
        keyitem2.setOnClickListener(this);
        keyitem3.setOnClickListener(this);
        keyitem4.setOnClickListener(this);
        keyitem5.setOnClickListener(this);
        keyitem6.setOnClickListener(this);
        keyitem7.setOnClickListener(this);
        //  keyitem8.setOnClickListener(this);
        keyitem9.setOnClickListener(this);
        keyitem10.setOnClickListener(this);
        keyitem11.setOnClickListener(this);
        keyitem12.setOnClickListener(this);
        keyitem13.setOnClickListener(this);
        keyitem14.setOnClickListener(this);
        // keyitem15.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chose_item1_layout:
                now_position = 0;
                showKeybord();
                break;
            case R.id.chose_item2_layout:
                now_position = 1;
                showKeybord();
                break;
            case R.id.chose_item3_layout:
                now_position = 2;
                showKeybord();
                break;
            case R.id.chose_item4_layout:
                now_position = 3;
                showKeybord();
                break;
            case R.id.chose_item5_layout:
                now_position = 4;
                showKeybord();
                break;
            case R.id.chose_item6_layout:
                now_position = 5;
                showKeybord();
                break;
            case R.id.chose_qian:
                if (check(0)) {
                    qian_state = !qian_state;
                    setBack(view, qian_state);
                } else {
                    showTost(1);
                }
                break;
            case R.id.chose_bai:
                if (check(1)) {
                    bai_state = !bai_state;
                    setBack(view, bai_state);
                } else {
                    showTost(1);
                }
                break;
            case R.id.chose_shi:
                if (check(2)) {
                    shi_state = !shi_state;
                    setBack(view, shi_state);
                } else {
                    showTost(1);
                }
                break;
            case R.id.chose_ge:
                if (check(3)) {
                    ge_state = !ge_state;
                    setBack(view, ge_state);
                } else {
                    showTost(1);
                }
                break;
            case R.id.chose_qian2:
                if (check(0)) {
                    qian2_state = !qian2_state;
                    setBack(view, qian2_state);
                } else {
                    showTost(1);
                }
                break;
            case R.id.chose_bai2:
                if (check(1)) {
                    bai2_state = !bai2_state;
                    setBack(view, bai2_state);
                } else {
                    showTost(1);
                }
                break;
            case R.id.chose_shi2:
                if (check(2)) {
                    shi2_state = !shi2_state;
                    setBack(view, shi2_state);
                } else {
                    showTost(1);
                }
                break;
            case R.id.chose_ge2:
                if (check(3)) {
                    ge2_state = !ge2_state;
                    setBack(view, ge2_state);
                } else {
                    showTost(1);
                }
                break;
            case R.id.chose_dao1:
                if (check2(3)) {
                    dao1_state = !dao1_state;
                    setBack(view, dao1_state);
                } else {
                    showTost(3);
                }
                break;
            case R.id.chose_dao2:
                if (check2(3)) {
                    dao2_state = !dao2_state;
                    setBack(view, dao2_state);
                } else {
                    showTost(3);
                }
                break;
            case R.id.chose_dao3:
                if (check2(3)) {
                    dao3_state = !dao3_state;
                    setBack(view, dao3_state);
                } else {
                    showTost(3);
                }
                break;
            case R.id.chose_dao4:
                if (check2(3)) {
                    dao4_state = !dao4_state;
                    setBack(view, dao4_state);
                } else {
                    showTost(3);
                }
                break;
            case R.id.title_item3:
                hitKeybord();
                if (checkAll()) {
                    Intent intent = new Intent(getActivity(), NumList.class);
                    intent.putExtra("item1", item1.getText().toString());
                    intent.putExtra("item2", item2.getText().toString());
                    intent.putExtra("item3", item3.getText().toString());
                    intent.putExtra("item4", item4.getText().toString());
                    intent.putExtra("item5", item5.getText().toString());
                    intent.putExtra("item6", item6.getText().toString());
                    intent.putExtra("qian", qian_state);
                    intent.putExtra("bai", bai_state);
                    intent.putExtra("shi", shi_state);
                    intent.putExtra("ge", ge_state);
                    intent.putExtra("qian2", qian2_state);
                    intent.putExtra("bai2", bai2_state);
                    intent.putExtra("shi2", shi2_state);
                    intent.putExtra("ge2", ge2_state);
                    intent.putExtra("dao1", dao1_state);
                    intent.putExtra("dao2", dao2_state);
                    intent.putExtra("dao3", dao3_state);
                    intent.putExtra("dao4", dao4_state);
                    startActivityForResult(intent, 1);
                } else {
                    showTost(2);
                }
                break;
            case R.id.chose_back:
                hitKeybord();
                break;

            case R.id.key_item12:

                break;


            default:
                writeText(view);
                break;
        }
    }

    private void showTost(int type) {
        switch (type) {
            case 1:
                Toast.makeText(getActivity(), "不可对“X”或“现”进行此操作！", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getActivity(), "“X”个数大于2或“X”与“现”同时存在！", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(getActivity(), "不可对“现”进行此操作！", Toast.LENGTH_LONG).show();
                break;
        }

    }


    private boolean checkAll() {
        boolean hasX = false;
        boolean hasZ = false;
        int numX = 0;
        for (int i = 0; i < 4; i++) {
            String data = list_item.get(i).getText().toString();
            for (int j = 0; j < data.length(); j++) {
                String str = data.substring(j, j + 1);
                if (str.equals("X")) {
                    hasX = true;
                    numX++;
                }
                if (str.equals("现")) {
                    hasZ = true;
                }
            }
        }
        if (numX < 3 && !(hasX && hasZ)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean check(int item) {
        String data = list_item.get(item).getText().toString();
        for (int i = 0; i < data.length(); i++) {
            String str = data.substring(i, i + 1);
            if (str.equals("X") || str.equals("现")) {
                return false;
            }
        }
        return true;
    }

    private boolean check2(int item) {
        String data = list_item.get(item).getText().toString();
        for (int i = 0; i < data.length(); i++) {
            String str = data.substring(i, i + 1);
            if (str.equals("现")) {
                return false;
            }
        }
        return true;
    }

    private void writeText(View view) {
        String data = now_text.getText().toString();
        String keytext = ((TextView) view).getText().toString();

        if (data.equals("X") || data.equals("现")) {
            return;
        }
        if (data.length() > 0 && (keytext.equals("X") || keytext.equals("现"))) {
            return;
        }
        if (keytext.equals("现") && now_position != 3) {
            return;
        }
        if (keytext.equals("现")) {
            dao1_state = false;
            dao2_state = false;
            dao3_state = false;
            dao4_state = false;
            setBack(dao1, false);
            setBack(dao2, false);
            setBack(dao3, false);
            setBack(dao4, false);
        }
        if ((now_position == 4 || now_position == 5) && keytext.equals("X")) {
            return;
        }
        for (int i = 0; i < data.length(); i++) {
            String str = data.substring(i, i + 1);
            if (str.equals(keytext)) {
                keytext = "";
                break;
            }
        }
        now_text.setText(data + keytext);
    }


    private void showKeybord() {
        keybord.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).bottom.setVisibility(View.GONE);
        for (int i = 0; i < list_clo.size(); i++) {
            if (now_position == i) {
                list_clo.get(i).setVisibility(View.VISIBLE);
                now_text = list_item.get(i);
                now_text.setText("");
            } else {
                list_clo.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void hitKeybord() {
        keybord.setVisibility(View.GONE);
        ((MainActivity) getActivity()).bottom.setVisibility(View.VISIBLE);
        for (int i = 0; i < list_clo.size(); i++) {
            list_clo.get(i).setVisibility(View.GONE);
        }
    }

    private void setBack(View view, boolean state) {
        if (state) {
            view.setBackgroundResource(R.drawable.textview_circle2);
        } else {
            view.setBackgroundResource(R.drawable.textview_circle);
        }
    }

    public void clearn() {
        for (int i = 0; i < list_item.size(); i++) {
            list_item.get(i).setText("");
        }
        qian_state = false;
        bai_state = false;
        shi_state = false;
        ge_state = false;
        qian2_state = false;
        bai2_state = false;
        shi2_state = false;
        ge2_state = false;
        setBack(qian, qian_state);
        setBack(bai, bai_state);
        setBack(shi, shi_state);
        setBack(ge, ge_state);
        setBack(qian2, qian2_state);
        setBack(bai2, bai2_state);
        setBack(shi2, shi2_state);
        setBack(ge2, ge2_state);
    }

    public void settitle(String title2) {
        this.title = title2;
        if (tv_title2 != null)
            tv_title2.setText(title2);
    }
}
