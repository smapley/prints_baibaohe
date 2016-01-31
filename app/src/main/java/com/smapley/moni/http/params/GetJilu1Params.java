package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.http.RequestParams;

/**
 * Created by smapley on 15/11/22.
 */
public class GetJilu1Params extends RequestParams {

    public GetJilu1Params(String user1) {
        super(MyData.URL_getJilu1);
        addBodyParameter("user1",user1);
    }

}
