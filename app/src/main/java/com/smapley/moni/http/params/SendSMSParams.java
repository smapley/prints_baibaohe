package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.http.RequestParams;

/**
 * Created by smapley on 15/11/22.
 */
public class SendSMSParams extends RequestParams {

    public SendSMSParams(String phone) {
        super(MyData.URL_sendSMS);
        addBodyParameter("phone",phone);
    }

}
