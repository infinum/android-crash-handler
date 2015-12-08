# Android Crash Handler

Provides a simple way for configuring your app crash handler.

## Usage

You only need to initialize the CrashHandler instance in your Application class. Also, you'll need to provide a global configuration by defining CrashHandlerConfiguration object.

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

All options in [CrashHandlerConfiguration.Builder](https://github.com/zplesac/android-crash-handler/blob/master/crashhandler/src/main/java/com/zplesac/crashhandler/CrashHandlerConfiguration.java) are optional. Use only those you really want to customize.

See all default values for config options [here](https://github.com/zplesac/android-crash-handler/blob/master/crashhandler/src/main/java/com/zplesac/crashhandler/CrashHandlerConfiguration.java).

## What is actually initialized with this library???

It does multiple things:

* initialize LeakCanary 
* setup Timber with default Debug tree
* initialize Crashlytics
* connect your Timber exceptions to Crashlytics with CrashReportingTree
* initialize default unexpected crash handler

## Proguard configuration

Add following code snippet to your proguard configuration:

```groovy
    -keep class org.eclipse.mat.** { *; }
    -keep class com.squareup.leakcanary.** { *; }
```