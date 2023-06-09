package com.driver.services.impl;

import com.driver.model.SpotType;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setAddress(address);
        parkingLot.setName(name);
        parkingLot.setSpotList(new ArrayList<>());
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {

        // Getting ParkingLot of given id
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        // Creating spot
        Spot spot = new Spot();
        //setting spot atrributes PricePerHour,OccuPied,Parkinglot,SpotType
        spot.setPricePerHour(pricePerHour);
        spot.setOccupied(false);
        spot.setParkingLot(parkingLot);

        if (numberOfWheels > 2) {

            spot.setSpotType(SpotType.FOUR_WHEELER);
        }
        else if (numberOfWheels > 4) {

            spot.setSpotType(SpotType.OTHERS);
        }
        else  {

            spot.setSpotType(SpotType.TWO_WHEELER);
        }
        spot.setReservationList(new ArrayList<>());

        List<Spot> spotList = parkingLot.getSpotList();
        if (spotList == null) {
            spotList = new ArrayList<>();
        }
        spotList.add(spot);
        // Setting spot in parking lot
        parkingLot.setSpotList(spotList);
        // saving parking lot
        parkingLotRepository1.save(parkingLot);

        return spot;


    }

    @Override
    public void deleteSpot(int spotId) {

        spotRepository1.deleteById(spotId);

    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        // Getting Spot from id

        Spot spot = null;
        ParkingLot parkingLot =parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spotList = parkingLot.getSpotList();
        for(Spot spot1 : spotList)
        {
            if(spot1.getId()==spotId)
            {
                spot1.setPricePerHour(pricePerHour);
                spotRepository1.save(spot1);
                spot =spot1;
                break;
            }
        }


        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {


        parkingLotRepository1.deleteById(parkingLotId);
    }
}