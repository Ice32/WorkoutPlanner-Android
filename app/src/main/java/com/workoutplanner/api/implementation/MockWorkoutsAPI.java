package com.workoutplanner.api.implementation;

import com.workoutplanner.api.interfaces.WorkoutsAPI;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.model.Workout;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class MockWorkoutsAPI implements WorkoutsAPI {
    private List<Workout> workouts = new LinkedList<>();
    private List<Exercise> exercises = new LinkedList<>();

    {
        exercises.add(new Exercise("Ball stretch"));
        exercises.add(new Exercise("Lower back stretch"));

        workouts.add(new Workout("Morning yoga", exercises));
        workouts.add(new Workout("Back stretches", exercises));
    }

    @Override
    public List<Workout> getCreatedWorkouts() {
        Logger.getAnonymousLogger().info("Returning " + workouts.size() + " workouts");
        return workouts;
    }

    @Override
    public void addWorkout(Workout workout) {
        workouts.add(workout);
    }
}
