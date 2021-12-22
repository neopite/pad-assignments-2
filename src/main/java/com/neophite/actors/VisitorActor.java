package com.neophite.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.neophite.RandomHelper;

import java.util.LinkedList;
import java.util.Random;

public class VisitorActor extends AbstractActor {
    private String name;
    private LinkedList<ActorRef> operators;
    private ActorRef currentOperator;
    private int operatorIndex;

    public VisitorActor(LinkedList<ActorRef> operators,String name) {
        this.name = name;
        this.operators = operators;
        chooseRandomOperator();
    }

    private void chooseRandomOperator(){
        operatorIndex = RandomHelper.getRandomNumber(operators.size());
        var randomOperator = operators.get(operatorIndex);
        randomOperator.tell(VisitorCommands.CAN_I_CONNECT,getSelf());
    }

    public static Props props(String name) {
        return Props.create(VisitorActor.class,name);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals(OperatorActor.OperatorCommands.CONNECT_USER , callback -> {
            connectToOperator();
        }).matchEquals(OperatorActor.OperatorCommands.SUCCESFULL_DIALOG_ENDED, callback -> {
            onSucessfullDialogEnded();
        }).build();
    }

    private void connectToOperator()
    {
        currentOperator = getSender();
        currentOperator.tell(VisitorCommands.CONNECT_TO_OPERATOR,getSelf());
    }

    private void onSucessfullDialogEnded(){
        System.out.println("VISITOR : " + name + " ENDED DIALOG WITH : " + operatorIndex);
        getSender().tell(VisitorCommands.END_DIALOG,getSelf());
    }
    public enum VisitorCommands {
        CAN_I_CONNECT,
        CONNECT_TO_OPERATOR,
        END_DIALOG,
        FAILED_CONNECTION
    }

}
