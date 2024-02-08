package it.appventurers.taskflow.ui.welcome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.appventurers.taskflow.R;
import it.appventurers.taskflow.databinding.FragmentLoginBinding;

/**
 * Login fragment class
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = NavHostFragment.findNavController(this);

        binding.loginButton.setOnClickListener(view1 -> {
            binding.loginButton.setVisibility(View.INVISIBLE);
            binding.googleLoginButton.setVisibility(View.INVISIBLE);
            binding.loginProgress.setVisibility(View.VISIBLE);
            navController.navigate(R.id.action_loginFragment_to_mainActivity);
            requireActivity().finish();
        });

        binding.googleLoginButton.setOnClickListener(view1 -> {
            binding.loginButton.setVisibility(View.INVISIBLE);
            binding.googleLoginButton.setVisibility(View.INVISIBLE);
            binding.loginProgress.setVisibility(View.VISIBLE);
            navController.navigate(R.id.action_loginFragment_to_mainActivity);
            requireActivity().finish();
        });

        binding.forgotPasswordButton.setOnClickListener(view1 ->
                navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment));

        binding.registerButton.setOnClickListener(view1 ->
                navController.navigate(R.id.action_loginFragment_to_registerFragment));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}