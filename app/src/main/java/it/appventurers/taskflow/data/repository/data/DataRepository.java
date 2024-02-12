package it.appventurers.taskflow.data.repository.data;

import android.provider.ContactsContract;

import androidx.lifecycle.MutableLiveData;

import it.appventurers.taskflow.data.source.BaseRemoteData;
import it.appventurers.taskflow.model.Daily;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.ToDo;
import it.appventurers.taskflow.model.User;

public class DataRepository implements IDataCallback {

    private final BaseRemoteData remoteData;
    private final MutableLiveData<Result> data;

    public DataRepository(BaseRemoteData remoteData) {
        this.remoteData = remoteData;
        remoteData.setBaseRemoteData(this);
        data = new MutableLiveData<>();
    }

    public MutableLiveData<Result> getData() {
        return data;
    }

    public void saveUser(User user) {
        remoteData.saveUser(user);
    }

    public void updateUser(User user) {
        remoteData.updateUser(user);
    }

    public void deleteUser(User user) {
        remoteData.deleteUser(user);
    }

    public void saveHabit(User user, Habit habit) {
        remoteData.saveHabit(user, habit);
    }

    public void updateHabit(User user, Habit habit) {
        remoteData.updateHabit(user, habit);
    }

    public void deleteHabit(User user, Habit habit) {
        remoteData.deleteHabit(user, habit);
    }

    public void saveDaily(User user, Daily daily) {
        remoteData.saveDaily(user, daily);
    }

    public void updateDaily(User user, Daily daily) {
        remoteData.updateDaily(user, daily);
    }

    public void deleteDaily(User user, Daily daily) {
        remoteData.deleteDaily(user, daily);
    }

    public void saveToDo(User user, ToDo toDo) {
        remoteData.saveToDo(user, toDo);
    }

    public void updateToDo(User user, ToDo toDo) {
        remoteData.updateToDo(user, toDo);
    }

    public void deleteToDo(User user, ToDo toDo) {
        remoteData.deleteToDo(user, toDo);
    }

    @Override
    public void onSuccessUser() {
        Result.UserSuccess result = new Result.UserSuccess(null);
        data.postValue(result);
    }

    @Override
    public void onSuccessHabit(Habit habit) {
        Result.HabitSuccess result = new Result.HabitSuccess(habit);
        data.postValue(result);
    }

    @Override
    public void onSuccessDaily(Daily daily) {
        Result.DailySuccess result = new Result.DailySuccess(daily);
        data.postValue(result);
    }

    @Override
    public void onSuccessToDo(ToDo toDo) {
        Result.ToDoSuccess result = new Result.ToDoSuccess(toDo);
        data.postValue(result);
    }

    @Override
    public void onFailure(String error) {
        Result.Fail result = new Result.Fail(error);
        data.postValue(result);
    }
}
