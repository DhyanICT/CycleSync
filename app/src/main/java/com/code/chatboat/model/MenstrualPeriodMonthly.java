package com.code.chatboat.model;

public class MenstrualPeriodMonthly {
    private MenstrualPeriodResume jan;
    private MenstrualPeriodResume feb;
    private MenstrualPeriodResume mar;
    private MenstrualPeriodResume apr;
    private MenstrualPeriodResume may;
    private MenstrualPeriodResume jun;
    private MenstrualPeriodResume jul;
    private MenstrualPeriodResume aug;
    private MenstrualPeriodResume sep;
    private MenstrualPeriodResume oct;
    private MenstrualPeriodResume nov;
    private MenstrualPeriodResume dec;

    public MenstrualPeriodMonthly() {
        // Default constructor
    }

    public MenstrualPeriodMonthly(MenstrualPeriodResume jan, MenstrualPeriodResume feb, MenstrualPeriodResume mar, MenstrualPeriodResume apr, MenstrualPeriodResume may, MenstrualPeriodResume jun, MenstrualPeriodResume jul, MenstrualPeriodResume aug, MenstrualPeriodResume sep, MenstrualPeriodResume oct, MenstrualPeriodResume nov, MenstrualPeriodResume dec) {
        this.jan = jan;
        this.feb = feb;
        this.mar = mar;
        this.apr = apr;
        this.may = may;
        this.jun = jun;
        this.jul = jul;
        this.aug = aug;
        this.sep = sep;
        this.oct = oct;
        this.nov = nov;
        this.dec = dec;
    }

    public MenstrualPeriodResume getJan() {
        return jan;
    }

    public void setJan(MenstrualPeriodResume jan) {
        this.jan = jan;
    }

    public MenstrualPeriodResume getFeb() {
        return feb;
    }

    public void setFeb(MenstrualPeriodResume feb) {
        this.feb = feb;
    }

    public MenstrualPeriodResume getMar() {
        return mar;
    }

    public void setMar(MenstrualPeriodResume mar) {
        this.mar = mar;
    }

    public MenstrualPeriodResume getApr() {
        return apr;
    }

    public void setApr(MenstrualPeriodResume apr) {
        this.apr = apr;
    }

    public MenstrualPeriodResume getMay() {
        return may;
    }

    public void setMay(MenstrualPeriodResume may) {
        this.may = may;
    }

    public MenstrualPeriodResume getJun() {
        return jun;
    }

    public void setJun(MenstrualPeriodResume jun) {
        this.jun = jun;
    }

    public MenstrualPeriodResume getJul() {
        return jul;
    }

    public void setJul(MenstrualPeriodResume jul) {
        this.jul = jul;
    }

    public MenstrualPeriodResume getAug() {
        return aug;
    }

    public void setAug(MenstrualPeriodResume aug) {
        this.aug = aug;
    }

    public MenstrualPeriodResume getSep() {
        return sep;
    }

    public void setSep(MenstrualPeriodResume sep) {
        this.sep = sep;
    }

    public MenstrualPeriodResume getOct() {
        return oct;
    }

    public void setOct(MenstrualPeriodResume oct) {
        this.oct = oct;
    }

    public MenstrualPeriodResume getNov() {
        return nov;
    }

    public void setNov(MenstrualPeriodResume nov) {
        this.nov = nov;
    }

    public MenstrualPeriodResume getDec() {
        return dec;
    }

    public void setDec(MenstrualPeriodResume dec) {
        this.dec = dec;
    }

    // Getters and setters for the fields (omitted for brevity)
}