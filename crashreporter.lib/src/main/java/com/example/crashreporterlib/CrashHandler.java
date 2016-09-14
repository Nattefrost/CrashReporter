package com.example.crashreporterlib;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Benoit on 14/09/2016.
 */
public class CrashHandler extends Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        StringBuilder builder = new StringBuilder();
        builder.append(e.getMessage());

        for (StackTraceElement elem : arr) {
            builder.append(elem.toString());
            builder.append(";"+ System.lineSeparator());
        }
        ExceptionLog ex = new ExceptionLog(builder.toString(),new Date(System.currentTimeMillis()));
        ex.Save();
    }
}
