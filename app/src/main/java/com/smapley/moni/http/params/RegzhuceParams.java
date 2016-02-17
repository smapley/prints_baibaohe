package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

/**
 * Created by smapley on 16/1/25.
 */
public class RegzhuceParams extends RequestParams {

    public RegzhuceParams(String phone, String mi) {
        super(MyData.URL_regzhuce);
        addBodyParameter("phone", phone);
        addBodyParameter("mi", mi);
        try {
            LogUtil.d(toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
