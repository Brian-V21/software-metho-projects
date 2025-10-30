package org.example.project3softwaremetho.util;

import org.example.project3softwaremetho.collections.Fleet;
import org.example.project3softwaremetho.collections.Reservation;
import org.example.project3softwaremetho.entities.Booking;
import org.example.project3softwaremetho.entities.Campus;
import org.example.project3softwaremetho.util.*;
import org.example.project3softwaremetho.entities.Vehicle;
//hello
/**
 * *@author Brian V and Travis F;
 */

public final class  Sort {

    /**
     * Private constructor to prevent instantitation
     */
    private Sort() { 
    }

    /**
     * Returns the city name associated with a given campus, or an empty string is it is null
     * @param c the campus object
     * @return the campus city name, or an empty string if c == null
     */
    private static String citySafe(Campus c) {
        return (c == null) ? "" : c.getCity();
    }

    /**
     * Compares two Date objects safely, handling null values
     * @param a first date to compare
     * @param b second date to compare
     * @return negative if a < b, positive if a > b and 0 if equal or both are null
     */
    private static int safeDateCompare(Date a, Date b) {
        if (a == null && b == null)
            return 0;
        if (a == null)
            return -1;
        if (b == null)
            return 1;
        return a.compareTo(b);
    }

    /**
     * Swap two vehicles in a fleet
     * @param fleet the fleet collection
     * @param i     index of the first vehicle
     * @param j     index of the second vehicle
     */
    private static void swapVehicles(Fleet fleet, int i, int j) {
        Vehicle tmp = fleet.get(i);
        fleet.set(i, fleet.get(j));
        fleet.set(j, tmp);
    }

    /**
     * Swaps two bookings in a reservation
     * @param res the reservation collection
     * @param i   index of the first booking
     * @param j   index of the second booking
     */
     private static void swapBookings(Reservation res, int i, int j) {
        Booking tmp = res.get(i);
        res.set(i, res.get(j));
        res.set(j, tmp);
    }


    /**
     * Compares two Vehicle objects for sorting by City, Make then Date obtained
     * @param a the first vehicle
     * @param b the second vehicle
     * @return a negative integer if a < b, positive if a > b, or 0 if equal
     */
    private static int cmpVehicle(Vehicle a, Vehicle b) {
        String ca = citySafe(a.getCampus());
        String cb = citySafe(b.getCampus());
        int c = ca.compareTo(cb);
        if (c != 0)
            return c;

        int m = a.getMake().compareTo(b.getMake());
        if (m != 0)
            return m;

        return safeDateCompare(a.getObtained(), b.getObtained());
    }
    
    /**
     * Compares two Booking objects by City, License plate, and Begin date
     * @param a the first booking
     * @param b the second booking
     * @return a negative integer if a < b, positive if a > b, or 0 if equal
     */
    private static int cmpBookingByCityPlateBegin(Booking a, Booking b) {
        String ca = citySafe(a.getPickup());
        String cb = citySafe(b.getPickup());
        int c = ca.compareTo(cb);
        if (c != 0)
            return c;
        
        String pa = a.getVehicle().getPlate();
        String pb = b.getVehicle().getPlate();
        int p = pa.compareTo(pb);
        if (p != 0)
            return p;

        return safeDateCompare(a.getBegin(), b.getBegin());
    }

     /**
     * Compares two Booking objects by Department, then Employee
     * @param a the first booking
     * @param b the second booking
     * @return a negative integer if a < b, positive if a > b, or 0 if equal
     */
    private static int cmpBookingByDeptEmployee(Booking a, Booking b) {
        String da = a.getEmployee().getDept().displayName();
        String db = b.getEmployee().getDept().displayName();
        int d = da.compareTo(db);
        if (d != 0)
            return d;

        String ea = a.getEmployee().name();
        String eb = b.getEmployee().name();
        return ea.compareTo(eb);
    }

    /**
     * Sorts a fleet of vehicles in place by City, Make, then Date obtained
     * @param fleet the fleet to be sorted
     */
    public static void sortFleetByLocationMakeDate(Fleet fleet) {
        int n = fleet.size();
        for (int i = 0; i < n - 1; i++) {
            int min = 1;
            for (int j = i + 1; j < n; j++) {
                if (cmpVehicle(fleet.get(j), fleet.get(min)) < 0) {
                    min = j;
                }
            }
            if (min != i)
                swapVehicles(fleet, i, min);
        }
    }

    /**
     * Sorts a Reservation of bookings in place by City, License plate, then Begin date
     * @param res the reservation list to be sorted
     */
    public static void sortBookingsByLocationPlateBegin( Reservation res) {
        int n = res.size();
        for (int i = 0; i < n -1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (cmpBookingByCityPlateBegin(res.get(j), res.get(min)) < 0) {
                    min = j;
                }
            }
            if (min != i)
                swapBookings(res, i, min);
        }
    }

    /**
     * Sorts a Reservation of bookings in place by Department then Employee
     * @param res the reservation list to be sorted
     */
    public static void sortBookingsByDeptEmployee(Reservation res) {
        int n = res.size();
        for (int i = 0; i < n - 1; i++) {
            int min = 1;
            for (int j = i + 1; j < n; j++) {
                if (cmpBookingByDeptEmployee(res.get(j), res.get(min)) < 0) {
                    min = j;
                }
            }
            if (min != i)
                swapBookings(res, i, min);
        }
    }

}
