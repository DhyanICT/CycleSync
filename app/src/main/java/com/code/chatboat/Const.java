package com.code.chatboat;

public class Const {
    // url
    public static final String URL_COVID19_CHECKUP = "https://www.halodoc.com/tanya-jawab-seputar-virus-corona/";
    public static final String URL_COVID19_API = "https://pomber.github.io/";
    public static final String URL_NEW_COVID19_API = "https://api.covid19api.com/";
    public static final String URL_NEWS_API = "https://newsapi.org/v2/";

    // database
    public static final String PREF_NAME = "PREF_NAME";
    public static final String DATABASE_NAME = "mediku_database";

    // provider
    public static final String PROVIDER_GOOGLE = "google.com";
    public static final String PROVIDER_FIREBASE = "firebase";
    public static final String PROVIDER_PASSWORD = "password";

    // image compress
    public static final double MAX_IMAGE_SIZE = 500.0;
    public static final int COMPRESS_QUALITY = 95;
    public static final int SAMPLE_SIZE = 2;

    // fireStore
    public static final String COLLECTION_USER = "users";
    public static final String COLLECTION_MEDICAL_RECORD = "medical_records";
    public static final String COLLECTION_MENSTRUAL_PERIOD = "menstrual_period";

    // medical record
    public static final String CATEGORY_SICK = "Sick";
    public static final String CATEGORY_CHECKUP = "Checkup";
    public static final String CATEGORY_HOSPITAL = "Hospital";

    // date format
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT_NO_TIME = "yyyy-MM-dd";
    public static final String TIME_GENERAL_HH_MM = "HH:mm";
    public static final String DAY_NAME_DATE_MONTH_NAME = "EEE, dd MMM";
    public static final String DAY_FULL_WITH_DATE_LOCALE = "EEE, dd MMM yyyy";
    public static final String DATE_ENGLISH_YYYY_MM_DD = "yyyy-M-d";
    public static final String DATE_FORMAT_NEW_COVID19 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSS'Z'";
    public static final String DATE_FORMAT_NEW_COVID19_2 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_FORMAT_SHOWN_COVID19 = "EEE, dd MMM yyyy - hh:mm";
    public static final String DATE_NEWS_SHOWN = "dd/MM/yyyy - hh:mm";

    // notification
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    public static final String NOTIFICATION_TYPE_COVID = "covid";

    // storage
    public static final String STORAGE_PROFILE_PHOTO = "profile_photos";

    // status new covid 19 data
    public static final String STATUS_NEW_COVID19_CONFIRMED = "confirmed";
    public static final String STATUS_NEW_COVID19_RECOVERED = "recovered";
    public static final String STATUS_NEW_COVID19_DEATHS = "deaths";

    // news
    public static final String API_KEY = "261d82dd7e26494e841fb1039a4fdaf7";
    public static final String NEWS_HEALTH = "health";

    // month key
    public static final String KEY_JAN = "January";
    public static final String KEY_FEB = "February";
    public static final String KEY_MAR = "March";
    public static final String KEY_APR = "April";
    public static final String KEY_MAY = "May";
    public static final String KEY_JUN = "June";
    public static final String KEY_JUL = "July";
    public static final String KEY_AUG = "August";
    public static final String KEY_SEP = "September";
    public static final String KEY_OCT = "October";
    public static final String KEY_NOV = "November";
    public static final String KEY_DEC = "December";

    // preferences
    public static final String PREF_FIREBASE_TOKEN = "PREF_FIREBASE_TOKEN";
    public static final String PREF_COUNTRY = "EXTRA_COUNTRY";
}
