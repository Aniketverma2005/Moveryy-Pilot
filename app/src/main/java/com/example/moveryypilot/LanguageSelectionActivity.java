package com.example.moveryypilot;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.button.MaterialButton;
import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {

    private CardView hindiBubble, englishBubble;
    private TextView previewCard;
    private CardView cardEnglish, cardSpanish, cardFrench, cardGerman, cardHindi, cardArabic;
    private View checkEnglish, checkSpanish, checkFrench, checkGerman, checkHindi, checkArabic;
    private ImageView audioWaveEnglish, audioWaveHindi, audioWaveBengali, audioWaveMarathi, audioWaveMalayalam, audioWaveKannada;
    private TextView dotsEnglish, dotsHindi, dotsBengali, dotsMarathi, dotsMalayalam, dotsKannada;
    private LinearLayout illustrationContainer;
    private MaterialButton btnContinue, helpButton;
    private CardView languageSelectionCard;
    private TextView selectLanguageTitle;
    
    private String selectedLanguage = "";
    private boolean isLanguageSelected = false;
    private SharedPreferences prefs;

    // Language translations
    private static class LanguageData {
        String greeting;
        String welcome;
        String appName;
        String emoji;
        String backgroundText;
        String selectTitle;
        String confirmButton;

        LanguageData(String greeting, String welcome, String appName, String emoji, String backgroundText, String selectTitle, String confirmButton) {
            this.greeting = greeting;
            this.welcome = welcome;
            this.appName = appName;
            this.emoji = emoji;
            this.backgroundText = backgroundText;
            this.selectTitle = selectTitle;
            this.confirmButton = confirmButton;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        prefs = getSharedPreferences("moveryy_prefs", MODE_PRIVATE);
        
        if (prefs.getBoolean("language_selected", false)) {
            navigateToWelcome();
            return;
        }
        
        setContentView(R.layout.activity_language_selection);

        initializeViews();
        setupClickListeners();
        startEntranceAnimations();
    }

    private void initializeViews() {
        // Illustration bubbles and containers
        hindiBubble = findViewById(R.id.hindiBubble);
        englishBubble = findViewById(R.id.englishBubble);
        illustrationContainer = findViewById(R.id.illustrationContainer);
        
        // Preview text (large language text)
        previewCard = findViewById(R.id.previewCard);
        
        // Language selection card
        languageSelectionCard = findViewById(R.id.languageSelectionCard);
        selectLanguageTitle = findViewById(R.id.selectLanguageTitle);
        
        // Language cards
        cardEnglish = findViewById(R.id.cardEnglish);
        cardSpanish = findViewById(R.id.cardSpanish);
        cardFrench = findViewById(R.id.cardFrench);
        cardGerman = findViewById(R.id.cardGerman);
        cardHindi = findViewById(R.id.cardHindi);
        cardArabic = findViewById(R.id.cardArabic);

        // Radio buttons
        checkEnglish = findViewById(R.id.checkEnglish);
        checkSpanish = findViewById(R.id.checkSpanish);
        checkFrench = findViewById(R.id.checkFrench);
        checkGerman = findViewById(R.id.checkGerman);
        checkHindi = findViewById(R.id.checkHindi);
        checkArabic = findViewById(R.id.checkArabic);
        
        // Audio waves and dots for all languages
        audioWaveEnglish = findViewById(R.id.audioWaveEnglish);
        dotsEnglish = findViewById(R.id.dotsEnglish);
        
        audioWaveHindi = findViewById(R.id.audioWaveHindi);
        dotsHindi = findViewById(R.id.dotsHindi);
        
        audioWaveBengali = findViewById(R.id.audioWaveBengali);
        dotsBengali = findViewById(R.id.dotsBengali);
        
        audioWaveMarathi = findViewById(R.id.audioWaveMarathi);
        dotsMarathi = findViewById(R.id.dotsMarathi);
        
        audioWaveMalayalam = findViewById(R.id.audioWaveMalayalam);
        dotsMalayalam = findViewById(R.id.dotsMalayalam);
        
        audioWaveKannada = findViewById(R.id.audioWaveKannada);
        dotsKannada = findViewById(R.id.dotsKannada);

        // Buttons
        btnContinue = findViewById(R.id.btnContinue);
        helpButton = findViewById(R.id.helpButton);
    }

    private void setupClickListeners() {
        // Language selection
        cardEnglish.setOnClickListener(v -> selectLanguage("en", checkEnglish, cardEnglish));
        cardHindi.setOnClickListener(v -> selectLanguage("hi", checkHindi, cardHindi));
        cardSpanish.setOnClickListener(v -> selectLanguage("bn", checkSpanish, cardSpanish)); // Bengali
        cardFrench.setOnClickListener(v -> selectLanguage("mr", checkFrench, cardFrench)); // Marathi
        cardGerman.setOnClickListener(v -> selectLanguage("ml", checkGerman, cardGerman)); // Malayalam
        cardArabic.setOnClickListener(v -> selectLanguage("kn", checkArabic, cardArabic)); // Kannada

        // Continue button
        btnContinue.setOnClickListener(v -> {
            animateButtonClick(btnContinue);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                prefs.edit()
                    .putString("selected_language", selectedLanguage)
                    .putBoolean("language_selected", true)
                    .apply();
                setLocale(selectedLanguage);
                navigateToWelcome();
            }, 300);
        });

        // Help button
        helpButton.setOnClickListener(v -> {
            // TODO: Show help dialog or navigate to help screen
        });
    }

    private void startEntranceAnimations() {
        // Set initial states
        previewCard.setAlpha(0f);
        previewCard.setScaleX(0.8f);
        previewCard.setScaleY(0.8f);
        
        languageSelectionCard.setAlpha(0f);
        languageSelectionCard.setTranslationY(200f);
        
        helpButton.setAlpha(0f);

        // Animate preview card
        previewCard.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(800)
            .setInterpolator(new OvershootInterpolator())
            .setStartDelay(200)
            .start();

        // Animate language selection card
        languageSelectionCard.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(700)
            .setInterpolator(new DecelerateInterpolator())
            .setStartDelay(600)
            .start();

        // Animate help button
        helpButton.animate()
            .alpha(1f)
            .setDuration(500)
            .setStartDelay(400)
            .start();

        // Start floating animation for preview card
        startFloatingAnimation();
    }

    private void startFloatingAnimation() {
        // Preview card floating
        ObjectAnimator previewFloat = ObjectAnimator.ofFloat(previewCard, "translationY", 0f, -10f, 0f);
        previewFloat.setDuration(3000);
        previewFloat.setRepeatCount(ValueAnimator.INFINITE);
        previewFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        previewFloat.setStartDelay(1000);
        previewFloat.start();
    }

    private void selectLanguage(String languageCode, View radioButton, CardView card) {
        selectedLanguage = languageCode;
        
        // First time selection - transition from bubbles to preview
        if (!isLanguageSelected) {
            isLanguageSelected = true;
            transitionToPreviewCard();
        }
        
        // Get language data
        LanguageData data = getLanguageData(languageCode);
        
        // Update preview card with animation
        updatePreviewCard(data);
        
        // Animate card selection
        animateCardSelection(card);
        
        // Reset all cards to unselected state
        resetAllCards();
        
        // Set selected card style
        card.setCardBackgroundColor(getColor(R.color.white));
        View cardLayout = card.getChildAt(0);
        cardLayout.setBackgroundResource(R.drawable.language_card_selected);
        
        // Update all radio buttons
        checkEnglish.setBackgroundResource(R.drawable.radio_button_unselected);
        checkHindi.setBackgroundResource(R.drawable.radio_button_unselected);
        checkSpanish.setBackgroundResource(R.drawable.radio_button_unselected);
        checkFrench.setBackgroundResource(R.drawable.radio_button_unselected);
        checkGerman.setBackgroundResource(R.drawable.radio_button_unselected);
        checkArabic.setBackgroundResource(R.drawable.radio_button_unselected);
        
        // Set selected radio button
        radioButton.setBackgroundResource(R.drawable.radio_button_selected);
        
        // Show audio wave for selected language
        hideAllAudioWaves();
        showAudioWaveForLanguage(languageCode);
        
        // Animate radio button
        radioButton.setScaleX(0.8f);
        radioButton.setScaleY(0.8f);
        radioButton.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(300)
            .setInterpolator(new OvershootInterpolator())
            .start();

        // Enable and style continue button
        btnContinue.setEnabled(true);
        btnContinue.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_dark));
        btnContinue.setTextColor(getColor(R.color.white));
        btnContinue.setText(data.confirmButton);
        
        // Update select language title
        selectLanguageTitle.setText(data.selectTitle);
        
        // Animate button
        btnContinue.animate()
            .scaleX(1.05f)
            .scaleY(1.05f)
            .setDuration(200)
            .withEndAction(() -> {
                btnContinue.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(200)
                    .start();
            })
            .start();
    }
    
    private void hideAllAudioWaves() {
        // Hide all audio waves and show dots
        if (audioWaveEnglish != null) {
            audioWaveEnglish.setVisibility(View.GONE);
            dotsEnglish.setVisibility(View.VISIBLE);
        }
        if (audioWaveHindi != null) {
            audioWaveHindi.setVisibility(View.GONE);
            dotsHindi.setVisibility(View.VISIBLE);
        }
        if (audioWaveBengali != null) {
            audioWaveBengali.setVisibility(View.GONE);
            dotsBengali.setVisibility(View.VISIBLE);
        }
        if (audioWaveMarathi != null) {
            audioWaveMarathi.setVisibility(View.GONE);
            dotsMarathi.setVisibility(View.VISIBLE);
        }
        if (audioWaveMalayalam != null) {
            audioWaveMalayalam.setVisibility(View.GONE);
            dotsMalayalam.setVisibility(View.VISIBLE);
        }
        if (audioWaveKannada != null) {
            audioWaveKannada.setVisibility(View.GONE);
            dotsKannada.setVisibility(View.VISIBLE);
        }
    }
    
    private void showAudioWaveForLanguage(String languageCode) {
        ImageView audioWave = null;
        TextView dots = null;
        
        switch (languageCode) {
            case "en":
                audioWave = audioWaveEnglish;
                dots = dotsEnglish;
                break;
            case "hi":
                audioWave = audioWaveHindi;
                dots = dotsHindi;
                break;
            case "bn":
                audioWave = audioWaveBengali;
                dots = dotsBengali;
                break;
            case "mr":
                audioWave = audioWaveMarathi;
                dots = dotsMarathi;
                break;
            case "ml":
                audioWave = audioWaveMalayalam;
                dots = dotsMalayalam;
                break;
            case "kn":
                audioWave = audioWaveKannada;
                dots = dotsKannada;
                break;
        }
        
        if (audioWave != null && dots != null) {
            dots.setVisibility(View.GONE);
            audioWave.setVisibility(View.VISIBLE);
            startAudioWaveAnimation(audioWave);
        }
    }
    
    private void resetAllCards() {
        View[] cards = {
            cardEnglish.getChildAt(0),
            cardHindi.getChildAt(0),
            cardSpanish.getChildAt(0),
            cardFrench.getChildAt(0),
            cardGerman.getChildAt(0),
            cardArabic.getChildAt(0)
        };
        
        for (View cardLayout : cards) {
            cardLayout.setBackgroundResource(R.drawable.language_card_unselected);
        }
        
        // Hide all audio waves, show all dots
        hideAllAudioWaves();
    }
    
    private void transitionToPreviewCard() {
        // Fade out and scale down bubbles
        illustrationContainer.animate()
            .alpha(0f)
            .scaleX(0.8f)
            .scaleY(0.8f)
            .setDuration(400)
            .withEndAction(() -> {
                illustrationContainer.setVisibility(View.GONE);
                // Show preview card
                previewCard.setVisibility(View.VISIBLE);
                previewCard.setAlpha(0f);
                previewCard.setScaleX(0.8f);
                previewCard.setScaleY(0.8f);
                previewCard.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(500)
                    .setInterpolator(new OvershootInterpolator())
                    .start();
            })
            .start();
    }
    
    private void startAudioWaveAnimation(ImageView audioWave) {
        if (audioWave != null) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(audioWave, "scaleX", 1f, 1.1f, 1f);
            scaleX.setDuration(800);
            scaleX.setRepeatCount(ValueAnimator.INFINITE);
            scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
            
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(audioWave, "scaleY", 1f, 1.2f, 1f);
            scaleY.setDuration(800);
            scaleY.setRepeatCount(ValueAnimator.INFINITE);
            scaleY.setInterpolator(new AccelerateDecelerateInterpolator());
            
            scaleX.start();
            scaleY.start();
        }
    }

    private LanguageData getLanguageData(String languageCode) {
        switch (languageCode) {
            case "hi": // Hindi
                return new LanguageData(
                    "नमस्ते",
                    "रैपिडो कप्तान में\nआपका स्वागत है",
                    "Moveryy Pilot",
                    "🙏",
                    "हिंदी",
                    "ऐप भाषा चुनें",
                    "कन्फर्म करें"
                );
            case "bn": // Bengali
                return new LanguageData(
                    "নমস্কার",
                    "Moveryy Pilot-এ\nস্বাগতম",
                    "Moveryy Pilot",
                    "🙏",
                    "বাংলা",
                    "অ্যাপের ভাষা নির্বাচন করুন",
                    "নিশ্চিত করুন"
                );
            case "mr": // Marathi
                return new LanguageData(
                    "नमस्कार",
                    "Moveryy Pilot मध्ये\nआपले स्वागत आहे",
                    "Moveryy Pilot",
                    "🙏",
                    "मराठी",
                    "अॅप भाषा निवडा",
                    "पुष्टी करा"
                );
            case "ml": // Malayalam
                return new LanguageData(
                    "നമസ്കാരം",
                    "Moveryy Pilot-ലേക്ക്\nസ്വാഗതം",
                    "Moveryy Pilot",
                    "🙏",
                    "മലയാളം",
                    "ആപ്പ് ഭാഷ തിരഞ്ഞെടുക്കുക",
                    "സ്ഥിരീകരിക്കുക"
                );
            case "kn": // Kannada
                return new LanguageData(
                    "ನಮಸ್ಕಾರ",
                    "Moveryy Pilot ಗೆ\nಸ್ವಾಗತ",
                    "Moveryy Pilot",
                    "🙏",
                    "ಕನ್ನಡ",
                    "ಅಪ್ಲಿಕೇಶನ್ ಭಾಷೆಯನ್ನು ಆಯ್ಕೆಮಾಡಿ",
                    "ದೃಢೀಕರಿಸಿ"
                );
            default: // English
                return new LanguageData(
                    "Hello",
                    "Welcome to\nMoveryy Pilot",
                    "Moveryy Pilot",
                    "👋",
                    "ENGLISH",
                    "Select App Language",
                    "Confirm"
                );
        }
    }

    private void updatePreviewCard(LanguageData data) {
        // Simply update the large language text
        previewCard.setText(data.backgroundText);
        
        // Animate the text change
        previewCard.setAlpha(0f);
        previewCard.setScaleX(0.8f);
        previewCard.setScaleY(0.8f);
        
        previewCard.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(400)
            .setInterpolator(new OvershootInterpolator())
            .start();
    }

    private void animateCardSelection(CardView card) {
        card.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(100)
            .withEndAction(() -> {
                card.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(200)
                    .setInterpolator(new OvershootInterpolator())
                    .start();
            })
            .start();
    }

    private void animateButtonClick(View button) {
        button.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(100)
            .withEndAction(() -> {
                button.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .start();
            })
            .start();
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        
        Configuration config = new Configuration();
        config.setLocale(locale);
        
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void navigateToWelcome() {
        Intent intent = new Intent(LanguageSelectionActivity.this, IntroActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        // Don't allow going back to splash
    }
}
