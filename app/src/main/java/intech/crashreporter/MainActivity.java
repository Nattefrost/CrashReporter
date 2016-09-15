package intech.crashreporter;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.crashreporterlib.CrashReporter;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashReporter.init(getApplication());
        setContentView(R.layout.activity_main);

        Button b = (Button)findViewById(R.id.crashButton1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] myIntArray = {1,2,3};
                int res = myIntArray[4];
            }
        });
    }
}
