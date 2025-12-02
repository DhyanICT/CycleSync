package com.code.chatboat.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.applandeo.materialcalendarview.EventDay;
// import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;  // REMOVED
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.code.chatboat.BaseActivity;
import com.code.chatboat.Const;
import com.code.chatboat.R;
import com.code.chatboat.databinding.ActivityMenstrualBinding;
import com.code.chatboat.model.MenstrualPeriodMonthlyIntent;
import com.code.chatboat.model.MenstrualPeriodResumeIntent;
import com.code.chatboat.room.AppDatabase;
import com.code.chatboat.utils.MenstrualPeriodResumeMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class MenstrualActivity extends BaseActivity<ActivityMenstrualBinding> {

    private MenstrualPeriodResumeMapper menstrualPeriodResumeMapper = MenstrualPeriodResumeMapper.getInstance();
    MenstrualPeriodMonthlyIntent activeYearData = null;

    String activeYear = "";
    int maxCycleLong = 0;
    int minCycleLong = 0;
    int periodLong = 0;
    int activeMonth = 0;

    @Override
    public ActivityMenstrualBinding getViewBinding() {
        return ActivityMenstrualBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initData() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        setupCalendarListener();

        // NEW: no try/catch, the method no longer throws OutOfDateRangeException
        binding.calendarMenstrual.setDate(Calendar.getInstance().getTime());

        String year = new SimpleDateFormat("yyyy").format(binding.calendarMenstrual.getCurrentPageDate().getTime());
        getMenstrualPeriod(year);
        showEventData(null);
    }

    public void getMenstrualPeriod(String year) {
        //TODO get from db
        new LoadData(year).execute();
    }

    private void setupLoadingMenstrual(boolean isLoading) {
        if (isLoading) {
            binding.layoutIllustrationMenstrual.setVisibility(View.GONE);
            binding.pbMenstrual.setVisibility(View.VISIBLE);
        } else {
            binding.pbMenstrual.setVisibility(View.GONE);
        }
    }

    private void setupMenstrualMonthly(MenstrualPeriodMonthlyIntent data) {
        activeYearData = data;
        Log.e("TAG", "setupMenstrualMonthly: " + new SimpleDateFormat("MMMM", Locale.US).format(binding.calendarMenstrual.getCurrentPageDate().getTime()));
        switch (new SimpleDateFormat("MMMM", Locale.US).format(binding.calendarMenstrual.getCurrentPageDate().getTime())) {
            case Const.KEY_JAN:
                showDataCalendar(activeYearData.getJan(), activeYearData.getFeb(), null);
                break;
            case Const.KEY_FEB:
                showDataCalendar(activeYearData.getFeb(), activeYearData.getMar(), activeYearData.getJan());
                break;
            case Const.KEY_MAR:
                showDataCalendar(activeYearData.getMar(), activeYearData.getApr(), activeYearData.getFeb());
                break;
            case Const.KEY_APR:
                showDataCalendar(activeYearData.getApr(), activeYearData.getMay(), activeYearData.getMar());
                break;
            case Const.KEY_MAY:
                showDataCalendar(activeYearData.getMay(), activeYearData.getJun(), activeYearData.getApr());
                break;
            case Const.KEY_JUN:
                showDataCalendar(activeYearData.getJun(), activeYearData.getJul(), activeYearData.getMay());
                break;
            case Const.KEY_JUL:
                showDataCalendar(activeYearData.getJul(), activeYearData.getAug(), activeYearData.getJun());
                break;
            case Const.KEY_AUG:
                showDataCalendar(activeYearData.getAug(), activeYearData.getSep(), activeYearData.getJul());
                break;
            case Const.KEY_SEP:
                showDataCalendar(activeYearData.getSep(), activeYearData.getOct(), activeYearData.getAug());
                break;
            case Const.KEY_OCT:
                showDataCalendar(activeYearData.getOct(), activeYearData.getNov(), activeYearData.getSep());
                break;
            case Const.KEY_NOV:
                showDataCalendar(activeYearData.getNov(), activeYearData.getDec(), activeYearData.getOct());
                break;
            case Const.KEY_DEC:
                showDataCalendar(activeYearData.getDec(), null, activeYearData.getNov());
                break;
        }
    }

    private void showDataCalendar(MenstrualPeriodResumeIntent current, MenstrualPeriodResumeIntent next, MenstrualPeriodResumeIntent previous) {
        if (current != null) {
            ArrayList<EventDay> calendarMenstrual = new ArrayList<>();
            calendarMenstrual.addAll(showMenstrualDataCalendar(current));
            calendarMenstrual.addAll(showFertileDataCalendar(current));
            if (next != null) {
                calendarMenstrual.addAll(showMenstrualDataCalendar(next));
                calendarMenstrual.addAll(showFertileDataCalendar(next));
            }
            if (previous != null) {
                calendarMenstrual.addAll(showMenstrualDataCalendar(previous));
                calendarMenstrual.addAll(showFertileDataCalendar(previous));
            }
            binding.calendarMenstrual.setEvents(calendarMenstrual);
        }
    }

    private ArrayList<EventDay> showMenstrualDataCalendar(MenstrualPeriodResumeIntent data) {
        ArrayList<EventDay> listEvent = new ArrayList<>();
        Calendar firstMenstrualCalendar = Calendar.getInstance();
        Calendar lastMenstrualCalendar = Calendar.getInstance();
        Date firstMenstrualDate = null;
        Date lastMenstrualDate = null;

        try {
            firstMenstrualDate = new SimpleDateFormat(Const.DEFAULT_DATE_FORMAT_NO_TIME, Locale.US).parse(data.getFirstDayPeriod());
            lastMenstrualDate = new SimpleDateFormat(Const.DEFAULT_DATE_FORMAT_NO_TIME, Locale.US).parse(data.getLastDayPeriod());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        firstMenstrualCalendar.setTime(firstMenstrualDate);
        lastMenstrualCalendar.setTime(lastMenstrualDate);
        while (firstMenstrualCalendar.before(lastMenstrualCalendar)) {
            Calendar eventCalendar = Calendar.getInstance();
            eventCalendar.setTime(firstMenstrualCalendar.getTime());
            listEvent.add(new EventDay(eventCalendar, R.drawable.ic_blood_event));
            firstMenstrualCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.e("TAG", "showMenstrualDataCalendar: " + firstMenstrualDate);
        Log.e("TAG", "showMenstrualDataCalendar:-- " + listEvent.size());
        return listEvent;
    }

    private ArrayList<EventDay> showFertileDataCalendar(MenstrualPeriodResumeIntent data) {
        ArrayList<EventDay> listEvent = new ArrayList<>();
        Calendar firstFertileCalendar = Calendar.getInstance();
        Calendar lastFertileCalendar = Calendar.getInstance();
        Date firstFertileDate = null;
        Date lastFertileDate = null;
        try {
            firstFertileDate = new SimpleDateFormat(Const.DEFAULT_DATE_FORMAT_NO_TIME, Locale.US).parse(data.getFirstDayFertile());
            lastFertileDate = new SimpleDateFormat(Const.DEFAULT_DATE_FORMAT_NO_TIME, Locale.US).parse(data.getLastDayFertile());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        firstFertileCalendar.setTime(firstFertileDate);
        lastFertileCalendar.setTime(lastFertileDate);
        while (firstFertileCalendar.before(lastFertileCalendar)) {
            Calendar eventCalendar = Calendar.getInstance();
            eventCalendar.setTime(firstFertileCalendar.getTime());
            listEvent.add(new EventDay(eventCalendar, R.drawable.ic_baby_event));
            firstFertileCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return listEvent;
    }

    private void setupCalendarListener() {
        binding.calendarMenstrual.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                onChangeDate(binding.calendarMenstrual.getCurrentPageDate());
            }
        });
        binding.calendarMenstrual.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                onChangeDate(binding.calendarMenstrual.getCurrentPageDate());
            }
        });
        binding.calendarMenstrual.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                showEventData(eventDay);
            }
        });
    }

    private void onChangeDate(Calendar currentPageDate) {
        String currentYear = new SimpleDateFormat("yyyy").format(currentPageDate.getTime());
        String currentMonth = new SimpleDateFormat("MMMM", Locale.US).format(currentPageDate.getTime());
        currentPageDate.add(Calendar.MONTH, 1);
        String nextMonth = new SimpleDateFormat("MMMM", Locale.US).format(currentPageDate.getTime());
        currentPageDate.add(Calendar.MONTH, -2);
        String previousMonth = new SimpleDateFormat("MMMM", Locale.US).format(currentPageDate.getTime());

        MenstrualPeriodResumeIntent menstrualResumeDataCurrentMonth = getMenstrualResumeDataCurrentMonth(currentMonth);
        MenstrualPeriodResumeIntent menstrualResumeDataNextMonth = getMenstrualResumeDataCurrentMonth(nextMonth);
        MenstrualPeriodResumeIntent menstrualResumeDataPreviousMonth = getMenstrualResumeDataCurrentMonth(previousMonth);

        if (menstrualResumeDataCurrentMonth != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATE_FORMAT_NO_TIME, Locale.US);
            Date firstDayMenstrual = null;
            try {
                firstDayMenstrual = dateFormat.parse(menstrualResumeDataCurrentMonth.getLastDayPeriod());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Calendar firstDayMenstrualCalendar = Calendar.getInstance();
            firstDayMenstrualCalendar.setTime(firstDayMenstrual);
            firstDayMenstrualCalendar.add(Calendar.DAY_OF_MONTH, menstrualResumeDataCurrentMonth.getLongCycle());
            MenstrualPeriodResumeIntent menstrualPeriodResume = createMenstrualPeriodResume(firstDayMenstrualCalendar, String.valueOf(periodLong), String.valueOf(maxCycleLong), String.valueOf(minCycleLong), menstrualResumeDataNextMonth != null ? menstrualResumeDataNextMonth.isEdit() : false);

            showDataCalendar(menstrualResumeDataCurrentMonth, menstrualResumeDataNextMonth, menstrualResumeDataPreviousMonth);

            if (menstrualResumeDataNextMonth == null) {
                addMenstrualPeriod(menstrualPeriodResume, firstDayMenstrualCalendar.getTime());
            } else if (!menstrualPeriodResume.isEdit()) {
                addMenstrualPeriod(menstrualPeriodResume, firstDayMenstrualCalendar.getTime());
            }
        }
        showEventData(null);
        if (!currentYear.equals(activeYear)) {
            getMenstrualPeriod(currentYear);
        }
    }

    public Date getTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        return c.getTime();
    }

    public MenstrualPeriodResumeIntent createMenstrualPeriodResume(Calendar selectedCalendar, String periodLong, String maxCycleLong, String minCycleLong, boolean isEdit) {
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

        if (selectedCalendar != null) {
            selectedCalendar.add(Calendar.DAY_OF_MONTH, firstDayFertile - Integer.parseInt(periodLong));
        }
        String firstDayFertileString = dateFormat.format(selectedCalendar != null ? selectedCalendar.getTime() : getTime());

        if (selectedCalendar != null) {
            selectedCalendar.add(Calendar.DAY_OF_MONTH, lastDayFertile - firstDayFertile);
        }
        String lastDayFertileString = dateFormat.format(selectedCalendar != null ? selectedCalendar.getTime() : getTime());

        int id = new Random().nextInt();
        return new MenstrualPeriodResumeIntent(id, year, month, firstDayPeriodString, lastDayPeriodString, firstDayFertileString, lastDayFertileString, firstDayFertile, lastDayFertile, cycleLong, Integer.parseInt(periodLong), isEdit);
    }

    // UPDATED: no more getImageDrawable(), uses date membership instead
    @SuppressLint("RestrictedApi")
    private void showEventData(EventDay eventDay) {
        if (eventDay != null) {
            binding.layoutIllustrationMenstrual.setVisibility(View.VISIBLE);
            Date clickedDate = eventDay.getCalendar().getTime();

            if (isFertileDate(clickedDate)) {
                showFertileData(clickedDate);
            } else if (isMenstrualDate(clickedDate)) {
                showMenstrualData(clickedDate);
            } else {
                showNormalData(clickedDate);
            }

        } else {
            binding.layoutIllustrationMenstrual.setVisibility(View.GONE);
        }
    }

    private boolean isSameDay(Calendar c1, Calendar c2) {
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    private boolean isMenstrualDate(Date date) {
        if (activeYearData == null) return false;

        Calendar clickedCal = Calendar.getInstance();
        clickedCal.setTime(date);

        List<MenstrualPeriodResumeIntent> allMonths = new ArrayList<>();
        allMonths.add(activeYearData.getJan());
        allMonths.add(activeYearData.getFeb());
        allMonths.add(activeYearData.getMar());
        allMonths.add(activeYearData.getApr());
        allMonths.add(activeYearData.getMay());
        allMonths.add(activeYearData.getJun());
        allMonths.add(activeYearData.getJul());
        allMonths.add(activeYearData.getAug());
        allMonths.add(activeYearData.getSep());
        allMonths.add(activeYearData.getOct());
        allMonths.add(activeYearData.getNov());
        allMonths.add(activeYearData.getDec());

        for (MenstrualPeriodResumeIntent resumeIntent : allMonths) {
            if (resumeIntent == null) continue;
            ArrayList<EventDay> events = showMenstrualDataCalendar(resumeIntent);
            for (EventDay e : events) {
                if (isSameDay(e.getCalendar(), clickedCal)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isFertileDate(Date date) {
        if (activeYearData == null) return false;

        Calendar clickedCal = Calendar.getInstance();
        clickedCal.setTime(date);

        List<MenstrualPeriodResumeIntent> allMonths = new ArrayList<>();
        allMonths.add(activeYearData.getJan());
        allMonths.add(activeYearData.getFeb());
        allMonths.add(activeYearData.getMar());
        allMonths.add(activeYearData.getApr());
        allMonths.add(activeYearData.getMay());
        allMonths.add(activeYearData.getJun());
        allMonths.add(activeYearData.getJul());
        allMonths.add(activeYearData.getAug());
        allMonths.add(activeYearData.getSep());
        allMonths.add(activeYearData.getOct());
        allMonths.add(activeYearData.getNov());
        allMonths.add(activeYearData.getDec());

        for (MenstrualPeriodResumeIntent resumeIntent : allMonths) {
            if (resumeIntent == null) continue;
            ArrayList<EventDay> events = showFertileDataCalendar(resumeIntent);
            for (EventDay e : events) {
                if (isSameDay(e.getCalendar(), clickedCal)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void showFertileData(Date date) {
        binding.layoutIllustrationMenstrual.setBackground(ContextCompat.getDrawable(this, R.drawable.rect_gradient_green));
        binding.ivStatusMenstrual.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baby));
        binding.tvDate.setTextColor(getColor(R.color.white));
        binding.tvDate.setText(new SimpleDateFormat("MMMM dd").format(date));
        binding.layoutMenstrual.setVisibility(View.VISIBLE);
        binding.tvLabelMenstrual.setText(getString(R.string.dummy_status_fertile));
    }

    private void showMenstrualData(Date date) {
        binding.layoutIllustrationMenstrual.setBackground(ContextCompat.getDrawable(this, R.drawable.rect_gradient_red));
        binding.ivStatusMenstrual.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_blood));
        binding.tvDate.setTextColor(getColor(R.color.white));
        binding.tvDate.setText(new SimpleDateFormat("MMMM dd").format(date));
        binding.tvLabelMenstrual.setVisibility(View.VISIBLE);
        binding.tvLabelMenstrual.setText(getString(R.string.dummy_status_menstrual));
    }

    private void showNormalData(Date date) {
        binding.layoutIllustrationMenstrual.setBackground(ContextCompat.getDrawable(this, R.color.bg));
        binding.ivStatusMenstrual.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_doctor));
        binding.tvDate.setTextColor(getColor(R.color.black));
        binding.tvDate.setText(new SimpleDateFormat("MMMM dd").format(date));
        binding.tvLabelMenstrual.setVisibility(View.GONE);
    }

    public void addMenstrualPeriod(MenstrualPeriodResumeIntent menstrualPeriodResume, Date date) {
        String month = new SimpleDateFormat("MMMM", Locale.US).format(date);
        addMenstrualResumeDataMonth(month, menstrualPeriodResume);

        //TODO add to DB
        new AddData(menstrualPeriodResume).execute();
    }

    public MenstrualPeriodResumeIntent getMenstrualResumeDataCurrentMonth(String month) {
        switch (month) {
            case Const.KEY_JAN:
                return activeYearData != null ? activeYearData.getJan() : null;
            case Const.KEY_FEB:
                return activeYearData != null ? activeYearData.getFeb() : null;
            case Const.KEY_MAR:
                return activeYearData != null ? activeYearData.getMar() : null;
            case Const.KEY_APR:
                return activeYearData != null ? activeYearData.getApr() : null;
            case Const.KEY_MAY:
                return activeYearData != null ? activeYearData.getMay() : null;
            case Const.KEY_JUN:
                return activeYearData != null ? activeYearData.getJun() : null;
            case Const.KEY_JUL:
                return activeYearData != null ? activeYearData.getJul() : null;
            case Const.KEY_AUG:
                return activeYearData != null ? activeYearData.getAug() : null;
            case Const.KEY_SEP:
                return activeYearData != null ? activeYearData.getSep() : null;
            case Const.KEY_OCT:
                return activeYearData != null ? activeYearData.getOct() : null;
            case Const.KEY_NOV:
                return activeYearData != null ? activeYearData.getNov() : null;
            case Const.KEY_DEC:
                return activeYearData != null ? activeYearData.getDec() : null;
            default:
                return null;
        }
    }

    private void addMenstrualResumeDataMonth(String month, MenstrualPeriodResumeIntent data) {
        switch (month) {
            case Const.KEY_JAN:
                if (activeYearData != null) {
                    activeYearData.setJan(data);
                }
                break;
            case Const.KEY_FEB:
                if (activeYearData != null) {
                    activeYearData.setFeb(data);
                }
                break;
            case Const.KEY_MAR:
                if (activeYearData != null) {
                    activeYearData.setMar(data);
                }
                break;
            case Const.KEY_APR:
                if (activeYearData != null) {
                    activeYearData.setApr(data);
                }
                break;
            case Const.KEY_MAY:
                if (activeYearData != null) {
                    activeYearData.setMay(data);
                }
                break;
            case Const.KEY_JUN:
                if (activeYearData != null) {
                    activeYearData.setJun(data);
                }
                break;
            case Const.KEY_JUL:
                if (activeYearData != null) {
                    activeYearData.setJul(data);
                }
                break;
            case Const.KEY_AUG:
                if (activeYearData != null) {
                    activeYearData.setAug(data);
                }
                break;
            case Const.KEY_SEP:
                if (activeYearData != null) {
                    activeYearData.setSep(data);
                }
                break;
            case Const.KEY_OCT:
                if (activeYearData != null) {
                    activeYearData.setOct(data);
                }
                break;
            case Const.KEY_NOV:
                if (activeYearData != null) {
                    activeYearData.setNov(data);
                }
                break;
            case Const.KEY_DEC:
                if (activeYearData != null) {
                    activeYearData.setDec(data);
                }
                break;
        }
    }

    public class LoadData extends AsyncTask<Void, Void, MenstrualPeriodMonthlyIntent> {
        String year;

        public LoadData(String year) {
            this.year = year;
        }

        @Override
        protected MenstrualPeriodMonthlyIntent doInBackground(Void... voids) {

            activeYear = year;
            periodLong = 0;
            activeMonth = 0;
            MenstrualPeriodMonthlyIntent menstrualPeriodMonthly = new MenstrualPeriodMonthlyIntent();

            List<MenstrualPeriodResumeIntent> dbList = AppDatabase.getDatabase(MenstrualActivity.this).getDao().getAllMenstrualPeriodData();

            for (int i = 0; i < dbList.size(); i++) {
                MenstrualPeriodResumeIntent data = dbList.get(i);

                if (data != null) {
                    if (maxCycleLong == 0 && minCycleLong == 0) {
                        maxCycleLong = data.getLongCycle();
                        minCycleLong = data.getLongCycle();
                    }
                    if (data.getLongCycle() > maxCycleLong) {
                        maxCycleLong = data.getLongCycle();
                    }
                    if (data.getLongCycle() < minCycleLong) {
                        minCycleLong = data.getLongCycle();
                    }
                    periodLong += data.getLongPeriod();
                    activeMonth++;
                }

                Log.e("TAG", "doInBackground: " + data.getMonth());
                switch (data.getMonth()) {
                    case Const.KEY_JAN:
                        menstrualPeriodMonthly.setJan(data);
                        break;
                    case Const.KEY_FEB:
                        menstrualPeriodMonthly.setFeb(data);
                        break;
                    case Const.KEY_MAR:
                        menstrualPeriodMonthly.setMar(data);
                        break;
                    case Const.KEY_APR:
                        menstrualPeriodMonthly.setApr(data);
                        break;
                    case Const.KEY_MAY:
                        menstrualPeriodMonthly.setMay(data);
                        break;
                    case Const.KEY_JUN:
                        menstrualPeriodMonthly.setJun(data);
                        break;
                    case Const.KEY_JUL:
                        menstrualPeriodMonthly.setJul(data);
                        break;
                    case Const.KEY_AUG:
                        menstrualPeriodMonthly.setAug(data);
                        break;
                    case Const.KEY_SEP:
                        menstrualPeriodMonthly.setSep(data);
                        break;
                    case Const.KEY_OCT:
                        menstrualPeriodMonthly.setOct(data);
                        break;
                    case Const.KEY_NOV:
                        menstrualPeriodMonthly.setNov(data);
                        break;
                    case Const.KEY_DEC:
                        menstrualPeriodMonthly.setDec(data);
                        break;
                }
            }
            try {
                periodLong /= activeMonth;
            } catch (ArithmeticException e) {
                e.printStackTrace();
                periodLong = 0;
                activeMonth = 0;
            }

            return menstrualPeriodMonthly;
        }

        @Override
        protected void onPostExecute(MenstrualPeriodMonthlyIntent menstrualPeriodMonthlyIntent) {
            super.onPostExecute(menstrualPeriodMonthlyIntent);
            setupMenstrualMonthly(menstrualPeriodMonthlyIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MenstrualEditActivity.REQ_CODE_EDIT) {
            if (resultCode == MenstrualEditActivity.RES_CODE_EDIT) {
                String year = new SimpleDateFormat("yyyy").format(binding.calendarMenstrual.getCurrentPageDate().getTime());
                getMenstrualPeriod(year);
            } else {
                finish();
            }
        }
    }

    public class AddData extends AsyncTask<Void, Void, Void> {

        MenstrualPeriodResumeIntent menstrualPeriodResume;

        public AddData(MenstrualPeriodResumeIntent menstrualPeriodResume) {
            this.menstrualPeriodResume = menstrualPeriodResume;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (!isRecordExists(menstrualPeriodResume.getYear(), menstrualPeriodResume.getMonth())) {
                AppDatabase.getDatabase(MenstrualActivity.this).getDao().insertData(menstrualPeriodResume);
            }
            return null;
        }

        // Create a method to check if a record with the same year and month exists
        private boolean isRecordExists(String year, String month) {
            List<MenstrualPeriodResumeIntent> recordsLiveData = AppDatabase.getDatabase(MenstrualActivity.this).getDao().getRecordsByYearAndMonth(year, month);
            List<MenstrualPeriodResumeIntent> records = recordsLiveData;
            return records != null && !records.isEmpty();
        }

    }

}
