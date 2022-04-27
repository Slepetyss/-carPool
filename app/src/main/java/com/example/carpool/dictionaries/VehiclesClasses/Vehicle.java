package com.example.carpool.dictionaries.VehiclesClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class Vehicle implements Serializable {
    private String owner;
    private String model;
    private String capacity;
    private String vehicleID;
    private ArrayList<String> ridersUIDs;
    private boolean open;
    private String vehicleType;
    private String basePrice;
    private String ownerUID;

    public Vehicle() {

    }

    public Vehicle(String owner, String model, String capacity, String vehicleType, String basePrice, String vehicleID, String ownerUID) {
        this.owner = owner;
        this.model = model;
        this.capacity = capacity;
        this.vehicleType = vehicleType;
        this.basePrice = basePrice;
        this.vehicleID = vehicleID;
        this.ownerUID = ownerUID;
        this.open = true;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public ArrayList<String> getRidersUIDs() {
        return ridersUIDs;
    }

    public void setRidersUIDs(ArrayList<String> ridersUIDs) {
        this.ridersUIDs = ridersUIDs;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "owner='" + owner + '\'' +
                ", model='" + model + '\'' +
                ", capacity='" + capacity + '\'' +
                ", vehicleID='" + vehicleID + '\'' +
                ", ridersUIDs=" + ridersUIDs +
                ", open=" + open +
                ", vehicleType='" + vehicleType + '\'' +
                ", basePrice='" + basePrice + '\'' +
                ", ownerUID='" + ownerUID + '\'' +
                '}';
    }

    public String getOwnerUID() {
        return ownerUID;
    }

    public void setOwnerUID(String ownerUID) {
        this.ownerUID = ownerUID;
    }
}
