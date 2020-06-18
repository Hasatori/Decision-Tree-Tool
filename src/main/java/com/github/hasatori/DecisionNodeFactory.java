package com.github.hasatori;

import static com.github.hasatori.ActionLibrary.doNothing;
import static com.github.hasatori.EvaluationLibrary.alwaysTrue;

public class DecisionNodeFactory {


    private DecisionNodeFactory(){}


    public static DecisionNode root(){
        return new DecisionNode("root",alwaysTrue(),doNothing()){};
    }
    public static DecisionNode decide(IEvaluation evaluation,IAction action){
        return new DecisionNode("",evaluation,action){};
    }
    public static DecisionNode decide(String logMessage,IEvaluation evaluation,IAction action){
        LoggingActionDecorator loggingActionDecorator=new LoggingActionDecorator(logMessage,action);
        return new DecisionNode(logMessage,evaluation,loggingActionDecorator){};
    }
}
