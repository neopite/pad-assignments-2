package com.neophite.actors;

import akka.actor.*;

import java.time.Duration;
import java.util.LinkedList;

public class OperatorActor extends AbstractActor {
    private ActorRef currentVisitor;
    private String name;
    private LinkedList<ActorRef> visitorsQueue;
    private ActorRef onlineVisitor;

    public OperatorActor(String name , ActorSystem actorSystem) {
        this.name = name;
        this.visitorsQueue = new LinkedList<>();

    }

    public static Props props(String name) {
        return Props.create(OperatorActor.class,name);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals(VisitorActor.VisitorCommands.CAN_I_CONNECT , callback -> {
            onVisitorRequestToConnect();
        }).matchEquals(VisitorActor.VisitorCommands.CONNECT_TO_OPERATOR , callback -> {
            onConnectedToOperatorProcces();
        }).matchEquals(VisitorActor.VisitorCommands.END_DIALOG,callback -> {
            onSucessfullDisconnectionAction();
        }).

                build();
    }

    private void onSucessfullDisconnectionAction(){
        System.out.println("OPERATOR : " + name + " is free");
    }

    private void onVisitorRequestToConnect(){
        if(onlineVisitor==null){
            getSender().tell(OperatorCommands.CONNECT_USER,getSelf());
        }else {
            getSender().tell(OperatorCommands.WAIT_FOR_CONNECT , getSelf());
        }
    }

    private void onConnectedToOperatorProcces(){
        getContext().system().scheduler().scheduleOnce(Duration.ofMillis(5000),
                currentVisitor, OperatorCommands.SUCCESFULL_DIALOG_ENDED, getContext().system().dispatcher(), getSelf());
    }

    public enum OperatorCommands{
        CONNECT_USER,
        WAIT_FOR_CONNECT,
        SUCCESFULL_DIALOG_ENDED
    }

}
