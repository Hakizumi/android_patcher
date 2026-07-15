package com.yukino.androidpatcher.core.condition.dsl;

import androidx.annotation.NonNull;

import com.yukino.androidpatcher.core.condition.annotations.VersionConditional;
import com.yukino.androidpatcher.core.condition.dsl.comparator.VersionNameComparator;
import com.yukino.androidpatcher.core.model.VersionInfo;

import org.jetbrains.annotations.Contract;

/**
 * The {@link VersionConditional} annotation's DSL expression parser.
 *
 * @see VersionConditional
 */
public class VersionDslEvaluator {
    /**
     * Evaluate whether the DSL {@code expression} matches the given {@code versionInfo}.
     *
     * @param expression The DSL expression,see {@link VersionConditional#value()}
     */
    public static boolean evaluate(String expression, VersionInfo versionInfo) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression is empty");
        }

        String[] conditions = expression.split("&");
        for (String condition : conditions) {
            if (!evaluateCondition(condition.trim(), versionInfo)) {
                return false;
            }
        }
        return true;
    }

    /** Evaluate one condition (split by logic separator {@code &}) */
    private static boolean evaluateCondition(@NonNull String condition, VersionInfo versionInfo) {
        // Do normalize
        // Process whitespace first
        String normalized = condition.replaceAll("\\s+", " ").trim();

        // Chain comparison: Like 4 < versionCode < 6
        String[] parts = normalized.split(" ");
        if (parts.length == 5) {
            // Format: left op1 middle op2 right
            String left = parts[0];
            String op1 = parts[1];
            String middle = parts[2];
            String op2 = parts[3];
            String right = parts[4];

            boolean first = compare(left, op1, middle, versionInfo);
            boolean second = compare(middle, op2, right, versionInfo);
            return first && second;
        }

        // Normal comparison: Like versionCode > 4
        if (parts.length == 3) {
            String left = parts[0];
            String op = parts[1];
            String right = parts[2];
            return compare(left, op, right, versionInfo);
        }

        throw new IllegalArgumentException("invalid condition: " + condition);
    }

    /** Compute one inequality  */
    private static boolean compare(String left, String operator, String right, VersionInfo versionInfo) {
        Value leftValue = resolveValue(left, versionInfo);
        Value rightValue = resolveValue(right, versionInfo);

        if (leftValue.type != rightValue.type) {
            throw new IllegalArgumentException("Type mismatch: " + left + " " + operator + " " + right);
        }

        int compare;
        if (leftValue.type == ValueType.NUMBER) {
            compare = Long.compare((Long) leftValue.value, (Long) rightValue.value);
        } else {
            compare = new VersionNameComparator().compare((String) leftValue.value, (String) rightValue.value);
        }

        return switch (operator) {
            case ">" -> compare > 0;
            case "<" -> compare < 0;
            case ">=" -> compare >= 0;
            case "<=" -> compare <= 0;
            case "=" -> compare == 0;
            case "!=" -> compare != 0;
            default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
        };
    }

    @Contract("_, _ -> new")
    private static @NonNull Value resolveValue(String token, VersionInfo versionInfo) {
        if ("versionCode".equals(token) || "version_code".equals(token)) {
            return new Value(ValueType.NUMBER, versionInfo.versionCode());
        }
        if ("versionName".equals(token) || "version_name".equals(token)) {
            return new Value(ValueType.VERSION_NAME, versionInfo.versionName());
        }

        // Constant number
        if (token.matches("\\d+")) {
            return new Value(ValueType.NUMBER, Long.parseLong(token));
        }

        // Version name string
        return new Value(ValueType.VERSION_NAME, token);
    }

    enum ValueType {
        NUMBER,
        VERSION_NAME
    }

    record Value(ValueType type, Object value) {
    }
}
