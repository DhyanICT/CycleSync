//package com.code.chatboat.activity;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import android.view.View;
//
//import com.code.chatboat.BaseActivity;
//import com.code.chatboat.databinding.ActivityTrackerBinding;
//import com.code.chatboat.model.MenstrualPeriodResumeIntent;
//import com.code.chatboat.room.AppDatabase;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class TrackerActivity extends BaseActivity<ActivityTrackerBinding> {
//
//    private String firstDayPeriod = "";
//    private String nextMonthPeriod = "";
//    private String currentDayPeriod = "";
//
//    @Override
//    protected ActivityTrackerBinding getViewBinding() {
//        return ActivityTrackerBinding.inflate(getLayoutInflater());
//    }
//
//    @Override
//    protected void initData() {
//
//        binding.ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        new LoadData().execute();
//
//
//    }
//
//    public class LoadData extends AsyncTask<Void, Void, Void> {
//
//        public LoadData() {
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            Calendar calendar = Calendar.getInstance();
//            String currentMonthName = new SimpleDateFormat("MMMM", Locale.US).format(calendar.getTime());
//            calendar.add(Calendar.MONTH, 1);
//            String nextMonthName = new SimpleDateFormat("MMMM", Locale.US).format(calendar.getTime());
//            int currentYear = calendar.get(Calendar.YEAR);
//            List<MenstrualPeriodResumeIntent> list = AppDatabase.getDatabase(TrackerActivity.this).getDao().getRecordsByYearAndMonth(String.valueOf(currentYear), currentMonthName);
//            List<MenstrualPeriodResumeIntent> listnext = AppDatabase.getDatabase(TrackerActivity.this).getDao().getRecordsByYearAndMonth(String.valueOf(currentYear), nextMonthName);
//            if (list.size() > 0) {
//                if(listnext.size()>0){
//                    String inputDate = listnext.get(0).getFirstDayPeriod();
//                    try {
//                        String inputDateCurrent = list.get(0).getFirstDayPeriod();
//                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//                        Date date = inputFormat.parse(inputDate);
//                        Date dateCurrent = inputFormat.parse(inputDateCurrent);
//                        firstDayPeriod = formatDate(dateCurrent);
//                        nextMonthPeriod = formatDate(date);
//                        currentDayPeriod = calculateDay(list.get(0).getFirstDayPeriod(), list.get(0).getLastDayPeriod(), list.get(0).getLongPeriod());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }else {
//                    try {
//                        String inputDateCurrent = list.get(0).getFirstDayPeriod();
//                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//                        Date dateCurrent = inputFormat.parse(inputDateCurrent);
//                        firstDayPeriod = formatDate(dateCurrent);
//                        nextMonthPeriod =  "No Data Found" ;
//                        currentDayPeriod = calculateDay(list.get(0).getFirstDayPeriod(), list.get(0).getLastDayPeriod(), list.get(0).getLongPeriod());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }else {
//                firstDayPeriod = "No Data Found";
//                nextMonthPeriod =  "No Data Found";
//                currentDayPeriod = "No Data Found";
//
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//            super.onPostExecute(unused);
//
//            binding.currentPeriodDay.setText(currentDayPeriod);
//            binding.nextMonthPeriod.setText(nextMonthPeriod);
//        }
//
//
//        private String formatDate(Date date) {
//            SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.US);
//            String day = dayFormat.format(date);
//
//            String month;
//            SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.US);
//            month = monthFormat.format(date);
//
//            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.US);
//            String year = yearFormat.format(date);
//
//            return day + getDayOfMonthSuffix(Integer.parseInt(day)) + " " + month;
//        }
//
//        private String getDayOfMonthSuffix(int day) {
//            if (day >= 11 && day <= 13) {
//                return "th";
//            }
//            switch (day % 10) {
//                case 1:
//                    return "st";
//                case 2:
//                    return "nd";
//                case 3:
//                    return "rd";
//                default:
//                    return "th";
//            }
//        }
//
//        private String calculateDay(String firstDayString, String lastDayString, int length) {
//
//            // Parse the date strings into Date objects
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//            Date firstDay = null, lastDay = null;
//
//            try {
//                firstDay = dateFormat.parse(firstDayString);
//                lastDay = dateFormat.parse(lastDayString);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // Calculate the days between the two dates
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(firstDay);
//
//            int dayCount = 1; // Start with the first day
//            while (calendar.getTime().before(lastDay)) {
//                calendar.add(Calendar.DAY_OF_MONTH, 1);
//                dayCount++;
//            }
//
//            // Determine the ordinal number for the day
//            String ordinal;
//            switch (dayCount) {
//                case 1:
//                    ordinal = "first";
//                    break;
//                case 2:
//                    ordinal = "second";
//                    break;
//                case 3:
//                    ordinal = "third";
//                    break;
//                default:
//                    ordinal = dayCount + "th";
//                    break;
//            }
//
//            Log.e("TAG", "calculateDay: length" + length);
//            Log.e("TAG", "calculateDay: dayCount" + dayCount);
//            if (dayCount > length) {
//                Log.e("TAG", "calculateDay: " + firstDayString);
//                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//                Date date = null;
//                try {
//                    date = inputFormat.parse(firstDayString);
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//                return "Your period was \n" + formatDate(date);
//            }
//            // Print the result
//            System.out.println("The day is the " + ordinal + " day between the first day and last day of the period.");
//
//            return ordinal;
//        }
//    }
//
//}
package com.code.chatboat.activity;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.code.chatboat.BaseActivity;
import com.code.chatboat.databinding.ActivityTrackerBinding;
import com.code.chatboat.model.MenstrualPeriodResumeIntent;
import com.code.chatboat.room.AppDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TrackerActivity extends BaseActivity<ActivityTrackerBinding> {

    private String firstDayPeriod = "";
    private String nextMonthPeriod = "";
    private String currentDayPeriod = "";

    @Override
    protected ActivityTrackerBinding getViewBinding() {
        return ActivityTrackerBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        new LoadData().execute();
    }

    public class LoadData extends AsyncTask<Void, Void, Void> {

        public LoadData() {}

        @Override
        protected Void doInBackground(Void... voids) {

            Calendar calendar = Calendar.getInstance();

            // ✅ Get current month & year before moving calendar
            String currentMonthName = new SimpleDateFormat("MMMM", Locale.US)
                    .format(calendar.getTime());
            int currentYear = calendar.get(Calendar.YEAR);

            // ✅ Get next month & year separately
            calendar.add(Calendar.MONTH, 1);
            String nextMonthName = new SimpleDateFormat("MMMM", Locale.US)
                    .format(calendar.getTime());
            int nextYear = calendar.get(Calendar.YEAR);

            // ✅ Query DB with correct month-year pairs
            List<MenstrualPeriodResumeIntent> list =
                    AppDatabase.getDatabase(TrackerActivity.this)
                            .getDao()
                            .getRecordsByYearAndMonth(String.valueOf(currentYear), currentMonthName);

            List<MenstrualPeriodResumeIntent> listnext =
                    AppDatabase.getDatabase(TrackerActivity.this)
                            .getDao()
                            .getRecordsByYearAndMonth(String.valueOf(nextYear), nextMonthName);

            if (list.size() > 0) {
                if (listnext.size() > 0) {
                    String inputDate = listnext.get(0).getFirstDayPeriod();
                    try {
                        String inputDateCurrent = list.get(0).getFirstDayPeriod();
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        Date date = inputFormat.parse(inputDate);
                        Date dateCurrent = inputFormat.parse(inputDateCurrent);
                        firstDayPeriod = formatDate(dateCurrent);
                        nextMonthPeriod = formatDate(date);
                        currentDayPeriod = calculateDay(list.get(0).getFirstDayPeriod(),
                                list.get(0).getLastDayPeriod(),
                                list.get(0).getLongPeriod());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        String inputDateCurrent = list.get(0).getFirstDayPeriod();
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        Date dateCurrent = inputFormat.parse(inputDateCurrent);
                        firstDayPeriod = formatDate(dateCurrent);
                        nextMonthPeriod = "No Data Found";
                        currentDayPeriod = calculateDay(list.get(0).getFirstDayPeriod(),
                                list.get(0).getLastDayPeriod(),
                                list.get(0).getLongPeriod());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                firstDayPeriod = "No Data Found";
                nextMonthPeriod = "No Data Found";
                currentDayPeriod = "No Data Found";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            binding.currentPeriodDay.setText(currentDayPeriod);
            binding.nextMonthPeriod.setText(nextMonthPeriod);
        }

        private String formatDate(Date date) {
            SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.US);
            String day = dayFormat.format(date);
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.US);
            String month = monthFormat.format(date);
            return day + getDayOfMonthSuffix(Integer.parseInt(day)) + " " + month;
        }

        private String getDayOfMonthSuffix(int day) {
            if (day >= 11 && day <= 13) {
                return "th";
            }
            switch (day % 10) {
                case 1:
                    return "st";
                case 2:
                    return "nd";
                case 3:
                    return "rd";
                default:
                    return "th";
            }
        }

        private String calculateDay(String firstDayString, String lastDayString, int length) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date firstDay = null, lastDay = null;

            try {
                firstDay = dateFormat.parse(firstDayString);
                lastDay = dateFormat.parse(lastDayString);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(firstDay);

            int dayCount = 1;
            while (calendar.getTime().before(lastDay)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                dayCount++;
            }

            String ordinal;
            switch (dayCount) {
                case 1:
                    ordinal = "first";
                    break;
                case 2:
                    ordinal = "second";
                    break;
                case 3:
                    ordinal = "third";
                    break;
                default:
                    ordinal = dayCount + "th";
                    break;
            }

            Log.e("TAG", "calculateDay: length " + length);
            Log.e("TAG", "calculateDay: dayCount " + dayCount);

            if (dayCount > length) {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date = null;
                try {
                    date = inputFormat.parse(firstDayString);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                return "Your period was \n" + formatDate(date);
            }

            return ordinal;
        }
    }
}
