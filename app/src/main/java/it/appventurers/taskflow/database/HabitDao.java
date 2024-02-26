package it.appventurers.taskflow.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.appventurers.taskflow.model.Habit;


@Dao
public interface HabitDao {
    @Insert
    void insert(Habit habit);

    @Update
    void update(Habit habit);

    @Delete
    void delete(Habit habit);

    @Query("SELECT * FROM habits WHERE user_id = :userId")
    LiveData<List<Habit>> getHabitsByUserId(String userId);

    @Query("SELECT * FROM habits WHERE synced = 0")
    List<Habit> getUnsyncedHabits();

    @Query("SELECT * FROM habits")
    List<Habit> getHabits();

    // Aggiunto metodo per ottenere gli habit in modo sincrono
    @Query("SELECT * FROM habits WHERE user_id = :userId")
    List<Habit> getHabitsByUserIdNonLive(String userId);
}