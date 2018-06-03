package com.workoutplanner.model;

public class Exercise {
    public int id;
    public String name;
    public int defaultSets;
    public int defaultReps;

    public Exercise(String name) {
        this.name = name;
    }

    public Exercise(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Exercise(int id, String name, int defaultSets, int defaultReps) {
        this.id = id;
        this.name = name;
        this.defaultSets = defaultSets;
        this.defaultReps = defaultReps;
    }
}
