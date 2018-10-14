package com.workoutplanner.view.statistics;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workoutplanner.R;
import com.workoutplanner.view.statistics.DoneWorkoutsListFragment.OnListFragmentInteractionListener;
import com.workoutplanner.model.ScheduledWorkout;

import java.text.SimpleDateFormat;
import java.util.List;

public class DoneWorkoutsViewAdapter extends RecyclerView.Adapter<DoneWorkoutsViewAdapter.ViewHolder> {

    private final List<ScheduledWorkout> mValues;
    private final OnListFragmentInteractionListener mListener;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd/MM");
    private SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");

    public DoneWorkoutsViewAdapter(List<ScheduledWorkout> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workouts_done_workout_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).workout.name);
        holder.mScheduledDateView.setText(
                simpleDateFormat.format(mValues.get(position).time)
        );
        holder.mScheduledTimeView.setText(
                simpleTimeFormat.format(mValues.get(position).time)
        );

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
        public final View mView;
        public final TextView mNameView;
        public final TextView mScheduledDateView;
        public final TextView mScheduledTimeView;
        public ScheduledWorkout mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.doneWorkoutsHeader);
            mScheduledDateView = view.findViewById(R.id.doneWorkoutsSubHeader);
            mScheduledTimeView = view.findViewById(R.id.doneWorkoutsSubHeader2);;
        }
    }
}
