package it.appventurers.taskflow.ui.elements.create;

import static it.appventurers.taskflow.util.Constant.LOAD_FRAGMENT;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.atomic.AtomicInteger;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.FragmentCreateHabitBinding;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.ui.elements.main.MainActivity;
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
    private String title;
    private String note;
    private boolean negative;
    private boolean positive;
    private int difficulty;
    private int resetCounter;

    public CreateHabitFragment() {
        // Required empty public constructor
    }

    public static CreateHabitFragment newInstance() {
        return new CreateHabitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        negative = false;
        positive = false;
        difficulty = 0;
        resetCounter = 0;
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

        binding.negativePositiveButtonGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            positive = checkedId == R.id.positive_button && isChecked;
            negative = checkedId == R.id.negative_button && isChecked;
        });

        binding.difficultyButtonGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == R.id.trivial_button) {
                difficulty = 1;
            } else if (checkedId == R.id.easy_button) {
                difficulty = 2;
            } else if (checkedId == R.id.medium_button) {
                difficulty = 3;
            } else if (checkedId == R.id.hard_button) {
                difficulty = 4;
            }
        });

        binding.resetCounterButtonGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == R.id.daily_button) {
                resetCounter = 1;
            } else if (checkedId == R.id.weekly_button) {
                resetCounter = 2;
            } else if (checkedId == R.id.monthly_button) {
                resetCounter = 3;
            }
        });

        binding.createButton.setOnClickListener(view1 -> {
            binding.createButton.setVisibility(View.INVISIBLE);
            binding.habitProgress.setVisibility(View.VISIBLE);
            title = binding.titleEditText.getText().toString().trim();
            note = binding.noteEditText.getText().toString().trim();
            if (!title.isEmpty() && (positive || negative) && difficulty != 0 && resetCounter != 0) {
                Habit habit = new Habit(title, note, negative, positive, difficulty, resetCounter);
                dataViewModel.saveHabit(userViewModel.getLoggedUser(), habit);
                dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
                    if (result.isSuccess()) {
                        Snackbar.make(view, "Habit created", Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra(LOAD_FRAGMENT, "HabitFragment");
                        startActivity(intent);
                        requireActivity().finish();
                    } else {
                        String error = ((Result.Fail) result).getError();
                        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
                        binding.createButton.setVisibility(View.VISIBLE);
                        binding.habitProgress.setVisibility(View.INVISIBLE);
                    }
                });
            } else {
                Snackbar.make(view, "Insert all fields", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}