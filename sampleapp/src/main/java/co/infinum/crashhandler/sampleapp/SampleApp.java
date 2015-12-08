package co.infinum.crashhandler.sampleapp;

import co.infinum.crashhandler.CrashHandler;
import co.infinum.crashhandler.CrashHandlerConfiguration;
import co.infinum.crashhandler.models.TimberTreeType;

import android.app.Application;

/**
 * Created by Željko Plesac on 19/11/15.
 */
public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandlerConfiguration configuration = new CrashHandlerConfiguration.Builder(this)
                .setTimberTreeType(TimberTreeType.CRASH_REPORTING)
                .setHomeActivity(MainActivity.class).build();
        CrashHandler.getInstance().init(configuration);
    }
}
