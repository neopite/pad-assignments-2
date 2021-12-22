package com.neophite;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.neophite.actors.VisitorActor;

import java.util.List;

public class VisitorProvider implements Runnable {

    private final List<ActorRef> cashCells;
    private final ActorSystem actorSystem;

    public VisitorProvider(List<ActorRef> cashCells, ActorSystem actorSystem){
        this.cashCells = cashCells;
        this.actorSystem = actorSystem;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            actorSystem.actorOf(VisitorActor.props(Integer.toString(i)));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }
}
