package org.example.project3softwaremetho.collections;
import org.example.project3softwaremetho.entities.*;
import org.example.project3softwaremetho.util.List;
import java.text.DecimalFormat;

/**
 * @author Travis F and Brian V
 */

public class TripList  extends List<Trip> {
   

    /**
     * Adds a trip to the List, maintaining ascending order based on the booking's end date.
     * @param trip the trip to add
     */
    @Override
    public void add(Trip trip) {
        if (trip == null)
        return;
        super.add(trip);

        int i = size() - 1;
        while (i > 0) {
            Trip curr = get(i);
            Trip prev = get(i - 1);
            int cmp = curr.getBooking().getEnd().compareTo(prev.getBooking().getEnd());
            if (cmp < 0) {
                set(i, prev);
                set(i-1, curr);
                i--;
            } else {
                break;
            }
        }
    }
    /**
     * Prints all trips in the list in order.
     * If the list is empty, a message is displayed instead.
     */
    public void print() {
        if (isEmpty()) {
            System.out.println("There is no archived trips");
            return;
        }
        for (Trip t : this) {
            System.out.println(t);
        }
    }

    /**
     * Removes the trip associated with the booking
     * @param booking the booking whose trip should be removed
     * @return true if the trip was found and removed, otherwise false
     */
    public boolean remove(Booking booking) {
        if (booking == null)
            return false;
        for (int i = 0; i < size(); i++) {
            Trip t = get(i);
            if (t.getBooking().equals(booking)) {
                super.remove(t);
                return true;
            }
        }
        return false;
    }

    /**
     * Print charges based off department and displays total charge per department
     */
    public void printCharges() {
        if (this.isEmpty()) {
            System.out.println("There is no archived trips");
            return;
        }

        DecimalFormat money = new DecimalFormat ("$#,##0.00");

        Department[] order = Department.values();
        for (int d = 0; d < order.length; d++) {
            Department dept = order[d];

            boolean hasAny = false;
            for (Trip t : this) {
                if (t.getBooking().getEmployee().getDept() == dept) {
                    hasAny = true;
                    break;
                }
            }
            if (!hasAny)
            continue;

            System.out.println("--" + dept.toString() + "--");

            double deptTotal = 0.0;
            for (Trip t : this) {
                if (t.getBooking().getEmployee().getDept() != dept)
                continue;

                int miles = t.milesUsed();
                double charge = t.getCharge();
                double surcharge = t.getSurchargeAmount();
                double total = charge + surcharge;
                deptTotal += total;

                System.out.println(t.getBooking().getVehicle().getPlate() + " " +
                    t.getBooking().getBegin().toString() + " ~ " +
                    t.getBooking().getEnd().toString() + " " +
                    "charge: " + money.format(charge) + " " +
                    "surcharge: " + money.format(surcharge) + " " +
                    "total: " + money.format(total));
            }
            System.out.println("Department total: " + money.format(deptTotal));
        }
    }
}
