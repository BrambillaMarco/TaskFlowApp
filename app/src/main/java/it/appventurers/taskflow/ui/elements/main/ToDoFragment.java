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
import it.appventurers.taskflow.adapter.ToDoAdapter;
import it.appventurers.taskflow.databinding.FragmentToDoBinding;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.ToDo;
import it.appventurers.taskflow.ui.viewmodel.data.DataViewModel;
import it.appventurers.taskflow.ui.viewmodel.user.UserViewModel;
import it.appventurers.taskflow.util.ClassBuilder;

/**
 * To Do fragment class
 */
public class ToDoFragment extends Fragment {

    private FragmentToDoBinding binding;
    private UserViewModel userViewModel;
    private DataViewModel dataViewModel;
    private ArrayList<ToDo> toDoList;

    public ToDoFragment() {
        // Required empty public constructor
    }

    public static ToDoFragment newInstance() {
        return new ToDoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDoList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentToDoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewToDo = view.findViewById(R.id.to_do_recycler);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);
        userViewModel = new UserViewModel(
                ClassBuilder.getClassBuilder()
                        .getUserRepository(requireActivity().getApplication()));
        dataViewModel = new DataViewModel(
                ClassBuilder.getClassBuilder()
                        .getDataRepository(requireActivity().getApplication()));


    }
}