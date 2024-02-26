package it.appventurers.taskflow.database;

import static it.appventurers.taskflow.util.Constant.APP_DATABASE;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import it.appventurers.taskflow.model.Habit;

@Database(entities = {Habit.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HabitDao habitDao();

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

