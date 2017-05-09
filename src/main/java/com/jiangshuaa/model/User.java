package com.jiangshuaa.model;

/**
 * Created by jiangshuhua on 2017/5/9.
 */
public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public User(String name){
        this.name = name;
    }
}
