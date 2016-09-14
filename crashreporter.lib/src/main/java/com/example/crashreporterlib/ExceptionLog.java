package com.example.crashreporterlib;

import java.util.Date;

/**
 * Created by Benoit on 14/09/2016.
 */
public class ExceptionLog {

    private String mStacktrace;
    private Date mDate;

    ExceptionLog (String stackTrace,Date date){
        this.mDate=date;
        this.mStacktrace=stackTrace;
    }
    public String getStacktrace() {
        return mStacktrace;
    }

    public Date getDate() {
        return mDate;
    }
    public void Save(){

    }
}
