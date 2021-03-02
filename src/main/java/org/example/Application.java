package org.example;

import org.example.impl.NumberActor;
import org.example.impl.message.AddOneMessage;

public class Application {

    public static void main(String[] args) {

        Actor root = new Actor("/") {
            @Override
            protected void handleMessage(Message message) {

            }
        };


        Actor actorA = new NumberActor("/a", 3);


        actorA.ask(root, new AddOneMessage());
        actorA.ask(root, new AddOneMessage());

    }
    int share;
    void a() {
        share += 1;
    }
}