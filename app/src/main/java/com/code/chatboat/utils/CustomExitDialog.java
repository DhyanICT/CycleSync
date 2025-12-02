package com.code.chatboat.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.code.chatboat.BaseActivity;
import com.code.chatboat.R;

public class CustomExitDialog extends Dialog {
    TextView dialogTitle;
    TextView tvOk;
    TextView tvNo;

    Boolean  isLang = false;
    SharedPreferencesManager sharedPrefs;

    BaseActivity baseActivity;

    public CustomExitDialog(@NonNull BaseActivity context,Boolean isLang) {
        super(context);
        setContentView(R.layout.custom_exit_dialog);
        this.baseActivity = context;
        this.isLang = isLang;
        sharedPrefs = SharedPreferencesManager.getInstance(context);

        // Adjust dialog size
        getWindow().setLayout(
                (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8),
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        init();
    }

    private void init() {
        dialogTitle = findViewById(R.id.dialogTitle);
        tvOk = findViewById(R.id.tvOk);
        tvNo = findViewById(R.id.tvNo);
        setTitleText("");
        setMessageText("","");
        // Set click listener for the OK button
        ConstraintLayout okButton = findViewById(R.id.okButton);
        ConstraintLayout cancelButton = findViewById(R.id.cancelButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLang) {
                    Log.e("TAG", "onClick: okButton eng" );
                    sharedPrefs.putString("language", "eng");
                    dismiss();
                }else {
                    System.exit(0);
                    baseActivity.finishAffinity();
                    dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLang) {
                    Log.e("TAG", "onClick: okButton guj" );
                    sharedPrefs.putString("language", "guj");
                }
                dismiss();
            }
        });
    }

    public void setTitleText(String title) {
        dialogTitle.setText(title);
    }

    public void setMessageText(String message, String msg) {
        tvOk.setText(message);
        tvNo.setText(msg);
    }
}
