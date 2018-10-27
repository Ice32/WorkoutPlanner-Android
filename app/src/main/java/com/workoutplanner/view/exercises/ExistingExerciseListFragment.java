package com.workoutplanner.view.exercises;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workoutplanner.R;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.service.ExercisesService;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ExistingExerciseListFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;
    private ConstraintLayout view;
    private RecyclerView recyclerView;
    private TextView createdExercisesEmptyView;

    public ExistingExerciseListFragment() {
    }

    @SuppressWarnings("unused")
    public static ExistingExerciseListFragment newInstance() {
        return new ExistingExerciseListFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadExercises();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (ConstraintLayout) inflater.inflate(R.layout.exercises_list, container, false);
        recyclerView = view.findViewById(R.id.createdExercisesRecyclerView);
        createdExercisesEmptyView = view.findViewById(R.id.createdExercisesEmptyView);
        // Set the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ExistingExerciseViewAdapter(
                new ArrayList<Exercise>(),
                mListener
        ));
        loadExercises();
        return view;
    }

    private void loadExercises() {
        new ExercisesService().findExercises(exercises -> {
            recyclerView.setAdapter(new ExistingExerciseViewAdapter(
                    exercises,
                    mListener
            ));
            if (exercises.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                createdExercisesEmptyView.setVisibility(View.VISIBLE);
            }
            else {
                recyclerView.setVisibility(View.VISIBLE);
                createdExercisesEmptyView.setVisibility(View.GONE);
            }
        });
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

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Exercise item);
    }
}
