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

public class DailiesRecyclerViewAdapter extends
        RecyclerView.Adapter<DailiesRecyclerViewAdapter.DailiesViewHolder> {

    ArrayList<Dailies> dailies;

    public DailiesRecyclerViewAdapter(ArrayList<Dailies> dailies) {
        this.dailies = dailies;
    }
    public static class DailiesViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        public DailiesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.daily_title);
            Button buttonPositive = itemView.findViewById(R.id.positive_button);
            Button buttonnegative = itemView.findViewById(R.id.negative_button);
            itemView.setOnClickListener(this);
            buttonPositive.setOnClickListener(this);
        }

        public void bind(ArrayList<Dailies> dailies) {
            title.setText(dailies.getTitle());
        }
    }
    @Override
    public DailiesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.daily_item, viewGroup, false);

        return new DailiesViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DailiesViewHolder holder, final int position) {
        holder.getTextView().setText(habitsList[position]);
    }
    @Override
    public int getItemCount() {
        return dailies.length;
    }
}

