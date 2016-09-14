package com.example.crashreporterlib;

import android.app.Application;

import java.util.List;

/**
 * Created by Benoit on 14/09/2016.
 */
public class CrashReporter {
    private static CrashReporter sReporter;
    private Application mApp;

    private CrashReporter(Application app){
        this.mApp=app;

        Thread.setDefaultUncaughtExceptionHandler(CrashHandler);


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
