package it.appventurers.taskflow.data.repository.data;

import java.util.ArrayList;

import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.User;

public interface IDataCallback {

    void onSuccessUser();
    void onSuccessUpdateUser(User user);
    void onSuccessGetUser(User user);

    void onSuccessHabit(Habit habit);
    void onSuccessGetHabit(ArrayList<Habit> habitList);

    void onFailure(String error);
}
