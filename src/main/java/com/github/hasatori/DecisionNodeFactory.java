package com.github.hasatori;

import static com.github.hasatori.ActionLibrary.doNothing;
import static com.github.hasatori.EvaluationLibrary.alwaysTrue;

public class DecisionNodeFactory {


    private DecisionNodeFactory() {
    }

    public static ADecisionNode root() {
        ADecisionNode decoratedNode = new Node(alwaysTrue(), doNothing());
        return new RootNode(decoratedNode);
    }

    public static ADecisionNode decide(IEvaluation evaluation, IAction action) {
        return new Node(evaluation, action);

    }

    public static ADecisionNode decide(String logMessage, IEvaluation evaluation, IAction action) {
        LoggingActionDecorator loggingActionDecorator = new LoggingActionDecorator(logMessage, action);
        return new Node(evaluation, loggingActionDecorator);
    }
}
