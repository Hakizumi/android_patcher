package com.yukino.androidpatcher.core.hook;

import androidx.annotation.NonNull;

import com.yukino.androidpatcher.core.HookRegistry;
import com.yukino.androidpatcher.core.condition.CompositeConditionStrategy;
import com.yukino.androidpatcher.core.condition.ConditionStrategy;
import com.yukino.androidpatcher.core.condition.VersionConditionalStrategy;
import com.yukino.androidpatcher.core.model.VersionInfo;
import com.yukino.androidpatcher.core.utils.Logger;

import java.util.List;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * The abstract hook class.
 * Each hook should inherit this class,
 * and be managed centrally by the {@link HookRegistry}.
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

    /** Get the {@link ConditionStrategy} to decide should hook */
    protected ConditionStrategy getConditionStrategies() {
        return new CompositeConditionStrategy(this.defaultConditionStrategies());
    }

    /** Default/Builtin {@link ConditionStrategy}s */
    protected final @NonNull List<ConditionStrategy> defaultConditionStrategies() {
        return List.of(new VersionConditionalStrategy());
    }

    /**
     * Hook public entrypoint method.
     * Do the wrapping work for internal hooking action: {@link #hook(XC_LoadPackage.LoadPackageParam, VersionInfo, Object)}.
     */
    @SuppressWarnings("unchecked")
    public void accept(XC_LoadPackage.LoadPackageParam lpparam, @NonNull VersionInfo versionInfo) {
        // Check should hook
        if (!getConditionStrategies().shouldHook(
                (Class<? extends Hook<?>>) getClass(),
                lpparam,
                versionInfo
        )) {
            Logger.debug("Skip running hook " + name() + " due to conditions");
        }

        T profile = this.strategy.provideProfile(versionInfo);
        if (profile == null) {
            Logger.info("Hook " + name() + " did not provide a legal profile,skipped");
            return;
        }

        try {
            hook(lpparam,versionInfo,profile);
        } catch (Exception e) {
            Logger.error("Hook " + this.name() + " failed:" + e.getMessage());
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
