package com.example.crashreporterlib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Damien Escande on 15/09/2016.
 */
public class DbHandler extends SQLiteOpenHelper {
    // STATIC VARS
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reports";
    private static final String TABLE_REPORTS = "REPORTS";
    private static final String KEY_ID = "id";
    private static final String KEY_STACK_TRACE = "stack_trace";
    private static final String KEY_DATE = "crash_date";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // region Overriding parent
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE REPORTS(id PRIMARY KEY UNIQUE AUTOINCREMENT NOT NULL, stack_trace TEXT NOT NULL, crash_date TEXT NOT NULL );";
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.close();
    }

    // Upgrading database, won't be used
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    //endregion

    /** Inserts Report into DB
     *
     * @return void
     * @see ExceptionLog
     */
    public static void addReport(ExceptionLog report) {
        DbHandler dh = new DbHandler(CrashReporter.getReporter().getApp());
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        String stackTrace = report.getStacktrace();
        String date = String.valueOf(report.getDate());
        values.put(KEY_STACK_TRACE, stackTrace);
        values.put(KEY_DATE, date);
        // Actually inserting
        db.insert(TABLE_REPORTS, null, values );
        db.close();
    }

    /** Gets a single Report by id
     *
     * @return ExceptionLog
     * @see ExceptionLog
     */
    public static ExceptionLog getReportById(int id) {
        DbHandler dh = new DbHandler(CrashReporter.getReporter().getApp());
        SQLiteDatabase db = dh.getReadableDatabase();
        String query = "SELECT * FROM" + TABLE_REPORTS + "WHERE" + KEY_ID + "=" + id + ";";
        Cursor cursor = db.rawQuery(query, null);
        int reportId = cursor.getInt(0);
        String stackTrace = cursor.getString(1);
        Date reportDate = new Date(cursor.getString(2));
        cursor.close();
        db.close();
        return new ExceptionLog(reportId, stackTrace, reportDate);
    }

    // Getting All Contacts
    /** Gets a list of all Reports in DB
     *
     * @return List of ExceptionLog
     * @see ExceptionLog
     */
    public static List<ExceptionLog> getAllReports() {
        DbHandler dh = new DbHandler(CrashReporter.getReporter().getApp());
        List<ExceptionLog> reportList = new ArrayList<>();
        String selectQuery = "SELECT * FROM" + TABLE_REPORTS + ";";
        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String stackTrace = cursor.getString(1);
                Date crashDate = new Date(cursor.getString(2));
                ExceptionLog report = new ExceptionLog(id, stackTrace, crashDate);
                reportList.add(report);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return reportList;
    }

    /** Cleans the whole db
     *
     * @return void
     * @see ExceptionLog
     */
    public static void deleteAllReports() {
        DbHandler dh = new DbHandler(CrashReporter.getReporter().getApp());
        SQLiteDatabase db = dh.getWritableDatabase();
        String DELETE_ALL_CONTACTS = "DELETE FROM" + TABLE_REPORTS + "WHERE id >= 0;";
        db.execSQL(DELETE_ALL_CONTACTS);
        db.close();
    }
}
