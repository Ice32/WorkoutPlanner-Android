package com.workoutplanner.api.interfaces;

import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.model.Workout;

import java.util.List;

public interface WorkoutsAPI {
    public List<Workout> getCreatedWorkouts();
    public void addWorkout(Workout workout);
    public List<ScheduledWorkout> getScheduledWorkouts();
}
