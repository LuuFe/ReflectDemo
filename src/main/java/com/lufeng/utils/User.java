package com.lufeng.utils;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Lufo on 2018/7/13.
 */
public class User {
    private String name;

    public String getName() {
        return name;
    }

    private String password;
    private int age;
    private String[] hobby;
    private Date birthday;
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getHobby() {
        return hobby;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", hobby=" + Arrays.toString(hobby) +
                ", birthday=" + birthday +
                '}';
    }
}
