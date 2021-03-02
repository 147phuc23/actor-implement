package org.example.impl.message;

import org.example.Message;

import java.util.concurrent.CompletableFuture;

public class AddTwoThenReturnValueMessage extends Message {
    CompletableFuture<Integer> value;

    public CompletableFuture<Integer> getValue() {
        return value;
    }

    public void setValue(CompletableFuture<Integer> value) {
        this.value = value;
    }
}
