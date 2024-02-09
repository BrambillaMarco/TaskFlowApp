package it.appventurers.taskflow.ui.elements.welcome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import it.appventurers.taskflow.databinding.FragmentForgotPasswordBinding;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.ui.viewmodel.UserViewModel;
import it.appventurers.taskflow.util.ClassBuilder;

/**
 * Forgot Password fragment class
 */
public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;
    private UserViewModel userViewModel;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = NavHostFragment.findNavController(this);
        userViewModel = new UserViewModel(
                ClassBuilder.getClassBuilder().getUserRepository(requireActivity().getApplication()));

        binding.recoverButton.setOnClickListener(view1 -> {
            binding.emailTextLayout.setError(null);
            String email = binding.emailEditText.getText().toString().trim();
            if (!email.isEmpty()) {
                binding.recoverProgress.setVisibility(View.VISIBLE);
                binding.recoverButton.setVisibility(View.INVISIBLE);
                userViewModel.retrievePassword(email);
                userViewModel.getUserData().observe(getViewLifecycleOwner(), result -> {
                    if (result.isSuccess()) {
                        Snackbar.make(view, "Email sent", Snackbar.LENGTH_SHORT).show();
                        navController.navigateUp();
                    } else {
                        String error = ((Result.Fail) result).getError();
                        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
                        binding.recoverProgress.setVisibility(View.INVISIBLE);
                        binding.recoverButton.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                binding.emailTextLayout.setError("Insert email");
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}