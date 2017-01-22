package com.cs442.apipalia.memorygame;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private String score;

    public Item(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

}
