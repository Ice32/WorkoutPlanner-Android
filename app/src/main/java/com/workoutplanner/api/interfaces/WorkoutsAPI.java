package com.workoutplanner.api.interfaces;

import com.workoutplanner.model.Workout;

import java.util.List;

public interface WorkoutsAPI {
    public List<Workout> getCreatedWorkouts();
}
