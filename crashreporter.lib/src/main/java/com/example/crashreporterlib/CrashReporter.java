package com.example.crashreporterlib;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

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
     * @param app The application to attach the reporter to
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
            sb.append("CRASH DATE : " + report.getDate());
            sb.append(System.getProperty("line.separator" ));
            sb.append(report.getStacktrace());
            sb.append("########################################## LOG END ##########################################" );
        }
        String deviceInfo = getInfoAboutDevice();
        sb.append("DEVICE INFOS : ");
        sb.append(System.getProperty("line.separator" ));
        sb.append(deviceInfo);

        String body = sb.toString();
        // Process all the reports
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", targetMail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Application crash report");
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        getReporter().mApp.startActivity(Intent.createChooser(emailIntent, "Send email..."));

        reset();

    }

    /** Retrieves information about device
     * @return string describing various device information
     * Adapted from https://github.com/simon-heinen/SimpleUi/blob/master/SimpleUI/srcAndroid/simpleui/util/DeviceInformation.java
     */
    public static String getInfoAboutDevice() {
        String s = "";

        s += "\n OS Version: " + System.getProperty("os.version") + " ("
                + android.os.Build.VERSION.INCREMENTAL + ")";
        s += "\n OS API Level: " + android.os.Build.VERSION.SDK;
        s += "\n Device: " + android.os.Build.DEVICE;
        s += "\n Model (and Product): " + android.os.Build.MODEL + " ("
                + android.os.Build.PRODUCT + ")";

        s += "\n Manufacturer: " + android.os.Build.MANUFACTURER;
        s += "\n Other TAGS: " + android.os.Build.TAGS;
        s += "\n SD Card state: " + Environment.getExternalStorageState();

        Properties p = System.getProperties();
        Enumeration keys = p.keys();
        String key = "";
        while (keys.hasMoreElements()) {
            key = (String) keys.nextElement();
            s += "\n > " + key + " = " + (String) p.get(key);
        }
        return s;
    }

    /** Removes all the crashes logged in the DB
     */
    public static void reset(){
        DbHandler.deleteAllReports();
    }

}
