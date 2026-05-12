package com.example.moveryypilot;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SplashActivity extends AppCompatActivity {
    
    private static final int SPLASH_DURATION = 2500; // 2.5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        // Animate elements
        animateSplashElements();
        
        // Navigate to LanguageSelectionActivity after delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LanguageSelectionActivity.class);
            startActivity(intent);
            finish();
            // Add smooth transition animation
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, SPLASH_DURATION);
    }
    
    private void animateSplashElements() {
        // Find views
        CardView logoCard = findViewById(R.id.logo_card);
        View appName = findViewById(R.id.splash_app_name);
        View tagline = findViewById(R.id.splash_tagline);
        
        // Set initial alpha to 0
        logoCard.setAlpha(0f);
        appName.setAlpha(0f);
        tagline.setAlpha(0f);
        
        // Animate logo card
        ObjectAnimator logoAnimator = ObjectAnimator.ofFloat(logoCard, "alpha", 0f, 1f);
        logoAnimator.setDuration(800);
        logoAnimator.setInterpolator(new DecelerateInterpolator());
        logoAnimator.setStartDelay(200);
        logoAnimator.start();
        
        // Animate app name
        ObjectAnimator nameAnimator = ObjectAnimator.ofFloat(appName, "alpha", 0f, 1f);
        nameAnimator.setDuration(600);
        nameAnimator.setInterpolator(new DecelerateInterpolator());
        nameAnimator.setStartDelay(600);
        nameAnimator.start();
        
        // Animate tagline
        ObjectAnimator taglineAnimator = ObjectAnimator.ofFloat(tagline, "alpha", 0f, 1f);
        taglineAnimator.setDuration(600);
        taglineAnimator.setInterpolator(new DecelerateInterpolator());
        taglineAnimator.setStartDelay(900);
        taglineAnimator.start();
    }
}
