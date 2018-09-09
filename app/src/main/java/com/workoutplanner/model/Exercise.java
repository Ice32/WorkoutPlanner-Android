package com.workoutplanner.model;

public class Exercise {
    public int id;
    public String name;
    public int sets;
    public int reps;

    public Exercise(String name) {
        this.name = name;
    }

    public Exercise(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Exercise(String name, int sets, int reps) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
    }
}
