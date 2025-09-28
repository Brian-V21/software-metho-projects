package software.metho.project1.collections;

import software.metho.project1.entities.Trip;

import software.metho.project1.entities.*;

public class TripList {
    private Node last;

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
                return;
            }
            prev = curr;
            curr = curr.next;
        } while (curr != last.next);

        newNode.next = last.next;
        last.next = newNode;
        last = newNode;
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

    public boolean hasBookingForVehicle(Vehicle vehicle) {
        if (last == null)
        return false;

        Node curr = last.next;
        do {
            if (curr.data.getBooking().getVehicle().equals(vehicle)) {
                return true;
            }
            curr = curr.next;
        } while (curr != last.next);
        return false;
    }

    public Booking getBooking(Date endDate, Vehicle vehicle) {
        if (last == null)
        return null;

        Node curr = last.next;
        do {
            Booking booking = curr.data.getBooking();
            if (booking.getVehicle().equals(vehicle) && booking.getEnd().equals(endDate)) {
                return booking;
            }
            curr = curr.next;
        } while (curr != last.next);
    
        return null;
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
