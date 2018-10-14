package com.workoutplanner.view.exercises;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workoutplanner.R;
import com.workoutplanner.view.exercises.ExistingExerciseListFragment.OnListFragmentInteractionListener;
import com.workoutplanner.model.Exercise;
import java.util.List;

public class ExistingExerciseViewAdapter extends RecyclerView.Adapter<ExistingExerciseViewAdapter.ViewHolder> {

    private final List<Exercise> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ExistingExerciseViewAdapter(List<Exercise> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.existing_exercise_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Exercise exercise = mValues.get(position);
        holder.mItem = exercise;
        holder.mNameView.setText(exercise.name);
        String setsReps = exercise.sets + " sets, " + exercise.reps + " reps";
        holder.mSetsRepsView.setText(setsReps);

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mNameView;
        final TextView mSetsRepsView;
        public Exercise mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.addedExerciseHeader);
            mSetsRepsView = view.findViewById(R.id.addedExerciseSubHeader);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
