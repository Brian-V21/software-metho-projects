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
    private static final int UNDEFINED = -1;

    public Reservation() {
        this.bookings = new Booking[CAPACITY];
        this.size = 0;
    }

    private int findBooking(Booking booking) {
        for (int i = 0; i < size; i++) {
            if(bookings[i].equals(booking)) {
                return i;
            }
        }
        return UNDEFINED;
    }
    private void grow () {
        Booking[] newArray = new Booking[bookings.length + CAPACITY];
        for (int i = 0; i < size; i++) {
            newArray[i] = bookings[i];
        }
        bookings = newArray;
    }

    public void addB(Booking booking) {
        if (size == bookings.length) {
            grow();
        }
        bookings[size++] = booking;
    }
    public void removeB(Booking booking) {
        int index = findBooking(booking);
        if (index != UNDEFINED) {
            bookings[index] = bookings[size - 1];
            bookings[size - 1] = null;
            size--;
        }
    }
    public boolean contains(Booking booking) {
        return findBooking(booking) != UNDEFINED;
    }
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

    public static void main(String[] args) {
        Date d1 = new Date(2025, 10, 25);
        Date d2 = new Date(2025, 10, 30);
        Vehicle v1 = new Vehicle("58718D", d1, Make.FORD, 64390);
        Booking b1 = new Booking(d1, d2, Employee.KAUR, v1);

        Reservation r = new Reservation();
        r.addB(b1);
        r.printByVehicle();

    }

}
