package it.appventurers.taskflow.model;


import androidx.annotation.ColorInt;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "habits")
public class Habit {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    private String userId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "negative")
    private boolean negative;
    @ColumnInfo(name = "positive")
    private boolean positive;
    @ColumnInfo(name = "difficulty")
    private int difficulty;
    @ColumnInfo(name = "reset_counter")
    private int resetCounter;
    @ColumnInfo(name = "synced")
    private boolean isSynced;

    public Habit() {
    }

    public Habit(String name, String note,
                 boolean negative, boolean positive,
                 int difficulty, int resetCounter, boolean isSynced) {
        this.name = name;
        this.note = note;
        this.negative = negative;
        this.positive = positive;
        this.difficulty = difficulty;
        this.resetCounter = resetCounter;
        this.isSynced = isSynced;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isNegative() {
        return negative;
    }

    public void setNegative(boolean negative) {
        this.negative = negative;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getResetCounter() {
        return resetCounter;
    }

    public void setResetCounter(int resetCounter) {
        this.resetCounter = resetCounter;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }
}
