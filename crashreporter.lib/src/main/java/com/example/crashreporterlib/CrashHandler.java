package com.example.crashreporterlib;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Benoit on 14/09/2016.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    Thread.UncaughtExceptionHandler mBaseHandler;

    CrashHandler(Thread.UncaughtExceptionHandler baseHandler){
        super();
        mBaseHandler = baseHandler;
    }
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
        ExceptionLog ex = new ExceptionLog(builder.toString(),new Date(System.currentTimeMillis()));
        ex.Save();
        mBaseHandler.uncaughtException(t,e);
    }
}
