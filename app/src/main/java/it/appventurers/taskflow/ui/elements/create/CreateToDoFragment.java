package it.appventurers.taskflow.ui.elements.create;

import static it.appventurers.taskflow.util.Constant.LOAD_FRAGMENT;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import it.appventurers.taskflow.databinding.FragmentCreateToDoBinding;
import it.appventurers.taskflow.model.Daily;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.ToDo;
import it.appventurers.taskflow.ui.elements.main.MainActivity;
import it.appventurers.taskflow.ui.viewmodel.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.UserViewModel;
import it.appventurers.taskflow.util.ClassBuilder;

/**
 * Create to do fragment class
 */
public class CreateToDoFragment extends Fragment {

    private FragmentCreateToDoBinding binding;
    private UserViewModel userViewModel;
    private DataViewModel dataViewModel;

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
        binding = FragmentCreateToDoBinding.inflate(inflater, container, false);
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
            binding.toDoProgress.setVisibility(View.VISIBLE);
            String title = binding.titleEditText.getText().toString().trim();
            String note = binding.noteEditText.getText().toString().trim();
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
            ToDo toDo = new ToDo(title, note, difficulty);
            dataViewModel.saveToDo(userViewModel.getLoggedUser(), toDo);
            dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
                if (result.isSuccess()) {
                    Snackbar.make(view, "To do created", Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra(LOAD_FRAGMENT, "ToDoFragment");
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    String error = ((Result.Fail) result).getError();
                    Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
                    binding.createButton.setVisibility(View.VISIBLE);
                    binding.toDoProgress.setVisibility(View.INVISIBLE);
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