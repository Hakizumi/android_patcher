package com.yukino.androidpatcher.duolingo;

import com.yukino.androidpatcher.core.HookPipeline;
import com.yukino.androidpatcher.core.HookRegistry;
import com.yukino.androidpatcher.duolingo.hooks.hooks.UserPlusHook;

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

        registry.add(new UserPlusHook());

        new HookPipeline(registry).runAllHooks(lpparam);
    }
}
