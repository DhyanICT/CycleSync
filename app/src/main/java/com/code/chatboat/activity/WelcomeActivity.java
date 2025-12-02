package com.code.chatboat.activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.code.chatboat.BaseActivity;
import com.code.chatboat.databinding.ActivityWelcomeBinding;
import com.code.chatboat.utils.SharedPreferencesManager;

public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding> {

    @Override
    protected ActivityWelcomeBinding getViewBinding() {
        return ActivityWelcomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        SharedPreferencesManager sharedPrefs = SharedPreferencesManager.getInstance(this);
        boolean isIntro = sharedPrefs.getBoolean("isIntro", false);
        boolean isLanguage = sharedPrefs.getBoolean("isLanguage", false);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isLanguage) {
                    startActivity(new Intent(WelcomeActivity.this, LanguageActivity.class));
                } else if (!isIntro) {
                    startActivity(new Intent(WelcomeActivity.this, IntroActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
            }
        },2000);
    }
}