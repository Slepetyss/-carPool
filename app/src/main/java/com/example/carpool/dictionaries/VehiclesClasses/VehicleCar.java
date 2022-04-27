package com.example.carpool.dictionaries.VehiclesClasses;

public class VehicleCar extends Vehicle {
    private String rangeKm;

    public VehicleCar() {
    }

    public VehicleCar(String owner, String model, String capacity, String vehicleType, String basePrice, String vehicleID, String ownerUID,String rangeKm) {
        super(owner, model, capacity, vehicleType, basePrice, vehicleID, ownerUID);
        this.rangeKm = rangeKm;
    }

    public String getRangeKm() {
        return rangeKm;
    }

    public void setRangeKm(String rangeKm) {
        this.rangeKm = rangeKm;
    }

    @Override
    public String toString() {
        return "VehicleCar{" +
                "rangeKm='" + rangeKm + '\'' +
                '}';
    }
}
