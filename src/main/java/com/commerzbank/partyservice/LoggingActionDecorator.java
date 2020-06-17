package com.commerzbank.partyservice;

public class LoggingActionDecorator implements IAction {

    private final String mesasge;
    private final IAction action;

    public LoggingActionDecorator(String mesasge, IAction action) {
        this.mesasge = mesasge;
        this.action = action;
    }

    @Override
    public void execute() {
        System.out.println(mesasge);
        this.action.execute();
    }
}
