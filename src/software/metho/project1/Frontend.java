package software.metho.project1;
/**
 * *@author Brian V and Travis F;
 */
import software.metho.project1.collections.Fleet;
import software.metho.project1.collections.Reservation;
import software.metho.project1.collections.TripList;
import software.metho.project1.entities.*;


import java.util.Scanner;


/*
Main class, only library classes we can have are Scanner,
StringTokenizer, Calendar, DecimalFormat
Handle invalid cmds
System.in/system.out only in user interface, testbed main(), & print()
 */

/*
process cmd lines entered on terminal,
an instance of this class can process
single cmd, multi cmds including empty lines
* */
public class Frontend {
    private Fleet fleet;
    private TripList completedTrips;
    private Reservation bookings;



    public Frontend(Fleet fleet, Reservation bookings, TripList completedTrips) {
        this.fleet = fleet;
        this.bookings = bookings;
        this.completedTrips = completedTrips;
    }

    public Frontend() {
        this.fleet = new Fleet();
        this.bookings = new Reservation();
        this.completedTrips = new TripList();
    }

    public void run(){
            System.out.println("Vehicle Management System is running.");
            Scanner input = new Scanner(System.in); //user input

            boolean running = true;
            while (running) {

                String line = input.nextLine().trim(); //trim extra spaces
                if (line.isEmpty()) continue; //running with empty inputs

                String[] tokens = line.split("\\s+");
                String type = tokens[0]; //Splitting user input into separate strings

                switch (type) {
                    case "A":
                        if (tokens.length < 5) {
                            System.out.println("Invalid input!");
                            break;
                        }

                        String plate = tokens[1];
                        String dateString = tokens[2];
                        String makeString = tokens[3].toUpperCase();
                        int mileage;

                        try {
                            mileage = Integer.parseInt(tokens[4]);
                        } catch (NumberFormatException e) {
                            System.out.println(tokens[4] + " - invalid mileage.");
                            break;
                        }

                        Make make;
                        try {
                            make = Make.valueOf(makeString);
                        } catch (IllegalArgumentException e) {
                            System.out.println(tokens[3] + " - invalid make.");
                            break;
                        }

                        if (mileage <= 0) {
                            System.out.println(tokens[4] + " - invalid mileage.");
                            break;
                        }

                        Date dateObtained;
                        try {
                            dateObtained = Date.fromString(dateString);
                            if (!dateObtained.isValid()) {
                                System.out.println(dateString + " - invalid calendar date.");
                                break;
                            }
                            if (!dateObtained.isPastDate()) {
                                System.out.println(dateString + " - is today or a future date");
                                break;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(dateString + " - invalid calendar date.");
                            break;
                        }

                        Vehicle v = new Vehicle(plate, dateObtained, make, mileage);
                        if (fleet.contains(v)) {
                            System.out.println(v + " is already in the fleet.");
                            break;
                        }

                        fleet.add(v);
                        System.out.println(v + " has been added to the fleet.");
                        break;

                    case "D":
                        if (tokens.length < 2) {
                            System.out.println("Invalid input!");
                            break;
                        }

                        String plate2 = tokens[1];
                        Vehicle delVehicle = new Vehicle(plate2);

                        if (!fleet.contains(delVehicle)) {
                            System.out.println(plate2 + " is not in the fleet.");
                            break;
                        }

                        // cannot delete if there’s an active booking
                        if (bookings.hasBookingForVehicle(delVehicle)) {
                            System.out.println(delVehicle + " cannot be deleted, booked.");
                            break;
                        }
                        fleet.remove(delVehicle);
                        break;
                    case "B":
                        if (tokens.length < 5) {
                            System.out.println("Invalid input!");
                            break;
                        }

                        String startDateStr = tokens[1];
                        String endDateStr = tokens[2];
                        String bookPlate = tokens[3];
                        String empStr = tokens[4].toUpperCase();

                        // check vehicle exists
                        Vehicle vehicleToBook = fleet.findVehicle(bookPlate);
                        if (vehicleToBook == null) {
                            System.out.println(bookPlate + " is not in the fleet.");
                            break;
                        }

                        // parse dates
                        Date startDate, endDate;
                        try {
                            startDate = Date.fromString(startDateStr);
                            endDate = Date.fromString(endDateStr);

                            if (!startDate.isValid()) {
                                System.out.println(startDateStr + " is not a valid date.");
                                break;
                            }
                            if (!endDate.isValid()) {
                                System.out.println(endDateStr + " is not a valid date.");
                                break;
                            }
                            if (endDate.compareTo(startDate) < 0) {
                                System.out.println("Invalid booking period.");
                                break;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid date input.");
                            break;
                        }

                        // check employee
                        Employee emp;
                        try {
                            emp = Employee.valueOf(empStr);
                        } catch (IllegalArgumentException e) {
                            System.out.println(empStr + " is not a valid employee.");
                            break;
                        }

                        // create booking
                        Booking newBooking = new Booking(startDate, endDate, emp, vehicleToBook);
                        bookings.add(newBooking);

                        System.out.println("Booking completed: " + newBooking);
                        break;
                    case "C":
                        if (tokens.length < 3) {
                            System.out.println("Invalid input!");
                            break;
                        }

                        String cancelDateStr = tokens[1];
                        String cancelPlate = tokens[2];
                        Vehicle cancelVehicle = new Vehicle(cancelPlate);

                        Date cancelDate;
                        try {
                            cancelDate = Date.fromString(cancelDateStr);
                            if (!cancelDate.isValid()) {
                                System.out.println(cancelDateStr + " is not a valid date.");
                                break;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid date input.");
                            break;
                        }

                        Booking bookingToCancel = bookings.getBooking(cancelDate, cancelVehicle);
                        if (bookingToCancel == null) {
                            System.out.println("No booking found.");
                            break;
                        }

                        bookings.remove(bookingToCancel);
                        System.out.println("Booking canceled: " + bookingToCancel);
                        break;
                    case "R":
                        if (tokens.length < 4) {
                            System.out.println("Invalid input!");
                            break;
                        }

                        String returnDateString = tokens[1];
                        String returnPlate = tokens[2];
                        int newMileage;

                        try {
                            newMileage = Integer.parseInt(tokens[3]);
                        } catch (NumberFormatException e) {
                            System.out.println("Mileage invalid.");
                            break;
                        }

                        Date returnDate;
                        try {
                            returnDate = Date.fromString(returnDateString);
                            if (!returnDate.isValid()) {
                                System.out.println(returnDateString + " is not a valid date.");
                                break;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid date input.");
                            break;
                        }

                        Vehicle returnVehicle = fleet.findVehicle(returnPlate);
                        if (returnVehicle == null) {
                            System.out.println("Vehicle not found in fleet.");
                            break;
                        }

                        Booking bookingToReturn = bookings.getBooking(returnDate, returnVehicle);
                        if (bookingToReturn == null) {
                            System.out.println("No booking found.");
                            break;
                        }
                        if (!bookings.isEarliestEndingDate(bookingToReturn)) {
                            System.out.println("Return not allowed: earlier bookings exist.");
                            break;
                        }
                        if (newMileage <= bookingToReturn.getVehicle().getMileage()) {
                            System.out.println("Mileage invalid.");
                            break;
                        }

                        int beginMileage = bookingToReturn.getVehicle().getMileage();
                        bookingToReturn.getVehicle().setMileage(newMileage);
                        Trip completedTrip = new Trip(bookingToReturn, beginMileage, newMileage);
                        completedTrips.add(completedTrip);
                        bookings.remove(bookingToReturn);

                        System.out.println("Vehicle returned: " + bookingToReturn);
                        break;
                    case "PF":
                        if (fleet.isEmpty()) {
                            System.out.println("There is no vehicle in the fleet");
                        } else {
                        System.out.println("*List of vehicles in the fleet, ordered by make then date obtained.");
                        fleet.printByMake();
                        System.out.println("*end of list.");
                        System.out.println();
                        }
                        break;
                    case "PR":
                        if (bookings.isEmpty()) {
                            System.out.println("There is no booking record");
                        } else {
                        System.out.println("*List of reservations ordered by license plate then beginning date.");
                        bookings.printByVehicle();
                        System.out.println("*end of list.");
                        System.out.println();
                        }
                        break;
                    case "PD":
                        if (bookings.isEmpty()) {
                            System.out.println("There is no booking record");
                        } else {
                        System.out.println("*List of reservations ordered by Department then employee.");
                        bookings.printByDept();
                        System.out.println("*end of list.");
                        System.out.println();
                        }
                        break;
                    case "PT":
                        if (completedTrips.isEmpty()) {
                            System.out.println("There is no archived trips");
                        } else {
                        System.out.println("*List of completed trips ordered by ending date.");
                        completedTrips.print();
                        System.out.println("*end of list.");
                        System.out.println();
                        }
                        break;
                    case "Q":
                        running = false;
                        break;
                    default:
                        System.out.println(type + " - invalid command!");
                }
            }

        System.out.println("Vehicle Management System is terminated.");
        input.close();

        //Potential helpers we might need below
    }
    /**
     * Testbed main for testing
     */
    public static void main(String[] args) {
        Frontend frontend = new Frontend();
        frontend.run();
    }
}
