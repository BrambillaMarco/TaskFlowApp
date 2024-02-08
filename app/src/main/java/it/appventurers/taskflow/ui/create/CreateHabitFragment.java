package it.appventurers.taskflow.ui.create;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.FragmentCreateHabitBinding;

/**
 * Create habit fragment class
 */
public class CreateHabitFragment extends Fragment {

    private FragmentCreateHabitBinding binding;

    public CreateHabitFragment() {
        // Required empty public constructor
    }

    public static CreateHabitFragment newInstance() {
        return new CreateHabitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backButton.setOnClickListener(view1 -> requireActivity().finish());

        binding.createButton.setOnClickListener(view1 -> {
            binding.createButton.setVisibility(View.INVISIBLE);
            binding.habitProgress.setVisibility(View.VISIBLE);
            requireActivity().finish();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}