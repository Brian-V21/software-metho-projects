package software.metho.project1.collections;
import software.metho.project1.entities.Booking;
import software.metho.project1.entities.Department;
import software.metho.project1.entities.Employee;
import software.metho.project1.entities.Vehicle;

import software.metho.project1.entities.Date;
import software.metho.project1.entities.Make;

public class Reservation {
    private Booking[] bookings;
    private int size;
    private static final int CAPACITY = 4;
    private static final int NOT_FOUND = -1;


    /**
     * Construct an empty list with an initial capacity
     */
    public Reservation() {
        this.bookings = new Booking[CAPACITY];
        this.size = 0;
    }

    /**
     * Searcges for a booking within the list
     * 
     * @param booking the booking to look for
     * @return the index if found, or UNDEFINED if not in the list
     */
    private int findBooking(Booking booking) {
        for (int i = 0; i < size; i++) {
            if(bookings[i].equals(booking)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Resizes the array by increasing its capacity by 4.
     */
    private void grow () {
        Booking[] newArray = new Booking[bookings.length + CAPACITY];
        for (int i = 0; i < size; i++) {
            newArray[i] = bookings[i];
        }
        bookings = newArray;
    }

    /**
     * Adds a new booking to the end of the list.
     * 
     * @param booking the booking that is added
     */
    public void add(Booking booking) {
        if (size == bookings.length) {
            grow();
        }
        bookings[size++] = booking;
    }

    /**
     * Removes a booking from the list. Overwrites booking with the last element of the list.
     * 
     * @param booking the booking that is removed.
     */
    public void remove(Booking booking) {
        int index = findBooking(booking);
        if (index != NOT_FOUND) {
            bookings[index] = bookings[size - 1];
            bookings[size - 1] = null;
            size--;
        }
    }

    /**
     * Checks whether a given booking exists.
     * 
     * @param booking the booking being checked
     * @return true if the booking is in the list, otherwise false
     */
    public boolean contains(Booking booking) {
        return findBooking(booking) != NOT_FOUND;
    }

    /**
     * Prints the list of bookings ordered by plate numbers, then beginning date.
     */
    public void printByVehicle(){
        for (int i = 0; i < size - 1; i++) {
            int minIndex = 1;
            for (int j = i + 1; j < size; j++) {
                String plateMin = bookings[minIndex].getVehicle().getPlate();
                String plateJ = bookings[j].getVehicle().getPlate();
            
                if (plateJ.compareTo(plateMin) < 0 || (plateJ.equals(plateMin) && bookings[j].getBegin().compareTo(bookings[minIndex].getBegin()) < 0)) {
                    minIndex = j;
                }
            }
            Booking temp = bookings[i];
            bookings[i] = bookings[minIndex];
            bookings[minIndex] = temp;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(bookings[i]);
        }
    }
   

    /**
     * Prints the list of bookings ordered by department, then employee.
     */
    public void printByDept(){
        for (int i = 0; i < size - 1; i++) {
            int minIndex = 1;
            for (int j = i + 1; j < size; j++) {
                Department deptMin = bookings[minIndex].getEmployee().getDept();
                 Department deptJ = bookings[j].getEmployee().getDept();

                if (deptJ.name().compareTo(deptMin.name()) < 0 || (deptJ.name().equals(deptMin.name()) && bookings[j].getEmployee().name().compareTo(bookings[minIndex].getEmployee().name()) < 0)) {
                        minIndex = j;
                }
            }
            Booking temp = bookings[i];
            bookings[i] = bookings[minIndex];
            bookings[minIndex] = temp;    
         }
         for (int i = 0; i < size; i++) {
            System.out.println(bookings[i]);
         }
    }

    /**
     * Testbed main() for testing/debugging purposes.
     */
    public static void main(String[] args) {
        Date d1 = new Date(2025, 10, 25);
        Date d2 = new Date(2025, 10, 30);
        Vehicle v1 = new Vehicle("58718D", d1, Make.FORD, 64390);
        Booking b1 = new Booking(d1, d2, Employee.KAUR, v1);

        Reservation r = new Reservation();
        r.add(b1);
        r.printByVehicle();

    }

}
