package com.workoutplanner.api;

public class SimpleValueContainer<T> {
    private T value;

    public SimpleValueContainer(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
