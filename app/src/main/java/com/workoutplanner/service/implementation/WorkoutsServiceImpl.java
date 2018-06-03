package com.workoutplanner.service.implementation;

import com.workoutplanner.api.implementation.MockWorkoutsAPI;
import com.workoutplanner.api.interfaces.WorkoutsAPI;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.model.Workout;
import com.workoutplanner.service.interfaces.WorkoutsService;

import java.util.ArrayList;
import java.util.List;

public class WorkoutsServiceImpl implements WorkoutsService {
    private WorkoutsAPI workoutsAPI = new MockWorkoutsAPI();

    @Override
    public List<Exercise> getAllCreatedExercises() {
        List<Workout> workouts = workoutsAPI.getCreatedWorkouts();

        List<Exercise> exercises = new ArrayList<>();

        for (Workout w: workouts) {
            exercises.addAll(w.exercises);
        }

        return exercises;
    }

    @Override
    public List<ScheduledWorkout> getAllScheduledWorkouts() {
        return workoutsAPI.getScheduledWorkouts();
    }
}
