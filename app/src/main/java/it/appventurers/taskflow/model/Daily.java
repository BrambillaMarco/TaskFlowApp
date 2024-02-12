package it.appventurers.taskflow.model;

public class Daily {

    private String name;
    private String note;
    private int difficulty;
    private int resetCounter;

    public Daily(String name, String note, int difficulty, int resetCounter) {
        this.name = name;
        this.note = note;
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
