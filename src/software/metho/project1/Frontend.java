package software.metho.project1;
/**
 * *@author Brian V and Travis F;
 */
import software.metho.project1.collections.Fleet;
import software.metho.project1.collections.Reservation;
import software.metho.project1.collections.TripList;
import software.metho.project1.entities.*;
import software.metho.project1.util.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Frontend {
    private final Fleet fleet;
    private final TripList completedTrips;
    private final Reservation bookings;


    
    /**
     * Constructor for Frontend
     * @param fleet parameter for fleet collections
     * @param bookings parameter for bookings collections
     * @param completedTrips parameter for TripList collection
     */
    public Frontend(Fleet fleet, Reservation bookings, TripList completedTrips) {
        this.fleet = fleet;
        this.bookings = bookings;
        this.completedTrips = completedTrips;
    }

    /**
     * Default constructor
     */
    public Frontend() {
        this.fleet = new Fleet();
        this.bookings = new Reservation();
        this.completedTrips = new TripList();
    }

    /**
     * runs the command line for vehicle management system=
     */
    public void run() {
        System.out.println("Vehicle Management System is running.");
        Scanner in = new Scanner(System.in);
        boolean running = true;
        while (running && in.hasNextLine()) {
            String line = in.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] t = line.split("\\s+");
            switch (t[0]) {
                case "L": handleLoad(); break;
                case "A": handleAdd(t); break;
                case "D": handleDelete(t); break;
                case "B": handleBook(t); break;
                case "C": handleCancel(t); break;
                case "R": handleReturn(t); break;
                case "PF": handlePF(); break;
                case "PR": handlePR(); break;
                case "PD": handlePD(); break;
                case "PT": handlePT(); break;
                case "PC": handlePC(); break;
                case "Q" :
                    running = false;
                    break;
                default: System.out.println(t[0] + " - invalid command!");
            }
        }
        System.out.println("Vehicle Management System is terminated.");
    }

    /**
     * Loads vehicle.txt file into fileScanner
     */
    private void handleLoad() {
        try (Scanner fileScanner = new Scanner(new File("vehicles.txt"))) {
            int count = fleet.load(fileScanner);
            System.out.println(count + " vehicles loaded into the fleet.");
        } catch (FileNotFoundException e) {
            System.out.println("vehicles.txt not found");
        }
    }

    /**
     * adds a vehicle into fleet collection
     * @param t user input from terminal
     */
    private void handleAdd(String[] t) {
        if (t.length < 6) {
            System.out.println("Invalid input!"); 
            return;
        }

        String plate = t[1];
        String obtainedStr = t[2];
        String makeStr = t[3].toUpperCase();
        String mileageStr = t[4];
        String campusStr = t[5];

        if (!Vehicle.isValidPlate(plate)) {
            System.out.println(plate + " - invalid license plate.");
            return;
        }

        int mileage;
        try {
            mileage = Integer.parseInt(mileageStr);
            if (mileage <= 0)  
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println(mileageStr + " - invalid mileage.");
            return;
        }

        Make make;
        try {
            make = Make.valueOf(makeStr);
        } catch (IllegalArgumentException e) {
            System.out.println(t[3] + " - invalid make.");
            return;
        }

        Campus campus;
        try {
            campus = Campus.fromString(campusStr);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        Date obtained = null;
        try {
            obtained = Date.fromString(obtainedStr);
            if (!obtained.isValid()) {
                System.out.println(obtainedStr + " - invalid calendar date.");
                return;
            }
            if (!obtained.isPastDate()) {
                System.out.println(obtainedStr + " - is today or a futu=re date.");
                return;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(obtainedStr + " - invalid calendar date.");
        }
        
        char type = Character.toUpperCase(plate.charAt(5));
        Vehicle v;
            switch(type) {
                case 'S': v = new Sedan(plate, obtained, make, mileage, campus);
                    break;
                case 'D': v = new Utility(plate, obtained, make, mileage, campus);
                    break;
                case 'X': v = new Truck(plate, obtained, make, mileage, campus);
                    break;
                default:
                    System.out.println(plate + " - invalid license plate.");
                    return;
            }

            if (fleet.contains(v)) {
                System.out.println(v + " is already in the fleet.");
                return;
            }
            fleet.add(v);
            System.out.println(v + " has been added to the fleet.");
    }

    /**
     * Handles the deletion of a vehicle from fleet
     * @param t user input from Terminal
     */
    public void handleDelete(String[] t) {
        if (t.length < 2) {
            System.out.println("Invalid input!");
            return;
        }
        
        String plate = t[1];
        Vehicle vehicle = fleet.findVehicle(plate);
        if (vehicle == null) {
            System.out.println(plate + " is not in the fleet.");
            return;
        }

        if (bookings.hasBookingForVehicle(vehicle)) {
            System.out.println(vehicle + " cannot be deleted, booked.");
            return;
        }
        fleet.remove(vehicle);
    }

    /**
     * Handles the adding another booking into bookings collection
     * @param t user input from terminal
     */
    private void handleBook(String[] t) {
        if (t.length < 6) {
            System.out.println("Invalid input!");
            return;
        }

        Date begin, end;
        try {
            begin = Date.fromString(t[1]);
            end = Date.fromString(t[2]);
            if (!begin.isValid()) {
                System.out.println(t[1] + " is not a valid calendar date.");
                return;
            }
            if (!end.isValid()) {
                System.out.println(t[2] + " is not a valid calendar date.");
                return;
            }
            if (!begin.isFutureDate()) {
                System.out.println(t[1] + " Beginning date is not today or a future date.");
                return;
            }
            if (end.compareTo(begin) < 0) {
                System.out.println(t[2] + " ending date must be equal or after the beginning date.");
                return;
            }
            if (begin.beyondDate()) {
                System.out.println(t[1] + " beginning date beyond 3 months");
                return;
            }
            if (begin.durationExceedsWeek(end)) {
                System.out.println(t[1] + " ~ " + t[2] + " duration is more than a week");
                return;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date input");
            return;
        }

        String plate = t[3];
        Vehicle vehicle = fleet.findVehicle(plate);
        if (vehicle == null) {
            System.out.println(plate + " is not in the fleet.");
            return;
        }

        Employee emp;
        try {
            emp = Employee.valueOf(t[4].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(t[4].toUpperCase() + " not an eligible employee to book.");
            return;
        }

        Campus dropoff;
        try {
            dropoff = Campus.fromString(t[5]);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (!bookings.isAvailable(vehicle, begin, end)) {
            System.out.println(vehicle.getPlate() + " - booking with " + begin + " ~ " + end + " not available");
            return;
        }
        if (!bookings.isEmpAvailable(emp, begin, end)) {
            System.out.println(emp + " - has an existing booking conflicting with the beginning date " + begin);
            return;
        }
    
         Booking b = new Booking(begin, end, emp, vehicle, dropoff);
         bookings.add(b);
         System.out.println(b + " booked.");
    }

    /**
     * Handles cancellation of a booking from bookings collection
     * @param t user input from terminal
     */
    private void handleCancel(String[] t) {
        if (t.length < 3) {
            System.out.println("Invalid input!");
            return;
        }
        Date end;
        try {
            end = Date.fromString(t[1]);
            if (!end.isValid()) {
                System.out.println(t[1] + " is not a valid date.");
                return;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(t[1] + "Invalid date input");
            return;
        }
        Vehicle v = fleet.findVehicle(t[2]);
        if (v == null) {
            System.out.println(t[3] + ":" + t[1] + " ~ " + t[2] + " - Cannot find the booking");
            return;
        }
        Booking b = bookings.getBooking(end, v);
        if (b == null) {
            System.out.println(v.getPlate() + " booked with ending date " + end + " - cannot find the booking.");
            return;
        }
        bookings.remove(b);
        System.out.println("Booking canceled: " + b);
    }

    /**
     * Handles returning a vehicle after Trip has been completed
     * @param t user input from terminal
     */
    private void handleReturn(String[] t) {
        if (t.length < 4) {
            System.out.println( "Invalid input!");
            return;
        }
        Date end;
        try {
            end = Date.fromString(t[1]);
            if (!end.isValid()) {
                System.out.println(t[1] + " is not a valid date.");
                return;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date input.");
            return;
        }

        Vehicle v = fleet.findVehicle(t[2]);
        if (v == null) {
            System.out.println("Vehicle not found in fleet.");
            return;
        }

        int newMileage;
        try {
            newMileage = Integer.parseInt(t[3]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid mileage - current mileage: " + v.getMileage() + " entered mileage" + t[3]);
            return;
        }

        Booking b = bookings.getBooking(end, v);
        if (b == null) {
            System.out.println(v.getPlate() + " booked with ending date " + end + " - cannot find booking");
            return;
        }
        if (!bookings.isEarliestEndingDate(b)) {
            System.out.println(b.getVehicle().getPlate() + " booked with ending date " + b.getEnd() + " - returning not in order of ending date");
            return;
        }
        if (newMileage == 0) {
            System.out.println(newMileage + " - invalid mileage");
            return;
        }
        if (newMileage <= v.getMileage()) {
            System.out.println("Invalid mileage - current mileage: " + v.getMileage() + " entered mileage: " + newMileage);
            return;
        }

        int beginMileage = v.getMileage();
        v.setMileage(newMileage);

        boolean surcharge = (b.getDropoff() != null) && (b.getPickup() != b.getDropoff());
        Trip trip = new Trip(b, beginMileage, newMileage, surcharge);
        completedTrips.add(trip);

        if (b.getDropoff() != null)
            v.setCampus(b.getDropoff());

        bookings.remove(b);
        System.out.println(trip.toString());
    }

    /**
     * Displays list of vehicles in fleet collection based off city, make, then date obtained
     */
    private void handlePF() {
        if (fleet.isEmpty()) {
            System.out.println("There is no vehicle in the fleet.");
            return;
        }
        System.out.println("*List of vehicles in the fleet, ordered by city, make, then date obtained.");
        Sort.sortFleetByLocationMakeDate(fleet);
        fleet.printByMake();
        System.out.println("*end of list.");
        System.out.println();
    }

    /**
     * Displays list of reservations based off city, license plate, then beginning date
     */
    private void handlePR() {
        if (bookings.isEmpty()) {
            System.out.println("There is no booking record.");
            return;
        }
        System.out.println("*List reservations ordered by city, license plate, then beginning date.");
        Sort.sortBookingsByLocationPlateBegin(bookings);
        bookings.printByVehicle();
        System.out.println("*end of list.");
        System.out.println();
    }

    /**
     * Display list of reservations based off department then employee
     */
    private void handlePD() {
        if (bookings.isEmpty()) {
            System.out.println("There is no booking record.");
            return;
        }
        System.out.println("*List of reservations ordered by Department then employee.");
        bookings.printByDept();
        System.out.println("*end of list.");
        System.out.println();
    }

    /**
     * Display list of completed trips based off ending date
     */
    private void handlePT() {
        if (completedTrips.isEmpty()) {
            System.out.println("There is no archived trips.");
            return;
        }
        System.out.println("*List of completed trips ordered by ending date.");
        completedTrips.print();
        System.out.println("*end of list.");
        System.out.println();
    }

    /**
     * Display cost based off department
     */
    private void handlePC() {
        if (completedTrips.isEmpty()) {
            System.out.println("There is no archived trips.");
            return;
        }
        System.out.println("*Cost report by department*");
        completedTrips.printCharges();
        System.out.println("*end of list.");
        System.out.println();
    }


    /**
     * Testbed main for testing
     */
    public static void main(String[] args) {
        Frontend frontend = new Frontend();
        frontend.run();
    }
}