package it.appventurers.taskflow.data.source.data;

import it.appventurers.taskflow.data.repository.data.IDataCallback;
import it.appventurers.taskflow.data.repository.user.IUserCallback;
import it.appventurers.taskflow.model.Daily;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.ToDo;
import it.appventurers.taskflow.model.User;

public abstract class BaseRemoteData {

    protected IDataCallback dataCallback;

    public void setBaseRemoteData(IDataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    public abstract void saveUser(User user);
    public abstract void updateUser(User user);
    public abstract void deleteUser(User user);

    public abstract void saveHabit(User user, Habit habit);
    public abstract void updateHabit(User user, Habit habit);
    public abstract void deleteHabit(User user, Habit habit);

    public abstract void saveDaily(User user, Daily daily);
    public abstract void updateDaily(User user, Daily daily);
    public abstract void deleteDaily(User user, Daily daily);

    public abstract void saveToDo(User user, ToDo toDo);
    public abstract void updateToDo(User user, ToDo toDo);
    public abstract void deleteToDo(User user, ToDo toDo);
}
