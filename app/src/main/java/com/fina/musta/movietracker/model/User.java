package com.fina.musta.movietracker.model;

/**
 * Created by musta on 1/3/2018.
 */

public class User {
    private int id;
    private String name;
    private String Password;

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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
