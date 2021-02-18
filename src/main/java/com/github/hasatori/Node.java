package com.github.hasatori;

import java.util.ArrayList;
import java.util.List;

public class Node extends ADecisionNode {
    private final List<ADecisionNode> allMatchingLists = new ArrayList<>();
    private final List<List<ADecisionNode>> firstMatchingLists = new ArrayList<>();
    private ADecisionNode notAssignedNode;

    public Node(IEvaluation evaluation, IAction action) {
        super(evaluation, action);
    }

    @Override
    public  ADecisionNode ifThen(ADecisionNode decisionNode) {
        if (wasPreviousIf) {
            this.allMatchingLists.add(notAssignedNode);
        }
        this.notAssignedNode = decisionNode;
        this.wasPreviousIf = true;
        this.wasPreviousElseIf = false;


        return this;
    }

    @Override
    public  ADecisionNode elseIfThen(ADecisionNode decisionNode) {
        if (!wasPreviousIf && !wasPreviousElseIf) {
            throw new IllegalStateException("ElseIfThen has to be after ifThe or elseIfThen");
        }
        if (wasPreviousIf) {
            fillCurrentFirstMatchingList(decisionNode);
        } else {
            this.firstMatchingLists.get(this.firstMatchingLists.size() - 1).add(decisionNode);
        }
        this.wasPreviousIf = false;
        this.wasPreviousElseIf = true;
        return this;
    }

    @Override
    public ADecisionNode elseThen(ADecisionNode decisionNode) {
        if (!wasPreviousIf && !wasPreviousElseIf) {
            throw new IllegalStateException("ElseThen has to be after ifThen or elseIfThen");
        }
        if (wasPreviousIf) {
            fillCurrentFirstMatchingList(decisionNode);
        } else {
            this.firstMatchingLists.get(this.firstMatchingLists.size() - 1).add(decisionNode);
        }
        this.wasPreviousIf = false;
        this.wasPreviousElseIf = false;
        this.firstMatchingLists.get(this.firstMatchingLists.size() - 1).add(decisionNode);
        return this;
    }

    @Override
    protected void process() {

        this.action.execute();
        if (notAssignedNode != null && this.wasPreviousIf) {
            this.allMatchingLists.add(this.notAssignedNode);
        }
        this.allMatchingLists
                .stream()
                .filter(decisionNode -> decisionNode.evaluation.evaluate())
                .forEach(ADecisionNode::process);
        this.firstMatchingLists.forEach(firstMatchingList -> {
            firstMatchingList
                    .stream()
                    .filter(decisionNode -> decisionNode.evaluation.evaluate())
                    .findFirst()
                    .ifPresent(ADecisionNode::process);
        });

    }

    private void fillCurrentFirstMatchingList(ADecisionNode decisionNode) {
        List<ADecisionNode> firstMatchingList = new ArrayList<>();
        firstMatchingList.add(notAssignedNode);
        firstMatchingList.add(decisionNode);
        this.firstMatchingLists.add(firstMatchingList);
        this.notAssignedNode = null;
    }

}
