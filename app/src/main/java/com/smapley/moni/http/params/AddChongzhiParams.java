package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

/**
 * Created by smapley on 16/1/25.
 */
public class AddChongzhiParams extends RequestParams {

    public AddChongzhiParams(String user1, String gold) {
        super(MyData.URL_addChongzhi);
        addBodyParameter("user1", user1);
        addBodyParameter("gold", gold);
        try {
            LogUtil.d(toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
