package com.mamahow.runningappchecker;

import android.content.ContentResolver;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RunningAppChecker";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind service
//        Intent bindIntent = new Intent(this, MyService.class);
//        bindService(bindIntent, connection, BIND_AUTO_CREATE);

        // simple service start
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);

        //Keep wakeup
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//         final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
//        this.mWakeLock.acquire();



    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
