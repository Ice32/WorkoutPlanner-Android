package com.workoutplanner.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.workoutplanner.R;
import com.workoutplanner.history.DoneWorkoutsListFragment.OnListFragmentInteractionListener;
import com.workoutplanner.model.ScheduledWorkout;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class DoneWorkoutsViewAdapter extends RecyclerView.Adapter<DoneWorkoutsViewAdapter.ViewHolder> {

    private final List<ScheduledWorkout> mValues;
    private final OnListFragmentInteractionListener mListener;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM HH:mm");

    public DoneWorkoutsViewAdapter(List<ScheduledWorkout> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.done_workout_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).workout.name);
        holder.mScheduledDateView.setText(
                simpleDateFormat.format(mValues.get(position).time)
        );

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
        public final TextView mNameView;
        public final TextView mScheduledDateView;
        public ScheduledWorkout mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.name);
            mScheduledDateView = view.findViewById(R.id.scheduled_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
