package software.metho.project1.util;

import software.metho.project1.collections.Fleet;
import software.metho.project1.collections.Reservation;
import software.metho.project1.entities.Booking;
import software.metho.project1.entities.Campus;
import software.metho.project1.util.*;
import software.metho.project1.entities.Vehicle;

public final class Sort {

    private Sort() { 
    }

    private static String citySafe(Campus c) {
        return (c == null) ? "" : c.getCity();
    }

    private static int safeDateCompare(Date a, Date b) {
        if (a == null && b == null)
            return 0;
        if (a == null)
            return -1;
        if (b == null)
            return 1;
        return a.compareTo(b);
    }

    private static void swapVehicles(Fleet fleet, int i, int j) {
        Vehicle tmp = fleet.get(i);
        fleet.set(i, fleet.get(j));
        fleet.set(j, tmp);
    }

     private static void swapBookings(Reservation res, int i, int j) {
        Booking tmp = res.get(i);
        res.set(i, res.get(j));
        res.set(j, tmp);
    }



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
