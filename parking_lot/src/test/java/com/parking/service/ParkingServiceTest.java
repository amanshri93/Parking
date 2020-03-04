package com.parking.service;

import com.parking.helper.Constants;
import com.parking.models.Vehicle;
import com.parking.service.ParkingService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingServiceTest {

    ParkingService parkingService;
    Vehicle vehicle = new Vehicle("123", "red");
    Vehicle vehicle1 = new Vehicle("1234", "blue");
    Vehicle vehicle2 = new Vehicle("1235", "red");
    @Before
    public void setUp() throws Exception {
        parkingService = new ParkingService(2);
    }

    @Test
    public void park() throws Exception {
        Assert.assertEquals(String.format(Constants.ALLOCATED_SLOT, 1), parkingService.park(vehicle));
        Assert.assertEquals(String.format(Constants.ALLOCATED_SLOT, 2), parkingService.park(vehicle1));
        Assert.assertEquals(Constants.NO_SPACE, parkingService.park(vehicle2));
    }

    @Test
    public void removeVehicle() throws Exception {
        parkingService.park(vehicle);
        parkingService.park(vehicle1);
        Assert.assertEquals(String.format(Constants.SLOT_FREED, 2), parkingService.removeVehicle(2));
        Assert.assertEquals(String.format(Constants.SLOT_FREED, 1), parkingService.removeVehicle(1));
        Assert.assertEquals(Constants.ALREADY_EMPTY, parkingService.removeVehicle(2));
    }

    @Test
    public void slotNumberForRegistrationNumber() throws Exception {
        parkingService.park(vehicle);
        parkingService.park(vehicle1);
        Assert.assertEquals("1", parkingService.slotNumberForRegistrationNumber("123"));
        Assert.assertEquals(Constants.NOT_PARKED_IN_PARKING_LOT, parkingService.slotNumberForRegistrationNumber("1"));
    }

    @Test
    public void slotNumbersForCarsWithColour() throws Exception {
        parkingService.park(vehicle);
        parkingService.park(vehicle1);
        Assert.assertEquals("1", parkingService.slotNumbersForCarsWithColour("red"));
        Assert.assertEquals(Constants.COLOR_CAR_NOT_PARKED_IN_PARKING_LOT, parkingService.slotNumbersForCarsWithColour("white"));
    }

    @Test
    public void registrationNumbersForCarsWithColour() throws Exception {
        parkingService.park(vehicle);
        parkingService.park(vehicle1);
        Assert.assertEquals("123", parkingService.registrationNumbersForCarsWithColour("red"));
        Assert.assertEquals(Constants.COLOR_CAR_NOT_PARKED_IN_PARKING_LOT, parkingService.registrationNumbersForCarsWithColour("white"));
    }

}
