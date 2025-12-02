package com.code.chatboat.activity;

public interface MenstrualEditActionListener {
    void onClickLastPeriod();
    void onCLickSave(String lastPeriod, String periodLong, String cycleLong, boolean isEdit);
}
