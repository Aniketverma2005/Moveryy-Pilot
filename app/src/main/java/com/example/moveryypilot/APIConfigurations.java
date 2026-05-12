package com.example.moveryypilot;

public class APIConfigurations {

    // Base URL for the Moveryy backend
    // 10.0.2.2 = localhost when running on Android Emulator
    // Change to your actual server IP/domain for physical device or production
    public static final String BASE_URL = "http://10.0.2.2:8000";

    // API version prefix
    public static final String API_PREFIX = "api/v1";

    // ─── Driver Auth Endpoints ───────────────────────────────────────────────
    public static final String SEND_OTP   = API_PREFIX + "/drivers/send-otp";
    public static final String VERIFY_OTP = API_PREFIX + "/drivers/verify-otp";
}
