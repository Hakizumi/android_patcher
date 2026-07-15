package com.yukino.androidpatcher.duolingo;

import com.yukino.androidpatcher.core.HookPipeline;
import com.yukino.androidpatcher.core.HookRegistry;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import org.jetbrains.annotations.NotNull;

/**
 * The main hook.
 * {@code Xposed} module entrypoint.
 */
public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.@NotNull LoadPackageParam lpparam) {
        HookRegistry registry = new HookRegistry();

        new HookPipeline(registry).runAllHooks(lpparam);
    }
}
