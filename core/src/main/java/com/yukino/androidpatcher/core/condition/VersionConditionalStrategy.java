package com.yukino.androidpatcher.core.condition;

import androidx.annotation.NonNull;

import com.yukino.androidpatcher.core.condition.annotations.VersionConditional;
import com.yukino.androidpatcher.core.condition.dsl.VersionDslEvaluator;
import com.yukino.androidpatcher.core.hook.Hook;
import com.yukino.androidpatcher.core.model.VersionInfo;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * The {@link ConditionStrategy} implementation class.
 * Decide should hook by the {@link VersionConditional} and its DSL expression.
 *
 * @see VersionConditional
 * @see VersionDslEvaluator
 */
public class VersionConditionalStrategy implements ConditionStrategy {
    /**
     * Scan the {@link VersionConditional} annotation,
     * then delegate to {@link VersionDslEvaluator}
     * and evaluate should hook by its DSL expression
     */
    @Override
    public boolean shouldHook(
            @NonNull Class<? extends Hook<?>> hookClass,
            XC_LoadPackage.LoadPackageParam lpparam,
            VersionInfo versionInfo
    ) {
        VersionConditional annotation = hookClass.getAnnotation(VersionConditional.class);

        if (annotation == null) return true;

        return VersionDslEvaluator.evaluate(annotation.value(),versionInfo);
    }
}
