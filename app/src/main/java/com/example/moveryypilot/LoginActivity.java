package com.example.moveryypilot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moveryypilot.api.ApiClient;
import com.example.moveryypilot.api.ApiService;
import com.example.moveryypilot.model.SendOtpRequest;
import com.example.moveryypilot.model.SendOtpResponse;
import com.example.moveryypilot.model.VerifyOtpRequest;
import com.example.moveryypilot.model.VerifyOtpResponse;
import com.example.moveryypilot.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ImageButton backButton;
    private LinearLayout helpButton, backToEmailLayout;
    private EditText emailEditText;
    private EditText otp1, otp2, otp3, otp4, otp5, otp6;
    private MaterialButton loginButton, changeEmailButton;
    private TextView resendOtpText, otpSentToText;
    private LinearLayout emailSection, otpSection;

    private boolean isOtpStep = false;
    private String currentEmail = "";

    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiService     = ApiClient.getApiService();
        sessionManager = new SessionManager(this);

        // If already logged in, skip to appropriate screen
        if (sessionManager.isLoggedIn()) {
            navigateAfterLogin(sessionManager.isProfileComplete());
            return;
        }

        initializeViews();
        setupOtpAutoFocus();
        setupClickListeners();
    }

    private void initializeViews() {
        backButton        = findViewById(R.id.backButton);
        helpButton        = findViewById(R.id.helpButton);
        emailEditText     = findViewById(R.id.emailEditText);
        loginButton       = findViewById(R.id.loginButton);
        changeEmailButton = findViewById(R.id.changeEmailButton);
        resendOtpText     = findViewById(R.id.resendOtpText);
        otpSentToText     = findViewById(R.id.otpSentToText);
        emailSection      = findViewById(R.id.emailSection);
        otpSection        = findViewById(R.id.otpSection);
        backToEmailLayout = findViewById(R.id.backToEmailLayout);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);

        // Set hint color programmatically
        emailEditText.setHintTextColor(0xFFBBBBBB);
    }

    private void setupOtpAutoFocus() {
        setupOtpField(otp1, null, otp2);
        setupOtpField(otp2, otp1, otp3);
        setupOtpField(otp3, otp2, otp4);
        setupOtpField(otp4, otp3, otp5);
        setupOtpField(otp5, otp4, otp6);
        setupOtpField(otp6, otp5, null);
    }

    private void setupOtpField(EditText current, EditText prev, EditText next) {
        current.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    current.setBackgroundResource(R.drawable.otp_box_active);
                    if (next != null) next.requestFocus();
                } else {
                    current.setBackgroundResource(R.drawable.otp_box_inactive);
                    if (s.length() == 0 && prev != null) prev.requestFocus();
                }
            }
        });
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(v -> onBackPressed());

        helpButton.setOnClickListener(v ->
            Toast.makeText(this, "Help coming soon", Toast.LENGTH_SHORT).show());

        loginButton.setOnClickListener(v -> {
            if (!isOtpStep) {
                handleSendOtp();
            } else {
                handleVerifyOtp();
            }
        });

        backToEmailLayout.setOnClickListener(v -> showEmailStep());
        changeEmailButton.setOnClickListener(v -> showEmailStep());

        resendOtpText.setOnClickListener(v -> {
            clearOtpFields();
            callSendOtpApi(currentEmail);
        });
    }

    // ─── Step 1: Send OTP ────────────────────────────────────────────────────

    private void handleSendOtp() {
        String email = emailEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Please enter your email");
            emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email");
            emailEditText.requestFocus();
            return;
        }

        currentEmail = email;
        callSendOtpApi(email);
    }

    private void callSendOtpApi(String email) {
        setLoading(true);

        Call<SendOtpResponse> call = apiService.sendOtp(new SendOtpRequest(email));
        call.enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    SendOtpResponse body = response.body();
                    if (body.isSuccess()) {
                        showOtpStep(email);
                        Toast.makeText(LoginActivity.this,
                                "OTP sent to " + email, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                body.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Failed to send OTP. Please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SendOtpResponse> call, Throwable t) {
                setLoading(false);
                Toast.makeText(LoginActivity.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // ─── Step 2: Verify OTP ──────────────────────────────────────────────────

    private void handleVerifyOtp() {
        String otp = getEnteredOtp();
        if (otp.length() < 6) {
            Toast.makeText(this, "Please enter the complete 6-digit OTP",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);

        Call<VerifyOtpResponse> call = apiService.verifyOtp(
                new VerifyOtpRequest(currentEmail, otp));

        call.enqueue(new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call,
                                   Response<VerifyOtpResponse> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    VerifyOtpResponse body = response.body();
                    if (body.isSuccess() && body.getData() != null) {
                        // ✅ Success — green boxes then navigate
                        animateOtpBoxes(true, () -> handleVerifySuccess(body));
                    } else {
                        // ❌ Wrong OTP — red boxes then shake
                        animateOtpBoxes(false, () -> {
                            clearOtpFields();
                            otp1.requestFocus();
                        });
                        Toast.makeText(LoginActivity.this,
                                body.getMessage() != null ? body.getMessage()
                                        : "Invalid OTP. Please try again.",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    // ❌ Server error — red boxes
                    animateOtpBoxes(false, () -> {
                        clearOtpFields();
                        otp1.requestFocus();
                    });
                    Toast.makeText(LoginActivity.this,
                            "Invalid OTP. Please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                setLoading(false);
                animateOtpBoxes(false, null);
                Toast.makeText(LoginActivity.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Sequential wave animation:
     * Each box lights up (green/red) one by one left→right,
     * then resets one by one left→right, then runs callback.
     */
    private void animateOtpBoxes(boolean success, Runnable onComplete) {
        EditText[] boxes = {otp1, otp2, otp3, otp4, otp5, otp6};
        int colorRes  = success ? R.drawable.otp_box_success : R.drawable.otp_box_error;
        int textColor = success ? 0xFF22C55E : 0xFFEF4444;
        long stepDelay = 80L; // ms between each box lighting up

        // Phase 1: light up boxes one by one (left → right)
        for (int i = 0; i < boxes.length; i++) {
            final EditText box = boxes[i];
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                box.setBackgroundResource(colorRes);
                box.setTextColor(textColor);
                // Small scale pop when it lights up
                box.animate()
                    .scaleX(1.12f).scaleY(1.12f)
                    .setDuration(80)
                    .withEndAction(() ->
                        box.animate()
                            .scaleX(1f).scaleY(1f)
                            .setDuration(80)
                            .start())
                    .start();
            }, i * stepDelay);
        }

        // Phase 2: reset boxes one by one (left → right)
        // starts after all boxes have lit up + 300ms hold
        long resetStart = boxes.length * stepDelay + 300;
        for (int i = 0; i < boxes.length; i++) {
            final EditText box = boxes[i];
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                box.setBackgroundResource(R.drawable.otp_box_inactive);
                box.setTextColor(0xFF1A1A1A);
            }, resetStart + i * stepDelay);
        }

        // Phase 3: callback after full animation completes
        long totalDuration = resetStart + boxes.length * stepDelay + 100;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (onComplete != null) onComplete.run();
        }, totalDuration);
    }

    private void handleVerifySuccess(VerifyOtpResponse response) {
        VerifyOtpResponse.Data data     = response.getData();
        VerifyOtpResponse.Driver driver = data.getDriver();

        // Save session
        sessionManager.saveSession(
                data.getAccessToken(),
                data.getRefreshToken(),
                driver.getDriverId(),
                driver.getEmail(),
                driver.getFullName(),
                driver.getStatus(),
                driver.isProfileComplete()
        );

        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();

        // Navigate based on nextStep
        navigateAfterLogin(driver.isProfileComplete());
    }

    private void navigateAfterLogin(boolean isProfileComplete) {
        // Always go to location selection first after login/register
        Intent intent = new Intent(LoginActivity.this, LocationSelectionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    // ─── UI Helpers ──────────────────────────────────────────────────────────

    private void showOtpStep(String email) {
        isOtpStep = true;
        otpSentToText.setText(email);  // chip shows just the email
        emailSection.setVisibility(View.GONE);
        otpSection.setVisibility(View.VISIBLE);
        changeEmailButton.setVisibility(View.VISIBLE);
        loginButton.setText("Verify & Proceed");
        otp1.requestFocus();
    }

    private void showEmailStep() {
        isOtpStep = false;
        otpSection.setVisibility(View.GONE);
        emailSection.setVisibility(View.VISIBLE);
        changeEmailButton.setVisibility(View.GONE);
        loginButton.setText("Proceed");
        clearOtpFields();
    }

    private void setLoading(boolean loading) {
        loginButton.setEnabled(!loading);
        loginButton.setText(loading
                ? (isOtpStep ? "Verifying..." : "Sending OTP...")
                : (isOtpStep ? "Verify & Proceed" : "Proceed"));
    }

    private String getEnteredOtp() {
        return getText(otp1) + getText(otp2) + getText(otp3)
             + getText(otp4) + getText(otp5) + getText(otp6);
    }

    private String getText(EditText et) {
        return et.getText() != null ? et.getText().toString() : "";
    }

    private void clearOtpFields() {
        for (EditText et : new EditText[]{otp1, otp2, otp3, otp4, otp5, otp6}) {
            et.setText("");
            et.setBackgroundResource(R.drawable.otp_box_inactive);
        }
    }

    @Override
    public void onBackPressed() {
        if (isOtpStep) {
            showEmailStep();
        } else {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}
