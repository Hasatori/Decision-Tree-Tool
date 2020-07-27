package com.github.hasatori;


import java.util.ArrayList;
import java.util.List;


public abstract class ADecisionNode {

    protected final IAction action;
    protected final IEvaluation evaluation;
    protected final List<ADecisionNode> children;
    protected boolean wasPreviousIf = false;
    protected boolean wasPreviousElseIf = false;


    public ADecisionNode(IEvaluation evaluation, IAction action) {
        this.action = action;
        this.evaluation = evaluation;
        this.children = new ArrayList<>();
    }

    abstract ADecisionNode ifThen(ADecisionNode decisionNode);

    abstract ADecisionNode elseIfThen(ADecisionNode decisionNode);

    abstract ADecisionNode elseThen(ADecisionNode decisionNode);


    protected abstract void process();

}
