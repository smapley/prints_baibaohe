package com.smapley.moni.fragment;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.sdk.app.PayTask;
import com.smapley.moni.R;
import com.smapley.moni.activity.Gaimi;
import com.smapley.moni.activity.KaiJiangNum;
import com.smapley.moni.activity.Login;
import com.smapley.moni.activity.MyHeZhuang;
import com.smapley.moni.activity.MyJingCai;
import com.smapley.moni.http.params.AddChongzhiParams;
import com.smapley.moni.http.params.GetJinParams;
import com.smapley.moni.http.service.AddChongzhiService;
import com.smapley.moni.http.service.GetJinService;
import com.smapley.moni.pay.PayResult;
import com.smapley.moni.pay.SignUtils;
import com.smapley.moni.util.HttpUtils;
import com.smapley.moni.util.MyData;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by smapley on 15/10/23.
 */
public class Set extends Fragment {


    private static final int LOGOUT = 2;
    private TextView tv_title2;
    private TextView tv_title3;

    private static final int GETVERSION = 1;
    private TextView item0;
    private TextView item2;
    private TextView item4;
    private TextView item6;
    private TextView item7;
    private TextView item8;
    private TextView item10;
    private TextView menu1;


    // 商户PID
    public static final String PARTNER = "2088021078637071";
    // 商户收款账号
    public static final String SELLER = "316344445@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJbSGOfIspREM+Bd3MBt9Xxs1egAJFpWMFlBVLuTJaTiiLlg7FwqFOc3sZ4R5faN4BjjuMpqZlMkYlCq2tsqCPyB2LOJEYWCcVKfYICUGDgpxkspt9UB0lDNSGIW64sIKmvcFOS349wv0GRIL+Jp5rFDLDvjZ1f41kWKKVzUfhgVAgMBAAECgYEAiGmG9T3lp4z4jtrWq4XJH70gzDI0rzB9kn0wsmepCLWMjH9JySKWvXr2P85YfORd6KUvooUR/+lMs0GVqd0fOjjfzaE2CXoW2mKd2pkb++KUQ3u1Yc5SVkVwItrPUWOTTHQqBav01OoAceE/FhL9BihaPQ9e2iy6NwxvvXpeOBECQQDx4ju6K8/7XV1yDJGlgoeuJQBAIqYvpTb5AyHzErmIfAqt8BGwp56/TxVu7WyNHAx5HMcchvmpx2OSQ27HzNU/AkEAn59fkRsLBYw1MTsjZqcmdf9qdPjRugZiga9eztOijLz6GhgRPqShOcEfcD1vr7Csm+ZIkBjwD5IE4x8VwYeZqwJAJJGDXh4Jj4MKAZgM3Ozi/lzxsMCMR1++896ZX1pRWmUGaE2HHyH4Sgv2vZJ/esXmzNig8ZsmW5idYRt4wBQjmQJATeVKj9dwo35unt3LQtcjL8Y7P2YFgxCGld7tF2W0F5ZJPt6r27Qfcb3LB80TaduAAHx6wMdKr26EsAmFZnI0DQJAcJsIx7jpl8PemwuI73O8h8alhAIvJ39wlr4R5k/qYLfgdCOmD/as/1RDKjaEhcTswwWTERTDfAqH96fjPFkq9Q==";
    // 支付宝公钥
//	public static final String RSA_PUBLIC = "";

    private static final int SDK_PAY_FLAG = 3;

    private static final int ADDCHONGZHI = 4;
    private String id;
    private String price;


    private ProgressDialog dialog;
    private String title = "";

    private ProgressDialog progressDialog;

    private GetJinService getJinService = new GetJinService() {
        @Override
        public void Succ(String data) {
            Map<String, String> map = JSON.parseObject(data, new TypeReference<Map<String, String>>() {
            });
            item0.setText("￥" + map.get("gold").toString());
        }
    };
    private AddChongzhiService addChongzhiService = new AddChongzhiService() {
        @Override
        public void Succ(String data) {
            progressDialog.dismiss();
            Message msg = new Message();
            msg.what = ADDCHONGZHI;
            msg.obj = data;
            mhandler.handleMessage(msg);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set, container, false);
        initView(view);
        progressDialog = new ProgressDialog(getActivity());
        getJinService.load(new GetJinParams(MyData.UserName));
        return view;
    }

    private void initView(View view) {

        tv_title2 = (TextView) view.findViewById(R.id.title_item2);
        tv_title2.setText("设置");

        tv_title3 = (TextView) view.findViewById(R.id.title_item3);
        tv_title3.setText("充值");

        tv_title3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请输入充值元宝数量：");
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.layout_inputtext, null, false);
                final EditText editText = (EditText) view1.findViewById(R.id.editext);
                builder.setView(view1);
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final String num = editText.getText().toString();
                        if (num != null) {
                            price = num;
                            addChongzhiService.load(new AddChongzhiParams(MyData.UserName, num));
                            progressDialog.show();
                        }
                    }
                });
                builder.create().show();
            }
        });


        item0 = (TextView) view.findViewById(R.id.set_item0);
        item2 = (TextView) view.findViewById(R.id.set_item2);
        item4 = (TextView) view.findViewById(R.id.set_item4);
        item6 = (TextView) view.findViewById(R.id.set_item6);
        item7 = (TextView) view.findViewById(R.id.set_item7);
        item8 = (TextView) view.findViewById(R.id.set_item8);
        item10 = (TextView) view.findViewById(R.id.set_item10);

        item10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Gaimi.class));
            }
        });


        item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyHeZhuang.class));
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
                                map.put("user1", MyData.UserName);
                                mhandler.obtainMessage(LOGOUT, HttpUtils.updata(map, MyData.URL_Reg2)).sendToTarget();
                            }
                        }).start();

                    }
                });
                builder.create().show();

            }
        });
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyJingCai.class));
            }
        });
        item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), KaiJiangNum.class));
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
                        editor.putBoolean("islogin", false);
                        editor.commit();
                        MyData.Login = false;
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

                    case ADDCHONGZHI:
                        progressDialog.dismiss();
                        Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map.get("newid").toString()) > 0) {
                            id = map.get("dingdan").toString();
                            // 订单
                            String orderInfo = getOrderInfo(getString(R.string.app_name), "0", price, id);
                            // 对订单做RSA 签名
                            String sign = sign(orderInfo);
                            try {
                                // 仅需对sign 做URL编码
                                sign = URLEncoder.encode(sign, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            // 完整的符合支付宝参数规范的订单信息
                            final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                                    + getSignType();

                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    // 构造PayTask 对象
                                    PayTask alipay = new PayTask(getActivity());
                                    // 调用支付接口，获取支付结果
                                    String result = alipay.pay(payInfo);

                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mhandler.sendMessage(msg);
                                }
                            };

                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }
                        break;

                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((String) msg.obj);

                        Log.i("qianbao", payResult.toString());
                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        String resultInfo = payResult.getResult();

                        String resultStatus = payResult.getResultStatus();

                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            getJinService.load(new GetJinParams(MyData.UserName));
                            Toast.makeText(getActivity(), "支付成功！",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                Toast.makeText(getActivity(), "支付结果确认中。。。",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                Toast.makeText(getActivity(), "支付失败！",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                        break;
                    }
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


    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price, String id) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + id + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + MyData.URL_ADDGOLD
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    public void getData() {
        getJinService.load(new GetJinParams(MyData.UserName));
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}
