package com.smapley.moni.http.service;

import com.smapley.moni.http.callback.SimpleCallback;
import com.smapley.moni.http.params.UpdateZt1Params;

import org.xutils.x;


/**
 * Created by smapley on 15/12/18.
 */
public abstract class UpdateZt1Service {

    public void load(UpdateZt1Params params) {

        x.http().post(params,new SimpleCallback() {
            @Override
            public void Success(final String data) {
                Succ(data);
            }
        });
    }


    public abstract void Succ(String data);
}