package com.workoutplanner.existingExercises;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workoutplanner.R;
import com.workoutplanner.existingExercises.SelectableExerciseFragment.OnListFragmentInteractionListener;
import com.workoutplanner.model.Exercise;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Exercise} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SelectableExerciseViewAdapter extends RecyclerView.Adapter<SelectableExerciseViewAdapter.ViewHolder> {

    private final List<Exercise> mValues;
    private final OnListFragmentInteractionListener mListener;

    public SelectableExerciseViewAdapter(List<Exercise> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_selectableexercise, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.selectableExerciseHeader.setText(mValues.get(position).name);
        holder.selectableExerciseSubHeader.setText("4 res");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView selectableExerciseHeader;
        public final TextView selectableExerciseSubHeader;
        public Exercise mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            selectableExerciseHeader = view.findViewById(R.id.selectableExerciseHeader);
            selectableExerciseSubHeader =view.findViewById(R.id.selectableExerciseSubHeader);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + selectableExerciseSubHeader.getText() + "'";
        }
    }
}
