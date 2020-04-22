package Login;
/**
 * Created by Zhenyan li and Haopeng Song, Group 27, Comp215, University of Liverpool, referred to some codes in github
 */

/** A class to define user format */
public class User {
    private int IDNumber;
    private String name;
    private String password;
    public User(int IDNumber, String name, String password) {
        this.IDNumber=IDNumber;
        this.name = name;
        this.password = password;
    }

    public int getIDNumber() {
        return IDNumber;
    }
    public void setIDNumber(int IDNumber) {
        this.IDNumber = IDNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User{" +
                "IDNumber="+ String.valueOf(IDNumber) + '\'' +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

