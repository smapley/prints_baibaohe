package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.http.RequestParams;

/**
 * Created by smapley on 16/1/25.
 */
public class XiaZhuParams extends RequestParams {

    public XiaZhuParams(String user1, String mi, String number, String money, String tag) {
        super(MyData.URL_xiazhu);
        addBodyParameter("user1", user1);
        addBodyParameter("mi", mi);
        addBodyParameter("number", number);
        addBodyParameter("money", money);
        int sizixian = 0;
        int zhuan = 0;
        if (tag.equals("现")) {
            sizixian = 1;
        } else if (tag.equals("倒")) {
            zhuan = 1;
        }

        addBodyParameter("sizixian", sizixian + "");
        addBodyParameter("zhuan", zhuan + "");
    }
}
