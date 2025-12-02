package com.code.chatboat.activity;

import android.content.Intent;
import android.view.View;
import com.code.chatboat.BaseActivity;
import com.code.chatboat.databinding.ActivityLanguageBinding;
import com.code.chatboat.utils.SharedPreferencesManager;

public class LanguageActivity extends BaseActivity<ActivityLanguageBinding> {


    @Override
    protected ActivityLanguageBinding getViewBinding() {
        return ActivityLanguageBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        SharedPreferencesManager sharedPrefs = SharedPreferencesManager.getInstance(this);


        binding.btnEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefs.putBoolean("isLanguage", true);
                sharedPrefs.putString("language", "eng");
                startActivity(new Intent(LanguageActivity.this,IntroActivity.class));
                finish();
            }
        });

//        binding.btnGuj.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sharedPrefs.putBoolean("isLanguage", true);
//                sharedPrefs.putString("language", "guj");
//                startActivity(new Intent(LanguageActivity.this,IntroActivity.class));
//                finish();
//            }
//        });

    }
}