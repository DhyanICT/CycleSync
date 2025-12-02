package com.code.chatboat.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.code.chatboat.Const;
import com.code.chatboat.model.MenstrualPeriodResumeIntent;

@Database(entities = {MenstrualPeriodResumeIntent.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDao getDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        AppDatabase tempInstance = INSTANCE;
        if (tempInstance != null) {
            return tempInstance;
        }
        synchronized (AppDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, Const.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return INSTANCE;
        }
    }
}
