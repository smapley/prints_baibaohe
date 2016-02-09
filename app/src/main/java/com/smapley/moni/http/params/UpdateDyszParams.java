package com.smapley.moni.http.params;


import com.smapley.moni.util.MyData;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

/**
 * Created by smapley on 16/1/25.
 */
public class UpdateDyszParams extends RequestParams {

    public UpdateDyszParams(String user1, String mi,String ming,String beizhu,String erdpei,String sandpei,
                            String sidpei,String erxpei,String sanxpei,String sixpei) {
        super(MyData.URL_updateDysz);
        addBodyParameter("user1", user1);
        addBodyParameter("mi", mi);
        addBodyParameter("ming", ming);
        addBodyParameter("beizhu", beizhu);
        addBodyParameter("erdpei", erdpei);
        addBodyParameter("sandpei", sandpei);
        addBodyParameter("sidpei", sidpei);
        addBodyParameter("erxpei", erxpei);
        addBodyParameter("sanxpei", sanxpei);
        addBodyParameter("sixpei", sixpei);
        try {
            LogUtil.d(toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
