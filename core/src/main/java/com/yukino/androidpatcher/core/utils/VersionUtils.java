package com.yukino.androidpatcher.core.utils;

import androidx.annotation.NonNull;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.yukino.androidpatcher.core.model.VersionInfo;

import org.jetbrains.annotations.Contract;

/**
 * Android software version utility class.
 */
public final class VersionUtils {
    /** Return the wrapped version info from {@code context} */
    @Contract("_ -> new")
    public static @NonNull VersionInfo getVersion(Context context) {
        return new VersionInfo(
                getVersionCode(context),
                getVersionName(context)
        );
    }

    /** Return the numeric version code from {@code context} */
    @SuppressWarnings("deprecation")
    public static long getVersionCode(@NonNull Context context) {
        PackageInfo info = getPackageInfo(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return info.getLongVersionCode();
        } else {
            // Old style
            return info.versionCode;
        }
    }

    /** Return the string version name from {@code context} */
    public static String getVersionName(@NonNull Context context) {
        return getPackageInfo(context).versionName;
    }

    private static PackageInfo getPackageInfo(@NonNull Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
