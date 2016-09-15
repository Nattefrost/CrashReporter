package com.example.crashreporterlib;

import android.app.Application;

import java.util.List;

/**
 * Created by Benoit on 14/09/2016.
 */
public class CrashReporter  {
    private static CrashReporter sReporter;
    private Application mApp;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashReporter(Application app){
        this.mApp=app;

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(mDefaultHandler));


    }
    public static void init(Application app){
        if( sReporter == null) sReporter = new CrashReporter(app);
    }
    public static List<ExceptionLog> getExceptions(){
        return null;
    }
    public static void sendExceptions(String mail){
    }
    public static void reset(){

    }

}
