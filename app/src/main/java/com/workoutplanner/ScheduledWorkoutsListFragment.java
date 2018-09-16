package com.workoutplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.workoutplanner.api.interfaces.WorkoutsAPI;
import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.service.JwtTokenProvider;
import com.workoutplanner.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ScheduledWorkoutsListFragment extends Fragment {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private OnListFragmentInteractionListener mListener;
    private WorkoutsAPI workoutsAPI;
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

        workoutsAPI = new ServiceGenerator(new JwtTokenProvider(getActivity())).createService(WorkoutsAPI.class);
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
        Call<List<ScheduledWorkout>> workoutsRequest = workoutsAPI.getScheduledWorkouts();
        workoutsRequest.enqueue(new Callback<List<ScheduledWorkout>>() {
            @Override
            public void onResponse(@NonNull Call<List<ScheduledWorkout>> call, @NonNull Response<List<ScheduledWorkout>> response) {
                if(response.isSuccessful()) {
                    List<ScheduledWorkout> workouts = response.body();
                    view.setAdapter(new ScheduledWorkoutsViewAdapter(
                            workouts,
                            mListener
                    ));
                } else {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ScheduledWorkout>> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
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
        void onButtonClick(ScheduledWorkout item);
    }
}
