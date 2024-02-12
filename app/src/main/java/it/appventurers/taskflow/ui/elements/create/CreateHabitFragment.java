package it.appventurers.taskflow.ui.elements.create;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.atomic.AtomicInteger;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.FragmentCreateHabitBinding;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.ui.viewmodel.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.UserViewModel;
import it.appventurers.taskflow.util.ClassBuilder;

/**
 * Create habit fragment class
 */
public class CreateHabitFragment extends Fragment {

    private FragmentCreateHabitBinding binding;
    private UserViewModel userViewModel;
    private DataViewModel dataViewModel;

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
        userViewModel = new UserViewModel(
                ClassBuilder.getClassBuilder()
                        .getUserRepository(requireActivity().getApplication()));
        dataViewModel = new DataViewModel(
                ClassBuilder.getClassBuilder()
                        .getDataRepository(requireActivity().getApplication()));

        binding.backButton.setOnClickListener(view1 -> requireActivity().finish());

        binding.createButton.setOnClickListener(view1 -> {
            binding.createButton.setVisibility(View.INVISIBLE);
            binding.habitProgress.setVisibility(View.VISIBLE);
            String title = binding.titleEditText.getText().toString().trim();
            String note = binding.noteEditText.getText().toString().trim();
            boolean positive = binding.positiveButton.isSelected();
            boolean negative = binding.negativeButton.isSelected();
            int difficulty = 0;
            if (binding.trivialButton.isSelected()) {
                difficulty = 1;
            } else if (binding.easyButton.isSelected()) {
                difficulty = 2;
            } else if (binding.mediumButton.isSelected()) {
                difficulty = 3;
            } else if (binding.hardButton.isSelected()) {
                difficulty = 4;
            }
            int resetCounter = 0;
            if (binding.dailyButton.isSelected()) {
                resetCounter = 1;
            } else if (binding.weeklyButton.isSelected()) {
                resetCounter = 2;
            } else if (binding.monthlyButton.isSelected()) {
                resetCounter = 3;
            }
            Habit habit = new Habit(title, note, negative, positive, difficulty, resetCounter);
            dataViewModel.saveHabit(userViewModel.getLoggedUser(), habit);
            dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
                if (result.isSuccess()) {
                    Snackbar.make(view, "Habit created", Snackbar.LENGTH_SHORT).show();
                    requireActivity().finish();
                } else {
                    String error = ((Result.Fail) result).getError();
                    Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
                    binding.createButton.setVisibility(View.VISIBLE);
                    binding.habitProgress.setVisibility(View.INVISIBLE);
                }
            });

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}