package com.example.moveryypilot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LocationSelectionActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST = 1001;

    private ImageButton backButton;
    private LinearLayout helpButton, useCurrentLocationBtn, searchCityLayout;
    private TextView selectedCityText, changeCityButton;
    private EditText searchCityEditText;
    private LinearLayout citySuggestionsList;
    private MaterialButton confirmCityButton;
    private CardView selectedCityCard;

    private String selectedCity = "";
    private FusedLocationProviderClient fusedLocationClient;

    // Predefined Indian cities list
    private final List<String[]> cities = Arrays.asList(
        new String[]{"Mumbai", "Maharashtra"},
        new String[]{"Delhi", "Delhi"},
        new String[]{"Bangalore", "Karnataka"},
        new String[]{"Hyderabad", "Telangana"},
        new String[]{"Chennai", "Tamil Nadu"},
        new String[]{"Kolkata", "West Bengal"},
        new String[]{"Pune", "Maharashtra"},
        new String[]{"Ahmedabad", "Gujarat"},
        new String[]{"Jaipur", "Rajasthan"},
        new String[]{"Surat", "Gujarat"},
        new String[]{"Lucknow", "Uttar Pradesh"},
        new String[]{"Kanpur", "Uttar Pradesh"},
        new String[]{"Nagpur", "Maharashtra"},
        new String[]{"Indore", "Madhya Pradesh"},
        new String[]{"Bhopal", "Madhya Pradesh"},
        new String[]{"Visakhapatnam", "Andhra Pradesh"},
        new String[]{"Patna", "Bihar"},
        new String[]{"Vadodara", "Gujarat"},
        new String[]{"Ghaziabad", "Uttar Pradesh"},
        new String[]{"Ludhiana", "Punjab"},
        new String[]{"Agra", "Uttar Pradesh"},
        new String[]{"Nashik", "Maharashtra"},
        new String[]{"Ranchi", "Jharkhand"},
        new String[]{"Jamshedpur", "Jharkhand"},
        new String[]{"Coimbatore", "Tamil Nadu"},
        new String[]{"Kochi", "Kerala"},
        new String[]{"Thiruvananthapuram", "Kerala"},
        new String[]{"Guwahati", "Assam"},
        new String[]{"Chandigarh", "Punjab"},
        new String[]{"Noida", "Uttar Pradesh"}
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        initViews();
        setupClickListeners();
        setupCitySearch();

        // Try to auto-detect location on open
        requestLocationPermission();
    }

    private void initViews() {
        backButton           = findViewById(R.id.backButton);
        helpButton           = findViewById(R.id.helpButton);
        useCurrentLocationBtn = findViewById(R.id.useCurrentLocationBtn);
        searchCityLayout     = findViewById(R.id.searchCityLayout);
        selectedCityText     = findViewById(R.id.selectedCityText);
        changeCityButton     = findViewById(R.id.changeCityButton);
        searchCityEditText   = findViewById(R.id.searchCityEditText);
        citySuggestionsList  = findViewById(R.id.citySuggestionsList);
        confirmCityButton    = findViewById(R.id.confirmCityButton);
        selectedCityCard     = findViewById(R.id.selectedCityCard);

        // Disable confirm until city selected
        confirmCityButton.setEnabled(false);
        confirmCityButton.setAlpha(0.5f);
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(v -> onBackPressed());

        helpButton.setOnClickListener(v ->
            Toast.makeText(this, "Help coming soon", Toast.LENGTH_SHORT).show());

        changeCityButton.setOnClickListener(v -> {
            searchCityLayout.setVisibility(View.VISIBLE);
            searchCityEditText.requestFocus();
        });

        useCurrentLocationBtn.setOnClickListener(v -> requestLocationPermission());

        confirmCityButton.setOnClickListener(v -> {
            if (!selectedCity.isEmpty()) {
                navigateToNext();
            }
        });
    }

    private void setupCitySearch() {
        searchCityEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence s, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable s) {
                filterCities(s.toString().trim());
            }
        });
    }

    private void filterCities(String query) {
        citySuggestionsList.removeAllViews();

        if (query.isEmpty()) return;

        List<String[]> filtered = new ArrayList<>();
        for (String[] city : cities) {
            if (city[0].toLowerCase().startsWith(query.toLowerCase())) {
                filtered.add(city);
                if (filtered.size() >= 5) break;
            }
        }

        for (String[] city : filtered) {
            addCitySuggestion(city[0], city[1]);
        }
    }

    private void addCitySuggestion(String cityName, String stateName) {
        View item = LayoutInflater.from(this)
                .inflate(R.layout.item_city, citySuggestionsList, false);

        ((TextView) item.findViewById(R.id.cityName)).setText(cityName);
        ((TextView) item.findViewById(R.id.cityState)).setText(stateName);

        // Divider between items
        View divider = new View(this);
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1));
        divider.setBackgroundColor(0xFFEEEEEE);

        item.setOnClickListener(v -> selectCity(cityName));

        citySuggestionsList.addView(item);
        citySuggestionsList.addView(divider);
    }

    private void selectCity(String city) {
        selectedCity = city;
        selectedCityText.setText(city);
        searchCityLayout.setVisibility(View.GONE);
        searchCityEditText.setText("");
        citySuggestionsList.removeAllViews();

        // Enable confirm button with animation
        confirmCityButton.setEnabled(true);
        confirmCityButton.animate().alpha(1f).setDuration(300).start();

        // Animate the city card
        selectedCityCard.animate()
            .scaleX(1.02f).scaleY(1.02f)
            .setDuration(150)
            .withEndAction(() ->
                selectedCityCard.animate()
                    .scaleX(1f).scaleY(1f)
                    .setDuration(150)
                    .start())
            .start();
    }

    // ── Location Permission & Detection ──────────────────────────────────────

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            detectCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        }
    }

    private void detectCurrentLocation() {
        selectedCityText.setText("Detecting...");

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                getCityFromLocation(location);
            } else {
                selectedCityText.setText("Select a city");
            }
        }).addOnFailureListener(e ->
            selectedCityText.setText("Select a city"));
    }

    private void getCityFromLocation(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(
                    location.getLatitude(), location.getLongitude(), 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String city = address.getLocality();
                if (city == null) city = address.getSubAdminArea();
                if (city == null) city = address.getAdminArea();

                if (city != null) {
                    selectCity(city);
                } else {
                    selectedCityText.setText("Select a city");
                }
            }
        } catch (IOException e) {
            selectedCityText.setText("Select a city");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                detectCurrentLocation();
            } else {
                selectedCityText.setText("Select a city");
                Toast.makeText(this,
                        "Location permission denied. Please select city manually.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToNext() {
        getSharedPreferences("moveryy_prefs", MODE_PRIVATE)
                .edit()
                .putString("selected_city", selectedCity)
                .apply();

        // Go to vehicle selection next
        Intent intent = new Intent(this, VehicleSelectionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
