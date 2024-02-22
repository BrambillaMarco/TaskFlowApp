package it.appventurers.taskflow.ui.elements.create;

import static it.appventurers.taskflow.util.Constant.DAILY_FRAGMENT;
import static it.appventurers.taskflow.util.Constant.LOAD_FRAGMENT;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.data.repository.data.DataRepository;
import it.appventurers.taskflow.data.repository.user.UserRepository;
import it.appventurers.taskflow.databinding.FragmentCreateDailyBinding;
import it.appventurers.taskflow.model.Daily;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.ui.elements.main.MainActivity;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModelFactory;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModel;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModelFactory;
import it.appventurers.taskflow.util.ClassBuilder;

/**
 * Create daily fragment class
 */
public class CreateDailyFragment extends Fragment {

    private FragmentCreateDailyBinding binding;

    private UserViewModel userViewModel;
    private DataViewModel dataViewModel;

    private String title;
    private String note;
    private int difficulty;
    private int resetCounter;

    public CreateDailyFragment() {
        // Required empty public constructor
    }

    public static CreateDailyFragment newInstance() {
        return new CreateDailyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        difficulty = 0;
        resetCounter = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateDailyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backButton.setOnClickListener(view1 -> requireActivity().finish());

        binding.createButton.setOnClickListener(view1 -> {
            binding.createButton.setVisibility(View.INVISIBLE);
            binding.dailyProgress.setVisibility(View.VISIBLE);

            title = binding.titleEditText.getText()
                    .toString()
                    .trim();
            note = binding.noteEditText.getText()
                    .toString()
                    .trim();

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
                    difficulty != 0 &&
                    resetCounter != 0) {
                Daily daily = new Daily(title, note, difficulty, resetCounter);

                dataViewModel.saveDaily(userViewModel.getLoggedUser(), daily);
                dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
                    if (result.isSuccess()) {
                        Snackbar.make(view,
                                getString(R.string.daily_created),
                                Snackbar.LENGTH_SHORT).show();

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra(LOAD_FRAGMENT, DAILY_FRAGMENT);
                        startActivity(intent);
                        requireActivity().finish();
                    } else {
                        String error = ((Result.Fail) result).getError();
                        Snackbar.make(view,
                                error,
                                Snackbar.LENGTH_SHORT).show();

                        binding.createButton.setVisibility(View.VISIBLE);
                        binding.dailyProgress.setVisibility(View.INVISIBLE);
                    }
                });
            } else {
                Snackbar.make(view,
                        getString(R.string.insert_fields),
                        Snackbar.LENGTH_SHORT).show();

                binding.createButton.setVisibility(View.VISIBLE);
                binding.dailyProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}