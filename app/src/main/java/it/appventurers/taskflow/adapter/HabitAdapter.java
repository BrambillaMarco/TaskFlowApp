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

import java.util.List;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.model.Habit;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {

    private final List<Habit> habitList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Button negativeButton;
        private final TextView titleTextView;
        private final CardView habitCardView;
        private final Button positiveButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            negativeButton = (Button) itemView.findViewById(R.id.negative_button);
            titleTextView = (TextView) itemView.findViewById(R.id.habit_title_text);
            habitCardView = (CardView) itemView.findViewById(R.id.habit_card);
            positiveButton = (Button) itemView.findViewById(R.id.positive_button);
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

    public HabitAdapter(List<Habit> habitList) {
        this.habitList = habitList;
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
        holder.getNegativeButton().setOnClickListener(view -> {
            Snackbar.make(view, "Tasto negativo premuto!", Snackbar.LENGTH_LONG).show();
        });
        holder.getHabitCardView().setOnClickListener(view -> {
            Snackbar.make(view, "Card view premuta!", Snackbar.LENGTH_LONG).show();
        });
        holder.getTitleTextView().setText(habitList.get(position).getName());
        holder.getPositiveButton().setOnClickListener(view -> {
            Snackbar.make(view, "Tasto positivo premuto!", Snackbar.LENGTH_LONG).show();
        });
    }


    @Override
    public int getItemCount() {
        return habitList.size();
    }
}
