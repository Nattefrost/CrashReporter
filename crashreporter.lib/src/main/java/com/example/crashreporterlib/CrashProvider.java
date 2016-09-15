package com.example.crashreporterlib;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Benoit on 15/09/2016.
 */
public class CrashProvider extends ContentProvider {
    Context mCtx;
    DbHandler mDb;
    String AUTHORITY;
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    Uri CONTENT_URI;
    final int TASKS_LIST = 1;
    final int TASKS_ITEM = 2;
    String CONTENT_TYPE ;
    String CONTENT_ITEM_TYPE ;
    final String TABLE = DbHandler.TABLE_REPORTS;

    public CrashProvider(){
        super();
    }
    @Override
    public boolean onCreate() {
        mCtx = getContext();
        mDb = new DbHandler(mCtx);
        AUTHORITY =  mCtx.getPackageName();
        CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+ TABLE);
        CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/CrashReporter.Reports";
        CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/CrashReporter/Reports";
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case TASKS_LIST:
                cursor = DbHandler.getExceptionsCursor();
                break;

            case TASKS_ITEM:
                cursor = DbHandler.getExceptionCursorById(Integer.parseInt(uri.getLastPathSegment()));
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TASKS_LIST:
                return CONTENT_TYPE;

            case TASKS_ITEM:
                return CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }
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
