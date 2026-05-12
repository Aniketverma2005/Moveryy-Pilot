package com.example.moveryypilot.model;

import com.google.gson.annotations.SerializedName;

public class VerifyOtpResponse {

    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    public int getStatusCode() { return statusCode; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Data getData() { return data; }

    public static class Data {
        @SerializedName("driver")
        private Driver driver;

        @SerializedName("accessToken")
        private String accessToken;

        @SerializedName("refreshToken")
        private String refreshToken;

        @SerializedName("nextStep")
        private String nextStep;

        public Driver getDriver() { return driver; }
        public String getAccessToken() { return accessToken; }
        public String getRefreshToken() { return refreshToken; }
        public String getNextStep() { return nextStep; }
    }

    public static class Driver {
        @SerializedName("driverId")
        private int driverId;

        @SerializedName("email")
        private String email;

        @SerializedName("fullName")
        private String fullName;

        @SerializedName("phone")
        private String phone;

        @SerializedName("status")
        private String status;

        @SerializedName("isProfileComplete")
        private boolean isProfileComplete;

        public int getDriverId() { return driverId; }
        public String getEmail() { return email; }
        public String getFullName() { return fullName; }
        public String getPhone() { return phone; }
        public String getStatus() { return status; }
        public boolean isProfileComplete() { return isProfileComplete; }
    }
}
