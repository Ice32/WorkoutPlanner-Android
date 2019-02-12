package com.workoutplanner.view.common;

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
import com.workoutplanner.model.Workout;
import com.workoutplanner.service.WorkoutsService;

import java.util.ArrayList;



public class WorkoutsListFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;
    private ConstraintLayout view;
    private RecyclerView recyclerView;
    private TextView createdWorkoutsEmptyView;

    public WorkoutsListFragment() {
    }

    @SuppressWarnings("unused")
    public static WorkoutsListFragment newInstance(int columnCount) {
        return new WorkoutsListFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (ConstraintLayout) inflater.inflate(R.layout.common_workout_list, container, false);
        recyclerView = view.findViewById(R.id.createdWorkoutsRecyclerView);
        createdWorkoutsEmptyView = view.findViewById(R.id.createdWorkoutsEmptyView);
        // Set the adapter
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new WorkoutsViewAdapter(
                new ArrayList<Workout>(),
                mListener
        ));

        loadData();
        return view;
    }

    private void loadData() {
        new WorkoutsService().getCreatedWorkouts(workouts -> {
            recyclerView.setAdapter(new WorkoutsViewAdapter(
                    workouts,
                    mListener
            ));
            if (workouts.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                createdWorkoutsEmptyView.setVisibility(View.VISIBLE);
            }
            else {
                recyclerView.setVisibility(View.VISIBLE);
                createdWorkoutsEmptyView.setVisibility(View.GONE);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Workout item);
    }
}
