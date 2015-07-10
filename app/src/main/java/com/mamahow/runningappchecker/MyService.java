package com.mamahow.runningappchecker;

import android.app.ActivityManager;
import android.app.Service;
import android.util.Log;
import android.content.Intent;
import android.os.IBinder;
import android.content.Context;
import android.os.AsyncTask;
import java.util.List;
import java.net.URL;


/**
 * Created by fangyu on 6/27/15.
 */
public class MyService extends Service{

    private static final String TAG = "RunningAppChecker_svc";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        new RunningChecker().execute(null, null, null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        new RunningChecker().execute(null, null, null);
        // fangyu: you need to return START_STICKY to make sure service will restart without new intent
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO: clean up
        Log.d(TAG, "onDestroy() executed");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class RunningChecker extends AsyncTask<URL, Integer, Long> {
        protected Long doInBackground(URL... urls) {
            _runningChecker();
            return null;
        }
    }

    // Use AsyncTask or Runnable?
    private void _runningChecker() {
        int count = 0;
        while(true){
            while(!isRunning()) {
                count++;

                // invoke intent
                // TODO: change package name for your own app
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.tiggly.tales");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                Log.i(TAG, "App is NOT running, bring back my app again (" + count + ")");

                break;
            }
            // delay
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isRunning() {
        Context context = getBaseContext();

        //Log.i(TAG, "Checking if app is running");
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> services = activityManager.getRunningAppProcesses();

        boolean isForeground = false;

        for (int i=0; i<services.size(); i++){
            // TODO: change package name for your own app
            if (services.get(i).processName.toString().equalsIgnoreCase("com.tiggly.tales")) {
                if (services.get(i).importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                    isForeground = true;
                    // delay
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return isForeground;
    }

}
