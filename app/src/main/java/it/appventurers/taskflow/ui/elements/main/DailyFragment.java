package it.appventurers.taskflow.ui.elements.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.adapter.DailyAdapter;
import it.appventurers.taskflow.data.repository.data.DataRepository;
import it.appventurers.taskflow.data.repository.user.UserRepository;
import it.appventurers.taskflow.databinding.FragmentDailyBinding;
import it.appventurers.taskflow.model.Daily;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModelFactory;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModel;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModelFactory;
import it.appventurers.taskflow.util.ClassBuilder;

/**
 * Daily fragment class
 */
public class DailyFragment extends Fragment {

    private FragmentDailyBinding binding;

    private UserViewModel userViewModel;
    private DataViewModel dataViewModel;

    private ArrayList<Daily> dailyList;

    RecyclerView.LayoutManager layoutManager;

    public DailyFragment() {
        // Required empty public constructor
    }

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dailyList = new ArrayList<>();

        UserRepository userRepository = ClassBuilder.getClassBuilder()
                .getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                this,
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
        binding = FragmentDailyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager = new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);

        dataViewModel.getAllDaily(userViewModel.getLoggedUser());
        dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                dailyList.addAll(((Result.DailySuccess) result).getDailyList());
                DailyAdapter dailyAdapter = new DailyAdapter(dailyList,
                        new DailyAdapter.OnItemClickListener() {

                    @Override
                    public void onCheckBoxButtonClick(int position) {
                        userViewModel.getLoggedUser()
                                .setXp(userViewModel.getLoggedUser().getXp() +
                                        dailyList.get(position).getDifficulty() * 2);
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
                    public void onCardViewClick(int position) {

                    }
                });

                binding.dailyRecycler.setLayoutManager(layoutManager);
                binding.dailyRecycler.setAdapter(dailyAdapter);
            } else {
                String error = ((Result.Fail) result).getError();
                Snackbar.make(view,
                        error,
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}