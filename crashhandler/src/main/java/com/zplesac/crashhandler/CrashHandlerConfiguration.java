package com.zplesac.crashhandler;

import com.zplesac.crashhandler.models.LogLevel;

import android.app.Activity;
import android.app.Application;

/**
 * Created by Željko Plesac on 18/11/15.
 */
public class CrashHandlerConfiguration {

    private static final int DEFAULT_CRASH_THRESHOLD = 1000;

    private long crashThreshold;

    private Class<?> homeActivity;

    private Application context;

    private LogLevel logLevel;

    private boolean useRemoteDebuggingTree;

    private CrashHandlerConfiguration(Builder builder) {
        this.crashThreshold = builder.defaultCrashThreshold;
        this.context = builder.application;
        this.homeActivity = builder.homeActivity;
        this.logLevel = builder.logLevel;
        this.useRemoteDebuggingTree = builder.useRemoteDebuggingTree;
    }

    public boolean isUseRemoteDebuggingTree() {
        return useRemoteDebuggingTree;
    }

    public long getCrashThreshold() {
        return crashThreshold;
    }

    public Application getContext() {
        return context;
    }

    public Class<?> getHomeActivity() {
        return homeActivity;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public static class Builder {

        /**
         * Application context.
         */
        private Application application;

        /**
         * Threshold value which is used in AppCrashHandler for detecting crash loops. Default value is 1000.
         */
        private long defaultCrashThreshold = DEFAULT_CRASH_THRESHOLD;

        /**
         * Home activity of your application.
         */
        private Class<?> homeActivity;

        /**
         * Log level for CrashHandler instance. Default value is DEBUG.
         */
        private LogLevel logLevel = LogLevel.DEBUG;

        /**
         * Should use remote debugging tree, which sends all logs to Crashlytics when an crash happens. Default value is false.
         */
        private boolean useRemoteDebuggingTree = false;

        public Builder(Application application) {
            this.application = application;
        }

        public Builder setCrashThreshold(int threshold) {
            this.defaultCrashThreshold = threshold;
            return this;
        }

        public Builder setHomeActivity(Class<? extends Activity> activity) {
            this.homeActivity = activity;
            return this;
        }

        public Builder setLogLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Builder setUserRemoteDebuggingTree(boolean shouldUse) {
            this.useRemoteDebuggingTree = shouldUse;
            return this;
        }

        public CrashHandlerConfiguration build() {
            return new CrashHandlerConfiguration(this);
        }
    }
}
