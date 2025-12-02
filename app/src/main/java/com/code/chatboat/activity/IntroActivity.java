package com.code.chatboat.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.code.chatboat.BaseActivity;
import com.code.chatboat.R;
import com.code.chatboat.adapter.IntroPagerAdapter;
import com.code.chatboat.databinding.ActivityIntroBinding;
import com.code.chatboat.utils.SharedPreferencesManager;

public class IntroActivity extends BaseActivity<ActivityIntroBinding> {

    int currentPos = 0;

    @Override
    protected ActivityIntroBinding getViewBinding() {
        return ActivityIntroBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        SharedPreferencesManager sharedPrefs = SharedPreferencesManager.getInstance(this);

        IntroPagerAdapter pagerAdapter = new IntroPagerAdapter(this);
        binding.viewPager.setAdapter(pagerAdapter);

        // Add indicators for each intro screen
        setupIndicators();

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPos >= 2) {
                    sharedPrefs.putBoolean("isIntro", true);
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    finish();
                } else {
                    currentPos++;
                    binding.viewPager.setCurrentItem(currentPos);
                    updateIndicators(currentPos);
                }
            }
        });
    }

    private void setupIndicators() {
        for (int i = 0; i < 3; i++) {
            ImageView indicator = new ImageView(this);
            indicator.setImageDrawable(ContextCompat.getDrawable(this,
                    i == 0 ? R.drawable.indicator_selected : R.drawable.indicator_unselected));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(25, 25);
            layoutParams.setMargins(8, 0, 8, 0);
            indicator.setLayoutParams(layoutParams);

            binding.indicatorLayout.addView(indicator);
        }

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPos = position;
                updateIndicators(position);
            }
        });
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < binding.indicatorLayout.getChildCount(); i++) {
            ImageView indicator = (ImageView) binding.indicatorLayout.getChildAt(i);
            indicator.setImageDrawable(ContextCompat.getDrawable(this,
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected));
        }
    }
}