package com.amtechsolutions.becpbas.network;


import com.amtechsolutions.becpbas.api.ApiResponse;
import com.amtechsolutions.becpbas.newdataModel.SuperData;
import com.amtechsolutions.becpbas.utils.CommonResponse;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("syncServiceReqMob/saveorUpdateSyncServiceRequest?lastSynDate=12-Mar-2022")
    Call<ApiResponse> upcomingOrders(@HeaderMap Map<String, String> headers, @Body SuperData postParam);

    @POST("supervisorReqMob/saveorUpdateSupervisorRequest")
    Call<ApiResponse> clockIn(@HeaderMap Map<String, String> headers, @Body  Map<String, String> postParam);

}
