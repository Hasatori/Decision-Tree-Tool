package com.github.hasatori;

public class RootNode extends ADecisionNode {
    private final ADecisionNode decoratedDecisionNode;
    private boolean shouldContinueProcessing = false;

    public RootNode(ADecisionNode decoratedDecisionNode) {
        super(decoratedDecisionNode.evaluation, decoratedDecisionNode.action);
        this.decoratedDecisionNode = decoratedDecisionNode;
        this.process();
    }

    @Override
    public  ADecisionNode ifThen(ADecisionNode decisionNode) {
        this.decoratedDecisionNode.ifThen(decisionNode);
        if (decisionNode.evaluation.evaluate()) {
            decisionNode.process();
            this.shouldContinueProcessing = false;
        } else {
            this.shouldContinueProcessing = true;
        }
        return this;
    }

    @Override
    public   ADecisionNode elseIfThen(ADecisionNode decisionNode) {
        this.decoratedDecisionNode.elseIfThen(decisionNode);
        if (this.shouldContinueProcessing && decisionNode.evaluation.evaluate()) {
            decisionNode.process();
            this.shouldContinueProcessing = false;
        }
        return this;
    }

    @Override
    public   ADecisionNode elseThen(ADecisionNode decisionNode) {
        this.decoratedDecisionNode.elseThen(decisionNode);
        if (this.shouldContinueProcessing && decisionNode.evaluation.evaluate()) {
            decisionNode.process();
            this.shouldContinueProcessing = false;
        }
        return this;
    }

    @Override
    protected void process() {
        this.decoratedDecisionNode.process();
    }
}
