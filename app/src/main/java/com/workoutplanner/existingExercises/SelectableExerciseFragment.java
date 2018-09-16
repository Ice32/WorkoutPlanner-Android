package com.workoutplanner.existingExercises;

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

import com.workoutplanner.R;
import com.workoutplanner.api.interfaces.ExercisesAPI;
import com.workoutplanner.model.Exercise;
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
public class SelectableExerciseFragment extends Fragment {
    private final String LOG_TAG = this.getClass().getSimpleName();

    private OnListFragmentInteractionListener mListener;
    private ExercisesAPI exercisesAPI;
    private RecyclerView view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SelectableExerciseFragment() {
    }

    @SuppressWarnings("unused")
    public static SelectableExerciseFragment newInstance() {
        return new SelectableExerciseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exercisesAPI = new ServiceGenerator(new JwtTokenProvider(getActivity())).createService(ExercisesAPI.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (RecyclerView) inflater.inflate(R.layout.fragment_selectableexercise_list, container, false);

        // Set the adapter
        view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        view.setAdapter(new SelectableExerciseViewAdapter(new ArrayList<Exercise>(), mListener));
        loadData();
        return view;
    }

    private void loadData() {
        Call<List<Exercise>> exercisesRequest = exercisesAPI.getAllCreatedExercises();
        exercisesRequest.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(@NonNull Call<List<Exercise>> call, @NonNull Response<List<Exercise>> response) {
                if(response.isSuccessful()) {
                    view.setAdapter(new SelectableExerciseViewAdapter(
                            response.body(),
                            mListener
                    ));
                } else {
                    if (response.errorBody() != null) {
                        Log.e(LOG_TAG, response.errorBody().toString());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Exercise>> call, @NonNull Throwable t) {
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
        void onListFragmentInteraction(Exercise item);
    }
}
