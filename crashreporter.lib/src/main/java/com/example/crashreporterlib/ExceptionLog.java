package com.example.crashreporterlib;

import java.util.Date;

/**
 * Created by Benoit on 14/09/2016.
 * Class used to store and manipulate datas rom crashes
 */
public class ExceptionLog {

    private String mStacktrace;
    private Date mDate;
    private int mId;

    public ExceptionLog (String stackTrace,Date date){
        this.mId=-1;
        this.mDate=date;
        this.mStacktrace=stackTrace;
    }
    public ExceptionLog (int id, String stackTrace,Date date){
        this.mId=id;
        this.mDate=date;
        this.mStacktrace=stackTrace;
    }
    public int getId(){return  mId ;}
    public String getStacktrace() {
        return mStacktrace;
    }

    public Date getDate() {
        return mDate;
    }

    /**Saves the object to the database
     **/
    public void Save(){
        DbHandler.addReport(this);
    }

}
