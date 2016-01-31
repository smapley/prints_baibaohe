package com.smapley.moni.http.params;


import com.smapley.moni.mode.ZhuangMode;
import com.smapley.moni.util.MyData;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

/**
 * Created by smapley on 16/1/25.
 */
public class AddZhuangParams extends RequestParams {

    public AddZhuangParams(ZhuangMode mode){
        super(MyData.URL_addZhuang);
        LogUtil.d(mode.toString());

        addBodyParameter("user1", mode.getUser1());
        addBodyParameter("mi", mode.getMi());
        addBodyParameter("biaoti", mode.getBiaoti());
        addBodyParameter("type", mode.getType());
        addBodyParameter("dan", mode.getDan());
        addBodyParameter("pei", mode.getPei());
        addBodyParameter("fjmi", mode.getFjmi());
    }
}
