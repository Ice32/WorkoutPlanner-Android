package com.workoutplanner.view.statistics;

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
import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.service.ScheduledWorkoutsService;

import java.util.ArrayList;


public class DoneWorkoutsListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private TextView doneWorkoutsEmptyView;

    public DoneWorkoutsListFragment() {}

    @SuppressWarnings("unused")
    public static DoneWorkoutsListFragment newInstance(int columnCount) {
        DoneWorkoutsListFragment fragment = new DoneWorkoutsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ConstraintLayout view = (ConstraintLayout) inflater.inflate(R.layout.workouts_done_workouts_list, container, false);
        recyclerView = view.findViewById(R.id.doneWorkoutsRecyclerView);
        doneWorkoutsEmptyView = view.findViewById(R.id.doneWorkoutsEmptyView);
        // Set the adapter
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new DoneWorkoutsViewAdapter(
            new ArrayList<ScheduledWorkout>(),
            mListener
        ));
        loadData();
        return view;
    }

    private void loadData() {
        new ScheduledWorkoutsService().getDoneScheduledWorkouts(scheduledWorkouts -> {
            recyclerView.setAdapter(new DoneWorkoutsViewAdapter(
                    scheduledWorkouts,
                    mListener
            ));
            if (scheduledWorkouts.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                doneWorkoutsEmptyView.setVisibility(View.VISIBLE);
            }
            else {
                recyclerView.setVisibility(View.VISIBLE);
                doneWorkoutsEmptyView.setVisibility(View.GONE);
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
        void onListFragmentInteraction(ScheduledWorkout item);
    }
}
