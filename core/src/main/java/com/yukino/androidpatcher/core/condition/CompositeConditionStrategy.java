package com.yukino.androidpatcher.core.condition;

import com.yukino.androidpatcher.core.hook.Hook;
import com.yukino.androidpatcher.core.model.VersionInfo;

import java.util.List;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * The compositing strategy implementation class.
 * <p>
 * Composite multiple {@link #strategies},
 * and compute whether {@link #shouldHook(Class, XC_LoadPackage.LoadPackageParam, VersionInfo)}
 * by the {@link #strategies}.
 */
public class CompositeConditionStrategy implements ConditionStrategy {
    private final List<ConditionStrategy> strategies;

    public CompositeConditionStrategy(List<ConditionStrategy> strategies) {
        this.strategies = strategies;
    }

    /**
     * Decide whether a hook should run by the {@link #strategies}.
     * <p>
     * Logic:
     * <li> One of the {@link #strategies} return false -> reject
     * <li> All of the {@link #strategies} return true -> accept
     */
    @Override
    public boolean shouldHook(
            Class<? extends Hook<?>> hookClass,
            XC_LoadPackage.LoadPackageParam lpparam,
            VersionInfo versionInfo) {
        for (ConditionStrategy strategy : this.strategies) {
            // One false -> false
            if (!strategy.shouldHook(hookClass,lpparam,versionInfo)) return false;
        }

        // All true -> true
        return true;
    }
}
