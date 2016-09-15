package com.example.multipleappscrashviewer;

import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.crashreporterlib.ExceptionLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProviderInfo validProvider = null;
        for (PackageInfo pack : getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS)) {
            ProviderInfo[] providers = pack.providers;
            if (providers != null) {
                for (ProviderInfo provider : providers) {
                    if(provider.name.equals("com.example.crashreporterlib.CrashProvider")){
                        validProvider=provider;
                    }
                }
            }
        }
        ArrayList<ExceptionLog> logs = new ArrayList<ExceptionLog>()
        if(validProvider!=null) {
            Cursor mCursor = getContentResolver().query(
                    Uri.parse("content://" + validProvider.authority), null, null, null, null);
            if (mCursor.moveToFirst()) {
                do {
                    int id = mCursor.getInt(0);
                    String stackTrace = mCursor.getString(1);
                    Date crashDate = new Date(mCursor.getString(2));
                    ExceptionLog report = new ExceptionLog(id, stackTrace, crashDate);
                    logs.add(report);
                } while (mCursor.moveToNext())
            }
        }
    }
}
