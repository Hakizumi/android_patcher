package com.yukino.androidpatcher.core.hook;

import androidx.annotation.NonNull;

import com.yukino.androidpatcher.core.model.VersionInfo;
import com.yukino.androidpatcher.core.strategy.HookStrategy;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * The abstract hook class.
 * Each hook should inherit this class,
 * and be managed centrally by the {@code hook registry}.
 *
 * @param <T> Accept profile POJO class,produce by {@link #strategy}.
 */
public abstract class Hook<T> {
    private final HookStrategy<T> strategy;

    public Hook(HookStrategy<T> strategy) {
        this.strategy = strategy;
    }

    /** Hook name,used for logging */
    public String name() {
        return getClass().getSimpleName();
    }

    /**
     * Hook public entrypoint method.
     * Do the wrapping work for internal hooking action: {@link #hook(XC_LoadPackage.LoadPackageParam, VersionInfo, Object)}.
     */
    public void accept(XC_LoadPackage.LoadPackageParam lpparam, @NonNull VersionInfo versionInfo) {
        T profile = this.strategy.provideProfile(versionInfo.versionName());

        try {
            hook(lpparam,versionInfo,profile);
        } catch (Exception e) {
            XposedBridge.log("Hook " + this.name() + " failed:" + e.getMessage());
        }
    }

    /**
     * Internal hooking action,
     * implement by child class.
     * <p>
     * Can customize the hooking action by {@code versionInfo} and hooking profile.
     */
    protected abstract void hook(XC_LoadPackage.LoadPackageParam lpparam, VersionInfo versionInfo, T profile);
}
