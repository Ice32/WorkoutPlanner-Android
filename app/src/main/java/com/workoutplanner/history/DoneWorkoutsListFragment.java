package com.workoutplanner.history;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.workoutplanner.R;
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
public class DoneWorkoutsListFragment extends Fragment {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private WorkoutsAPI workoutsAPI;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DoneWorkoutsListFragment() {
    }

    @SuppressWarnings("unused")
    public static DoneWorkoutsListFragment newInstance(int columnCount) {
        DoneWorkoutsListFragment fragment = new DoneWorkoutsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        workoutsAPI = new ServiceGenerator(new JwtTokenProvider(getActivity())).createService(WorkoutsAPI.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.done_workouts_fragment_item_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new DoneWorkoutsViewAdapter(
                new ArrayList<ScheduledWorkout>(),
                mListener
            ));

            Call<List<ScheduledWorkout>> workoutsRequest = workoutsAPI.getDoneScheduledWorkouts();
            workoutsRequest.enqueue(new Callback<List<ScheduledWorkout>>() {
                @Override
                public void onResponse(Call<List<ScheduledWorkout>> call, Response<List<ScheduledWorkout>> response) {
                    if(response.isSuccessful()) {
                        List<ScheduledWorkout> workouts = response.body();
                        recyclerView.setAdapter(new DoneWorkoutsViewAdapter(
                                workouts,
                                mListener
                        ));
                    } else {
                        System.out.println(response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<ScheduledWorkout>> call, Throwable t) {
                    Log.e(LOG_TAG, t.getMessage());
                }
            });
        }
        return view;
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
