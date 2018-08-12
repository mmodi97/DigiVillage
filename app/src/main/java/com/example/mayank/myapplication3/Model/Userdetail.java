package com.example.mayank.myapplication3.Model;

/**
 * Created by Mayank on 6/8/2018.
 */

public class Userdetail {
    private String name,email,email1;


    public Userdetail(String name, String email, String email1) {
        this.name = name;
        this.email = email;
        this.email1=email;


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

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public Userdetail(){

    }
}
