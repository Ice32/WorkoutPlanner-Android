package com.workoutplanner.api.implementation;

import com.workoutplanner.api.interfaces.WorkoutsAPI;
import com.workoutplanner.model.Workout;

import java.util.LinkedList;
import java.util.List;

public class MockWorkoutsAPI implements WorkoutsAPI {
    @Override
    public List<Workout> getCreatedWorkouts() {
        List<Workout> workouts = new LinkedList<>();
        workouts.add(new Workout("Morning yoga"));
        workouts.add(new Workout("Back stretches"));
        return workouts;
    }
}
