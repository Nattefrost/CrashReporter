package com.example.crashreporterlib;

import java.util.Date;

/**
 * Created by Benoit on 14/09/2016.
 */
public class ExceptionLog {

    private string mStacktrace;
    private Date mDate;

    ExceptionLog (string stackTrace,Date date){
        this.mDate=date;
        this.mStacktrace=stackTrace;
    }
    public string getStacktrace() {
        return mStacktrace;
    }

    public Date getDate() {
        return mDate;
    }
}
