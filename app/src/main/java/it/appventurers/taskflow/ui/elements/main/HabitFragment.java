package it.appventurers.taskflow.ui.elements.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.adapter.HabitAdapter;
import it.appventurers.taskflow.databinding.FragmentHabitBinding;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.ui.viewmodel.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.UserViewModel;
import it.appventurers.taskflow.util.ClassBuilder;

/**
 * Habit fragment class
 */
public class HabitFragment extends Fragment {

    private FragmentHabitBinding binding;
    private DataViewModel dataViewModel;
    private UserViewModel userViewModel;
    private ArrayList<Habit> habitList;
    private RecyclerView recyclerViewHabit;
    private RecyclerView.LayoutManager layoutManager;

    public HabitFragment() {
        // Required empty public constructor
    }

    public static HabitFragment newInstance() {
        return new HabitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        habitList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHabitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewHabit = view.findViewById(R.id.habit_recycler);
        layoutManager = new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);
        userViewModel = new UserViewModel(
                ClassBuilder.getClassBuilder()
                        .getUserRepository(requireActivity().getApplication()));
        dataViewModel = new DataViewModel(
                ClassBuilder.getClassBuilder()
                        .getDataRepository(requireActivity().getApplication()));

        dataViewModel.getAllHabit(userViewModel.getLoggedUser());
        dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                habitList.addAll(((Result.HabitSuccess) result).getHabitList());
                HabitAdapter habitAdapter = new HabitAdapter(habitList);
                recyclerViewHabit.setLayoutManager(layoutManager);
                recyclerViewHabit.setAdapter(habitAdapter);
            } else {
                String error = ((Result.Fail) result).getError();
                Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        habitList.clear();
    }
}