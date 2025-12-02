package com.code.chatboat.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class MenstrualPeriodResumeIntent implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo
    private int id = 0;
    @ColumnInfo
    private String year;
    @ColumnInfo
    private String month;
    @ColumnInfo
    private String firstDayPeriod;
    @ColumnInfo
    private String lastDayPeriod;
    @ColumnInfo
    private String firstDayFertile;
    @ColumnInfo
    private String lastDayFertile;
    @ColumnInfo
    private int firstDayFertileDay;
    @ColumnInfo
    private int lastDayFertileDay;
    @ColumnInfo
    private int longCycle;
    @ColumnInfo
    private int longPeriod;
    @ColumnInfo
    private boolean isEdit;

    public MenstrualPeriodResumeIntent() {
        // Default constructor
    }


    @Ignore
    public MenstrualPeriodResumeIntent(int id,String year, String month, String firstDayPeriod, String lastDayPeriod, String firstDayFertile, String lastDayFertile, int firstDayFertileDay, int lastDayFertileDay, int longCycle, int longPeriod, boolean isEdit) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.firstDayPeriod = firstDayPeriod;
        this.lastDayPeriod = lastDayPeriod;
        this.firstDayFertile = firstDayFertile;
        this.lastDayFertile = lastDayFertile;
        this.firstDayFertileDay = firstDayFertileDay;
        this.lastDayFertileDay = lastDayFertileDay;
        this.longCycle = longCycle;
        this.longPeriod = longPeriod;
        this.isEdit = isEdit;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFirstDayPeriod() {
        return firstDayPeriod;
    }

    public void setFirstDayPeriod(String firstDayPeriod) {
        this.firstDayPeriod = firstDayPeriod;
    }

    public String getLastDayPeriod() {
        return lastDayPeriod;
    }

    public void setLastDayPeriod(String lastDayPeriod) {
        this.lastDayPeriod = lastDayPeriod;
    }

    public String getFirstDayFertile() {
        return firstDayFertile;
    }

    public void setFirstDayFertile(String firstDayFertile) {
        this.firstDayFertile = firstDayFertile;
    }

    public String getLastDayFertile() {
        return lastDayFertile;
    }

    public void setLastDayFertile(String lastDayFertile) {
        this.lastDayFertile = lastDayFertile;
    }

    public int getFirstDayFertileDay() {
        return firstDayFertileDay;
    }

    public void setFirstDayFertileDay(int firstDayFertileDay) {
        this.firstDayFertileDay = firstDayFertileDay;
    }

    public int getLastDayFertileDay() {
        return lastDayFertileDay;
    }

    public void setLastDayFertileDay(int lastDayFertileDay) {
        this.lastDayFertileDay = lastDayFertileDay;
    }

    public int getLongCycle() {
        return longCycle;
    }

    public void setLongCycle(int longCycle) {
        this.longCycle = longCycle;
    }

    public int getLongPeriod() {
        return longPeriod;
    }

    public void setLongPeriod(int longPeriod) {
        this.longPeriod = longPeriod;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
