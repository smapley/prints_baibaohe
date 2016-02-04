package com.smapley.moni.http.service;


import com.smapley.moni.http.callback.SimpleCallback;
import com.smapley.moni.http.params.FangjianmimaParams;

import org.xutils.x;

/**
 * Created by smapley on 15/12/18.
 */
public abstract class FangjianmimaService {

    public void load(FangjianmimaParams params) {

        x.http().post(params,new SimpleCallback() {
            @Override
            public void Success(final String data) {
                Succ(data);
            }
        });
    }


    public abstract void Succ(String data);
}