package com.amtechsolutions.becpbas.utils;

import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

/**
 * Created by Shourav Paul on 17-03-2022.
 **/
public class CustomStringRequest extends Request {
    public CustomStringRequest(String url, Response.ErrorListener listener) {
        super(url, listener);
    }

    public CustomStringRequest(int method, String url, @Nullable Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(Object response) {

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
