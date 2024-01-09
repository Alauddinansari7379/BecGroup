package com.amtechsolutions.becpbas.utils;

import com.google.gson.annotations.SerializedName;

public class CommonResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
