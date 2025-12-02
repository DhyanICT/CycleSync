package com.code.chatboat.utils;

import com.code.chatboat.model.MenstrualPeriodResume;
import com.code.chatboat.model.MenstrualPeriodResumeIntent;

public class MenstrualPeriodResumeMapper implements IntentMapper<MenstrualPeriodResumeIntent, MenstrualPeriodResume> {

    private static MenstrualPeriodResumeMapper INSTANCE;

    public static MenstrualPeriodResumeMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MenstrualPeriodResumeMapper();
        }
        return INSTANCE;
    }

    @Override
    public MenstrualPeriodResume mapFromIntent(MenstrualPeriodResumeIntent data) {
        if (data == null) {
            return null;
        }

        return new MenstrualPeriodResume(
                data.getId(),
            data.getYear(),
            data.getMonth(),
            data.getFirstDayPeriod(),
            data.getLastDayPeriod(),
            data.getFirstDayFertile(),
            data.getLastDayFertile(),
            data.getFirstDayFertileDay(),
            data.getLastDayFertileDay(),
            data.getLongCycle(),
            data.getLongPeriod(),
            data.isEdit()
        );
    }

    @Override
    public MenstrualPeriodResumeIntent mapToIntent(MenstrualPeriodResume data) {
        if (data == null) {
            return null;
        }

        return new MenstrualPeriodResumeIntent(
                data.getId(),
            data.getYear(),
            data.getMonth(),
            data.getFirstDayPeriod(),
            data.getLastDayPeriod(),
            data.getFirstDayFertile(),
            data.getLastDayFertile(),
            data.getFirstDayFertileDay(),
            data.getLastDayFertileDay(),
            data.getLongCycle(),
            data.getLongPeriod(),
            data.isEdit()
        );
    }
}
