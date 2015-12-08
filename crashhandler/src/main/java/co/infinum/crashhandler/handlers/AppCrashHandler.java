package co.infinum.crashhandler.handlers;

import co.infinum.crashhandler.CrashHandler;
import co.infinum.crashhandler.cache.CrashHandlerCache;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Željko Plesac on 16/11/15.
 */
public class AppCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final int MILLISECONDS_DIVIDER = 1000;

    private Activity liveActivity;

    public AppCrashHandler(Application application) {

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                liveActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                liveActivity = null;
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }

    /**
     * Define custom uncaught exception handling in order to avoid system "App has crashed" dialog.
     *
     * @param thread Thread that is about to exit.
     * @param ex     Uncaught exception.
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        long time = System.currentTimeMillis() % MILLISECONDS_DIVIDER;

        // If app is still running, start the base activity with CLEAR_TOP intent flag
        if (liveActivity != null && CrashHandler.getInstance().getConfiguration().getHomeActivity() != null) {
            Intent intent = new Intent(CrashHandler.getInstance().getConfiguration().getContext(),
                    CrashHandler.getInstance().getConfiguration().getHomeActivity());

            /**
             * Determine if we are in crash loop. If the time between 2 crashes is lower than crash threshold, this means
             * that we are in crash loop - application is crashing as soon as it's started. If this is the case,
             * start the Home activity with IS_REPEATED_CRASH set to true, so that we know that we need to handle this case.
             */
            if (time - CrashHandlerCache.getLastCrashDate() > CrashHandler.getInstance().getConfiguration().getCrashThreshold()) {
                intent.putExtra(CrashHandler.IS_REPEATED_CRASH, false);

            } else {
                intent.putExtra(CrashHandler.IS_REPEATED_CRASH, true);
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            liveActivity.finish();
            liveActivity.startActivity(intent);
        }

        CrashHandlerCache.setLastCrashDate(time);
        // Kill the app
        System.exit(0);
    }
}
