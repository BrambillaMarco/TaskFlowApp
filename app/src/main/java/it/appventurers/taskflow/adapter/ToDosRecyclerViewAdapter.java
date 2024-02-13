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

public class ToDosRecyclerViewAdapter extends
        RecyclerView.Adapter<ToDosRecyclerViewAdapter.ToDosViewHolder> {

    ArrayList<ToDos> todos;

    public ToDosRecyclerViewAdapter(ArrayList<ToDos> todos) {
        this.todos = todos;
    }
    public static class ToDosViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        public ToDosViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.todo_title);
            itemView.setOnClickListener(this);
        }

        public void bind(ArrayList<ToDos> todos) {
            title.setText(todos.getTitle());
        }
    }
    @Override
    public ToDosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.todo_item, viewGroup, false);

        return new ToDosViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ToDosViewHolder holder, final int position) {
        holder.getTextView().setText(habitsList[position]);
    }
    @Override
    public int getItemCount() {
        return dailies.length;
    }
}
