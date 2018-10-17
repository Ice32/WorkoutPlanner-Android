package com.workoutplanner.model;

import java.io.Serializable;
import java.util.List;

public class Workout implements Serializable {
    public Workout(String name) {
        this.name = name;
    }
    public Workout(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Workout(int id, String name, List<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
    }

    public Workout(String name, List<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public int id;

    public String name;

    public List<Exercise> exercises;
}
