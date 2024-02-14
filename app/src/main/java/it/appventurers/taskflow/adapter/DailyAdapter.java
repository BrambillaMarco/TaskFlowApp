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
import it.appventurers.taskflow.model.Daily;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private final List<Daily> dailyList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox dailyCheckBox;
        private final CardView dailyCardView;
        private final TextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dailyCheckBox = (CheckBox) itemView.findViewById(R.id.daily_and_to_do_check_box);
            dailyCardView = (CardView) itemView.findViewById(R.id.daily_and_to_do_card);
            titleTextView = (TextView) itemView.findViewById(R.id.daily_to_do_title_text);
        }

        public CheckBox getDailyCheckBox() {
            return dailyCheckBox;
        }

        public CardView getDailyCardView() {
            return dailyCardView;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }
    }

    public DailyAdapter(List<Daily> dailyList) {
        this.dailyList = dailyList;
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
    public void onBindViewHolder(@NonNull DailyAdapter.ViewHolder holder, int position) {
        holder.getDailyCheckBox().setOnClickListener(view -> {
            Snackbar.make(view, "Checkbox premuta!", Snackbar.LENGTH_LONG).show();
        });
        holder.getDailyCardView().setOnClickListener(view -> {
            Snackbar.make(view, "Card view premuta!", Snackbar.LENGTH_LONG).show();
        });
        holder.getTitleTextView().setText(dailyList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }
}
