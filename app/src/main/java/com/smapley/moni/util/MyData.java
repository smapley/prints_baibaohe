package com.smapley.moni.util;

import android.text.format.Time;

/**
 * Created by smapley on 2015/5/20.
 */
public class MyData {

    public static String UserName;
    public static String PassWord;
    public static boolean Login = false;

    public static final String SP_USER = "user";
    public static final String SP_TABLE = "table";
    private static final String BASE_URL = "http://120.25.208.188/moni/";
    public static final String URL_UPDATEEDU = BASE_URL + "updateEdu.php";
    public static final String URL_ADDDATA = BASE_URL + "addData.php";
    public static final String URL_INDEX1 = BASE_URL + "xiazhuG1.php";
    public static final String URL_TUIMA1 = BASE_URL + "tuimaG2.php";
    public static final String URL_TUIMA3 = BASE_URL + "tuimaG3.php";
    public static final String URL_GETJILU1 = BASE_URL + "getJilu1.php";
    public static final String URL_GETJILU2 = BASE_URL + "getJilu2.php";
    public static final String URL_updateZt1 = BASE_URL + "updateZt1.php";
    public static final String URL_getSoudj = BASE_URL + "getSoudj.php";
    public static final String URL_updateZt2 = BASE_URL + "updateZt2.php";
    public static final String URL_reg = BASE_URL + "reg.php";
    public static final String URL_GETZILIAO = BASE_URL + "getZiliao.php";
    public static final String URL_UPDATAZILIAO = BASE_URL + "updateZiliao1.php";
    public static final String URL_GETMINGXI = BASE_URL + "getMingxi.php";
    public static final String URL_GETJIANG = BASE_URL + "getJiang.php";
    public static final String URL_GENGXIN = BASE_URL + "gengxin.php";
    public static final String URL_Reg2 = BASE_URL + "reg2.php";
    public static final String URL_GETZHANGDAN = BASE_URL + "getZhangdan.php";
    public static final String URL_XIAZAI = "http://www.newera98.com/dayin1.apk";
    public static final String URL_DELTINGYA =BASE_URL+"delTingya.php";
    public static final String URL_GETPEILV =BASE_URL+"getPeilv.php";


    public final static String URL_sendSMS=BASE_URL+"sendSMS.php";
    public final static String URL_reg1=BASE_URL+"reg1.php";
    public final static String URL_geijiangjilu=BASE_URL+"getJiangjilu.php";
    public final static String URL_getZhuangJ=BASE_URL+"getZhuangJ.php";
    public final static String URL_getJin=BASE_URL+"getJin.php";
    public final static String URL_addZhuang=BASE_URL+"addZhuang.php";
    public final static String URL_addjiang=BASE_URL+"addJiang.php";
    public final static String URL_getJilu1=BASE_URL+"getJilu1.php";
    public final static String URL_getZhuang=BASE_URL+"getZhuang.php";
    public final static String URL_xiazhu=BASE_URL+"xiazhu.php";
    public static final String URL_tuimaG3 = BASE_URL + "tuimaG3.php";
    public static final String URL_getHezhuang = BASE_URL + "getHezhuang.php";
    public static final String URL_addHezhuang = BASE_URL + "addHezhuang.php";

    /**
     * 获取服务器加密码
     * key
     *
     * @return
     */
    public static int getKey() {
        int key = 0;
        key = 1 + (int) (Math.random() * 999);
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        int date = t.monthDay;
        return key * 789 * date;
    }


}
