package com.amtechsolutions.becpbas.api.service;

import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

public class SellAchaApplication extends MultiDexApplication {
    private static final String TAG = "THIS";
    private static SellAchaApplication instance;

    public static SellAchaApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


    }

}
