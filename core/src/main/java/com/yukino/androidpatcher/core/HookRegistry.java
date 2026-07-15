package com.yukino.androidpatcher.core;

import androidx.annotation.NonNull;

import com.yukino.androidpatcher.core.hook.Hook;
import com.yukino.androidpatcher.core.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The hooks registry/container class.
 * Manage all hooks centrally.
 */
public class HookRegistry {
    private final Set<Hook<?>> hooks;

    public HookRegistry() {
        this.hooks = new HashSet<>();
    }

    /** Add a new hook */
    public void add(@NonNull Hook<?> hook) {
        Logger.debug("Registered hook " + hook.name());
        this.hooks.add(hook);
    }

    /** Get an unmodifiable set of {@link #hooks} */
    public Set<Hook<?>> getAllHooks() {
        return Collections.unmodifiableSet(this.hooks);
    }
}
