package com.example.crashreporterlib;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Benoit on 14/09/2016.
 * Class that implements the UncaughtExceptionHandler, to save a crash datas into DB
 */
class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mBaseHandler;

    CrashHandler(Thread.UncaughtExceptionHandler baseHandler){
        super();
        mBaseHandler = baseHandler;
    }
    /**Retrieves all the informations of an uncaughtException then creates a string of its stackTrace nd saves it into the DB
    **/
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //reading all lines of the stacktrace
        StackTraceElement[] arr = e.getStackTrace();
        StringBuilder builder = new StringBuilder();

        builder.append("--STACKTRACE");
        builder.append( System.lineSeparator());

        builder.append(e.toString());
        builder.append( System.lineSeparator());

        builder.append(e.getMessage());
        builder.append( System.lineSeparator());

        for (StackTraceElement elem : arr) {
            builder.append(elem.toString());
            builder.append(";"+ System.lineSeparator());
        }
        //IF the exception is thrown by ad AsyncTask

        Throwable cause = e.getCause();
        if(cause != null) {
            builder.append("--CAUSE");
            builder.append( System.lineSeparator());
            builder.append(cause.toString());
            builder.append(System.lineSeparator());

            arr = cause.getStackTrace();
            for (StackTraceElement elem : arr) {
                builder.append(elem.toString());
                builder.append(";"+ System.lineSeparator());
            }
        }
        //Create the object to save in the db
        ExceptionLog ex = new ExceptionLog(builder.toString(),new Date(System.currentTimeMillis()));
        //Save it
        ex.Save();
        //Calling the original handler to do his ob
        mBaseHandler.uncaughtException(t,e);
    }
}
