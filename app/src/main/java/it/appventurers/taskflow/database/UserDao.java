package it.appventurers.taskflow.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.appventurers.taskflow.model.User;

@Dao
public interface UserDao {


    @Insert
    void insert(User user);


    @Update
    void update(User user);


    @Delete
    void delete(User user);


    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    
    @Query("SELECT * FROM users WHERE uId = :uId")
    User getUserByUId(String uId);

}