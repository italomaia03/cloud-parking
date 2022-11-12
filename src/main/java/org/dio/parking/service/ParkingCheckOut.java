package org.dio.parking.service;

import org.dio.parking.model.Parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingCheckOut {

    public static final int FREE_PARKING_LIMIT = 10;
    public static final int ONE_HOUR = 60;
    public static final int TWENTY_FOUR_HOUR = 24*60;
    public static final double ONE_HOUR_VALUE = 5.00;
    public static final double ADDITIONAL_PER_HOUR_VALUE = 2.00;
    public static final double DAY_VALUE = 20.00;

    public static double getBill(Parking parking){
        return calculateBill(parking.getEntryDate(), parking.getExitDate());
    }

    private static double calculateBill(LocalDateTime entryDate, LocalDateTime exitDate){
        long minutes = entryDate.until(exitDate, ChronoUnit.MINUTES);
        double bill = 0.0;
        if (minutes <= FREE_PARKING_LIMIT) return 0.00;
        if (minutes <= ONE_HOUR) return ONE_HOUR_VALUE;
        if (minutes <= TWENTY_FOUR_HOUR){
            bill = ONE_HOUR_VALUE;
            long hours = minutes / ONE_HOUR;
            for (int i = 0; i < hours; i++)
                bill += ADDITIONAL_PER_HOUR_VALUE;
            return bill;
        }
        long days = minutes/TWENTY_FOUR_HOUR;
        for (int i = 0; i < days; i++)
            bill += DAY_VALUE;
        return bill;
    }

}
