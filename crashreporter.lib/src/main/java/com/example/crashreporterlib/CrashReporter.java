package com.example.crashreporterlib;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Benoit on 14/09/2016.
 * Class that handles the Crash reporting of an Android application built s Singleton
 */
public class CrashReporter  {
    static CrashReporter getReporter() {
        return sReporter;
    }

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
     *
     * @param targetMail the address to send the mail to
     */
    public static void sendExceptions(String targetMail){

        StringBuilder sb = new StringBuilder();
        List<ExceptionLog> reportList = DbHandler.getAllReports();
        for (ExceptionLog report : reportList) {
            sb.append("CRASH DATE : " + report.getDate( ));
            sb.append(System.getProperty("line.separator"));
            sb.append(report.getStacktrace());
            sb.append("########################################## LOG END ##########################################");
        }

        String body = sb.toString();
        // Process all the reports
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", targetMail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Application crash report");
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        getReporter().mApp.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        reset();

    }

    /** Removes all the crashes logged in the DB
     */
    public static void reset(){
        DbHandler.deleteAllReports();
    }

}
