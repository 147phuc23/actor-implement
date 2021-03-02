package org.example.impl;

import org.example.Actor;
import org.example.Message;

public class PrinterActor extends Actor {
    public PrinterActor(String name) {
        super(name);
    }

    @Override
    protected void handleMessage(Message message) {
        // do nothing
    }
}
