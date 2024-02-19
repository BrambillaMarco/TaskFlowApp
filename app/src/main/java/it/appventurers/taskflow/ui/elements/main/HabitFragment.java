package it.appventurers.taskflow.ui.elements.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.adapter.HabitAdapter;
import it.appventurers.taskflow.data.repository.data.DataRepository;
import it.appventurers.taskflow.databinding.FragmentHabitBinding;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.User;
import it.appventurers.taskflow.ui.viewmodel.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.DataViewModelFactory;
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
        DataRepository dataRepository = ClassBuilder.getClassBuilder()
                .getDataRepository(requireActivity().getApplication());
        dataViewModel = new ViewModelProvider(
                requireActivity(),
                new DataViewModelFactory(dataRepository)).get(DataViewModel.class);

        dataViewModel.getAllHabit(userViewModel.getLoggedUser());
        dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                habitList.addAll(((Result.HabitSuccess) result).getHabitList());
                HabitAdapter habitAdapter = new HabitAdapter(habitList, new HabitAdapter.OnItemClickListener() {
                    @Override
                    public void onPositiveButtonClick(int position) {
                        userViewModel.getLoggedUser()
                                .setXp(userViewModel.getLoggedUser().getXp() +
                                        habitList.get(position).getDifficulty() * 2);
                        if (userViewModel.getLoggedUser().getXp() >= 100) {
                            userViewModel.getLoggedUser()
                                    .setLevel(userViewModel.getLoggedUser().getLevel() + 1);
                            userViewModel.getLoggedUser()
                                    .setLife(userViewModel.getLoggedUser().getLife() + 10);
                            userViewModel.getLoggedUser()
                                    .setCurrentLife(userViewModel.getLoggedUser().getLife());
                            userViewModel.getLoggedUser()
                                    .setXp(0);
                        }
                        dataViewModel.updateUser(userViewModel.getLoggedUser());
                    }

                    @Override
                    public void onNegativeButtonClick(int position) {
                        //aggiorna lo User, riducendogli la vita
                        //se la vita passa a 0 riducigli il livello
                    }

                    @Override
                    public void onCardViewClick(int position) {
                        //apri schermata di modifica
                    }
                });
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