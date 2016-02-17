package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

/**
 * Created by smapley on 16/1/25.
 */
public class GetZhangdanHZParams extends RequestParams {

    public GetZhangdanHZParams(String user1) {
        super(MyData.URL_getZhangdanHZ);
        addBodyParameter("user1", user1);
        try {
            LogUtil.d(toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
