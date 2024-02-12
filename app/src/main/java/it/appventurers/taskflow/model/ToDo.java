package it.appventurers.taskflow.model;

public class ToDo {

    private String name;
    private String note;
    private int difficulty;

    public ToDo(String name, String note, int difficulty) {
        this.name = name;
        this.note = note;
        this.difficulty = difficulty;
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
}
