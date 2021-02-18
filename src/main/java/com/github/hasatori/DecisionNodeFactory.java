package com.github.hasatori;

import static com.github.hasatori.ActionLibrary.doNothing;
import static com.github.hasatori.EvaluationLibrary.alwaysTrue;

public class DecisionNodeFactory {


    private DecisionNodeFactory() {
    }

    public static ADecisionNode processDecisionTree(ADecisionNode decisionNode) {
        return new RootNode(decisionNode);
    }

    public static ADecisionNode root() {
        return new Node(alwaysTrue(),doNothing());

    }

    public static ADecisionNode decide(IEvaluation evaluation, IAction action) {
        return new Node(evaluation, action);

    }

    public static ADecisionNode decide(String logMessage, IEvaluation evaluation, IAction action) {
        LoggingActionDecorator loggingActionDecorator = new LoggingActionDecorator(logMessage, action);
        return new Node(evaluation, loggingActionDecorator);
    }
}
