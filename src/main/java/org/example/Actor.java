package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Actor {
    public String name;

    public Actor(String name) {
        this.name = name;
        Thread thread = new Thread(this::run);

    }

    private BlockingQueue<Message> mailboxes = new LinkedBlockingQueue<>();

    private synchronized void run() {
        while (true) {
            if (!mailboxes.isEmpty()) {
                try {
                    Message message = this.mailboxes.take();
                    handleMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void tell(Actor sender, Message message) {
        message.sender = sender;
        mailboxes.offer(message);

    }

    protected void ask(Actor sender, Message message) {
        message.sender = sender;
        mailboxes.offer(message);
        message.requireReturn = true;
    }

     protected abstract void handleMessage(Message message);
}
