package com.workoutplanner.view.exercises;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.workoutplanner.R;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.model.Workout;
import com.workoutplanner.service.ExercisesService;

import java.util.ArrayList;

public class SelectableExercisesFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;
    private RecyclerView view;

    private static final String workout_key = "workout_key";
    private Workout workout;

    public SelectableExercisesFragment() {
    }

    @SuppressWarnings("unused")
    public static SelectableExercisesFragment newInstance(Workout workout) {
        final SelectableExercisesFragment f = new SelectableExercisesFragment();

        Bundle args = new Bundle();
        args.putSerializable(workout_key, workout);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            workout = (Workout) args.getSerializable(workout_key);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (RecyclerView) inflater.inflate(R.layout.exercises_selectable_exercise_list, container, false);

        // Set the adapter
        view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        loadAllExercises();
        view.setAdapter(new SelectableExerciseViewAdapter(new ArrayList<Exercise>(), mListener, new ArrayList<>()));
        return view;
    }

    private void loadAllExercises() {
        new ExercisesService().findExercises(exercises -> view.setAdapter(new SelectableExerciseViewAdapter(
                exercises,
                mListener,
                workout != null ? workout.exercises : new ArrayList<>()
        )));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Exercise item, boolean selected);
    }
}
