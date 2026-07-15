package com.yukino.androidpatcher.duolingo.hooks.strategy;

import androidx.annotation.NonNull;

import com.yukino.androidpatcher.core.hook.HookStrategy;
import com.yukino.androidpatcher.core.model.VersionInfo;
import com.yukino.androidpatcher.duolingo.hooks.profile.UserPlusProfile;

/**
 * User {@code hasPlus} hook profile strategy provider.
 */
public class UserPlusStrategy implements HookStrategy<UserPlusProfile> {
    @Override
    public UserPlusProfile provideProfile(@NonNull VersionInfo version) {
        UserPlusProfile profile = new UserPlusProfile();

        if (version.versionCode() < 2409) {
            // Unsupported duolingo version
            return null;
        }

        profile.hasPlusField = "y";

        return profile;
    }
}
