package com.github.hasatori;

import java.util.Collection;

public final class EvaluationLibrary {
    private EvaluationLibrary() {
    }

    public static IEvaluation and(IEvaluation evaluation1, IEvaluation evaluation2, IEvaluation... evaluations) {
        return () -> {
            boolean gatheredEvaluations = true;
            for (IEvaluation evaluation : evaluations) {
                gatheredEvaluations = gatheredEvaluations && evaluation.evaluate();
            }
            return evaluation1.evaluate() && evaluation2.evaluate() && gatheredEvaluations;
        };
    }

    public static IEvaluation or(IEvaluation evaluation1, IEvaluation evaluation2, IEvaluation... evaluations) {
        return () -> {
            boolean gatheredEvaluations = false;
            for (IEvaluation evaluation : evaluations) {
                gatheredEvaluations = gatheredEvaluations || evaluation.evaluate();
            }
            return evaluation1.evaluate() || evaluation2.evaluate() || gatheredEvaluations;
        };
    }

    public static IEvaluation not(IEvaluation evaluation) {
        return () -> !evaluation.evaluate();
    }


    public static IEvaluation equalsValue(Object expected, Object actual) {
        return () -> expected.equals(actual);
    }

    public static IEvaluation notEquals(Object expected, Object actual) {
        return () -> !equalsValue(expected, actual).evaluate();
    }

    public static IEvaluation is(Object expected, Object actual) {
        return () -> expected == actual;
    }

    public static IEvaluation isNot(Object expected, Object actual) {
        return () -> !is(expected, actual).evaluate();
    }

    public static IEvaluation isNull(Object object) {
        return () -> object == null;
    }

    public static IEvaluation isNotNull(Object object) {
        return () -> object != null;
    }

    public static IEvaluation isBlank(String value) {
        return () -> value != null && !value.equals("");
    }

    public static IEvaluation isNotBlank(String value) {
        return () -> !isBlank(value).evaluate();
    }

    public static IEvaluation isTrue(Boolean bool) {
        return () -> bool;
    }

    public static IEvaluation isFalse(Boolean bool) {
        return () -> !bool;
    }

    public static IEvaluation anyMatch(Object expected, Collection collection) {
        return () -> collection.contains(expected);
    }

    public static IEvaluation noneMatch(Object expected, Collection collection) {
        return () -> !collection.contains(expected);
    }

    public static IEvaluation alwaysTrue() {
        return () -> true;
    }

}
