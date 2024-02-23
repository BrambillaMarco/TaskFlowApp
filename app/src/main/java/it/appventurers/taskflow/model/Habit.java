package it.appventurers.taskflow.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Habit implements Parcelable {

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

    protected Habit(Parcel in) {
        name = in.readString();
        note = in.readString();
        negative = in.readByte() != 0;
        positive = in.readByte() != 0;
        difficulty = in.readInt();
        resetCounter = in.readInt();
    }

    public static final Creator<Habit> CREATOR = new Creator<Habit>() {
        @Override
        public Habit createFromParcel(Parcel in) {
            return new Habit(in);
        }

        @Override
        public Habit[] newArray(int size) {
            return new Habit[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(note);
        dest.writeByte((byte) (negative ? 1 : 0));
        dest.writeByte((byte) (positive ? 1 : 0));
        dest.writeInt(difficulty);
        dest.writeInt(resetCounter);
    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }
}
