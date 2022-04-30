package com.example.carpool.dictionaries.UserClasses;

import java.util.ArrayList;

public class UserAnother extends User {
    private String extrainfo;

    public UserAnother() {
    }

    public UserAnother(String uid, String name, String email, String userType, ArrayList<String> ownedVehicles, ArrayList<String> bookedRides, String extrainfo) {
        super(uid, name, email, userType, ownedVehicles, bookedRides);
        this.extrainfo = extrainfo;
    }

    public String getExtrainfo() {
        return extrainfo;
    }

    public void setExtrainfo(String extrainfo) {
        this.extrainfo = extrainfo;
    }

    @Override
    public String toString() {
        return "UserAnother{" +
                "extrainfo='" + extrainfo + '\'' +
                '}';
    }
}
