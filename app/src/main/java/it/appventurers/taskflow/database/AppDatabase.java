package it.appventurers.taskflow.database;

import static it.appventurers.taskflow.util.Constant.APP_DATABASE;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import it.appventurers.taskflow.model.Habit;
import it.appventurers.taskflow.model.User;

@Database(entities = {Habit.class, User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HabitDao habitDao();
    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, APP_DATABASE)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

