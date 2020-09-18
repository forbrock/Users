package com.jdbc.model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class User {
    private int id;
    private String name;
    private LocalDate birth;
    private String email;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    public User() {
    }

    public User(int id, String name, LocalDate birth, String email) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.email = email;
    }

    public User(String name, String birth, String email) {
        this.name = name;
        this.birth = LocalDate.parse(birth, formatter);
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = LocalDate.parse(birth, formatter);
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
                ", birth=" + birth.format(formatter) +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        if (!birth.equals(user.birth)) return false;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + birth.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
