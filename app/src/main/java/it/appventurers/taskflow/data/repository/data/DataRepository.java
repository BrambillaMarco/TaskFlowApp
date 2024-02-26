package it.appventurers.taskflow.data.repository.data;

import static it.appventurers.taskflow.util.Constant.APP_DATABASE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import it.appventurers.taskflow.data.source.data.BaseRemoteData;
import it.appventurers.taskflow.database.AppDatabase;
import it.appventurers.taskflow.database.HabitDao;
import it.appventurers.taskflow.database.UserDao;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.Result;
import it.appventurers.taskflow.model.User;

public class DataRepository implements IDataCallback {

    private final BaseRemoteData remoteData;
    private final MutableLiveData<Result> data;
    private final MutableLiveData<User> userInfo;
    private HabitDao habitDao;
    private UserDao userDao;
    private Context context;
    private Executor ioExecutor;
    private Handler mainThreadHandler;


    public DataRepository(Context context, BaseRemoteData remoteData) {
        this.context = context.getApplicationContext();
        this.remoteData = remoteData;
        remoteData.setBaseRemoteData(this);
        data = new MutableLiveData<>();
        userInfo = new MutableLiveData<>();
        this.ioExecutor = Executors.newSingleThreadExecutor();
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, APP_DATABASE)
                .fallbackToDestructiveMigration()
                .build();
        this.habitDao = db.habitDao();
        this.userDao = db.userDao();
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
        ioExecutor.execute(() -> {
            try {
                boolean isConnected = isConnected();
                if (isConnected) {
                    remoteData.saveUser(user);
                }
                userDao.insert(user);
                mainThreadHandler.post(this::onSuccessUser);
                } catch (Exception e) {
                    mainThreadHandler.post(() -> {
                        onFailure("Unable to save habit: " + e.getMessage());
                    });
                }
            });
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
        ioExecutor.execute(() -> {
            try {
                boolean isConnected = isConnected();

                if (isConnected) {
                    habit.setSynced(true);
                    remoteData.saveHabit(user, habit);
                    syncSaveHabits(user.getUId(), user.getEmail());
                } else {
                    habit.setSynced(false);
                    }

                habitDao.insert(habit);
                mainThreadHandler.post(() -> {
                    onSuccessHabit(habit);
                });
            } catch (Exception e) {
                mainThreadHandler.post(() -> {
                    onFailure("Unable to save habit: " + e.getMessage());
                });
            }
        });
    }


    private void syncSaveHabits(String userId, String email) {
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
            return false;
        }
    }

    public void getAllHabit(User user) {
        if(isConnected()){
            syncSaveHabits(user.getUId(), user.getEmail());
            remoteData.getAllHabit(user);
        }else{
            postLocalHabits();
        }
    }


    private void postLocalHabits() {
        ioExecutor.execute(() -> {
            try {
                List<Habit> localHabits = habitDao.getHabits();
                ArrayList<Habit> habitArrayList = new ArrayList<>(localHabits);
                mainThreadHandler.post(() -> {
                    onSuccessGetHabit(habitArrayList);
                });
            } catch (Exception e) {
                mainThreadHandler.post(() -> {
                    onFailure("Unable to load the data: " + e.getMessage());
                });
            }
        });
    }


    public void updateHabit(User user, Habit habit) {
        ioExecutor.execute(() -> {
            try {
                boolean isConnected = isConnected();

                if (isConnected) {
                    remoteData.updateHabit(user, habit);
                    habit.setSynced(true);
                    syncUpdateHabits(user.getUId(), user.getEmail());
                } else {
                    habit.setSynced(false);
                }
                    habitDao.update(habit);
                    mainThreadHandler.post(() -> {
                    onSuccessHabit(habit);
                });
            } catch (Exception e) {
                mainThreadHandler.post(() -> {
                    onFailure("Unable to update habit: " + e.getMessage());
                });
            }
        });
    }


    private void syncUpdateHabits(String userId, String email) {
        new Thread(() -> {
            List<Habit> unSyncedHabits = habitDao.getUnsyncedHabits();
            for (Habit habit : unSyncedHabits) {
                boolean success = updateHabitRemotely(User.getInstance(userId, email), habit);
                if (success) {
                    habit.setSynced(true);
                    habitDao.update(habit);
                }
            }
        }).start();
    }

    private boolean updateHabitRemotely(User user, Habit habit) {
        try {
            remoteData.updateHabit(user, habit);
            return true;
        } catch (Exception e) {
            Log.e("DataRepository", "Errore nell'update remoto: ", e);
            return false;
        }
    }

    public void deleteHabit(User user, Habit habit) {
        new Thread(() -> {
        if (isConnected()) {
            habitDao.delete(habit);
            remoteData.deleteHabit(user, habit);
        }else{
            mainThreadHandler.post(() -> {
                onFailure("Please ensure you have an active connection and try again.");
            });
        }}).start();
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
        User.getInstance(user.getEmail(), user.getUId()).setLevel(user.getLevel());
        User.getInstance(user.getEmail(), user.getUId()).setCurrentLife(user.getCurrentLife());
        User.getInstance(user.getEmail(), user.getUId()).setLife(user.getLife());
        User.getInstance(user.getEmail(), user.getUId()).setXp(user.getXp());
        userInfo.postValue(User.getInstance(user.getEmail(), user.getUId()));
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
    public void onFailure(String error) {
        Result.Fail result = new Result.Fail(error);
        data.postValue(result);
    }
}
