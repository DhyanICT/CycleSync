package com.code.chatboat.activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.code.chatboat.BaseActivity;
import com.code.chatboat.Const;
import com.code.chatboat.R;
import com.code.chatboat.databinding.ActivityMenstrualEditBinding;
import com.code.chatboat.model.MenstrualPeriodResumeIntent;
import com.code.chatboat.room.AppDatabase;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class MenstrualEditActivity extends BaseActivity<ActivityMenstrualEditBinding> implements MenstrualEditActionListener {

    boolean isEdit = false;
    public static String EXTRA_MENSTRUAL_RESUME = "EXTRA_MENSTRUAL_RESUME";
    public static int RES_CODE_EDIT = 200;
    public static int REQ_CODE_EDIT = 100;

    Calendar selectedCalendar = null;

    @Override
    protected ActivityMenstrualEditBinding getViewBinding() {
        return ActivityMenstrualEditBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getDataIntent();
        observeViewModel();
        binding.etLastPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLastPeriod();
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCLickSave(
                        binding.etLastPeriod.getText().toString(),
                        binding.etLongPeriod.getText().toString(),
                        binding.etLongCycle.getText().toString(),
                        isEdit);
            }
        });
    }

    MenstrualPeriodResumeIntent menstrualIntentLive;

    private void getDataIntent() {
        if (getIntent().hasExtra(EXTRA_MENSTRUAL_RESUME)) {
            menstrualIntentLive =getIntent().getParcelableExtra(EXTRA_MENSTRUAL_RESUME);
        }
    }

    private void observeViewModel() {
        if (menstrualIntentLive != null) {
            isEdit = true;
            setupMenstrualResumeToView(menstrualIntentLive);
        }
    }

    @Override
    public void onClickLastPeriod() {
        // Get the current date
        Calendar currentDate = Calendar.getInstance();

// Set the current date to the first day of the current month
        currentDate.set(Calendar.DAY_OF_MONTH, 1);

// Now, currentDate represents the first day of the current month
        Calendar lastMonthFirstDay = (Calendar) currentDate.clone();
        lastMonthFirstDay.add(Calendar.MONTH, -1);

// Move to the last day of the current month
        currentDate.add(Calendar.MONTH, 1);
        currentDate.add(Calendar.DAY_OF_MONTH, -1);

        DatePickerBuilder builder = new DatePickerBuilder(this, new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendars) {
                for (Calendar calendar : calendars) {
                    selectedCalendar = calendar;
                    String dateShow = new SimpleDateFormat(Const.DATE_ENGLISH_YYYY_MM_DD, Locale.US)
                            .format(calendar.getTime());
                    binding.etLastPeriod.setText(dateShow);
                }
            }
        })
                .pickerType(CalendarView.ONE_DAY_PICKER)
                .minimumDate(lastMonthFirstDay)  // Set the minimum date to the first day of the last month
                .maximumDate(currentDate) // Set the maximum date to the last day of the current month
                .headerColor(R.color.white)
                .headerLabelColor(R.color.colorPrimary)
                .selectionColor(R.color.colorPrimary)
                .todayLabelColor(R.color.colorPrimary)
                .dialogButtonsColor(R.color.colorPrimary);

        DatePicker datePicker = builder.build();
        datePicker.show();

    }

    @Override
    public void onCLickSave(String lastPeriod, String periodLong, String cycleLong, boolean isEdit) {
        validateInput(lastPeriod, periodLong, cycleLong, isEdit);
    }

    public void setErrorMessage(TextInputLayout textInputLayout, String errorMessage) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(errorMessage);
    }
    private void validateInput(String lastPeriod, String periodLong, String cycleLong, boolean isEdit) {
        if (lastPeriod.isEmpty()) {
            setErrorMessage(binding.tilLastPeriod,getString(R.string.error_last_period_empty));
        } else if (periodLong.isEmpty()) {
            setErrorMessage(binding.tilLongPeriod,getString(R.string.error_period_long_empty));
        } else if (cycleLong.isEmpty()) {
            setErrorMessage(binding.tilLongCycle,getString(R.string.error_cycle_long_empty));
        } else if (Integer.parseInt(periodLong) < 1) {
            setErrorMessage(binding.tilLongPeriod,getString(R.string.error_period_long_less_than_1));
        } else if (Integer.parseInt(periodLong) > 12) {
            setErrorMessage(binding.tilLongPeriod,getString(R.string.error_period_long_more_than_12));
        } else if (Integer.parseInt(cycleLong) < 21) {
            setErrorMessage(binding.tilLongCycle,getString(R.string.error_cycle_long_less_than_21));
        } else if (Integer.parseInt(cycleLong) > 100) {
            setErrorMessage(binding.tilLongCycle,getString(R.string.error_cycle_long_more_than_100));
        } else {
           addMenstrualPeriod(createMenstrualPeriodResume(
                    selectedCalendar,
                    periodLong,
                    cycleLong,
                    cycleLong,
                    isEdit
            ));
        }
    }


    public MenstrualPeriodResumeIntent createMenstrualPeriodResume(
            Calendar selectedCalendar,
            String periodLong,
            String maxCycleLong,
            String minCycleLong,
            boolean isEdit
    ) {
        int cycleLong = (Integer.parseInt(maxCycleLong) + Integer.parseInt(minCycleLong)) / 2;

        SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATE_FORMAT_NO_TIME, Locale.US);

        String month = new SimpleDateFormat("MMMM", Locale.US).format(selectedCalendar != null ? selectedCalendar.getTime() : getTime());
        String year = new SimpleDateFormat("yyyy", Locale.US).format(selectedCalendar != null ? selectedCalendar.getTime() : getTime());

        int firstDayFertile = Integer.parseInt(minCycleLong) - 18;
        int lastDayFertile = Integer.parseInt(maxCycleLong) - 11;

        String lastDayPeriodString = dateFormat.format(selectedCalendar != null ? selectedCalendar.getTime() : getTime());
        if (selectedCalendar != null) {
            selectedCalendar.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(periodLong));
        }
        String firstDayPeriodString = dateFormat.format(selectedCalendar != null ? selectedCalendar.getTime() : getTime());

