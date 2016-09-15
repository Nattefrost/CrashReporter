package com.example.crashreporterlib;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Benoit on 15/09/2016.
 */
public class CrashProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        return false;
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
    public Uri insert(Uri uri, ContentValues values) {
        throw new OperationApplicationException("Not authorized")
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new OperationApplicationException("Not authorized")
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new OperationApplicationException("Not authorized")
    }
    //endregion
}
