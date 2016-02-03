package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

/**
 * Created by smapley on 16/1/25.
 */
public class AddHezhuangParams extends RequestParams {

    public AddHezhuangParams(String user1, String mi, String item1, String item2,String item3,String item4,String item5,String item6) {
        super(MyData.URL_addHezhuang);
        addBodyParameter("user1", user1);
        addBodyParameter("mi", mi);
        addBodyParameter("erdj", item1);
        addBodyParameter("sandj", item2);
        addBodyParameter("sidj", item3);
        addBodyParameter("erxj", item4);
        addBodyParameter("sanxj", item5);
        addBodyParameter("sixj", item6);
        try {
            LogUtil.d(toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
