package com.jdbc.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class User {
    private int id;
    private String name;
    private Date birth;
    private String email;
    SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy");

    public User() {
    }

    public User(String name, String birth, String email) {
        this.name = name;
        try {
            this.birth = sdf.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.email = email;
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date date) {
        this.birth = date;
    }

    public void setBirth(String date) {
        try {
            this.birth = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birth=" + sdf.format(birth) +
                ", email='" + email + '\'' +
                '}';
    }
}
