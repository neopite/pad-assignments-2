package com.neophite.actors;

import akka.actor.*;

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
        }).

                build();
    }

    private void onVisitorRequestToConnect(){
        if(onlineVisitor==null){
            getSender().tell(OperatorCommands.CONNECT_USER,getSelf());
        }
    }

    public enum OperatorCommands{
        CONNECT_USER,
        WAIT_FOR_CONNECT
    }

}
