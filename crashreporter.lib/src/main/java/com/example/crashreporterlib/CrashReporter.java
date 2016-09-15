package com.example.crashreporterlib;

import android.app.Application;

import java.util.List;

/**
 * Created by Benoit on 14/09/2016.
 * Class that handles the Crash reporting of an Android application built s Singleton
 */
public class CrashReporter  {
    private static CrashReporter sReporter;

    Application getApp() {
        return mApp;
    }

    private Application mApp;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /** Private constructor , can't eb called from the outside, should be called by the Init Method
     * @param app The application to attach th reporter to
    **/
    private CrashReporter(Application app){
        this.mApp=app;
        //Retrieve the default UncaughtExceptionHandler to call it after treatment
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(mDefaultHandler));

    }

    /**Instantiates an new CrashReporter
     * @param app The application to attach th reporter to
     */
    public static void init(Application app){
        if( sReporter == null) sReporter = new CrashReporter(app);
    }

    /**Returns a List of all the crashes logged in the DB
     *
     * @return a list of Exceptionlog
     * @see ExceptionLog
     */
    public static List<ExceptionLog> getExceptions(){
        return null;
    }

    /**Sends all the crashes logged in the DB to the provided email
     * @param mail the address to send the mail to
     */
    public static void sendExceptions(String mail){


        reset();

    }

    /** Removes all the crashes logged in the DB
     */
    public static void reset(){

    }

}
