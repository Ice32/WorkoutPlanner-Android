package com.workoutplanner.service.interfaces;

import com.workoutplanner.model.Exercise;
import com.workoutplanner.model.ScheduledWorkout;

import java.util.List;

public interface WorkoutsService {
    List<Exercise> getAllCreatedExercises();
    public List<ScheduledWorkout> getAllScheduledWorkouts();
}
