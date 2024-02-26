package it.appventurers.taskflow.ui.elements.create;

import static it.appventurers.taskflow.util.Constant.HABIT;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.data.repository.data.DataRepository;
import it.appventurers.taskflow.data.repository.user.UserRepository;
import it.appventurers.taskflow.databinding.FragmentCreateHabitBinding;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.ui.elements.main.MainActivity;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModelFactory;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModel;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModelFactory;
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

    private Habit currentHabit;
    private Bundle bundle;

    public CreateHabitFragment() {
        // Required empty public constructor
    }

    public static CreateHabitFragment newInstance() {
        return new CreateHabitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();

        UserRepository userRepository = ClassBuilder.getClassBuilder().getUserRepository(
                requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        DataRepository dataRepository = ClassBuilder.getClassBuilder().getDataRepository(
                requireActivity().getApplication());
        dataViewModel = new ViewModelProvider(
                requireActivity(),
                new DataViewModelFactory(dataRepository)).get(DataViewModel.class);

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

        if (bundle != null) {
            currentHabit = bundle.getParcelable(HABIT);

            binding.createButton.setVisibility(View.INVISIBLE);
            binding.deleteButton.setVisibility(View.VISIBLE);
            binding.saveButton.setVisibility(View.VISIBLE);
            binding.createHabitText.setVisibility(View.INVISIBLE);
            binding.updateHabitText.setVisibility(View.VISIBLE);
            binding.titleEditText.setText(currentHabit.getName());
            binding.titleEditText.setEnabled(false);
            binding.noteEditText.setText(currentHabit.getNote());
            binding.positiveButton.setChecked(currentHabit.isPositive());
            binding.negativeButton.setChecked(currentHabit.isNegative());
            switch (currentHabit.getDifficulty()) {
                case 1:
                    binding.trivialButton.setChecked(true);
                    break;
                case 2:
                    binding.easyButton.setChecked(true);
                    break;
                case 3:
                    binding.mediumButton.setChecked(true);
                    break;
                case 4:
                    binding.hardButton.setChecked(true);
                    break;
            }
            switch (currentHabit.getResetCounter()) {
                case 1:
                    binding.dailyButton.setChecked(true);
                    break;
                case 2:
                    binding.weeklyButton.setChecked(true);
                    break;
                case 3:
                    binding.monthlyButton.setChecked(true);
                    break;
            }
        }

        binding.saveButton.setOnClickListener(view1 -> {
            currentHabit.setNote(binding.noteEditText.getText().toString().trim());
            binding.saveButton.setVisibility(View.INVISIBLE);
            binding.deleteButton.setVisibility(View.INVISIBLE);
            binding.habitProgress.setVisibility(View.VISIBLE);

            if(binding.difficultyButtonGroup.getCheckedButtonId() == R.id.trivial_button){
                currentHabit.setDifficulty(1);
            } else if (binding.difficultyButtonGroup.getCheckedButtonId() == R.id.easy_button) {
                currentHabit.setDifficulty(2);
            } else if (binding.difficultyButtonGroup.getCheckedButtonId() == R.id.medium_button) {
                currentHabit.setDifficulty(3);
            } else if (binding.difficultyButtonGroup.getCheckedButtonId() == R.id.hard_button) {
                currentHabit.setDifficulty(4);
            } else {
                currentHabit.setDifficulty(0);
            }

            currentHabit.setPositive(binding.negativePositiveButtonGroup.getCheckedButtonIds()
                        .contains(R.id.positive_button));
            currentHabit.setNegative(binding.negativePositiveButtonGroup.getCheckedButtonIds()
                        .contains(R.id.negative_button));

            dataViewModel.updateHabit(userViewModel.getLoggedUser(), currentHabit);
            dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
                if (result.isSuccess()) {
                    Snackbar.make(requireView(),
                            getString(R.string.habit_updated),
                            Snackbar.LENGTH_SHORT).show();

                    Intent intent = new Intent(requireContext(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    String error = ((Result.Fail) result).getError();
                    Snackbar.make(view,
                            error,
                            Snackbar.LENGTH_SHORT).show();

                    binding.saveButton.setVisibility(View.VISIBLE);
                    binding.deleteButton.setVisibility(View.VISIBLE);
                    binding.habitProgress.setVisibility(View.INVISIBLE);
                }
            });
        });

        binding.deleteButton.setOnClickListener(view1 -> {

            binding.saveButton.setVisibility(View.INVISIBLE);
            binding.deleteButton.setVisibility(View.INVISIBLE);
            binding.habitProgress.setVisibility(View.VISIBLE);

            dataViewModel.deleteHabit(userViewModel.getLoggedUser(), currentHabit);
            dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
                if (result.isSuccess()) {
                    Snackbar.make(requireView(),
                            getString(R.string.habit_deleted),
                            Snackbar.LENGTH_SHORT).show();

                    Intent intent = new Intent(requireContext(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    String error = ((Result.Fail) result).getError();
                    Snackbar.make(view,
                            error,
                            Snackbar.LENGTH_SHORT).show();

                    binding.saveButton.setVisibility(View.VISIBLE);
                    binding.deleteButton.setVisibility(View.VISIBLE);
                    binding.habitProgress.setVisibility(View.INVISIBLE);
                }
            });
        });

        binding.backButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        binding.createButton.setOnClickListener(view1 -> {
            binding.createButton.setVisibility(View.INVISIBLE);
            binding.habitProgress.setVisibility(View.VISIBLE);

            title = binding.titleEditText.getText()
                    .toString()
                    .trim();
            note = binding.noteEditText.getText()
                    .toString()
                    .trim();

            positive = binding.negativePositiveButtonGroup.getCheckedButtonIds()
                    .contains(R.id.positive_button);
            negative = binding.negativePositiveButtonGroup.getCheckedButtonIds()
                    .contains(R.id.negative_button);

            if (binding.difficultyButtonGroup.getCheckedButtonId() == R.id.trivial_button) {
                difficulty = 1;
            } else if (binding.difficultyButtonGroup.getCheckedButtonId() == R.id.easy_button) {
                difficulty = 2;
            } else if (binding.difficultyButtonGroup.getCheckedButtonId() == R.id.medium_button) {
                difficulty = 3;
            } else if (binding.difficultyButtonGroup.getCheckedButtonId() == R.id.hard_button) {
                difficulty = 4;
            } else {
                difficulty = 0;
            }

            if (binding.resetCounterButtonGroup.getCheckedButtonId() == R.id.daily_button) {
                resetCounter = 1;
            } else if (binding.resetCounterButtonGroup.getCheckedButtonId() == R.id.weekly_button) {
                resetCounter = 2;
            } else if (binding.resetCounterButtonGroup.getCheckedButtonId() == R.id.monthly_button) {
                resetCounter = 3;
            } else {
                resetCounter = 0;
            }

            if (!title.isEmpty() &&
                    (positive || negative) &&
                    difficulty != 0 &&
                    resetCounter != 0) {
                Habit habit = new Habit(title, note, negative, positive, difficulty, resetCounter, false);

                dataViewModel.saveHabit(userViewModel.getLoggedUser(), habit);
                dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
                    if (result.isSuccess()) {
                        Snackbar.make(requireView(),
                                getString(R.string.habit_created),
                                Snackbar.LENGTH_SHORT).show();

                        Intent intent = new Intent(requireContext(), MainActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    } else {
                        String error = ((Result.Fail) result).getError();
                        Snackbar.make(view,
                                error,
                                Snackbar.LENGTH_SHORT).show();

                        binding.createButton.setVisibility(View.VISIBLE);
                        binding.habitProgress.setVisibility(View.INVISIBLE);
                    }
                });
            } else {
                Snackbar.make(view,
                        getString(R.string.insert_fields),
                        Snackbar.LENGTH_SHORT).show();

                binding.createButton.setVisibility(View.VISIBLE);
                binding.habitProgress.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}