package it.appventurers.taskflow.data.repository.data;

import it.appventurers.taskflow.model.Daily;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.ToDo;

public interface IDataCallback {

    void onSuccessUser();
    void onSuccessHabit(Habit habit);
    void onSuccessDaily(Daily daily);
    void onSuccessToDo(ToDo toDo);
    void onFailure(String error);
}
