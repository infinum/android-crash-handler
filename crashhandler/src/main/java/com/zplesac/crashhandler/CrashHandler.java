package com.zplesac.crashhandler;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.squareup.leakcanary.LeakCanary;
import com.zplesac.crashhandler.handlers.AppCrashHandler;

import android.util.Log;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by Željko Plesac on 18/11/15.
 */
public class CrashHandler {

    public static final String IS_REPEATED_CRASH = "IS_REPEATED_CRASH";

    private static volatile CrashHandler instance;

    private CrashHandlerConfiguration configuration;

    protected CrashHandler() {
        // empty constructor
    }

    /**
     * Initialize this instance with provided configuration.
     *
     * @param configuration CrashHandler configuration which is used in instance.
     */
    public synchronized void init(CrashHandlerConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException();
        }
        if (this.configuration == null) {
            this.configuration = configuration;
        }

        initDependencies();
    }

    /**
     * Returns singleton class instance.
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    private void initDependencies() {
        // Set custom uncaught exception handler
        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(configuration.getContext()));

        CrashlyticsCore crashlyticsCore = null;

        switch (configuration.getLogLevel()) {
            case DEBUG:
                Timber.plant(new Timber.DebugTree());
                LeakCanary.install(configuration.getContext());
                crashlyticsCore = new CrashlyticsCore.Builder().disabled(true).build();
                break;
            case FULL:
                crashlyticsCore = new CrashlyticsCore.Builder().disabled(false).build();
                Timber.plant(new CrashReportingTree());
                break;
            default:
                LeakCanary.install(configuration.getContext());
                crashlyticsCore = new CrashlyticsCore.Builder().disabled(true).build();
                break;
        }

        Fabric.with(configuration.getContext(), new Crashlytics.Builder().core(crashlyticsCore).build());
    }

    /**
     * When a crash is logged to Crashlytics, we will also get prior logs!
     */
    private static class CrashReportingTree extends Timber.Tree {

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            // will write to the crash report but NOT to logcat
            Crashlytics.log(message);

            if (t != null) {
                Crashlytics.logException(t);
            }
        }
    }

    public CrashHandlerConfiguration getConfiguration() {
        return configuration;
    }
}
