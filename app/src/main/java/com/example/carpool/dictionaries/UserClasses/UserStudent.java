package com.example.carpool.dictionaries.UserClasses;

import java.util.ArrayList;

public class UserStudent extends User {

    private String graduatingYear;

    public UserStudent() {
    }

    public UserStudent(String uid, String name, String email, String userType, ArrayList<String> ownedVehicles, ArrayList<String> bookedRides, String graduatingYear) {
        super(uid, name, email, userType, ownedVehicles, bookedRides);
        this.graduatingYear = graduatingYear;
    }

    public String getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(String graduatingYear) {
        this.graduatingYear = graduatingYear;
    }

    @Override
    public String toString() {
        return "UserStudent{" +
                "graduatingYear='" + graduatingYear + '\'' +
                '}';
    }
}
