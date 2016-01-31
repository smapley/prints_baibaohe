package com.smapley.moni.http.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.http.callback.SimpleCallback;
import com.smapley.moni.http.params.GetzhuangJParams;
import com.smapley.moni.mode.GetZhuangJMode;

import org.xutils.x;

import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public abstract class GetZhuangJService {

    public void load(GetzhuangJParams params) {
        x.http().post(params, new SimpleCallback() {
            @Override
            public void Success(final String data) {
                List<GetZhuangJMode> result= JSON.parseObject(data, new TypeReference<List<GetZhuangJMode>>() {
                });
                Succ(result);
            }
        });
    }


    public abstract void Succ(List<GetZhuangJMode> data);
}