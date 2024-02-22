package it.appventurers.taskflow.ui.viewmodel.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.appventurers.taskflow.data.repository.data.DataRepository;

public class DataViewModelFactory implements ViewModelProvider.Factory {
    private final DataRepository dataRepository;

    public DataViewModelFactory(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DataViewModel(dataRepository);
    }
}

