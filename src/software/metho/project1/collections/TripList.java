package software.metho.project1.collections;

import software.metho.project1.entities.Trip;

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

}
