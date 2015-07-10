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
    protected PowerManager.WakeLock mWakeLock;

    //Variable to store brightness value
    private int brightness = 255;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;


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

        //Brightness
        //Get the content resolver
        cResolver = getContentResolver();

//Get the current window
        window = getWindow();

        try
        {
            // To handle the auto
            android.provider.Settings.System.putInt(cResolver,
                    android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE, android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            //Get the current system brightness
            brightness = android.provider.Settings.System.getInt(cResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS);
            Log.i("brightness", String.valueOf(brightness));
            android.provider.Settings.System.putInt(cResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
            //Get the current window attributes
            WindowManager.LayoutParams layoutpars = window.getAttributes();
            //Set the brightness of this window
            layoutpars.screenBrightness = 255;
            //Apply attribute changes to this window
            window.setAttributes(layoutpars);

        }
        catch (Settings.SettingNotFoundException e)
        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }
}
