package com.workoutplanner.view.exercises;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.workoutplanner.R;
import com.workoutplanner.view.exercises.SelectableExerciseFragment.OnListFragmentInteractionListener;
import com.workoutplanner.model.Exercise;

import java.util.List;


public class SelectableExerciseViewAdapter extends RecyclerView.Adapter<SelectableExerciseViewAdapter.ViewHolder> {

    private final List<Exercise> mValues;
    private final OnListFragmentInteractionListener mListener;

    public SelectableExerciseViewAdapter(List<Exercise> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercises_selectable_exercise_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Exercise exercise = mValues.get(position);
        holder.mItem = exercise;
        holder.selectableExerciseHeader.setText(exercise.name);
        String setsReps = exercise.sets + " sets, " + exercise.reps + " reps";
        holder.selectableExerciseSubHeader.setText(setsReps);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> mListener.onListFragmentInteraction(holder.mItem, isChecked));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView selectableExerciseHeader;
        public final TextView selectableExerciseSubHeader;
        public final CheckBox checkBox;
        public Exercise mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            selectableExerciseHeader = view.findViewById(R.id.selectableExerciseHeader);
            selectableExerciseSubHeader = view.findViewById(R.id.selectableExerciseSubHeader);
            checkBox = view.findViewById(R.id.selectableExerciseCheckBox);
        }
    }
}
