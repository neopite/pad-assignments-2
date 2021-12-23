package com.neophite.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.neophite.RandomHelper;

import java.time.Duration;
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
        System.out.println("VISITOR  : " + name + " TRY CONNECT TO  : " + operatorIndex);
    }

    public static Props props(LinkedList<ActorRef> operators,String name) {
        return Props.create(VisitorActor.class,operators,name);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals(OperatorActor.OperatorCommands.CONNECT_USER , callback -> {
            connectToOperator();
        }).matchEquals(OperatorActor.OperatorCommands.SUCCESFULL_DIALOG_ENDED, callback -> {
            onSucessfullDialogEnded();
        }).matchEquals(OperatorActor.OperatorCommands.WAIT_FOR_CONNECT , callback -> {
            onConnectionWaitingAction();
        }).
                build();
    }

    private void onConnectionWaitingAction(){
        var chance = Math.random();
        if(chance>0.8){
            System.out.println("VISITOR : " + name + " FAILED");
        }else {
            getContext().system().scheduler().scheduleOnce(Duration.ofMillis(5000),
                    getSender(), VisitorCommands.CAN_I_CONNECT, getContext().system().dispatcher(), getSelf());
        }
    }

    private void connectToOperator()
    {
        System.out.println("VISITOR : " + name + " CONNECTED TO : " + operatorIndex );
        currentOperator = getSender();
        getSender().tell(VisitorCommands.CONNECT_TO_OPERATOR,getSelf());
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
