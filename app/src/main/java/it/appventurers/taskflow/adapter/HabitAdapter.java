package it.appventurers.taskflow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.model.Habit;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {

    public interface OnItemClickListener {

        void onPositiveButtonClick(int position);
        void onNegativeButtonClick(int position);
        void onCardViewClick(int position);
    }

    private final ArrayList<Habit> habitList;
    private final OnItemClickListener onItemClickListener;

    public HabitAdapter(ArrayList<Habit> habitList, OnItemClickListener onItemClickListener) {
        this.habitList = habitList;
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Button negativeButton;
        private final TextView titleTextView;
        private final CardView habitCardView;
        private final Button positiveButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            negativeButton = itemView.findViewById(R.id.negative_button);
            titleTextView = itemView.findViewById(R.id.habit_title_text);
            habitCardView = itemView.findViewById(R.id.habit_card);
            positiveButton = itemView.findViewById(R.id.positive_button);
        }

        public Button getNegativeButton() {
            return negativeButton;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public CardView getHabitCardView() {
            return habitCardView;
        }

        public Button getPositiveButton() {
            return positiveButton;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitAdapter.ViewHolder holder, int position) {
        if (habitList.get(position).getName() == null) {
            holder.getPositiveButton().setVisibility(View.INVISIBLE);
            holder.getNegativeButton().setVisibility(View.INVISIBLE);
            holder.getHabitCardView().setVisibility(View.INVISIBLE);
        }

        holder.getNegativeButton().setEnabled(habitList.get(position).isNegative());
        holder.getNegativeButton().setOnClickListener(view -> {
            onItemClickListener.onNegativeButtonClick(position);
        });

        holder.getTitleTextView().setText(habitList.get(position).getName());
        holder.getHabitCardView().setOnClickListener(view -> {
            onItemClickListener.onCardViewClick(position);
        });

        holder.getPositiveButton().setEnabled(habitList.get(position).isPositive());
        holder.getPositiveButton().setOnClickListener(view -> {
            onItemClickListener.onPositiveButtonClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }
}
