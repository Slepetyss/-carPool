package com.example.carpool.dictionaries.UserClasses;

import java.util.ArrayList;

public class UserParent extends User {

    private String amountOfChildren;

    public UserParent() {
    }

    public UserParent(String uid, String name, String email, String userType, ArrayList<String> ownedVehicles,ArrayList<String> bookedRides, String amountOfChildren) {
        super(uid, name, email, userType, ownedVehicles, bookedRides);
        this.amountOfChildren = amountOfChildren;
    }


    public String getAmountOfChildren() {
        return amountOfChildren;
    }

    public void setAmountOfChildren(String amountOfChildren) {
        this.amountOfChildren = amountOfChildren;
    }

    @Override
    public String toString() {
        return "UserParent{" +
                "amountOfChildren='" + amountOfChildren + '\'' +
                '}';
    }
}
