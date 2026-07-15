package com.yukino.androidpatcher.core.condition.dsl.comparator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yukino.androidpatcher.core.model.VersionInfo;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The {@link VersionInfo} comparator implementation.
 */
public class VersionNameComparator implements Comparator<VersionInfo> {
    /**
     * Compare 2 versions.
     * <p>
     * Compare logic:
     * <li> Compare {@link VersionInfo#versionCode()} first.
     * <li> If {@link VersionInfo#versionCode()} are the same,compare the {@code version name numbers}.
     * <li> If the version name numbers are the same,compare the {@code version name suffix}.
     */
    @Override
    public int compare(@NonNull VersionInfo left, @NonNull VersionInfo right) {
        // Compare version code first
        if (left.versionCode() != right.versionCode()) {
           long compare = left.versionCode() - right.versionCode();

           if (compare > Integer.MAX_VALUE) return Integer.MAX_VALUE;
           return (int) compare;
        }

        // Version codes are the same,compare version name
        List<Integer> leftNumbers = getNumbers(left.versionName());
        List<Integer> rightNumbers = getNumbers(right.versionName());

        int max = Math.max(leftNumbers.size(), rightNumbers.size());

        for (int i = 0; i < max; i++) {
            int a = i < leftNumbers.size() ? leftNumbers.get(i) : 0;
            int b = i < rightNumbers.size() ? rightNumbers.get(i) : 0;

            if (a != b) {
                return Integer.compare(a, b);
            }
        }

        // The numbers are the same, compare the priority of the suffix
        String leftSuffix = getSuffix(left.versionName());
        String rightSuffix = getSuffix(right.versionName());

        return Integer.compare(suffixWeight(leftSuffix), suffixWeight(rightSuffix));
    }

    /** Split the version name to 2 parts: {@code version name numbers} and {@code version name suffix} */
    @Contract("null -> fail")
    private static @NonNull String[] getParts(String versionName) {
        if (versionName == null || versionName.isBlank()) {
            throw new IllegalArgumentException("VersionName is blank");
        }

        return versionName.trim().split("-", 2);
    }

    /** Extract the {@code version name numbers} from version name */
    @Contract("null -> fail")
    private static @NonNull List<Integer> getNumbers(String versionName) {
        String[] parts = getParts(versionName);

        String numberPart = parts[0];
        String[] nums = numberPart.split("\\.");

        List<Integer> numberList = new ArrayList<>();
        for (String n : nums) {
            numberList.add(Integer.parseInt(n));
        }

        return numberList;
    }

    /** Extract the {@code version name suffix} from version name */
    private static @Nullable String getSuffix(String versionName) {
        String[] parts = getParts(versionName);
        return parts.length > 1 ? parts[1].toLowerCase() : null;
    }

    /**
     * Calculate the {@code version name suffix}'s priority/weight number.
     * <p>
     * {@code release} > {@code beta} > {@code alpha} > {@code unknown}
     */
    private int suffixWeight(String suffix) {
        if (suffix == null || suffix.isEmpty() || suffix.equalsIgnoreCase("release")) {
            return 3; // Normal release,the highest priority
        }
        if (suffix.equalsIgnoreCase("beta")) {
            return 2;
        }
        if (suffix.equalsIgnoreCase("alpha")) {
            return 1;
        }
        return 0; // Unknown suffix,the lowest priority
    }
}
