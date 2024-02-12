package it.appventurers.taskflow.data.source;

import static it.appventurers.taskflow.util.Constant.DAILY;
import static it.appventurers.taskflow.util.Constant.HABIT;
import static it.appventurers.taskflow.util.Constant.TO_DO;
import static it.appventurers.taskflow.util.Constant.USER;

import com.google.firebase.firestore.FirebaseFirestore;

import it.appventurers.taskflow.model.Daily;
import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.ToDo;
import it.appventurers.taskflow.model.User;

public class RemoteData extends BaseRemoteData{

    private FirebaseFirestore db;

    public RemoteData() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void saveUser(User user) {
        db.collection(USER).document(user.getuId())
                .set(user).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataCallback.onSuccessUser();
                    } else {
                        dataCallback.onFailure("Unable to sign up");
                    }
                });
    }

    @Override
    public void updateUser(User user) {
        db.collection(USER).document(user.getuId())
                .update("life", user.getLife(),
                        "currentLife", user.getCurrentLife(),
                        "level", user.getLevel())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataCallback.onSuccessUser();
                    } else {
                        dataCallback.onFailure("Unable to update data");
                    }
                });
    }

    @Override
    public void deleteUser(User user) {
        db.collection(USER).document(user.getuId())
                .delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataCallback.onSuccessUser();
                    } else {
                        dataCallback.onFailure("Unable to delete user");
                    }
                });
    }

    @Override
    public void saveHabit(User user, Habit habit) {
        db.collection(USER).document(user.getuId())
                .collection(HABIT).document(habit.getName())
                .set(habit).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataCallback.onSuccessHabit(habit);
                    } else {
                        dataCallback.onFailure("Unable to save habit");
                    }
                });
    }

    @Override
    public void updateHabit(User user, Habit habit) {

    }

    @Override
    public void deleteHabit(User user, Habit habit) {
        db.collection(USER).document(user.getuId())
                .collection(HABIT).document(habit.getName())
                .delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataCallback.onSuccessHabit(null);
                    } else {
                        dataCallback.onFailure("Unable to delete habit");
                    }
                });
    }

    @Override
    public void saveDaily(User user, Daily daily) {
        db.collection(USER).document(user.getuId())
                .collection(DAILY).document(daily.getName())
                .set(daily).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataCallback.onSuccessDaily(daily);
                    } else {
                        dataCallback.onFailure("Unable to save daily");
                    }
                });
    }

    @Override
    public void updateDaily(User user, Daily daily) {

    }

    @Override
    public void deleteDaily(User user, Daily daily) {
        db.collection(USER).document(user.getuId())
                .collection(DAILY).document(daily.getName())
                .delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataCallback.onSuccessDaily(null);
                    } else {
                        dataCallback.onFailure("Unable to delete daily");
                    }
                });
    }

    @Override
    public void saveToDo(User user, ToDo toDo) {
        db.collection(USER).document(user.getuId())
                .collection(TO_DO).document(toDo.getName())
                .set(toDo).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataCallback.onSuccessToDo(toDo);
                    } else {
                        dataCallback.onFailure("Unable to save to do");
                    }
                });
    }

    @Override
    public void updateToDo(User user, ToDo toDo) {

    }

    @Override
    public void deleteToDo(User user, ToDo toDo) {
        db.collection(USER).document(user.getuId())
                .collection(TO_DO).document(toDo.getName())
                .delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dataCallback.onSuccessToDo(null);
                    } else {
                        dataCallback.onFailure("Unable to delete to do");
                    }
                });
    }
}
