package it.appventurers.taskflow.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.appventurers.taskflow.R;

/**
 * Habit fragment class
 */
public class HabitFragment extends Fragment {

    public HabitFragment() {
        // Required empty public constructor
    }

    public static HabitFragment newInstance() {
        return new HabitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_habit, container, false);
    }
}