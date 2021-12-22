package com.neophite.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.util.LinkedList;
import java.util.Random;

public class VisitorActor extends AbstractActor {
    private String name;
    private LinkedList<ActorRef> operators;
    private ActorRef currentOperator;

    public VisitorActor(LinkedList<ActorRef> operators,String name) {
        this.name = name;
        this.operators = operators;
        chooseRandomOperator();
    }

    private void chooseRandomOperator(){
        var random = new Random();
        var currentCashCellIndex = random.nextInt(operators.size());
        var randomOperator = operators.get(currentCashCellIndex);
        randomOperator.tell(VisitorCommands.CAN_I_CONNECT,getSelf());
    }

    public static Props props(String name) {
        return Props.create(VisitorActor.class,name);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }

    public enum VisitorCommands {
        CAN_I_CONNECT
    }

}
