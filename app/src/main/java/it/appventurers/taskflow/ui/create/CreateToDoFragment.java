package it.appventurers.taskflow.ui.create;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.appventurers.taskflow.R;

/**
 * Create to do fragment class
 */
public class CreateToDoFragment extends Fragment {

    public CreateToDoFragment() {
        // Required empty public constructor
    }

    public static CreateToDoFragment newInstance() {
        return new CreateToDoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_to_do, container, false);
    }
}