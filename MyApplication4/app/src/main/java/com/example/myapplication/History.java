package com.example.myapplication;

import java.io.Serializable;

public class History implements Serializable {
    private int id, id_product;
    private String id_user, actions, time;

    public History() {
    }

    public History(int id, int id_product, String id_user, String actions, String time) {
        this.id = id;
        this.id_product = id_product;
        this.id_user = id_user;
        this.actions = actions;
        this.time = time;
    }

    public History(int id_product, String id_user, String actions, String time) {
        this.id_product = id_product;
        this.id_user = id_user;
        this.actions = actions;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
