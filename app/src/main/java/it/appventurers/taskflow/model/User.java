package it.appventurers.taskflow.model;

public class User {

    private String name;
    private String email;
    private String uId;
    private int life;
    private int currentLife;
    private int xp;
    private int level;

    public User(String name, String email, String uId) {
        this.name = name;
        this.email = email;
        this.uId = uId;
        this.life = 10;
        this.currentLife = 10;
        this.xp = 0;
        this.level = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public void setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
