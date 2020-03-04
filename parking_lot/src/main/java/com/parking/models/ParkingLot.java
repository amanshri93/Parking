package com.parking.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class ParkingLot {

    public final Integer CAPACITY;

    private PriorityQueue<ParkingSpot> emptyParkingSlots;

    private HashMap<Integer, ParkingSpot> occupiedParkingSlots;

    private HashMap<String, Vehicle> parkedVehicles;

    private HashMap<String, List<Vehicle>> colorToVehicleMap;


    public ParkingLot(Integer capacity, PriorityQueue<ParkingSpot> emptyParkingSlots) {
        CAPACITY = capacity;
        this.emptyParkingSlots = emptyParkingSlots;
        this.occupiedParkingSlots = new HashMap<>();
        this.parkedVehicles = new HashMap<>();
        this.colorToVehicleMap = new HashMap<>();
    }

    public PriorityQueue<ParkingSpot> getEmptyParkingSlots() {
        return emptyParkingSlots;
    }

    public void setEmptyParkingSlots(PriorityQueue<ParkingSpot> emptyParkingSlots) {
        this.emptyParkingSlots = emptyParkingSlots;
    }

    public HashMap<Integer, ParkingSpot> getOccupiedParkingSlots() {
        return occupiedParkingSlots;
    }

    public void setOccupiedParkingSlots(HashMap<Integer, ParkingSpot> occupiedParkingSlots) {
        this.occupiedParkingSlots = occupiedParkingSlots;
    }

    public HashMap<String, Vehicle> getParkedVehicles() {
        return parkedVehicles;
    }

    public void setParkedVehicles(HashMap<String, Vehicle> parkedVehicles) {
        this.parkedVehicles = parkedVehicles;
    }

    public HashMap<String, List<Vehicle>> getColorToVehicleMap() {
        return colorToVehicleMap;
    }

    public void setColorToVehicleMap(HashMap<String, List<Vehicle>> colorToVehicleMap) {
        this.colorToVehicleMap = colorToVehicleMap;
    }

    public boolean isParkingSpaceAvailable(){
        return !emptyParkingSlots.isEmpty();
    }

    /**
     * Gets the next empty slot closer to the entrance
     * @return
     */
    public ParkingSpot getNextEmptySlot(){
        return emptyParkingSlots.poll();
    }

    /**
     * Parks the given vehicle on the next available slot
     * @param vehicle
     * @return
     */
    public ParkingSpot parkVehicle(Vehicle vehicle){
        if(isParkingSpaceAvailable()) {
            ParkingSpot parkingSpot = getNextEmptySlot();
            vehicle.setParkingSpot(parkingSpot);
            parkingSpot.setVehicle(vehicle);
            parkedVehicles.put(vehicle.getRegistrationNumber(), vehicle);
            occupiedParkingSlots.put(parkingSpot.getSlotNumber(), parkingSpot);
            if (!colorToVehicleMap.containsKey(vehicle.getColor()) || colorToVehicleMap.get(vehicle.getColor()) == null) {
                createNewColor(vehicle);
            }
            colorToVehicleMap.get(vehicle.getColor()).add(vehicle);
            return parkingSpot;
        }
        return null;
    }

    /**
     *
     * @param vehicle
     */
    private void createNewColor(Vehicle vehicle) {
        List<Vehicle> vehicles = new ArrayList<>();
        colorToVehicleMap.put(vehicle.getColor(), vehicles);
    }

    public void removeVehicleOnSlot(int slotNumber) {
        ParkingSpot parkingSpot = occupiedParkingSlots.remove(slotNumber);
        Vehicle vehicle = parkedVehicles.remove(parkingSpot.getVehicle().getRegistrationNumber());
        colorToVehicleMap.get(vehicle.getColor()).remove(vehicle);
        parkingSpot.empty();
        emptyParkingSlots.add(parkingSpot);
    }
}