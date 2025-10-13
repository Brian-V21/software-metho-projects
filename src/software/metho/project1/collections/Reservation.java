package software.metho.project1.collections;
import software.metho.project1.entities.Booking;
import software.metho.project1.entities.Department;
import software.metho.project1.entities.Employee;
import software.metho.project1.entities.Vehicle;

import software.metho.project1.util.Date;
import software.metho.project1.util.List;

/**
 * @author Brian V and Travis F
 */
public class Reservation extends List<Booking> {

    public Reservation() {
        super();
    }


    /** 
    private Booking[] bookings;
    private int size;
    private static final int CAPACITY = 4;
    private static final int NOT_FOUND = -1;
    */

    /**
     * Construct an empty list with an initial capacity
     
    public Reservation() {
        this.bookings = new Booking[CAPACITY];
        this.size = 0;
    }
    */

    /**
     * Searcges for a booking within the list
     *
     * @param booking the booking to look for
     * @return the index if found, or UNDEFINED if not in the list
    
    private int find(Booking booking) {
        for (int i = 0; i < size; i++) {
            if (bookings[i].equals(booking)) {
                return i;
            }
        }
        return NOT_FOUND;
    }
    */

    /**
     * Resizes the array by increasing its capacity by 4.
     
    private void grow() {
        Booking[] newArray = new Booking[bookings.length + CAPACITY];
        for (int i = 0; i < size; i++) {
            newArray[i] = bookings[i];
        }
        bookings = newArray;
    }
    */

