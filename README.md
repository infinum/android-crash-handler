# Android Crash Handler

Provides a simple way for handling configuring your app crash handler.

## Usage

You only need to initialize CrashHandler instance in your Application class. Also, you'll also need to provide a global configuration by defining CrashHandlerConfiguration object.

```java
    
   public class SampleApp extends Application {
   
       @Override
       public void onCreate() {
           super.onCreate();
   
           CrashHandlerConfiguration configuration = new CrashHandlerConfiguration.Builder(this).setLogLevel(LogLevel.FULL)
                   .setHomeActivity(MainActivity.class).build();
           CrashHandler.getInstance().init(configuration);
       }
   }
```

All options in [CrashHandler.Builder](https://github.com/zplesac/android-crash-handler/blob/master/crashhandler/src/main/java/com/zplesac/crashhandler/CrashHandler.java) are optional. Use only those you really want to customize.

See all default values for config options [here](https://github.com/zplesac/android-crash-handler/blob/master/crashhandler/src/main/java/com/zplesac/crashhandler/CrashHandler.java).

## What is actually initialized with this library???

It does multiple things:

* initialize LeakCanary 
* setup Timber with default Debug tree
* initialize Crashlytics
* connect your Timber exceptions to Crashlytics with CrashReportingTree
* initialize default unexpected crash handler

