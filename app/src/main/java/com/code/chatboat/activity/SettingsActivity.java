package com.code.chatboat.activity;

import android.content.Intent;
import android.view.View;

import com.code.chatboat.BaseActivity;
import com.code.chatboat.databinding.ActivitySettingsBinding;
import com.code.chatboat.utils.CustomDialog;
import com.code.chatboat.utils.CustomExitDialog;

public class SettingsActivity extends BaseActivity<ActivitySettingsBinding> {

    @Override
    protected ActivitySettingsBinding getViewBinding() {
        return ActivitySettingsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(SettingsActivity.this);
                customDialog.setTitleText("About us");
                customDialog.setMessageText("We aim to improve the health and well-being of every girl and women, worldwide. We help women put themselves first to empower women by giving them a space they can access the knowledge and support they need to prioritize their health and well-being.");
                customDialog.show();
            }
        });

        binding.btnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(SettingsActivity.this);
                customDialog.setTitleText("Privacy Policy");
                customDialog.setMessageText("Your information will be kept, including the beginning date of your period, how long it lasts, and how long your menstrual cycle is.\n" +
                        "\n" +
                        "We never save any of your personal information other than, what is already indicated.\n");
                customDialog.show();
            }
        });

        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp();
            }
        });

        binding.btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomExitDialog customDialog = new CustomExitDialog(SettingsActivity.this,true);
                customDialog.setTitleText("Select your language");
                customDialog.setMessageText("Eng","Guj");
                customDialog.show();
            }
        });
    }
    private void shareApp() {
        String message = "Check out this awesome app! Download it from the Play Store:\n\n";
        String playStoreLink = "https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName();
        String textToShare = message + playStoreLink;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

}