package com.example.srushtee.dummy;

/**
 * Created by srushtee on 22-03-2017.
 */

public class Users {
    String userName;
    String phoneNumber;

    public Users() {
    }

    public Users(String phoneNumber, String userName) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}