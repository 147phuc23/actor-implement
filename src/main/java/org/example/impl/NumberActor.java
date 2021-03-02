package org.example.impl;

import org.example.Actor;
import org.example.Message;
import org.example.impl.message.AddOneMessage;
import org.example.impl.message.AddTwoThenReturnValueMessage;
import org.example.impl.message.DoubleMessage;

import java.util.Random;

public class NumberActor extends Actor {
    public static void randomProcessingDelay() {
        try {
            Thread.sleep(new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private int value = 0;
    public NumberActor(String name, int initialValue) {
        super(name);
        value = initialValue;
    }

    @Override
    protected void handleMessage(Message message) {
        if (message == null) return;
        if (message instanceof AddOneMessage) {
            randomProcessingDelay();
            value += 1;
        }
        if (message instanceof DoubleMessage) {
            randomProcessingDelay();
            value *= 2;
        }
        if (message instanceof AddTwoThenReturnValueMessage) {
            value += 2;
            randomProcessingDelay();
            ((AddTwoThenReturnValueMessage) message).getValue().complete(value);
        }
    }

}
