package com.parking.models;

public class ParkingSpot {

    private int slotNumber;

    private Vehicle vehicle;

    public ParkingSpot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void empty() {
        vehicle = null;
    }
}