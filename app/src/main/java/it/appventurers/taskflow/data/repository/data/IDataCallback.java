package it.appventurers.taskflow.data.repository.data;

import java.util.ArrayList;

import it.appventurers.taskflow.model.Daily;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.ToDo;
import it.appventurers.taskflow.model.User;

public interface IDataCallback {

    void onSuccessUser();
    void onSuccessUpdateUser(User user);
    void onSuccessGetUser(User user);
    void onSuccessHabit(Habit habit);
    void onSuccessGetHabit(ArrayList<Habit> habitList);
    void onSuccessDaily(Daily daily);
    void onSuccessGetDaily(ArrayList<Daily> dailyList);
    void onSuccessToDo(ToDo toDo);
    void onSuccessGetToDo(ArrayList<ToDo> toDoList);
    void onFailure(String error);
}
