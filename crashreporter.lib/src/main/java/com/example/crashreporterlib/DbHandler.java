package com.example.crashreporterlib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Damien Escande on 15/09/2016.
 */
public class DbHandler extends SQLiteOpenHelper {
    // STATIC VARS
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reports";
    private static final String TABLE_REPORTS = "REPORTS";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE REPORTS(id PRIMARY KEY UNIQUE AUTOINCREMENT NOT NULL, stack_trace TEXT NOT NULL, crash_date TEXT NOT NULL );";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database, won't be used
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_REPORTS);
        onCreate(db);
    }
    // Add a report
    public static void addReport(ExceptionLog report) {
        DbHandler db = new DbHandler(CrashHandler.class);
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // Actually inserting
        db.insert(TABLE_REPORTS, null, values);
        db.close();
    }

    // Getting All Contacts
    public List<ExceptionLog> getAllReports() {
        List<ExceptionLog> reportList = new ArrayList<ExceptionLog>();
        String selectQuery = "SELECT  * FROM " + TABLE_REPORTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ExceptionLog report = new Contact();
                //report.setID(Integer.parseInt(cursor.getString(0)));
                report.getStacktrace(cursor.getString(0));
                report.getDate(cursor.getString(1));
                // Adding contact to list
                reportList.add(report);
            } while (cursor.moveToNext());
        }

        return null;
    }

    public static void deleteAllReports(SQLiteDatabase db) {
        String DELETE_ALL_CONTACTS = "DELETE FROM REPORTS WHERE id >= 0;";
        db.execSQL(DELETE_ALL_CONTACTS);
    }
}
