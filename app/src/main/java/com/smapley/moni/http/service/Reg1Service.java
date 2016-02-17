package com.smapley.moni.http.service;


import com.smapley.moni.http.callback.SimpleCallback;
import com.smapley.moni.http.params.Reg1Params;

import org.xutils.x;

/**
 * Created by smapley on 15/12/18.
 */
public abstract class Reg1Service {

    public void load(Reg1Params params) {
        x.http().post(params, new SimpleCallback() {
            @Override
            public void Success(final String data) {
                Succ(data);

            }
        });
    }


    public abstract void Succ(String data);
}