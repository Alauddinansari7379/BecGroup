package com.amtechsolutions.becpbas.api.service;

import static com.amtechsolutions.becpbas.utils.AppConstants.token;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amtechsolutions.becpbas.BaseActivity;
import com.amtechsolutions.becpbas.R;
import com.amtechsolutions.becpbas.api.ApiResponse;
import com.amtechsolutions.becpbas.api.request.LoginRequest;
import com.amtechsolutions.becpbas.network.ApiInterface;
import com.amtechsolutions.becpbas.newdataModel.SuperData;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainService {

    private static final ApiInterface apiService = BaseService.getAPIClient("default").create(ApiInterface.class);

    public MainService() {

    }

    public static ApiResponse customResponse(Response<ApiResponse> response) {
        ApiResponse apiResponse = new ApiResponse();
        //   apiResponse.setStatusCode(response.body().getSuccess().equals("200"));
        apiResponse.setData(response.body().getData());
        apiResponse.setMessage(response.body().getMessage());
        //     apiResponse.setSuccess(String.valueOf(response.body().getSuccess()));
        return apiResponse;
    }

    public static ApiResponse tokenExpiredResponse(String msg, String code) {
        ApiResponse apiResponse = new ApiResponse();
        //   apiResponse.setSuccess(code);
        apiResponse.setData(null);
        apiResponse.setMessage(msg);
        apiResponse.setStatusCode(false);
        return apiResponse;
    }

    public static LiveData<ApiResponse> officeUpload(final Context context, SuperData postParam) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("x-auth-token", token);
        Call<ApiResponse> call = apiService.upcomingOrders(headers,postParam);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).showToast("hgfdhgfhgfdh");
            }
        });
        return data;
    }

    public static LiveData<ApiResponse> clockIn(final Context context,  Map<String, String> postParam ) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("x-auth-token", token);
        Call<ApiResponse> call = apiService.clockIn(headers,postParam);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() != null) {
                    data.setValue(customResponse(response));
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ((BaseActivity) context).showToast("hgfdhgfhgfdh");
            }
        });
        return data;
    }

}
