package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

//@SuppressWarnings("unchecked")
public class Actor<T> {
    public String name;
    private T object;


    public Actor(String name, Class<T> aNew) {
        this.name = name;
        try {
            object = aNew.newInstance();
            Thread thread = new Thread(this::run);
            thread.setDaemon(true);
            thread.setName(name);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  final BlockingQueue<Object> mailboxes = new LinkedBlockingQueue<>();


    private synchronized void run() {
        while (true) {
            if (!mailboxes.isEmpty()) {
                try {
                    Object task = this.mailboxes.take();
                    ((Runnable) task).run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void tell(Actor sender, Consumer<T> consumer) {
        Runnable wrapper = () -> {
            consumer.accept(object);
        };
        mailboxes.offer(wrapper);

    }

    protected  void tell(Actor sender, BiConsumer function) {
        Runnable wrapper = () -> {
            function.accept(sender.object, object);
        };
        mailboxes.offer(wrapper);

    }

    protected void ask(Actor sender, Function<T,Object> function, BiConsumer<Object,Object> callback) {
        Runnable wrapper = () -> {
            callback.accept(sender.object, function.apply(object));
        };
        mailboxes.offer(wrapper);
    }
}
