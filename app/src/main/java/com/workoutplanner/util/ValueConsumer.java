package com.workoutplanner.util;

@FunctionalInterface
public interface ValueConsumer<T> {
    void consume(T t) ;
}