    /**
     * Adds a new booking to the end of the list.
     *
     * @param booking the booking that is added
     
    public void add(Booking booking) {
        if (size == bookings.length) {
            grow();
        }
        bookings[size++] = booking;
    }
    */
    /**
     * Removes a booking from the list. Overwrites booking with the last element of the list.
     *
     * @param booking the booking that is removed.
     
    public void remove(Booking booking) {
        int index = find(booking);
        if (index != NOT_FOUND) {
            bookings[index] = bookings[size - 1];
            bookings[size - 1] = null;
            size--;
        }
    }
    */
    /**
     * Checks whether a given booking exists.
     *
     * @param booking the booking being checked
     * @return true if the booking is in the list, otherwise false
     
    public boolean contains(Booking booking) {
        return find(booking) != NOT_FOUND;
    }
    */
    /**
     * Prints the list of bookings ordered by plate numbers, then beginning date.
     */
    public void printByVehicle() {
        /**for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
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
        } */

        int n = size();
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                Booking a = get(min);
                Booking b = get(j);

                String cityA = a.getPickup().getCity();
                String cityB = b.getPickup().getCity();

                int cmp = cityA.compareTo(cityB);
                if (cmp > 0) {
                    min = j;
                } else if (cmp == 0) {
                    String plateA = a.getVehicle().getPlate();
                    String plateB = b.getVehicle().getPlate();
                    int cmpPlate = plateA.compareTo(plateB);
                    if (cmpPlate > 0) {
                        min = j;
                    } else if (cmpPlate == 0) {
                        if (a.getBegin().compareTo(b.getBegin()) > 0) {
                            min = j;
                        }
                    }
                }
            }
            if (min != i) {
                Booking tmp = get(i);
                set(i, get(min));
                set(min, tmp);
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println(get(i));
        }
    }


    /**
     * Prints the list of bookings ordered by department, then employee.
     */
    public void printByDept() {
        /**for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
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

        //Group them into each department
        Department currdept = null;
        for (int i = 0; i < size; i++) {
            Department department = bookings[i].getEmployee().getDept();
            if (currdept != department) {
                currdept = department;
                System.out.println("--" + department + "--");
            }
            System.out.println(bookings[i]);
        }
    */
            int n = size();
            for (int i = 0; i < n - 1; i++) {
                int min = i;
                for (int j = i + 1; j < n; j++) {
                    Booking a = get(min);
                    Booking b = get(j);

                    String deptA = a.getEmployee().getDept().displayName();
                    String deptB = b.getEmployee().getDept().displayName();

                    int cmpDept = deptA.compareTo(deptB);
                    if (cmpDept > 0) {
                        min = j;
                    } else if (cmpDept == 0) {
                        String empA = a.getEmployee().name();
                        String empB = b.getEmployee().name();
                        if (empA.compareTo(empB) > 0) {
                            min = j;
                        }
                    }
                }
                if (min != i) {
                    Booking tmp = get(i);
                    set(i, get(min));
                    set(min, tmp);
                }
            }
            Department currentDept = null;
            for (int i = 0; i < n; i++) {
                Booking b = get(i);
                Department dept = b.getEmployee().getDept();

                if (currentDept != dept) {
                    currentDept = dept;
                    System.out.println("--" + dept.displayName() + "--");
                }
                System.out.println(b);
            }
    }


    /**
     * Testbed main() for testing/debugging purposes.
     
    public static void main(String[] args) {
        Date d1 = new Date(2025, 10, 25);
        Date d2 = new Date(2025, 10, 30);
        Vehicle v1 = new Vehicle("58718D", d1, Make.FORD, 64390);
        Booking b1 = new Booking(d1, d2, Employee.KAUR, v1);

        Reservation r = new Reservation();
        r.add(b1);
        r.printByVehicle();

    }
    */    

    /**
     * Helper functions for frontend
     */

    /**
     * Checks whether a given vehicle already has an associated booking
     * @param vehicle the vehicle to check
     * @return true if a booking exists, false otherwise.
     */
    public boolean hasBookingForVehicle(Vehicle vehicle) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getVehicle().equals(vehicle)) 
                return true;
        }
        return false;
    }
    /**
     * Retrieves the booking matching a given vehicle and end date.
     * @param endDate the booking's end date
     * @param vehicle the vehicle associated with the booking
     * @return the booking if found, otherwise false
     */
    public Booking getBooking(Date endDate, Vehicle vehicle) {
        for (int i = 0; i < size(); i++) {
            Booking b = get(i);

            if (b.getVehicle().equals(vehicle) && b.getEnd().equals(endDate)) {
                return b;
            }
        }
        return null;
    }
    /**
     * Checks if the given booking is the earliest ending booking for its vehicle
     * @param targetBooking the booking to check
     * @return true if it is the earliest ending booking, otherwise false
     */
    public boolean isEarliestEndingDate(Booking targetBooking) {
        if (targetBooking == null) 
            return false;

        Vehicle targetVehicle = targetBooking.getVehicle();
        Date targetEnd = targetBooking.getEnd();

        for (int i = 0; i < size(); i++) {
            Booking b = get(i);
            if (b.getVehicle().equals(targetVehicle) &&
                    b.getEnd().compareTo(targetEnd) < 0) {
                return false;
            }
        }
        return true;
    }
    /**
     * Checks if the Reservation is empty
     * @return true if there are no bookings, otherwise false
     */
    public boolean isEmpty() {
        return size() == 0;
    }
    /**
     * Checks whether a vehicle is available in the given date range.
     * @param v the vehicle
     * @param startDate the requested start date
     * @param endDate the requested end date
     * @return if the vehicle is available, otherwise false
     */
    public boolean isAvailable(Vehicle v, Date startDate, Date endDate) {
        for (int i = 0; i < size(); i++) {
            Booking b = get(i);

            if(b.getVehicle().equals(v)) {
                Date bstart = b.getBegin();
                Date bend = b.getEnd();

                if(!(endDate.compareTo(bstart) < 0 || startDate.compareTo(bend) > 0)) {
                    return false;
                }
            }
            }
        return true;
    }
    /**
     * Checks whether an employee is availible in the given data range
     * @param emp the employee
     * @param startDate the requested start date
     * @param endDate the requested end date
     * @return true if the employee is available, otherwise false
     */
    public boolean isEmpAvailable(Employee emp, Date startDate, Date endDate) {
        for (int i = 0; i < size(); i++) {
            Booking b = get(i);
            if (b.getEmployee() == emp) {
                Date bstart = b.getBegin();
                Date bend = b.getEnd();

                if (!(endDate.compareTo(bstart) < 0 || startDate.compareTo(bend) > 0)) {
                    return false;
                }
            }
        }
    return true;
    }

}



