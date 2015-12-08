package co.infinum.crashhandler.cache;

import co.infinum.crashhandler.CrashHandler;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Željko Plesac on 18/11/15.
 */
public final class CrashHandlerCache {

    private CrashHandlerCache() {
        // private constructor
    }

    private static final String LAST_CRASH_DATE = "LAST_CRASH_DATE";

    /**
     * Fetch default shared preferences.
     */
    private static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(CrashHandler.getInstance().getConfiguration().getContext());
    }

    public static void setLastCrashDate(long date) {
        getPreferences().edit().putLong(LAST_CRASH_DATE, date).commit();
    }

    public static long getLastCrashDate() {
        return getPreferences().getLong(LAST_CRASH_DATE, 0);
    }
}
