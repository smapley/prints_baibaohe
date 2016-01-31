package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.http.RequestParams;

import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 16/1/25.
 */
public class TuimaG3Params extends RequestParams {

    public TuimaG3Params(String user1, String mi,List<Map<String,String>> datalist) {
        super(MyData.URL_tuimaG3);
        addBodyParameter("user1", user1);
        addBodyParameter("mi", mi);
        String data = "";
        for (Map map1 : datalist) {
            data = data + map1.get("id").toString() + "," + map1.get("biaoshi").toString() + ",";
        }
        addBodyParameter("tuima", data.substring(0, data.length() - 1));
    }
}
