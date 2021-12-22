package com.neophite;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.neophite.actors.OperatorActor;

import java.util.LinkedList;
import java.util.List;

public class AppRoot {
    public static void main(String[] args) {
        ActorSystem rootSystem = ActorSystem.create("lab-2-actor-system");
        var operators = new LinkedList<ActorRef>();
        operators.add(rootSystem.actorOf(OperatorActor.props("operator-0")));
        operators.add(rootSystem.actorOf(OperatorActor.props("operator-1")));
        operators.add(rootSystem.actorOf(OperatorActor.props("operator-2")));

        var visitorFlow = new VisitorProvider(operators,rootSystem);
        visitorFlow.run();
        }
    }