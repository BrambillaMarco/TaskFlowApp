package it.appventurers.taskflow.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    private static User instance;
    @PrimaryKey(autoGenerate = true)
    private int id; // Campo aggiunto per Room come chiave primaria

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "uId")
    private String uId;

    @ColumnInfo(name = "life")
    private int life;

    @ColumnInfo(name = "current_life")
    private int currentLife;

    @ColumnInfo(name = "xp")
    private int xp;

    @ColumnInfo(name = "level")
    private int level;

    // Costruttore vuoto richiesto da Room
    public User() {
    }

    public User(String email, String uId) {
        this.email = email;
        this.uId = uId;
        this.life = 10;
        this.currentLife = 10;
        this.xp = 0;
        this.level = 1;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static User getInstance(String email, String uId) {
        if (instance == null) {
            instance = new User(email, uId);
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUId() {
        return this.uId;
    }

    public void setUId(String uId) {
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

    public void logout() {
        User.instance = null;
    }
}
