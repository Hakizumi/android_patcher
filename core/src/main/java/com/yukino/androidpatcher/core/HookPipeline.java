package com.yukino.androidpatcher.core;

import android.app.Application;
import android.content.Context;

import com.yukino.androidpatcher.core.hook.Hook;
import com.yukino.androidpatcher.core.model.VersionInfo;
import com.yukino.androidpatcher.core.utils.VersionUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * The hooks pipeline.
 * <p>
 * Accept the {@link HookRegistry},
 * and prepare the method to run all hooks.
 */
public class HookPipeline {
    private final HookRegistry registry;

    public HookPipeline(HookRegistry registry) {
        this.registry = registry;
    }

    /**
     * The framework's main entrypoint.
     * <p>
     * Get the hooking app's version information,
     * then iterate all hooks and run them in order.
     */
    public void runAllHooks(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(
                Application.class,
                "attach",
                Context.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        Context context = (Context) param.args[0];

                        // The hooking app's version info
                        VersionInfo versionInfo = VersionUtils.getVersion(context);

                        for (Hook<?> hook : registry.getAllHooks()) {
                            hook.accept(lpparam,versionInfo);
                        }
                    }
                }
        );
    }
}
