package com.example.visualphysics10.database;

import java.io.Serializable;

public class DataItem implements Serializable {
    private long name;
    private final double gold;

    public DataItem(long id, double gold) {
        this.name = id;
        this.gold = gold;
    }

    public long getId() {
        return name;
    }

    public double getGold() {
        return gold;
    }

}
