package software.metho.project1;

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
    private TripList bookings;

    public Frontend(Fleet fleet, TripList bookings, TripList completedTrips) {
        this.fleet = fleet;
        this.bookings = bookings;
        this.completedTrips = completedTrips;
    }

    public Frontend() {
        this.fleet = new Fleet();
        this.bookings = new TripList();
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
                            System.out.println("Invalid input for adding vehicle");
                            break;
                        }
                        //separating tokens to param of vehicles
                        String plate = tokens[1];
                        String dateString = tokens[2];
                        Date dateObtained;
                        String makeString = tokens[3].toUpperCase();
                        int mileage = Integer.parseInt(tokens[4]);

                        Make make;
                        try {
                            make = Make.valueOf(makeString);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid input for make");
                            break;
                        }

                        if (mileage <= 0) {
                            System.out.println("Invalid input for mileage");
                            break;
                        }

                        try {
                            dateObtained = Date.fromString(dateString);
                            if (!dateObtained.isValid() || !dateObtained.isPastDate()) {
                                System.out.println("Invalid input for date");
                                break;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid input for date");
                            break;
                        }

                        Vehicle v = new Vehicle( plate, dateObtained, make, mileage);
                        if (fleet.contains(v)) {
                            System.out.println("Vehicle already exists in fleet");
                            break;
                        }

                        fleet.add(v);
                        System.out.println("Vehicle added: " + v);
                        break;
                    case "D":
                        if (tokens.length < 2) {
                            System.out.println("Invalid input for adding vehicle");
                            break;
                        }

                        String plate2 = tokens[1];
                        Vehicle delVehicle = new Vehicle(plate2);

                        if (!fleet.contains(delVehicle)) {
                            System.out.println("Vehicle not found in fleet");
                            break;
                        }
                        //Need hasBookingForVehicle helper method in Booking
                        if (bookings.hasBookingForVehicle(delVehicle)) {
                            System.out.println("Cannot delete vehicle with existing booking");
                        }

                        fleet.remove(delVehicle);
                        System.out.println("Vehicle removed: " + delVehicle);
                        //code remove vehicle
                        break;
                    case "B":
                        //code book a vehicle
                        if (tokens.length < 6) {
                            System.out.println("Invalid input for booking");
                            break;
                        }

                        String bookPlate = tokens[1];
                        String startDateStr = tokens[2];
                        String endDateStr = tokens[3];
                        String empStr = tokens[4].toUpperCase();

                        Vehicle vehicleToBook = fleet.findVehicle(bookPlate);
                        if (vehicleToBook == null) {
                            System.out.println("Vehicle not found in fleet");
                            break;
                        }

                        Date startDate;
                        Date endDate;
                        try {
                            startDate = Date.fromString(startDateStr);
                            endDate = Date.fromString(endDateStr);
                            if (!startDate.isValid() || !endDate.isValid() || endDate.compareTo(startDate) <= 0) {
                                System.out.println("Invalid booking Dates");
                                break;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("invalud input for booking dates");
                            break;
                        }
                        
                        Employee emp;
                        try {
                            emp = Employee.valueOf(empStr);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid employee");
                            break;
                        }
                        
                        Booking newBooking = new Booking(startDate, endDate, emp, vehicleToBook);
                        bookings.add(new Trip(newBooking, vehicleToBook.getMileage(), vehicleToBook.getMileage()));
                        System.out.println("Booking added for vehicle: " + vehicleToBook);
                        
                        break;
                    
                        
                    case "C":
                        //code for cancel existing booking
                        if (tokens.length < 3) {
                            System.out.println("Invalid input for canceling booking");
                        break;
                        }

                        String cancelDateStr = tokens[1];
                        String cancelPlate = tokens[2];
                        Vehicle cancelVehicle = new Vehicle(cancelPlate);
                        
                        Date cancelDate;
                        try {
                            cancelDate = Date.fromString(cancelDateStr);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid date for canceling booking");
                            break;
                        }
                        
                        Booking bookingToCancel = bookings.getBooking(cancelDate, cancelVehicle);
                        if (bookingToCancel == null) {
                            System.out.println("No booking found to cancel");
                            break;
                        }

                        bookings.remove(bookingToCancel);
                        System.out.println("Booking canceled: " + bookingToCancel);
                        break;

                    case "R":
                        if (tokens.length < 4) {
                            System.out.println("Invalid input for returning vehicle");
                            break;
                        }

                        String returnDateString = tokens[1];
                        String returnPlate = tokens[2];
                        int newMileage;

                        try {
                            newMileage = Integer.parseInt(tokens[3]);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input for return mileage");
                            break;
                        }

                        Vehicle returnVehicle = new Vehicle(returnPlate);
                        Date dateReturned;
                        try {
                            dateReturned = Date.fromString(returnDateString);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid input for date return");
                            break;
                        }

                        Booking bookingToReturn = bookings.getBooking(dateReturned, returnVehicle);
                        if (bookingToReturn == null) {
                            System.out.println("No matching booking found for return");
                            break;
                        }
                        if (!bookings.isEarliestEndingDate(bookingToReturn)) {
                            System.out.println("Ending date is not the earliest ending date");
                            break;
                        }

                        if (newMileage <= bookingToReturn.getVehicle().getMileage()) {
                            System.out.println("Not valid milage for return");
                            break;
                        }

                        int beginMileage = bookingToReturn.getVehicle().getMileage();
                        bookingToReturn.getVehicle().setMileage(newMileage);
                        Trip completedTrip = new Trip(bookingToReturn, beginMileage, newMileage);
                        completedTrips.add(completedTrip);
                        bookings.remove(bookingToReturn);

                        System.out.println("Vehicle returned: " + bookingToReturn);
                        //return the vehicle
                        break;
                /*P cmds-> display info, sorted w/ diff keys, if keys
                for sorting are same, order of display for those lines
                don't matter*/
                    case "PF":
                        fleet.printByMake();
                        break;
                    case "PR":
                        bookings.printByVehicle();
                        break;
                    case "PD":
                        bookings.printByDept();
                        break;
                    case "PT":
                        completedTrips.print();
                        break;
                    case "Q":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid command!");
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
