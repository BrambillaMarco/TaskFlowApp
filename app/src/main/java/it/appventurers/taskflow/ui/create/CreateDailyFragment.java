package it.appventurers.taskflow.ui.create;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.appventurers.taskflow.R;

/**
 * Create daily fragment class
 */
public class CreateDailyFragment extends Fragment {

    public CreateDailyFragment() {
        // Required empty public constructor
    }

    public static CreateDailyFragment newInstance() {
        return new CreateDailyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_daily, container, false);
    }
}