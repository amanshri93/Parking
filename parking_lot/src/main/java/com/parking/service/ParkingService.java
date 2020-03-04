package com.parking.service;

import com.parking.helper.Constants;
import com.parking.helper.Utils;
import com.parking.models.ParkingLot;
import com.parking.models.ParkingSpot;
import com.parking.models.Vehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ParkingService {



    private ParkingLot parkingLot;

    public ParkingService(int noOfParkingSlots) {
        if (noOfParkingSlots < 0) {
            Utils.print(Constants.INVALID_INPUT);
        }
        PriorityQueue<ParkingSpot> emptyParkingSpot = new PriorityQueue<>(noOfParkingSlots, new Comparator<ParkingSpot>() {
            @Override
            public int compare(ParkingSpot o1, ParkingSpot o2) {
                return o1.getSlotNumber() - o2.getSlotNumber();
            }
        });
        for (int i = 0; i < noOfParkingSlots; i++) {
            ParkingSpot parkingSpot = new ParkingSpot(i + 1);
            emptyParkingSpot.add(parkingSpot);
        }
        Utils.print(String.format(Constants.PARKING_LOT_CREATED, noOfParkingSlots));
        this.parkingLot = new ParkingLot(noOfParkingSlots, emptyParkingSpot);
    }

    public String park(Vehicle vehicle){
        if(!parkingLot.isParkingSpaceAvailable()){
            Utils.print(Constants.NO_SPACE);
            return Constants.NO_SPACE;
        }
        ParkingSpot parkingSpot = parkingLot.parkVehicle(vehicle);
        Utils.print(String.format(Constants.ALLOCATED_SLOT, parkingSpot.getSlotNumber()));
        return String.format(Constants.ALLOCATED_SLOT, parkingSpot.getSlotNumber());
    }

    public String removeVehicle(int slotNumber){
        if(!parkingLot.getOccupiedParkingSlots().containsKey(slotNumber)){
            Utils.print(Constants.ALREADY_EMPTY);
            return Constants.ALREADY_EMPTY;
        }
        parkingLot.removeVehicleOnSlot(slotNumber);
        Utils.print(String.format(Constants.SLOT_FREED, slotNumber));
        return String.format(Constants.SLOT_FREED, slotNumber);
    }

    public void getStatus(){
        if(parkingLot.getOccupiedParkingSlots().isEmpty()){
            Utils.print(Constants.ALL_PARKING_SLOT_EMPTY);
            return;
        }
        Utils.print(Constants.STATUS_HEADER);
        for(int i = 0; i <= parkingLot.CAPACITY; i++){
            if(parkingLot.getOccupiedParkingSlots().containsKey(i)){
                ParkingSpot parkingSpot = parkingLot.getOccupiedParkingSlots().get(i);
                System.out.format(String.format(Constants.STATUS_BODY, i, parkingSpot.getVehicle().getRegistrationNumber(), parkingSpot.getVehicle().getColor()));
            }
        }
    }

    public String slotNumberForRegistrationNumber(String registrationNumber){
        if(!parkingLot.getParkedVehicles().containsKey(registrationNumber)){
            Utils.print(String.format(Constants.NOT_PARKED_IN_PARKING_LOT, registrationNumber));
            return String.format(Constants.NOT_PARKED_IN_PARKING_LOT, registrationNumber);
        }
        Vehicle vehicle = parkingLot.getParkedVehicles().get(registrationNumber);
        Utils.print(Integer.toString(vehicle.getParkingSpot().getSlotNumber()));
        return Integer.toString(vehicle.getParkingSpot().getSlotNumber());
    }

    public String slotNumbersForCarsWithColour(String color){
        if(!parkingLot.getColorToVehicleMap().containsKey(color)){
            Utils.print(String.format(Constants.COLOR_CAR_NOT_PARKED_IN_PARKING_LOT, color));
            return String.format(Constants.COLOR_CAR_NOT_PARKED_IN_PARKING_LOT, color);
        }
        List<Vehicle> vehicles = parkingLot.getColorToVehicleMap().get(color);
        List<String> slots = new ArrayList<>();
        vehicles.forEach(v -> slots.add(Integer.toString(v.getParkingSpot().getSlotNumber())));
        Utils.print(String.join(", ", slots));
        return String.join(", ", slots);
    }

    public String registrationNumbersForCarsWithColour(String color){
        if(!vehicleColorExist(parkingLot, color)){
            Utils.print(String.format(Constants.COLOR_CAR_NOT_PARKED_IN_PARKING_LOT, color));
            return String.format(Constants.COLOR_CAR_NOT_PARKED_IN_PARKING_LOT, color);
        }
        List<Vehicle> vehicles = parkingLot.getColorToVehicleMap().get(color);
        List<String> registrationNos = new ArrayList<>();
        vehicles.forEach(v -> registrationNos.add(v.getRegistrationNumber()));
        Utils.print(String.join(", ", registrationNos));
        return String.join(", ", registrationNos);
    }

    private boolean vehicleColorExist(ParkingLot parkingLot, String color) {
        if (parkingLot != null && parkingLot.getColorToVehicleMap() != null) {
            return parkingLot.getColorToVehicleMap().containsKey(color);
        }
        return false;
    }


}