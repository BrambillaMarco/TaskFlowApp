package it.appventurers.taskflow.model;

public class Habit {

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
}
