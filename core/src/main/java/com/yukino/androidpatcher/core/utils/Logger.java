package com.yukino.androidpatcher.core.utils;

import android.util.Log;

/**
 * The logging util class.
 * Provide wrapping methods from {@link Log}.
 */
public final class Logger {
    /**
     * Logger tag, modifiable for other modules.
     * This is default tag.
     */
    public static String TAG = "AndroidPatcher_Core";

    private Logger() {}

    // Logging methods
    // From android.util.Log

    public static void verbose(String message) {
        Log.v(TAG, message);
    }

    public static void verbose(String message,Throwable throwable) {
        Log.v(TAG, message, throwable);
    }

    public static void debug(String message) {
        Log.d(TAG, message);
    }

    public static void debug(String message,Throwable throwable) {
        Log.i(TAG, message, throwable);
    }

    public static void info(String message) {
        Log.i(TAG, message);
    }

    public static void info(String message,Throwable throwable) {
        Log.i(TAG, message, throwable);
    }

    public static void warn(String message) {
        Log.w(TAG, message);
    }

    public static void warn(String message,Throwable throwable) {
        Log.w(TAG, message, throwable);
    }

    public static void error(String message) {
        Log.e(TAG, message);
    }

    public static void error(String message,Throwable throwable) {
        Log.e(TAG, message, throwable);
    }

    public static void fatal(String message) {
        Log.wtf(TAG, message);
    }

    public static void fatal(Throwable throwable) {
        Log.wtf(TAG, throwable);
    }

    public static void fatal(String message,Throwable throwable) {
        Log.wtf(TAG, message, throwable);
    }
}
