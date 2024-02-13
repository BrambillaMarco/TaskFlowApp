package it.appventurers.taskflow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.appventurers.taskflow.R;

public class HabitsRecyclerViewAdapter extends
        RecyclerView.Adapter<HabitsRecyclerViewAdapter.HabitsViewHolder> {

    ArrayList<Habits> habits;

    public HabitsRecyclerViewAdapter(ArrayList<Habits> habits) {
        this.habits = habits;
    }
    public static class HabitsViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        public HabitsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.habit_title);
            Button buttonPositive = itemView.findViewById(R.id.positive_button);
            Button buttonnegative = itemView.findViewById(R.id.negative_button);
            itemView.setOnClickListener(this);
            buttonPositive.setOnClickListener(this);
        }

        public void bind(String[] habitsList ) {
            title.setText(habitsList.getTitle());
        }
    }
    @Override
    public HabitsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.habit_item, viewGroup, false);

        return new HabitsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(HabitsViewHolder holder, final int position) {
        holder.getTextView().setText(habitsList[position]);
    }
    @Override
    public int getItemCount() {
        return habitsList.length;
    }
}
