package com.example.moveryypilot.model;

import com.google.gson.annotations.SerializedName;

public class SendOtpResponse {

    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public int getStatusCode() { return statusCode; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
