package com.example.carpool.dictionaries.UserClasses;

import java.util.ArrayList;

public class UserWorker extends User {

    private String jobName;

    public UserWorker() {
    }

    public UserWorker(String uid, String name, String email, String userType, ArrayList<String> ownedVehicles, ArrayList<String> bookedRides, String jobName) {
        super(uid, name, email, userType, ownedVehicles, bookedRides);
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public String toString() {
        return "UserWorker{" +
                "jobName='" + jobName + '\'' +
                '}';
    }
}
