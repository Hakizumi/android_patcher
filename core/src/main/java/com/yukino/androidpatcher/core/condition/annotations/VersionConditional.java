package com.yukino.androidpatcher.core.condition.annotations;

import com.yukino.androidpatcher.core.utils.comparator.VersionNameComparator;
import com.yukino.androidpatcher.core.model.VersionInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The version conditioned annotation.
 * Controls whether the hook should run by the given version.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VersionConditional {
    /**
     * The DSL expression.
     *
     * <h2>Supported expression forms</h2>
     * <p>The DSL supports the following comparison operators:
     *
     * <ul>
     *   <li>{@code >} greater than
     *   <li>{@code <} less than
     *   <li>{@code >=} greater than or equals
     *   <li>{@code <=} less than or equals
     *   <li>{@code =} equal to
     *   <li>{@code !=} not equal to
     * </ul>
     * <p>
     * It also supports logical AND using:
     * <ul>
     *   <li>{@code &}</li>
     * </ul>
     *
     * <h2>Basic examples
     * <pre>{@code
     * versionCode > 4
     * versionCode = 5
     * versionName < 2.3.6-alpha
     * versionName = 2.3.5-beta
     * }</pre>
     *
     * <h2>Range / chained comparison</h2>
     * The DSL supports chained comparisons:
     * <pre>{@code
     * 4 < versionCode < 6
     * 2.3.4 < versionName < 2.3.6-alpha
     * }</pre>
     * <p>
     * A chained comparison is interpreted as two comparisons joined by logical AND.
     * <p>
     * For example:
     * <pre>{@code
     * 4 < versionCode < 6
     * }</pre>
     * <p>
     * means:
     * <pre>{@code
     * 4 < versionCode & versionCode < 6
     * }</pre>
     *
     * <h2>Combining multiple conditions</h2>
     * <p>
     * Multiple conditions can be combined with {@code &}:
     * <pre>{@code
     * 4 < versionCode < 6 & 2.3.4 < versionName < 2.3.6-alpha
     * }</pre>
     * <p>
     * This expression is true only if all conditions are true.
     * <h2>Version name comparison rules</h2>
     * <p>
     * {@code versionName} is compared in two steps:
     * <ol>
     *   <li>Compare numeric segments separated by dots, such as {@code 2.3.4}
     *   <li>If numeric segments are equal, compare suffix priority
     * </ol>
     * <p>
     * Supported suffix priority:
     * <pre>{@code
     * release > beta > alpha
     * }</pre>
     * <p>
     * Examples:
     * <pre>{@code
     * 2.3.5       > 2.3.5-beta
     * 2.3.5-beta  > 2.3.5-alpha
     * 2.3.6-alpha > 2.3.5
     * }</pre>
     * <p>
     * A version without suffix is treated as a normal release version.
     *
     * <h2>Supported variable names</h2>
     * <ul>
     *   <li>{@code versionCode} or {@code version_code}</li>
     *   <li>{@code versionName} or {@code version_name}</li>
     * </ul>

     * <h2>Syntax requirements</h2>
     * <p>
     * This simplified parser expects tokens to be separated by spaces.
     * <p>
     * Recommended form:
     * <pre>{@code
     * 4 < versionCode < 6
     * versionName > 2.3.4-beta
     * 4 < versionCode < 6 & 2.3.4 < versionName < 2.3.6-alpha
     * }</pre>
     * <p>
     * Not recommended:
     * <pre>{@code
     * 4<versionCode<6
     * }</pre>
     * <p>
     * because the lightweight parser may not recognize expressions without spaces.
     *
     * <h2>Examples</h2>
     * <pre>{@code
     * VersionContext context = new VersionContext(5, "2.3.5-beta");
     *
     * VersionDslEvaluator.evaluate("4 < versionCode < 6", context); // true
     * VersionDslEvaluator.evaluate("versionCode = 5", context); // true
     * VersionDslEvaluator.evaluate("versionName > 2.3.5-alpha", context); // true
     * VersionDslEvaluator.evaluate("versionName < 2.3.5", context); // true
     * VersionDslEvaluator.evaluate(
     *     "4 < versionCode < 6 & 2.3.4 < versionName < 2.3.6-alpha",
     *     context
     * ); // true
     * }</pre>
     *
     * <h2>Limitations</h2>
     * <ul>
     *   <li>Only {@code >}, {@code <}, {@code >=}, {@code <=}, {@code =} and {@code !=} are supported</li>
     *   <li>Only logical AND ({@code &}) is supported</li>
     *   <li>Parentheses are not supported</li>
     *   <li>Expressions without spaces may not be parsed correctly</li>
     * </ul>
     *
     * @see VersionInfo
     * @see VersionNameComparator
     */
    String value();
}
