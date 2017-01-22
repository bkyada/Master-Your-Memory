package com.cs442.bkyada.memorygame;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String email;
    private String password;
    private String repassword;

    private int easyUnlockLevel=1;
    private int mediumUnlockLevel=1;
    private int hardUnlockLevel=1;

    public User(String name, String email, String password, String repassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.repassword = repassword;
    }

    public User(int id, String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public int getEasyUnlockLevel() {
        return easyUnlockLevel;
    }

    public void setEasyUnlockLevel(int easyUnlockLevel) {
        if(easyUnlockLevel <1)
            this.easyUnlockLevel = 1;
        else
            this.easyUnlockLevel = easyUnlockLevel;
    }

    public int getMediumUnlockLevel() {
        return mediumUnlockLevel;
    }

    public void setMediumUnlockLevel(int mediumUnlockLevel) {
        if(mediumUnlockLevel <1)
            this.mediumUnlockLevel = 1;
        else
            this.mediumUnlockLevel = mediumUnlockLevel;
    }

    public int getHardUnlockLevel() {
        return hardUnlockLevel;
    }

    public void setHardUnlockLevel(int hardUnlockLevel) {
        if(hardUnlockLevel <1)
            this.hardUnlockLevel = 1;
        else
            this.hardUnlockLevel = hardUnlockLevel;
    }

    public int getUnlockLevelByDifficulty(String difficulty) {
        if ("Easy".equals(difficulty) || "Fácil".equals(difficulty) || "簡單".equals(difficulty)) {
            return getEasyUnlockLevel();
        } else if ("Medium".equals(difficulty) || "Medio".equals(difficulty) || "中".equals(difficulty)) {
            return getMediumUnlockLevel();
        } else if ("Hard".equals(difficulty) || "Difícil".equals(difficulty) || "硬".equals(difficulty)) {
            return getHardUnlockLevel();
        }
        return 1;
    }

}
