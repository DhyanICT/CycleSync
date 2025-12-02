package com.code.chatboat.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.code.chatboat.model.MenstrualPeriodResumeIntent;

import java.util.List;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(MenstrualPeriodResumeIntent data);

    @Query("SELECT * FROM MenstrualPeriodResumeIntent")
    LiveData<List<MenstrualPeriodResumeIntent>> getMenstrualPeriodDataAll();

    @Query("SELECT * FROM MenstrualPeriodResumeIntent")
    List<MenstrualPeriodResumeIntent> getAllMenstrualPeriodData();

    @Query("SELECT * FROM MenstrualPeriodResumeIntent WHERE year = :year AND month = :month")
    List<MenstrualPeriodResumeIntent> getRecordsByYearAndMonth(String year, String month);


}
