package com.smapley.prints2.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lvrenyang.rwbt.BTHeartBeatThread;
import com.lvrenyang.rwusb.USBHeartBeatThread;
import com.lvrenyang.rwwifi.NETHeartBeatThread;
import com.lvrenyang.utils.DataUtils;
import com.lvrenyang.utils.FileUtils;
import com.smapley.prints2.R;
import com.smapley.prints2.adapter.Main_Viewpage_Adapter;
import com.smapley.prints2.fragment.Chose;
import com.smapley.prints2.fragment.Data;
import com.smapley.prints2.fragment.Print;
import com.smapley.prints2.fragment.Set;
import com.smapley.prints2.print.Global;
import com.smapley.prints2.print.WorkService;
import com.smapley.prints2.util.CustomViewPager;
import com.smapley.prints2.util.HttpUtils;
import com.smapley.prints2.util.MyData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends FragmentActivity implements View.OnClickListener {


    private TextView bottom_item1;
    private TextView bottom_item2;
    private TextView bottom_item3;
    private TextView bottom_item4;

    private CustomViewPager viewPager;
    public View bottom;

    private Main_Viewpage_Adapter pageViewAdapter;
    public Print print;
    public Set set;
    private Data data;
    private Chose chose;
    private List<Fragment> fragmentList;


    private static Handler mHandler = null;
    private static String TAG = "MainActivity";
    private final int UPDATA = 2;
    private final int UPDATA2 = 1;
    private final int UPDATA3 = 5;
    private final int CONNECTBT = 10;
    private final int CANUPDATA = 11;
    private static final int PRINT = 3;
    public static Dialog dialog;
    private Map map;
    private String allidString;
    private SharedPreferences sp_user;

    private static String title2;
    public static int position = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        position = 1;


        sp_user = getSharedPreferences("user", MODE_PRIVATE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_item1);
        builder.setMessage(R.string.dialog_item2);
        dialog = builder.create();

        initView();
        initViewPage();
        initPrint();

    }

    private void initView() {

        bottom_item1 = (TextView) findViewById(R.id.bottom_item1);
        bottom_item2 = (TextView) findViewById(R.id.bottom_item2);
        bottom_item3 = (TextView) findViewById(R.id.bottom_item3);
        bottom_item4 = (TextView) findViewById(R.id.bottom_item4);

        viewPager = (CustomViewPager) findViewById(R.id.fragment);
        bottom = findViewById(R.id.main_bottom);


        bottom_item1.setOnClickListener(this);
        bottom_item2.setOnClickListener(this);
        bottom_item3.setOnClickListener(this);
        bottom_item4.setOnClickListener(this);

    }

    private void initViewPage() {
        fragmentList = new ArrayList<>();
        set = new Set();
        chose = new Chose();
        data = new Data();
        print = new Print();
        fragmentList.add(print);
        fragmentList.add(chose);
        fragmentList.add(data);
        fragmentList.add(set);
        pageViewAdapter = new Main_Viewpage_Adapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pageViewAdapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_item1:
                viewPagerGo(0);
                position = 1;
                break;
            case R.id.bottom_item2:
                viewPagerGo(1);
                position = 2;
                break;
            case R.id.bottom_item3:
                viewPagerGo(2);
                position = 3;
                break;
            case R.id.bottom_item4:
                viewPagerGo(3);
                position = 4;
                break;
        }
    }

    public void viewPagerGo(int num) {
        switch (num) {
            case 0:
                print.getData();
                break;

            case 2:

                break;

            case 1:
                chose.clearn();
                chose.settitle(title2);
                break;
            case 3:
                set.settitle(title2);
                break;
        }

        viewPager.setCurrentItem(num);
    }
    private void changeTitle(String title2) {
        chose.settitle(title2);
        set.settitle(title2);
    }
    private void initPrint() {
        // 初始化字符串资源
        InitGlobalString();
        mHandler = new MHandler(MainActivity.this);
        WorkService.addHandler(mHandler);

        if (null == WorkService.workThread) {
            Intent intent = new Intent(this, WorkService.class);
            startService(intent);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (WorkService.workThread == null) {
                }
                mhandler.obtainMessage(CONNECTBT).sendToTarget();
            }
        }).start();
    }

    /**
     * 连接打印机
     */
    public void connectBT() {
        try {
            String address = sp_user.getString("address", "");
            if (address != null && !address.isEmpty() ) {
                if(!WorkService.workThread.isConnected()) {
                    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                    if (null != adapter) {
                        if (adapter.isEnabled()) {
                            WorkService.workThread.connectBt(address);
                            Toast.makeText(MainActivity.this, "正在连接打印机", Toast.LENGTH_SHORT).show();
                        } else {
                            if (adapter.enable()) {
                                while (!adapter.isEnabled())
                                    ;
                                connectBT();
                                Log.v(TAG, "Enable BluetoothAdapter");
                            }
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "蓝牙功能不可用！", Toast.LENGTH_SHORT).show();
                    }
                }

            }else{
                Toast.makeText(MainActivity.this, "请先手动连接一次打印机！", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "-------异常----------", Toast.LENGTH_SHORT).show();
        }
    }

    private void InitGlobalString() {
        Global.toast_success = getString(R.string.toast_success);
        Global.toast_fail = getString(R.string.toast_fail);
        Global.toast_notconnect = getString(R.string.toast_notconnect);
        Global.toast_usbpermit = getString(R.string.toast_usbpermit);
    }


    private void upData2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("allid", allidString);
                map.put("user1", MyData.UserName);
                mhandler.obtainMessage(UPDATA3, HttpUtils.updata(map, MyData.URL_updateZt1)).sendToTarget();
            }
        }).start();
    }


    private void upData(boolean isData, final String url) {
        if (isData) {
            dialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    HashMap map = new HashMap();
                    map.put("user1", MyData.UserName);
                    mhandler.obtainMessage(UPDATA, HttpUtils.updata(map, url)).sendToTarget();
                }
            }).start();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("提示：");
            builder.setMessage("没有可打印信息！");
            builder.setPositiveButton("确定", null);
            builder.create().show();
        }
    }

    public Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.dialog_item1);
            try {
                switch (msg.what) {
                    case CANUPDATA:
                        if (print.dataList.size() > 1) {
                            upData(true, MyData.URL_GETJILU1);
                        } else {
                            upData(false, MyData.URL_GETJILU1);

                        }
                        break;
                    case CONNECTBT:
                        connectBT();
                        break;
                    case UPDATA3:
                        Map resultmap = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(resultmap.get("newid").toString()) > 0) {
                            print.getData();
                        }
                        break;

                    case UPDATA2:
                        dialog.dismiss();
                        Map result = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(result.get("newid").toString()) > 0) {
                            Toast.makeText(MainActivity.this, "更新数据成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "没有数据需要更新！", Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case UPDATA:
                        dialog.dismiss();

                        map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map.get("count").toString()) > 0) {

                            mhandler.obtainMessage(PRINT).sendToTarget();

                        } else {
                            builder.setMessage(R.string.dialog_item3);
                            builder.setPositiveButton(R.string.dialog_item7, null);
                            dialog = builder.create();
                            dialog.show();
                        }
                        break;

                    case PRINT:
                        builder.setMessage(R.string.dialog_item4);
                        dialog = builder.create();
                        dialog.show();
                        allidString = map.get("allid").toString();
                        String allid = "编号：" + allidString;
                        String riqi = "日期：" + map.get("riqi").toString();
                        String name = "会员：" + map.get("ming").toString();
                        String qihao = "第" + print.qishu + "期，3天内有效！！";
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
                            Log.i(TAG, "1");
                        }
                        Log.i(TAG, "1");

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
                        Log.i(TAG, "1");


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
                            Toast.makeText(MainActivity.this, Global.toast_notconnect, Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    static class MHandler extends Handler {

        WeakReference<MainActivity> mActivity;

        MHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MainActivity theActivity = mActivity.get();
            switch (msg.what) {
                case Global.CMD_POS_WRITERESULT: {
                    try {
                        dialog.dismiss();
                        Detail.dialog.dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    int result = msg.arg1;
                    if (result == 1) {
                        Toast toast = Toast.makeText(theActivity, R.string.dialog_item8, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        theActivity.upData2();
                    } else {
                        Toast toast = Toast.makeText(theActivity, R.string.dialog_item9, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                    Log.v(TAG, "Result: " + result);
                    break;
                }
                /**
                 * DrawerService 的 onStartCommand会发送这个消息
                 */
                case Global.MSG_ALLTHREAD_READY: {
                    Log.v("MHandler", "MSG_ALLTHREAD_READY");
                    if (WorkService.workThread.isConnected()) {
                        title2 = theActivity.getString(R.string.print1);
                    } else {
                        title2 = theActivity.getString(R.string.print0);
                    }
                    theActivity.changeTitle(title2);
                    FileUtils.AddToFile("MHandler MSG_ALLTHREAD_READY\r\n",
                            FileUtils.sdcard_dump_txt);
                    break;
                }

                case BTHeartBeatThread.MSG_BTHEARTBEATTHREAD_UPDATESTATUS:
                case NETHeartBeatThread.MSG_NETHEARTBEATTHREAD_UPDATESTATUS:
                case USBHeartBeatThread.MSG_USBHEARTBEATTHREAD_UPDATESTATUS: {
                    int statusOK = msg.arg1;
                    int status = msg.arg2;
                    Log.v(TAG,
                            "statusOK: " + statusOK + " status: "
                                    + DataUtils.byteToStr((byte) status));
                    if (statusOK == 1) {
                        title2 = theActivity.getString(R.string.print1);
                    } else {
                        title2 = theActivity.getString(R.string.print0);
                    }
                    theActivity.changeTitle(title2);
                    FileUtils.DebugAddToFile("statusOK: " + statusOK + " status: "
                                    + DataUtils.byteToStr((byte) status) + "\r\n",
                            FileUtils.sdcard_dump_txt);
                    break;
                }

                case Global.CMD_POS_PRINTPICTURERESULT:
                case Global.CMD_POS_WRITE_BT_FLOWCONTROL_RESULT: {
                    int result = msg.arg1;
                    Log.v(TAG, "Result: " + result);
                    break;
                }
            }
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkService.delHandler(mHandler);
        mHandler = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            viewPagerGo(0);
            position = 1;
            String result = data.getStringExtra("data");
            print.mhandler.obtainMessage(-1, result).sendToTarget();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
