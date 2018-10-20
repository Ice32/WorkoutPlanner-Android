package com.workoutplanner.model;

import java.io.Serializable;
import java.util.Date;

public class ScheduledWorkout implements Serializable {
    public Long id;
    public Workout workout;
    public Date time;

    public ScheduledWorkout(Workout workout) {
        this.workout = workout;
    }

    public ScheduledWorkout(Workout workout, Date time) {
        this.workout = workout;
        this.time = time;
    }
}
