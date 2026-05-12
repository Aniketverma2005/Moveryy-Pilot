package com.example.moveryypilot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

public class IntroActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private MaterialButton btnNext;
    private View indicator1, indicator2, indicator3;
    private androidx.cardview.widget.CardView customerCard;
    private SharedPreferences prefs;

    private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    private String[] titles = {
            "Drive & Earn",
            "Flexible Schedule",
            "Safe & Secure"
    };
    private String[] descriptions = {
            "Start earning money by delivering packages and making a difference in your community",
            "Work on your own time and be your own boss with flexible delivery schedules",
            "Your safety is our priority with 24/7 support and verified delivery partners"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        prefs = getSharedPreferences("moveryy_prefs", MODE_PRIVATE);
        
        if (prefs.getBoolean("intro_completed", false)) {
            navigateToLogin();
            return;
        }
        
        setContentView(R.layout.activity_intro);

        initializeViews();
        setupViewPager();
        setupClickListeners();
    }

    private void initializeViews() {
        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);
        customerCard = findViewById(R.id.customerCard);
    }

    private void setupViewPager() {
        IntroSliderAdapter adapter = new IntroSliderAdapter();
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateIndicators(position);
                
                if (position == 2) {
                    btnNext.setText("Start Driving");
                } else {
                    btnNext.setText("Continue");
                }
            }
        });
    }

    private void setupClickListeners() {
        btnNext.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < 2) {
                viewPager.setCurrentItem(currentItem + 1);
            } else {
                completeIntro();
            }
        });
        
        customerCard.setOnClickListener(v -> {
            // TODO: Navigate to customer booking flow
            // For now, just complete intro and go to welcome
            completeIntro();
        });
    }

    private void updateIndicators(int position) {
        // Reset all indicators
        indicator1.setBackgroundResource(R.drawable.indicator_inactive);
        indicator2.setBackgroundResource(R.drawable.indicator_inactive);
        indicator3.setBackgroundResource(R.drawable.indicator_inactive);
        
        // Reset sizes
        ViewGroup.LayoutParams params1 = indicator1.getLayoutParams();
        params1.width = dpToPx(8);
        indicator1.setLayoutParams(params1);
        
        ViewGroup.LayoutParams params2 = indicator2.getLayoutParams();
        params2.width = dpToPx(8);
        indicator2.setLayoutParams(params2);
        
        ViewGroup.LayoutParams params3 = indicator3.getLayoutParams();
        params3.width = dpToPx(8);
        indicator3.setLayoutParams(params3);

        // Set active indicator
        View activeIndicator;
        ViewGroup.LayoutParams activeParams;
        
        switch (position) {
            case 0:
                activeIndicator = indicator1;
                break;
            case 1:
                activeIndicator = indicator2;
                break;
            case 2:
                activeIndicator = indicator3;
                break;
            default:
                return;
        }
        
        activeIndicator.setBackgroundResource(R.drawable.indicator_active);
        activeParams = activeIndicator.getLayoutParams();
        activeParams.width = dpToPx(32);
        activeIndicator.setLayoutParams(activeParams);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void completeIntro() {
        prefs.edit().putBoolean("intro_completed", true).apply();
        navigateToLogin();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        // Don't allow going back to language selection
    }

    // ViewPager Adapter
    private class IntroSliderAdapter extends RecyclerView.Adapter<IntroSliderAdapter.SlideViewHolder> {

        @NonNull
        @Override
        public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.slide_item, parent, false);
            return new SlideViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
            holder.slideImage.setImageResource(images[position]);
            holder.slideTitle.setText(titles[position]);
            holder.slideDescription.setText(descriptions[position]);
        }

        @Override
        public int getItemCount() {
            return images.length;
        }

        class SlideViewHolder extends RecyclerView.ViewHolder {
            ImageView slideImage;
            TextView slideTitle, slideDescription;

            SlideViewHolder(@NonNull View itemView) {
                super(itemView);
                slideImage = itemView.findViewById(R.id.slideImage);
                slideTitle = itemView.findViewById(R.id.slideTitle);
                slideDescription = itemView.findViewById(R.id.slideDescription);
            }
        }
    }
}
