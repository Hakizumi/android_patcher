package com.yukino.androidpatcher.core.hook;

import com.yukino.androidpatcher.core.model.VersionInfo;

/**
 * The hook strategy decider interface.
 * Responsible for deciding a hooking action's {@code params}
 * (Which method,which field or other profile) by the current hooking app's information.
 * 
 * @param <T> The provided hooking profile,a {@code POJO}.
 */
@FunctionalInterface
public interface HookStrategy<T> {
    T provideProfile(VersionInfo version);
}
