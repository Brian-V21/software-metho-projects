package software.metho.project1.collections;
import software.metho.project1.entities.*;
import software.metho.project1.util.List;

import java.text.DecimalFormat;

/**
 * @author Travis F
 */

public class TripList  extends List<Trip> {
   
   /** 
    private Node last;
    private int size;

    /**
     * Node represents an element in the circular Linked list
     
    private static class Node{
        Trip data;
        Node next;
        /**
         * Creates a new node containing the given trip
         * @param data the trip to store
         
        Node(Trip data) {
            this.data = data;
            this.next = null;
        }
    }
    */
    /**
     * Adds a trip to the List, maintaining ascending order based on the booking's end date.
     * @param trip the trip to add
     */
    @Override
    public void add(Trip trip) {
        /** 
        Node newNode = new Node(trip);

        //Empty list
        if (last == null) {
            last = newNode;
            last.next = last; //circular
            size++;
            return;
        }

        Node curr = last.next;
        Node prev = last;
        do {
            //checking where to add, 1 come before current, then if inserted before 1st node
            if (trip.getBooking().getEnd().compareTo(curr.data.getBooking().getEnd()) < 0) {
                newNode.next = curr;
                prev.next = newNode;
                if (curr == last.next) {
                    last.next = newNode;
                }
                size++;
                return;
            }
            prev = curr;
            curr = curr.next;
        } while (curr != last.next);
        // Insert at the end
        newNode.next = last.next;
        last.next = newNode;
        last = newNode;
        size++;
        */
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
        /**if (last == null) {
            System.out.println("No completed trips in this list");
            return;
        }

        Node curr = last.next;
        do {
            System.out.println(curr.data);
            curr = curr.next;
        } while (curr != last.next);
         */

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
     * @param booking the booking whos trip should be removed
     * @return true if the trip was found and removed, otherwise false
     */
    public boolean remove(Booking booking) {
        /**if (last == null)
            return false;

        Node curr = last.next;
        Node prev = last;

        do {
            if (curr.data.getBooking().equals(booking)) {
                if (curr == last && curr == last.next) {
                    last = null;
                }
                else if (curr == last) {
                    prev.next = curr.next;
                    last = prev;
                }
                else {
                    prev.next = curr.next;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        } while (curr != last.next);

        return false;
        */

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

/**
 * Testbed main()
 
public static void main(String[] args) {
    TripList list = new TripList();
    Date d1 = new Date(2025, 10, 25);
    Date d2 = new Date(2025, 10, 28);
    Vehicle v = new Vehicle("58719D", new Date(2017, 1, 31), Make.TOYOTA, 87120);
    Booking b = new Booking(d1, d2, Employee.KAUR, v);
    Trip t = new Trip(b, 87120, 87200);

    list.add(t);
    list.print();

    }
*/
}
