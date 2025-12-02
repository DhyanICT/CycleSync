package com.code.chatboat.model;

public class MenstrualPeriodResume {
    private int id = 0;
    private String year;
    private String month;
    private String firstDayPeriod;
    private String lastDayPeriod;
    private String firstDayFertile;
    private String lastDayFertile;
    private int firstDayFertileDay;
    private int lastDayFertileDay;
    private int longCycle;
    private int longPeriod;
    private boolean isEdit;

    public MenstrualPeriodResume() {
        // Default constructor
    }

    public MenstrualPeriodResume(int id,String year, String month, String firstDayPeriod, String lastDayPeriod,
                                 String firstDayFertile, String lastDayFertile, int firstDayFertileDay,
                                 int lastDayFertileDay, int longCycle, int longPeriod, boolean isEdit) {
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
