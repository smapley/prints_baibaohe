package com.smapley.moni.http.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.moni.http.callback.SimpleCallback;
import com.smapley.moni.http.params.GeijiangjiluParams;
import com.smapley.moni.mode.GaiJiangMode;

import org.xutils.x;

import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public abstract class GeiJiangjiluService {

    public void load() {

        x.http().post(new GeijiangjiluParams(),new SimpleCallback() {
            @Override
            public void Success(final String data) {
                List<GaiJiangMode> result= JSON.parseObject(data, new TypeReference<List<GaiJiangMode>>() {
                });
                Succ(result);
            }
        });
    }


    public abstract void Succ(List<GaiJiangMode> data);
}