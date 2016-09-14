package com.example.crashreporterlib;

/**
 * Created by Benoit on 14/09/2016.
 */
public class CrashHandler extends Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
    }
}
