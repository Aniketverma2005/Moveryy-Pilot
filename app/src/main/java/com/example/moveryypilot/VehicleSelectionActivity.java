package com.example.moveryypilot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.OvershootInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

public class VehicleSelectionActivity extends AppCompatActivity {

    private ImageButton backButton;
    private LinearLayout helpButton;

    // Cards
    private CardView cardBike, cardAuto, cardERickshaw, cardCab;

    // Inner backgrounds (for border switching)
    private LinearLayout bikeBg, autoBg, eRickshawBg, cabBg;

    // Radio buttons
    private View radioBike, radioAuto, radioERickshaw, radioCab;

    // Bottom
    private MaterialButton confirmVehicleButton;
    private TextView selectedVehicleInfo;

    private String selectedVehicle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_selection);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        backButton           = findViewById(R.id.backButton);
        helpButton           = findViewById(R.id.helpButton);
        cardBike             = findViewById(R.id.cardBike);
        cardAuto             = findViewById(R.id.cardAuto);
        cardERickshaw        = findViewById(R.id.cardERickshaw);
        cardCab              = findViewById(R.id.cardCab);
        bikeBg               = findViewById(R.id.bikeBg);
        autoBg               = findViewById(R.id.autoBg);
        eRickshawBg          = findViewById(R.id.eRickshawBg);
        cabBg                = findViewById(R.id.cabBg);
        radioBike            = findViewById(R.id.radioBike);
        radioAuto            = findViewById(R.id.radioAuto);
        radioERickshaw       = findViewById(R.id.radioERickshaw);
        radioCab             = findViewById(R.id.radioCab);
        confirmVehicleButton = findViewById(R.id.confirmVehicleButton);
        selectedVehicleInfo  = findViewById(R.id.selectedVehicleInfo);
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(v -> onBackPressed());
        helpButton.setOnClickListener(v ->
            Toast.makeText(this, "Help coming soon", Toast.LENGTH_SHORT).show());

        cardBike.setOnClickListener(v      -> selectVehicle("Bike",       "2-wheeler"));
        cardAuto.setOnClickListener(v      -> selectVehicle("Auto",       "3-wheeler"));
        cardERickshaw.setOnClickListener(v -> selectVehicle("E-Rickshaw", "Electric 3-wheeler"));
        cardCab.setOnClickListener(v       -> selectVehicle("Cab",        "4-wheeler"));

        confirmVehicleButton.setOnClickListener(v -> {
            if (!selectedVehicle.isEmpty()) {
                // Save and navigate
                getSharedPreferences("moveryy_prefs", MODE_PRIVATE)
                        .edit()
                        .putString("selected_vehicle", selectedVehicle)
                        .apply();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void selectVehicle(String vehicle, String subtitle) {
        selectedVehicle = vehicle;

        // Reset all cards
        resetCard(bikeBg,      radioBike);
        resetCard(autoBg,      radioAuto);
        resetCard(eRickshawBg, radioERickshaw);
        resetCard(cabBg,       radioCab);

        // Highlight selected
        LinearLayout selectedBg;
        View selectedRadio;
        CardView selectedCard;

        switch (vehicle) {
            case "Auto":       selectedBg = autoBg;      selectedRadio = radioAuto;      selectedCard = cardAuto;      break;
            case "E-Rickshaw": selectedBg = eRickshawBg; selectedRadio = radioERickshaw; selectedCard = cardERickshaw; break;
            case "Cab":        selectedBg = cabBg;       selectedRadio = radioCab;       selectedCard = cardCab;       break;
            default:           selectedBg = bikeBg;      selectedRadio = radioBike;      selectedCard = cardBike;      break;
        }

        selectedBg.setBackgroundResource(R.drawable.vehicle_card_selected);
        selectedRadio.setBackgroundResource(R.drawable.radio_button_selected);

        // Bounce animation on selected card
        selectedCard.animate()
            .scaleX(0.96f).scaleY(0.96f)
            .setDuration(100)
            .withEndAction(() ->
                selectedCard.animate()
                    .scaleX(1f).scaleY(1f)
                    .setDuration(200)
                    .setInterpolator(new OvershootInterpolator())
                    .start())
            .start();

        // Enable confirm button
        confirmVehicleButton.setBackgroundTintList(
                getColorStateList(R.color.moveryy_primary));
        confirmVehicleButton.setEnabled(true);

        // Show selected info
        selectedVehicleInfo.setText("✓  " + vehicle + " — " + subtitle + " selected");
        selectedVehicleInfo.setVisibility(View.VISIBLE);
        selectedVehicleInfo.animate().alpha(0f).setDuration(0).withEndAction(() ->
            selectedVehicleInfo.animate().alpha(1f).setDuration(300).start()
        ).start();
    }

    private void resetCard(LinearLayout bg, View radio) {
        bg.setBackgroundResource(R.drawable.vehicle_card_unselected);
        radio.setBackgroundResource(R.drawable.radio_button_unselected);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
