package com.github.hasatori;


import java.util.ArrayList;
import java.util.List;


public abstract class DecisionNode {

    protected final IAction action;
    protected final IEvaluation evaluation;
    protected final List<DecisionNode> children;
    private boolean wasPreviousIf = false;
    private boolean wasPreviousElseIf = false;
    private List<DecisionNode> allMatchingLists = new ArrayList<>();
    private List<List<DecisionNode>> firstMatchingLists = new ArrayList<>();
    private DecisionNode notAssignedNode;

    public DecisionNode(String name,IEvaluation evaluation,IAction action) {
        this.action = action;
        this.evaluation = evaluation;
        this.children = new ArrayList<>();
        System.out.println(name);
    }


    DecisionNode ifThen(DecisionNode decisionNode) {
        if (wasPreviousIf) {
            this.allMatchingLists.add(notAssignedNode);
        }
        this.notAssignedNode = decisionNode;
        this.wasPreviousIf = true;
        this.wasPreviousElseIf = false;
        return this;
    }

    DecisionNode elseIfThen(DecisionNode decisionNode) {
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

    DecisionNode elseThen(DecisionNode decisionNode) {
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

    private void fillCurrentFirstMatchingList(DecisionNode decisionNode){
        List<DecisionNode> firstMatchingList = new ArrayList<>();
        firstMatchingList.add(notAssignedNode);
        firstMatchingList.add(decisionNode);
        this.firstMatchingLists.add(firstMatchingList);
        this.notAssignedNode = null;
    }
    final void process() {
        this.action.execute();
        if (notAssignedNode != null && this.wasPreviousIf) {
            this.allMatchingLists.add(this.notAssignedNode);
        }
        this.allMatchingLists
                .stream()
                .filter(decisionNode -> decisionNode.evaluation.evaluate())
                .forEach(DecisionNode::process);
        this.firstMatchingLists.forEach(firstMatchingList -> {
            firstMatchingList
                    .stream()
                    .filter(decisionNode -> decisionNode.evaluation.evaluate())
                    .findFirst()
                    .ifPresent(DecisionNode::process);
        });
    }

    ;


}
