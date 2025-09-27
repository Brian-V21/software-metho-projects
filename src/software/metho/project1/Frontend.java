package software.metho.project1;

import software.metho.project1.collections.Fleet;
import software.metho.project1.collections.TripList;
import software.metho.project1.entities.Vehicle;
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
        private TripList bookings;
        private TripList completedTrips;

        public Frontend(Fleet fleet, TripList bookings, TripList completedTrips) {
            this.fleet = fleet;
            this.bookings = bookings;
            this.completedTrips = completedTrips;
        }

    public Frontend() {

    }

    public void run(){
            System.out.print("Vehicle Management System is running.");
            Scanner input = new Scanner(System.in); //user input

            boolean running = true;
            while (running) {
                String line = input.nextLine().trim(); //trim extra spaces
                if (line.isEmpty()) continue; //running with empty inputs

                String[] tokens = line.split("/");
                String type = tokens[0];

                switch (type) {
                    case "A":
                        //code add vehicle
                        break;
                    case "D":
                        //code remove vehicle
                        break;
                    case "B":
                        //code book a vehicle
                        break;
                    case "C":
                        //code for cancel existing booking
                        break;
                    case "R":
                        //return the vehicle
                        break;
                /*P cmds-> display info, sorted w/ diff keys, if keys
                for sorting are same, order of display for those lines
                don't matter*/
                    case "PF":
                        //display list of vehicles, order by make, then date obtained
                        break;
                    case "PR":
                        //Display List of bookings, ordered by license plate, then by beginning dates
                        break;
                    case "PD":
                        //Display list of bookings, ordered by department, then by employee
                        break;
                    case "PT":
                        //Display list of completed trips, sorted by ending date
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
}
