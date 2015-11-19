package com.zplesac.sampleapp;

import com.zplesac.crashhandler.CrashHandler;
import com.zplesac.crashhandler.CrashHandlerConfiguration;
import com.zplesac.crashhandler.models.LogLevel;

import android.app.Application;

/**
 * Created by Željko Plesac on 19/11/15.
 */
public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandlerConfiguration configuration = new CrashHandlerConfiguration.Builder(this).setLogLevel(LogLevel.FULL)
                .setHomeActivity(MainActivity.class).build();
        CrashHandler.getInstance().init(configuration);
    }
}
