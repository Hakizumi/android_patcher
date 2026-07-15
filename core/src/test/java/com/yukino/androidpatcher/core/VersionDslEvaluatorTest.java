package com.yukino.androidpatcher.core;

import static com.yukino.androidpatcher.core.condition.dsl.VersionDslEvaluator.evaluate;

import com.yukino.androidpatcher.core.condition.dsl.VersionDslEvaluator;
import com.yukino.androidpatcher.core.model.VersionInfo;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link VersionDslEvaluator} testing.
 */
public class VersionDslEvaluatorTest {
    private final VersionInfo versionInfo = new VersionInfo(100,"2.3.4");

    public VersionDslEvaluatorTest() {}

    @Test
    public void testVersionName() {
        Assert.assertTrue(evaluate("1.1.0 < version_name", versionInfo));
        Assert.assertTrue(evaluate("version_name < 5.6.7", versionInfo));
        Assert.assertTrue(evaluate("1.1.0 < version_name < 5.6.7", versionInfo));
        Assert.assertFalse(evaluate("version_name != 2.3.4", versionInfo));
        Assert.assertTrue(evaluate("version_name >= 2.3.4", versionInfo));
    }

    @Test
    public void testVersionNameSuffix() {
        Assert.assertTrue(evaluate("version_name != 2.3.4-alpha", versionInfo));
        Assert.assertTrue(evaluate("version_name > 2.3.4-beta", versionInfo));
        Assert.assertTrue(evaluate("version_name = 2.3.4-release", versionInfo));
        Assert.assertFalse(evaluate("version_name = 2.3.4-unknown", versionInfo));
        Assert.assertTrue(evaluate("version_name >= 1.2.3-alpha", versionInfo));
    }

    @Test
    public void testVersionCode() {
        Assert.assertTrue(evaluate("99 < version_code",versionInfo));
        Assert.assertTrue(evaluate("version_code < 200",versionInfo));
        Assert.assertTrue(evaluate("99 < version_code < 200",versionInfo));
        Assert.assertFalse(evaluate("version_code != 100",versionInfo));
        Assert.assertTrue(evaluate("version_code >= 100",versionInfo));
    }
}
