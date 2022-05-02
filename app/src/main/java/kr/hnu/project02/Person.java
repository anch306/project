package kr.hnu.project02;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private String password;

    public Person(String n, String p) {
        name = n;
        password = p;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
}
