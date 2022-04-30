package com.example.carpool.dictionaries.UserClasses;

import java.util.ArrayList;

public class UserTeacher extends User {

    private String inSchoolTitle;

    public UserTeacher() {
    }

    public UserTeacher(String uid, String name, String email, String userType, ArrayList<String> ownedVehicles, ArrayList<String> bookedRides, String inSchoolTitle) {
        super(uid, name, email, userType, ownedVehicles, bookedRides);
        this.inSchoolTitle = inSchoolTitle;
    }

    public String getInSchoolTitle() {
        return inSchoolTitle;
    }

    public void setInSchoolTitle(String inSchoolTitle) {
        this.inSchoolTitle = inSchoolTitle;
    }

    @Override
    public String toString() {
        return "UserTeacher{" +
                "inSchoolTitle='" + inSchoolTitle + '\'' +
                '}';
    }
}
