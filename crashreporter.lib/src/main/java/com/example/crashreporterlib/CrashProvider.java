package com.example.crashreporterlib;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Benoit on 15/09/2016.
 */
public class CrashProvider extends ContentProvider {
    Application mCtx;
    DbHandler mDb;
    final String AUTHORITY;
    final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    final Uri CONTENT_URI;
    final int TASKS_LIST = 1;
    final int TASKS_ITEM = 2;
    final String CONTENT_TYPE ;
    final String CONTENT_ITEM_TYPE ;
    final String TABLE;

    public CrashProvider(Application app){
        mCtx = app;
        AUTHORITY =  mCtx.getPackageName();
        TABLE = "REPORTS";
        CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+ TABLE);
        CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/CrashReporter.Reports";
        CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/CrashReporter/Reports";
    }
    @Override
    public boolean onCreate() {
        mDb = new DbHandler(mCtx);
        return mDb != null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }
    //region useless part
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)  {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
    //endregion
}
