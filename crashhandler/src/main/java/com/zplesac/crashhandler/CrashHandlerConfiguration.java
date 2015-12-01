package com.zplesac.crashhandler;

import com.zplesac.crashhandler.models.LogLevel;

import android.app.Activity;
import android.app.Application;

/**
 * Created by Željko Plesac on 18/11/15.
 */
public class CrashHandlerConfiguration {

    private long crashThreshold;

    private Class<?> homeActivity;

    private Application context;

    private LogLevel logLevel;

    private CrashHandlerConfiguration(Builder builder) {
        this.crashThreshold = builder.defaultCrashThreshold;
        this.context = builder.application;
        this.homeActivity = builder.homeActivity;
        this.logLevel = builder.logLevel;
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

        private Application application;

        private long defaultCrashThreshold;

        private Class<?> homeActivity;

        private LogLevel logLevel = LogLevel.DEBUG;

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

        public CrashHandlerConfiguration build() {
            return new CrashHandlerConfiguration(this);
        }
    }
}
