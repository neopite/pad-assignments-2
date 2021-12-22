package com.neophite;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.neophite.actors.VisitorActor;

import java.util.LinkedList;
import java.util.List;

public class VisitorProvider implements Runnable {

    private final LinkedList<ActorRef> operators;
    private final ActorSystem actorSystem;

    public VisitorProvider(LinkedList<ActorRef> operators, ActorSystem actorSystem){
        this.operators = operators;
        this.actorSystem = actorSystem;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            actorSystem.actorOf(VisitorActor.props(operators,Integer.toString(i)));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }
}
