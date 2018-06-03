package com.workoutplanner.model;

import java.util.Date;

public class ScheduledWorkout {
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
