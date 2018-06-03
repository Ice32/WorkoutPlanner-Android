package com.workoutplanner.api.implementation;

import com.workoutplanner.api.interfaces.WorkoutsAPI;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.model.Workout;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class MockWorkoutsAPI implements WorkoutsAPI {
    private List<Workout> workouts = new LinkedList<>();
    private List<Exercise> exercises = new LinkedList<>();
    private List<ScheduledWorkout> scheduledWorkouts = new LinkedList<>();

    {
        exercises.add(new Exercise("Ball stretch"));
        exercises.add(new Exercise("Lower back stretch"));

        workouts.add(new Workout("Morning yoga", exercises));
        workouts.add(new Workout("Back stretches", exercises));

        scheduledWorkouts.add(new ScheduledWorkout(workouts.get(0)));
        scheduledWorkouts.add(new ScheduledWorkout(workouts.get(1)));
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

    @Override
    public List<ScheduledWorkout> getScheduledWorkouts() {
        return scheduledWorkouts;
    }
}
