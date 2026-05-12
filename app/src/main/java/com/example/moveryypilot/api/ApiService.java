package com.example.moveryypilot.api;

import com.example.moveryypilot.model.SendOtpRequest;
import com.example.moveryypilot.model.SendOtpResponse;
import com.example.moveryypilot.model.VerifyOtpRequest;
import com.example.moveryypilot.model.VerifyOtpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // ─── Auth ────────────────────────────────────────────────────────────────

    /**
     * Send OTP to driver's email
     * POST /api/v1/drivers/send-otp
     * Body: { "email": "..." }
     */
    @POST("api/v1/drivers/send-otp")
    Call<SendOtpResponse> sendOtp(@Body SendOtpRequest request);

    /**
     * Verify OTP and login/register driver
     * POST /api/v1/drivers/verify-otp
     * Body: { "email": "...", "otp": "..." }
     * Response: { statusCode, success, message, data: { driver, accessToken, refreshToken, nextStep } }
     */
    @POST("api/v1/drivers/verify-otp")
    Call<VerifyOtpResponse> verifyOtp(@Body VerifyOtpRequest request);
}
