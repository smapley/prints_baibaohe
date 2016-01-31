package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

/**
 * Created by smapley on 16/1/25.
 */
public class AddZhuangParams extends RequestParams {

    public AddZhuangParams(String user1,String biaoti, String type, String dan, String pei,String mima) {
        super(MyData.URL_addZhuang);
        addBodyParameter("user1",user1);
        addBodyParameter("biaoti", biaoti);
        addBodyParameter("type", type);
        addBodyParameter("dan", dan);
        addBodyParameter("pei", pei);
        addBodyParameter("mi", mima);

        LogUtil.d(toJSONString());
    }
}
