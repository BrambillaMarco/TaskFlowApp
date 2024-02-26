package it.appventurers.taskflow.data.repository.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import it.appventurers.taskflow.data.source.data.BaseRemoteData;
import it.appventurers.taskflow.database.AppDatabase;
import it.appventurers.taskflow.database.HabitDao;
import it.appventurers.taskflow.model.Daily;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.ToDo;
import it.appventurers.taskflow.model.User;

public class DataRepository implements IDataCallback {

    private final BaseRemoteData remoteData;
    private final MutableLiveData<Result> data;
    private final MutableLiveData<User> userInfo;
    private HabitDao habitDao;
    private Context context;


    public DataRepository(Context context, BaseRemoteData remoteData) {
        this.context = context.getApplicationContext();
        this.remoteData = remoteData;
        remoteData.setBaseRemoteData(this);
        data = new MutableLiveData<>();
        userInfo = new MutableLiveData<>();
        
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "taskflow_database").build();
        this.habitDao = db.habitDao();
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public MutableLiveData<Result> getData() {
        return data;
    }

    public MutableLiveData<User> getUserInfo() {
        return userInfo;
    }

    public void saveUser(User user) {
        remoteData.saveUser(user);
    }

    public void getUserInfo(User user) {
        remoteData.getUserInfo(user);
    }

    public void updateUser(User user) {
        remoteData.updateUser(user);
    }

    public void deleteUser(User user) {
        remoteData.deleteUser(user);
    }

    public void saveHabit(User user, Habit habit) {
        new Thread(() -> {
            if (isConnected()) {
                habit.setSynced(true);
                remoteData.saveHabit(user, habit);
            } else {
                habit.setSynced(false);
            }
            habitDao.insert(habit);
        }).start();
    }


    private void syncHabits(String userId, String email) {
        new Thread(() -> {
            List<Habit> unSyncedHabits = habitDao.getUnsyncedHabits();
            for (Habit habit : unSyncedHabits) {
                boolean success = saveHabitRemotely(User.getInstance(userId, email), habit);
                if (success) {
                    habit.setSynced(true);
                    habitDao.update(habit);
                }
            }
        }).start();

    }

    private boolean saveHabitRemotely(User user, Habit habit) {
        try {
            remoteData.saveHabit(user, habit);
            return true;
        } catch (Exception e) {
            Log.e("DataRepository", "Errore nel salvataggio remoto: ", e);
            return false;
        }
    }



    public void getAllHabit(User user) {
        if(isConnected()){
            syncHabits(user.getuId(), user.getEmail());
            remoteData.getAllHabit(user);
        }else{
            postLocalHabits(user.getuId());
        }

    }

    private void postLocalHabits(String userId) {
        new Thread(() -> {
            List<Habit> localHabits = habitDao.getHabitsByUserIdNonLive(userId);
            Result.HabitSuccess result = new Result.HabitSuccess(new ArrayList<>(localHabits));
            data.postValue(result);
        }).start();
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

    public void getAllDaily(User user) {
        remoteData.getAllDaily(user);
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

    public void getAllToDo(User user) {
        remoteData.getAllToDo(user);
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
    public void onSuccessUpdateUser(User user) {
        onSuccessGetUser(user);
    }

    @Override
    public void onSuccessGetUser(User user) {
        User.getInstance(user.getEmail(), user.getuId()).setLevel(user.getLevel());
        User.getInstance(user.getEmail(), user.getuId()).setCurrentLife(user.getCurrentLife());
        User.getInstance(user.getEmail(), user.getuId()).setLife(user.getLife());
        User.getInstance(user.getEmail(), user.getuId()).setXp(user.getXp());
        userInfo.postValue(User.getInstance(user.getEmail(), user.getuId()));
    }

    @Override
    public void onSuccessHabit(Habit habit) {
        Result.HabitSuccess result = new Result.HabitSuccess(habit);
        data.postValue(result);
    }

    @Override
    public void onSuccessGetHabit(ArrayList<Habit> habitList) {
        Result.HabitSuccess result = new Result.HabitSuccess(habitList);
        data.postValue(result);
    }

    @Override
    public void onSuccessDaily(Daily daily) {
        Result.DailySuccess result = new Result.DailySuccess(daily);
        data.postValue(result);
    }

    @Override
    public void onSuccessGetDaily(ArrayList<Daily> dailyList) {
        Result.DailySuccess result = new Result.DailySuccess(dailyList);
        data.postValue(result);
    }

    @Override
    public void onSuccessToDo(ToDo toDo) {
        Result.ToDoSuccess result = new Result.ToDoSuccess(toDo);
        data.postValue(result);
    }

    @Override
    public void onSuccessGetToDo(ArrayList<ToDo> toDoList) {
        Result.ToDoSuccess result = new Result.ToDoSuccess(toDoList);
        data.postValue(result);
    }

    @Override
    public void onFailure(String error) {
        Result.Fail result = new Result.Fail(error);
        data.postValue(result);
    }
}
