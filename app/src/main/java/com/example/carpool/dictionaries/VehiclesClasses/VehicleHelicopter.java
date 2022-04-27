package com.example.carpool.dictionaries.VehiclesClasses;

public class VehicleHelicopter extends Vehicle {
    private String maxAltitude;
    private String maxAirSpeed;

    public VehicleHelicopter() {
    }

    public VehicleHelicopter(String owner, String model, String capacity, String vehicleType, String basePrice, String vehicleID, String ownerUID, String maxAltitude, String minAltitude) {
        super(owner, model, capacity, vehicleType, basePrice, vehicleID, ownerUID);
        this.maxAltitude = maxAltitude;
        this.maxAirSpeed = minAltitude;
    }

    public String getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(String maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    public String getMaxAirSpeed() {
        return maxAirSpeed;
    }

    public void setMaxAirSpeed(String maxAirSpeed) {
        this.maxAirSpeed = maxAirSpeed;
    }

    @Override
    public String toString() {
        return "VehicleHelicopter{" +
                "maxAltitude='" + maxAltitude + '\'' +
                ", maxAirSpeed='" + maxAirSpeed + '\'' +
                '}';
    }
}
