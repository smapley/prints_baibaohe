package com.smapley.moni.http.callback;

import android.widget.Toast;

import com.smapley.moni.LocalApplication;
import com.smapley.moni.R;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

/**
 * Created by smapley on 15/12/18.
 */
public abstract class SimpleCallback implements Callback.CommonCallback<String> {


    @Override
    public void onSuccess(String result) {
        LogUtil.d(result);
        Success(result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        Toast.makeText(LocalApplication.getInstance(), R.string.internet_err,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }


    public abstract void Success(String data);
}
