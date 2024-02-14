package it.appventurers.taskflow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.model.ToDo;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private final List<ToDo> toDoList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox toDoCheckBox;
        private final CardView toDoCardView;
        private final TextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            toDoCheckBox = (CheckBox) itemView.findViewById(R.id.daily_and_to_do_check_box);
            toDoCardView = (CardView) itemView.findViewById(R.id.daily_and_to_do_card);
            titleTextView = (TextView) itemView.findViewById(R.id.daily_to_do_title_text);
        }

        public CheckBox getToDoCheckBox() {
            return toDoCheckBox;
        }

        public CardView getToDoCardView() {
            return toDoCardView;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }
    }

    public ToDoAdapter(List<ToDo> toDoList) {
        this.toDoList = toDoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_and_to_do_row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {
        holder.getToDoCheckBox().setOnClickListener(view -> {
            Snackbar.make(view, "Checkbox premuta!", Snackbar.LENGTH_LONG).show();
        });
        holder.getToDoCardView().setOnClickListener(view -> {
            Snackbar.make(view, "Card view premuta!", Snackbar.LENGTH_LONG).show();
        });
        holder.getTitleTextView().setText(toDoList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }
}

