package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

/**
 * Created by smapley on 16/1/25.
 */
public class ReggaimiParams extends RequestParams {

    public ReggaimiParams(String user1, String oldmi,String newmi1,String newmi2) {
        super(MyData.URL_reggaimi);
        addBodyParameter("user1", user1);
        addBodyParameter("oldmi", oldmi);
        addBodyParameter("newmi1", newmi1);
        addBodyParameter("newmi2", newmi2);
        try {
            LogUtil.d(toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
