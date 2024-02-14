package it.appventurers.taskflow.ui.elements.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.adapter.DailyAdapter;
import it.appventurers.taskflow.databinding.FragmentDailyBinding;
import it.appventurers.taskflow.model.Daily;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.ui.viewmodel.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.UserViewModel;
import it.appventurers.taskflow.util.ClassBuilder;

/**
 * Daily fragment class
 */
public class DailyFragment extends Fragment {

    private FragmentDailyBinding binding;
    private UserViewModel userViewModel;
    private DataViewModel dataViewModel;
    private ArrayList<Daily> dailyList;

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

        RecyclerView recyclerViewDaily = view.findViewById(R.id.daily_recycler);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);
        userViewModel = new UserViewModel(
                ClassBuilder.getClassBuilder()
                        .getUserRepository(requireActivity().getApplication()));
        dataViewModel = new DataViewModel(
                ClassBuilder.getClassBuilder()
                        .getDataRepository(requireActivity().getApplication()));

        dataViewModel.getAllDaily(userViewModel.getLoggedUser());
        dataViewModel.getData().observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                ArrayList<Daily> retrievedDailyList = ((Result.DailySuccess) result).getDailyList();
                if (!retrievedDailyList.isEmpty()) {
                    dailyList.addAll(retrievedDailyList);
                    DailyAdapter dailyAdapter = new DailyAdapter(dailyList);
                    recyclerViewDaily.setLayoutManager(layoutManager);
                    recyclerViewDaily.setAdapter(dailyAdapter);
                }
            } else {
                String error = ((Result.Fail) result).getError();
                Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
            }
        });

    }
}