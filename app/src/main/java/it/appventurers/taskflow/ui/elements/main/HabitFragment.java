package it.appventurers.taskflow.ui.elements.main;

import static it.appventurers.taskflow.util.Constant.HABIT;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.adapter.HabitAdapter;
import it.appventurers.taskflow.data.repository.data.DataRepository;
import it.appventurers.taskflow.data.repository.user.UserRepository;
import it.appventurers.taskflow.databinding.FragmentHabitBinding;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.ui.elements.create.CreateActivity;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModelFactory;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModel;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModelFactory;
import it.appventurers.taskflow.util.ClassBuilder;

/**
 * Habit fragment class
 */
public class HabitFragment extends Fragment {

    private FragmentHabitBinding binding;
    private UserViewModel userViewModel;
    private DataViewModel dataViewModel;
    private ArrayList<Habit> habitList;
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

        UserRepository userRepository = ClassBuilder.getClassBuilder()
                .getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        DataRepository dataRepository = ClassBuilder.getClassBuilder()
                .getDataRepository(requireActivity().getApplication());
        dataViewModel = new ViewModelProvider(
                requireActivity(),
                new DataViewModelFactory(dataRepository)).get(DataViewModel.class);
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

        layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);

        NavController navController = NavHostFragment.findNavController(this);

        dataViewModel.getAllHabit(userViewModel.getLoggedUser());
        dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                habitList.clear();
                habitList.addAll(((Result.HabitSuccess) result).getHabitList());
                HabitAdapter habitAdapter = new HabitAdapter(habitList,
                        new HabitAdapter.OnItemClickListener() {
                            @Override
                            public void onPositiveButtonClick(int position) {
                                userViewModel.getLoggedUser()
                                        .setXp(userViewModel.getLoggedUser().getXp() +
                                                habitList.get(position).getDifficulty() * 2);
                                if (userViewModel.getLoggedUser().getCurrentLife() <
                                userViewModel.getLoggedUser().getLife()) {
                                    userViewModel.getLoggedUser()
                                            .setCurrentLife(userViewModel.getLoggedUser().getCurrentLife() +
                                                    habitList.get(position).getDifficulty() * 2);
                                    if (userViewModel.getLoggedUser().getCurrentLife() >
                                    userViewModel.getLoggedUser().getLife()) {
                                        userViewModel.getLoggedUser()
                                                .setCurrentLife(userViewModel.getLoggedUser().getLife());
                                    }
                                }
                                if (userViewModel.getLoggedUser().getXp() >= 100) {
                                    if (userViewModel.getLoggedUser().getLevel() < 10) {
                                        userViewModel.getLoggedUser()
                                                .setLevel(userViewModel.getLoggedUser().getLevel() + 1);
                                        userViewModel.getLoggedUser()
                                                .setLife(userViewModel.getLoggedUser().getLife() + 10);
                                        userViewModel.getLoggedUser()
                                                .setCurrentLife(userViewModel.getLoggedUser().getLife());
                                        userViewModel.getLoggedUser()
                                                .setXp(0);
                                    } else {
                                        userViewModel.getLoggedUser()
                                                .setLevel(userViewModel.getLoggedUser().getLevel() + 1);
                                        userViewModel.getLoggedUser()
                                                .setCurrentLife(userViewModel.getLoggedUser().getLife());
                                        userViewModel.getLoggedUser()
                                                .setXp(0);
                                    }
                                }

                                dataViewModel.updateUser(userViewModel.getLoggedUser());
                            }

                            @Override
                            public void onNegativeButtonClick(int position) {
                                userViewModel.getLoggedUser()
                                        .setCurrentLife(userViewModel.getLoggedUser().getCurrentLife() -
                                                habitList.get(position).getDifficulty());
                                if (userViewModel.getLoggedUser().getCurrentLife() <= 0) {
                                    userViewModel.getLoggedUser().setXp(0);
                                    if (userViewModel.getLoggedUser().getLevel() > 1 &&
                                    userViewModel.getLoggedUser().getLevel() <= 10) {
                                        userViewModel.getLoggedUser()
                                                .setLevel(userViewModel.getLoggedUser().getLevel() - 1);
                                        userViewModel.getLoggedUser()
                                                .setLife(userViewModel.getLoggedUser().getLife() - 10);
                                        userViewModel.getLoggedUser()
                                                .setCurrentLife(userViewModel.getLoggedUser().getLife());
                                    } else if (userViewModel.getLoggedUser().getLevel() > 10) {
                                        userViewModel.getLoggedUser()
                                                .setLevel(userViewModel.getLoggedUser().getLevel() - 1);
                                        userViewModel.getLoggedUser()
                                                .setLife(userViewModel.getLoggedUser().getLife());
                                        userViewModel.getLoggedUser()
                                                .setCurrentLife(userViewModel.getLoggedUser().getLife());
                                    } else {
                                        userViewModel.getLoggedUser().setLevel(1);
                                        userViewModel.getLoggedUser().setLife(10);
                                        userViewModel.getLoggedUser()
                                                .setCurrentLife(userViewModel.getLoggedUser().getLife());
                                    }
                                }

                                dataViewModel.updateUser(userViewModel.getLoggedUser());
                            }

                            @Override
                            public void onCardViewClick(int position) {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(HABIT, habitList.get(position));
                                Intent intent = new Intent(getContext(), CreateActivity.class);
                                intent.putExtra(HABIT, bundle);
                                startActivity(intent);
                                requireActivity().finish();
                            }
                        });

                binding.habitRecycler.setLayoutManager(layoutManager);
                binding.habitRecycler.setAdapter(habitAdapter);
            } else {
                String error = ((Result.Fail) result).getError();
                Snackbar.make(view,
                        error,
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        binding = null;
    }
}