package com.code.chatboat.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.code.chatboat.R;

public class CustomDialog extends Dialog {
    TextView dialogTitle;
    TextView dialogMessage;
    public CustomDialog(@NonNull Context context) {
        super(context);
        // Adjust dialog size
        setContentView(R.layout.custom_dialog);

        getWindow().setLayout(
                (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8),
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        init();
    }

    private void init() {
        dialogTitle = findViewById(R.id.dialogTitle);
        dialogMessage = findViewById(R.id.dialogMessage);
        setTitleText("");
        setMessageText("");
        // Set click listener for the OK button
        ConstraintLayout okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setTitleText(String title) {
        dialogTitle.setText(title);
    }

    public void setMessageText(String message) {
        dialogMessage.setText(message);
    }
}
