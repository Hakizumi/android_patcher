package com.yukino.androidpatcher.core.hook;

import com.yukino.androidpatcher.core.model.VersionInfo;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * The no-strategy hook.
 * Skip the tedious {@code strategy-mode} style,
 * and provide a simpler hooking way for {@code no-profile} hooks.
 */
public abstract class NoStrategyHook extends Hook<Object> {
    /** No-operation meaningless-resulted strategy */
    public static final HookStrategy<Object> EMPTY_STRATEGY = (versionInfo) -> new Object();

    public NoStrategyHook() {
        super(EMPTY_STRATEGY);
    }

    /**
     * The overwriting hook method,
     * calling by {@link Hook}.
     * Child classes should overwrite {@link #hook(XC_LoadPackage.LoadPackageParam, VersionInfo)}.
     */
    @Override
    protected final void hook(
            XC_LoadPackage.LoadPackageParam lpparam,
            VersionInfo versionInfo,
            Object profile) throws Throwable {
        this.hook(lpparam, versionInfo);
    }

    /**
     * Subclass accessing point.
     * No {@code profile} provided.
     */
    protected abstract void hook(
            XC_LoadPackage.LoadPackageParam lpparam,
            VersionInfo versionInfo
    ) throws Throwable;
}
