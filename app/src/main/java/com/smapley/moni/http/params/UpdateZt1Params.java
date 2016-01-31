package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.http.RequestParams;

/**
 * Created by smapley on 15/11/22.
 */
public class UpdateZt1Params extends RequestParams {

    public UpdateZt1Params(String allid) {
        super(MyData.URL_updateZt1);
        addBodyParameter("allid",allid);
    }

}
