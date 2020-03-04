package com.parking.service;

import com.parking.helper.Constants;
import com.parking.models.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotControllerTest {

    ParkingLotController parkingLotController;
    Vehicle vehicle = new Vehicle("123", "red");
    Vehicle vehicle1 = new Vehicle("1234", "blue");
    Vehicle vehicle2 = new Vehicle("1235", "red");
    @Before
    public void setUp() throws Exception {
        parkingLotController = new ParkingLotController(2);
    }

    @Test
    public void park() throws Exception {
        Assert.assertEquals(String.format(Constants.ALLOCATED_SLOT, 1), parkingLotController.park(vehicle));
        Assert.assertEquals(String.format(Constants.ALLOCATED_SLOT, 2), parkingLotController.park(vehicle1));
        Assert.assertEquals(Constants.NO_SPACE, parkingLotController.park(vehicle2));
    }

    @Test
    public void removeVehicle() throws Exception {
        parkingLotController.park(vehicle);
        parkingLotController.park(vehicle1);
        Assert.assertEquals(String.format(Constants.SLOT_FREED, 2), parkingLotController.removeVehicle(2));
        Assert.assertEquals(String.format(Constants.SLOT_FREED, 1), parkingLotController.removeVehicle(1));
        Assert.assertEquals(Constants.ALREADY_EMPTY, parkingLotController.removeVehicle(2));
    }

    @Test
    public void slotNumberForRegistrationNumber() throws Exception {
        parkingLotController.park(vehicle);
        parkingLotController.park(vehicle1);
        Assert.assertEquals("1", parkingLotController.slotNumberForRegistrationNumber("123"));
        Assert.assertEquals(Constants.NOT_PARKED_IN_PARKING_LOT, parkingLotController.slotNumberForRegistrationNumber("1"));
    }

    @Test
    public void slotNumbersForCarsWithColour() throws Exception {
        parkingLotController.park(vehicle);
        parkingLotController.park(vehicle1);
        Assert.assertEquals("1", parkingLotController.slotNumbersForCarsWithColour("red"));
        Assert.assertEquals(Constants.COLOR_CAR_NOT_PARKED_IN_PARKING_LOT, parkingLotController.slotNumbersForCarsWithColour("white"));
    }

    @Test
    public void registrationNumbersForCarsWithColour() throws Exception {
        parkingLotController.park(vehicle);
        parkingLotController.park(vehicle1);
        Assert.assertEquals("123", parkingLotController.registrationNumbersForCarsWithColour("red"));
        Assert.assertEquals(Constants.COLOR_CAR_NOT_PARKED_IN_PARKING_LOT, parkingLotController.registrationNumbersForCarsWithColour("white"));
    }

}
