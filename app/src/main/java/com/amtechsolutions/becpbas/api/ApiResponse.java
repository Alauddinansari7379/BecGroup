package com.amtechsolutions.becpbas.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    boolean statusCode;

//    @SerializedName("status")
//    private String success;

    @SerializedName("data")
    private JsonElement data;

    @SerializedName("message")
    private String message;
//
//    public boolean isStatusCode() {
//        return success.equals("200");
//    }

    public void setStatusCode(boolean statusCode) {
        this.statusCode = statusCode;
    }

//    public String getSuccess() {
//        setStatusCode(success.equals("200"));
//        return success;
//    }

  //  public void setSuccess(String success) {
//        this.success = success;
//    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        if (!(data instanceof JsonNull))
            this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}