//        String firstDayPeriodString = dateFormat.format(selectedCalendar != null ? selectedCalendar.getTime() : getTime());
//        if (selectedCalendar != null) {
//            selectedCalendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(periodLong));
//        }
//        String lastDayPeriodString = dateFormat.format(selectedCalendar != null ? selectedCalendar.getTime() : getTime());
        Log.e("TAG", "createMenstrualPeriodResume: "+lastDayPeriodString );
        if (selectedCalendar != null) {
            selectedCalendar.add(Calendar.DAY_OF_MONTH, (firstDayFertile - Integer.parseInt(periodLong)));
        }
        String firstDayFertileString = dateFormat.format(selectedCalendar != null ? selectedCalendar.getTime() : getTime());

        if (selectedCalendar != null) {
            selectedCalendar.add(Calendar.DAY_OF_MONTH, (lastDayFertile - firstDayFertile));
        }
        String lastDayFertileString = dateFormat.format(selectedCalendar != null ? selectedCalendar.getTime() : getTime());

        int id= new Random().nextInt();
        return new MenstrualPeriodResumeIntent(
                id,
                year,
                month,
                firstDayPeriodString,
                lastDayPeriodString,
                firstDayFertileString,
                lastDayFertileString,
                firstDayFertile,
                lastDayFertile,
                cycleLong,
                Integer.parseInt(periodLong),
                isEdit
        );
    }

    private Date getTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        return c.getTime();
    }

    public void addMenstrualPeriod(MenstrualPeriodResumeIntent menstrualPeriodResume) {
        new AddData(menstrualPeriodResume).execute();

    }


    public class AddData extends AsyncTask<Void,Void,Void> {

        MenstrualPeriodResumeIntent menstrualPeriodResume;
        public AddData(MenstrualPeriodResumeIntent menstrualPeriodResume) {
            this.menstrualPeriodResume = menstrualPeriodResume;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(!isRecordExists(menstrualPeriodResume.getYear(),menstrualPeriodResume.getMonth())) {
                AppDatabase.getDatabase(MenstrualEditActivity.this).getDao().insertData(menstrualPeriodResume);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(MenstrualEditActivity.this,getString(R.string.message_success_save_menstrual_period),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MenstrualEditActivity.this,MenstrualActivity.class));
//            setResult(RES_CODE_EDIT);
//            finish();
        }

        // Create a method to check if a record with the same year and month exists
        private boolean isRecordExists(String year, String month) {
            List<MenstrualPeriodResumeIntent> recordsLiveData = AppDatabase.getDatabase(MenstrualEditActivity.this).getDao().getRecordsByYearAndMonth(year, month);
            List<MenstrualPeriodResumeIntent> records = recordsLiveData;
            return records != null && !records.isEmpty();
        }
    }

    private void setupMenstrualResumeToView(MenstrualPeriodResumeIntent data) {
        binding.etLastPeriod.setText(data.getFirstDayPeriod());
        binding.etLongCycle.setText(String.valueOf(data.getLongCycle()));
        binding.etLongPeriod.setText(String.valueOf(data.getLongPeriod()));
    }

    private void setupLoading(boolean isLoading) {
        if (isLoading) {
            binding.btnSave.showProgress();
        } else {
            binding.btnSave.hideProgress();
        }
    }

}