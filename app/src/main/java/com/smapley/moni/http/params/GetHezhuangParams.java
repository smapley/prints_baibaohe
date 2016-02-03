package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.http.RequestParams;

/**
 * Created by smapley on 16/1/25.
 */
public class GetHezhuangParams extends RequestParams {

    public GetHezhuangParams(String user1, String mi) {
        super(MyData.URL_getHezhuang);
        addBodyParameter("user1", user1);
        addBodyParameter("mi", mi);
    }
}
