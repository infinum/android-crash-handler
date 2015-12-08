package co.infinum.crashhandler;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.squareup.leakcanary.LeakCanary;
import co.infinum.crashhandler.handlers.AppCrashHandler;

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

        // Check if we should enable AppCrashHandler
        if (configuration.isAppCrashHandlerEnabled()) {
            Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(configuration.getContext()));
        }

        // Check if we should enable Crashlytics
        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder().disabled(configuration.isCrashlyticsEnabled()).build();
        Fabric.with(configuration.getContext(), new Crashlytics.Builder().core(crashlyticsCore).build());

        // Check if we should enable LeakCanary
        if (configuration.isLeakCanaryEnabled()) {
            LeakCanary.install(configuration.getContext());
        }

        // Plant correct Timber tree
        switch (configuration.getTimberTreeType()) {
            case DEBUG:
                Timber.plant(new Timber.DebugTree());
                break;
            case CRASH_REPORTING:
                Timber.plant(new CrashReportingTree());
                break;
            case REMOTE_DEBUGGING:
                Timber.plant(new RemoteDebuggingTree());
                break;
            default:
                Timber.plant(new Timber.DebugTree());
        }
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

    /**
     * Logs everything to crashlytics, then we just need to log an exception and we should see all prior logs online!
     */
    private static class RemoteDebuggingTree extends Timber.Tree {

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            // will write to the crash report as well to logcat
            Crashlytics.log(priority, tag, message);

            if (t != null) {
                Crashlytics.logException(t);
            }
        }
    }

    public CrashHandlerConfiguration getConfiguration() {
        return configuration;
    }
}
