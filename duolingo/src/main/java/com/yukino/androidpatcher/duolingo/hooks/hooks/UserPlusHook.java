package com.yukino.androidpatcher.duolingo.hooks.hooks;

import androidx.annotation.NonNull;

import com.yukino.androidpatcher.core.hook.Hook;
import com.yukino.androidpatcher.core.model.VersionInfo;
import com.yukino.androidpatcher.duolingo.hooks.profile.UserPlusProfile;
import com.yukino.androidpatcher.duolingo.hooks.strategy.UserPlusStrategy;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * User {@code hasPlus} attribute hook.
 */
public class UserPlusHook extends Hook<UserPlusProfile> {
    public UserPlusHook() {
        super(new UserPlusStrategy());
    }

    /**
     * Run the {@code Hook}.
     * Hook the {@code hasPlus} field in {@code User} class,
     * and set it true-forever.
     */
    @Override
    protected void hook(
            @NonNull XC_LoadPackage.LoadPackageParam lpparam,
            VersionInfo versionInfo,
            @NonNull UserPlusProfile profile
    ) {
        Class<?> userClass = XposedHelpers.findClass(
                profile.userClass,
                lpparam.classLoader
        );

        // Force set the hasPlus field true-forever
        XposedBridge.hookAllConstructors(
                userClass,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        try {
                            XposedHelpers.setBooleanField(
                                    param.thisObject,
                                    profile.hasPlusField,
                                    true
                            );
                        } catch (Throwable t) {
                            XposedBridge.log("Hook Duolingo user-plus failed: " + t.getMessage());
                        }
                    }
                }
        );
    }
}
