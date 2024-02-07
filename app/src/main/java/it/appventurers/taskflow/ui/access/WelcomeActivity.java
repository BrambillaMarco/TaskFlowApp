package it.appventurers.taskflow.ui.access;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import it.appventurers.taskflow.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
}