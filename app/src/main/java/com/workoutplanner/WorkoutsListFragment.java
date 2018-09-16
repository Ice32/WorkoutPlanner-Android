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
import com.workoutplanner.model.Workout;
import com.workoutplanner.service.JwtTokenProvider;
import com.workoutplanner.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class WorkoutsListFragment extends Fragment {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private WorkoutsAPI workoutsAPI;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView view;

    public WorkoutsListFragment() {
    }

    @SuppressWarnings("unused")
    public static WorkoutsListFragment newInstance(int columnCount) {
        return new WorkoutsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutsAPI = new ServiceGenerator(new JwtTokenProvider(getActivity())).createService(WorkoutsAPI.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (RecyclerView) inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        view.setLayoutManager(new LinearLayoutManager(context));
        view.setAdapter(new WorkoutsViewAdapter(
            new ArrayList<Workout>(),
            mListener
        ));

        loadData();
        return view;
    }

    private void loadData() {
        Call<List<Workout>> workoutsRequest = workoutsAPI.getCreatedWorkouts();
        workoutsRequest.enqueue(new Callback<List<Workout>>() {
            @Override
            public void onResponse(@NonNull Call<List<Workout>> call, @NonNull Response<List<Workout>> response) {
                if(response.isSuccessful()) {
                    List<Workout> workouts = response.body();
                    view.setAdapter(new WorkoutsViewAdapter(
                            workouts,
                            mListener
                    ));
                } else {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Workout>> call, @NonNull Throwable t) {
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
