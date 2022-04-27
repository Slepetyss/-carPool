package com.example.carpool.dictionaries.VehiclesClasses;

public class VehicleBicycle extends Vehicle {
    private String bicycleType;
    private String weightCapacity;

    public VehicleBicycle() {
    }

    public VehicleBicycle(String owner, String model, String capacity, String vehicleType, String basePrice, String vehicleID, String ownerUID,String bicycleType, String weightCapacity) {
        super(owner, model, capacity, vehicleType, basePrice, vehicleID, ownerUID);
        this.bicycleType = bicycleType;
        this.weightCapacity = weightCapacity;
    }

    public String getBicycleType() {
        return bicycleType;
    }

    public void setBicycleType(String bicycleType) {
        this.bicycleType = bicycleType;
    }

    public String getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(String weightCapacity) {
        this.weightCapacity = weightCapacity;
    }

    @Override
    public String toString() {
        return "VehicleBicycle{" +
                "bicycleType='" + bicycleType + '\'' +
                ", weightCapacity='" + weightCapacity + '\'' +
                '}';
    }
}
