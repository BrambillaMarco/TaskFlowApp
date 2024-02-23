package it.appventurers.taskflow.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Habit implements Parcelable {
    private String name;
    private String note;
    private boolean negative;
    private boolean positive;
    private int difficulty;
    private int resetCounter;

    public Habit() {
    }

    public Habit(String name, String note,
                 boolean negative, boolean positive,
                 int difficulty, int resetCounter) {
        this.name = name;
        this.note = note;
        this.negative = negative;
        this.positive = positive;
        this.difficulty = difficulty;
        this.resetCounter = resetCounter;
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
    }
}
