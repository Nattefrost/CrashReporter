package intech.crashreporter;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.crashreporterlib.CrashReporter;
import com.example.crashreporterlib.DbHandler;
import com.example.crashreporterlib.ExceptionLog;

import java.util.List;

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
        Button c = (Button)findViewById(R.id.checkLogs);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ExceptionLog> errors = DbHandler.getAllReports();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                if(errors.size() ==0){
                    builder.setMessage("No Errors to display")
                            .setTitle("No Errors");
                }
                else{
                    builder.setMessage(errors.size() + " Errors to display")
                            .setTitle("Errors");
                }
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
