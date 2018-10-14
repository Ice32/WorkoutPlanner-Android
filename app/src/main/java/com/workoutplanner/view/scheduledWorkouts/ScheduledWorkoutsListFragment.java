package com.workoutplanner.view.scheduledWorkouts;

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
import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.service.ScheduledWorkoutsService;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ScheduledWorkoutsListFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;
    private RecyclerView view;

    public ScheduledWorkoutsListFragment() {
    }

    @SuppressWarnings("unused")
    public static ScheduledWorkoutsListFragment newInstance(int columnCount) {
        return new ScheduledWorkoutsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (RecyclerView) inflater.inflate(R.layout.scheduled_workouts_fragment_item_list, container, false);
        // Set the adapter
        Context context = view.getContext();
        view.setLayoutManager(new LinearLayoutManager(context));
        view.setAdapter(new ScheduledWorkoutsViewAdapter(
                new ArrayList<ScheduledWorkout>(),
                mListener
        ));
        loadData();
        return view;
    }

    private void loadData() {
        new ScheduledWorkoutsService().getSheduledWorkouts(scheduledWorkouts -> view.setAdapter(new ScheduledWorkoutsViewAdapter(
                scheduledWorkouts,
                mListener
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

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ScheduledWorkout item);
        void onButtonClick(ScheduledWorkout item);
    }
}
