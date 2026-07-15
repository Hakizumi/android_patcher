package com.yukino.androidpatcher.core.condition;

import com.yukino.androidpatcher.core.hook.Hook;
import com.yukino.androidpatcher.core.model.VersionInfo;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * The hook enabled-condition top strategy class.
 * Responsible for deciding whether a hook should run.
 */
public interface ConditionStrategy {
    /** Return whether a hook should run */
    boolean shouldHook(
            Class<? extends Hook<?>> hookClass,
            XC_LoadPackage.LoadPackageParam lpparam,
            VersionInfo versionInfo
    );
}
