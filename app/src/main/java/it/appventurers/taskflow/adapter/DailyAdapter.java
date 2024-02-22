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

import java.util.ArrayList;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.model.Daily;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    public interface OnItemClickListener {

        void onCheckBoxButtonClick(int position);
        void onCardViewClick(int position);
    }

    private final ArrayList<Daily> dailyList;
    private final DailyAdapter.OnItemClickListener onItemClickListener;

    public DailyAdapter(ArrayList<Daily> dailyList, OnItemClickListener onItemClickListener) {
        this.dailyList = dailyList;
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox dailyCheckBox;
        private final CardView dailyCardView;
        private final TextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dailyCheckBox = itemView.findViewById(R.id.daily_and_to_do_check_box);
            dailyCardView = itemView.findViewById(R.id.daily_and_to_do_card);
            titleTextView = itemView.findViewById(R.id.daily_to_do_title_text);
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
            onItemClickListener.onCheckBoxButtonClick(position);
        });

        holder.getTitleTextView().setText(dailyList.get(position).getName());
        holder.getDailyCardView().setOnClickListener(view -> {
            onItemClickListener.onCardViewClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }
}
