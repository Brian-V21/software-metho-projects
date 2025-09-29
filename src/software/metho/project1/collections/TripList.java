package software.metho.project1.collections;

import software.metho.project1.entities.Trip;

import software.metho.project1.entities.*;

public class TripList {
    private Node last;
    private int size;

    private static class Node{
        Trip data;
        Node next;

        Node(Trip data) {
            this.data = data;
            this.next = null;
        }
    }
    public void add(Trip trip) {
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

        newNode.next = last.next;
        last.next = newNode;
        last = newNode;
        size++;
    }
    public void print() {
        if (last == null) {
            System.out.println("No completed trips in this list");
            return;
        }

        Node curr = last.next;
        do {
            System.out.println(curr.data);
            curr = curr.next;
        } while (curr != last);
    }

    public boolean remove(Booking booking) {
        if (last == null)
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
                /**
                 * might need size here unsure
                 */
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        } while (curr != last.next);

        return false;
    }

    /**
     * Prints the list of bookings ordered by plate numbers, then beginning date.
     */
    public void printByVehicle() {
        if (last == null) {
            System.out.println("No bookings in the system.");
            return;
        }

        Node printedNodes = null; // We'll track which trips have been printed by repeated traversal
        Node start = last.next;

        boolean done = false;
        while (!done) {
            Node curr = last.next;
            Trip minTrip = null;

            // Find the trip with the minimum vehicle plate that hasn't been printed yet
            do {
                if ((minTrip == null || curr.data.getBooking().getVehicle().getPlate()
                        .compareTo(minTrip.getBooking().getVehicle().getPlate()) < 0)) {
                    minTrip = curr.data;
                }
                curr = curr.next;
            } while (curr != last.next);

            // Print all trips with that vehicle plate
            curr = last.next;
            do {
                if (curr.data.getBooking().getVehicle().getPlate()
                        .equals(minTrip.getBooking().getVehicle().getPlate())) {
                    System.out.println(curr.data);
                }
                curr = curr.next;
            } while (curr != last.next);

            // Remove all printed trips from consideration by marking them as null
            curr = last.next;
            do {
                if (curr.data.getBooking().getVehicle().getPlate()
                        .equals(minTrip.getBooking().getVehicle().getPlate())) {
                    curr.data = null;
                }
                curr = curr.next;
            } while (curr != last.next);

            // Check if all trips are printed
            done = true;
            curr = last.next;
            do {
                if (curr.data != null) {
                    done = false;
                    break;
                }
                curr = curr.next;
            } while (curr != last.next);
        }

        // Restore nulls to original trips if necessary
    }



    /**
     * Prints the list of bookings ordered by department, then employee.
     */
    public void printByDept() {
        if (last == null) {
            System.out.println("No bookings in the system.");
            return;
        }

        boolean printedAll = false;

        while (!printedAll) {
            Node curr = last.next;
            Trip minTrip = null;

            // Step 1: Find trip with smallest employee name (or enum order)
            do {
                if (curr.data != null && (minTrip == null ||
                        curr.data.getBooking().getEmployee().name()
                                .compareTo(minTrip.getBooking().getEmployee().name()) < 0)) {
                    minTrip = curr.data;
                }
                curr = curr.next;
            } while (curr != last.next);

            if (minTrip == null) {
                printedAll = true;
                break;
            }

            // Step 2: Print all trips with that employee
            curr = last.next;
            do {
                if (curr.data != null &&
                        curr.data.getBooking().getEmployee()
                                .equals(minTrip.getBooking().getEmployee())) {
                    System.out.println(curr.data);
                    // Mark as printed
                    curr.data = null;
                }
                curr = curr.next;
            } while (curr != last.next);
        }
    }


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

}
