package com.neophite;

import akka.actor.ActorSystem;
import com.neophite.actors.OperatorActor;

import java.util.List;

public class AppRoot {
    public static void main(String[] args) {
        ActorSystem rootSystem = ActorSystem.create("lab-2-actor-system");
        var listOfCashCells = List.of(
                rootSystem.actorOf(OperatorActor.props("operator-0")),
                rootSystem.actorOf(OperatorActor.props("operator-1")),
                        rootSystem.actorOf(OperatorActor.props("operator-2")));
        var visitorFlow = new VisitorProvider(listOfCashCells,rootSystem);
        visitorFlow.run();
        }
    }