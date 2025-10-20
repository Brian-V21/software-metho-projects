package software.metho.project1.collections;
import software.metho.project1.entities.*;
import software.metho.project1.util.Date;
import software.metho.project1.util.List;

import java.util.Scanner;

/**
 * *@author Brian V and Travis F;
 */

public class Fleet extends List<Vehicle> {

    /**
     * Constructor for an empty Fleet
     */
    public Fleet() {
       super();
    }

    /**
     * Add vehicle to fleet collection
     * If the vehicle already exists, nothing happens
     * @param vehicle Vehicle Object
     */
    public void add(Vehicle vehicle) {
        if (vehicle == null || contains(vehicle)) return;
        super.add(vehicle);
    }

    /**
     * Remove vehicle from fleet collection
     * The vehicle at the removed index is replaces with the last vehicle
     * @param vehicle Vehicle Object
     */
    public void remove(Vehicle vehicle) {
        if (!contains(vehicle)) {
            System.out.println(vehicle + " not found in the fleet");
        }
        super.remove(vehicle);
        System.out.println(vehicle + " has been removed from the fleet.");
    }

    /**
     * Print collection by make then date
     */
    public void printByMake() {
        int n = size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                Vehicle minVehicle = get(minIndex);
                Vehicle currVehicle = get(j);

                int cmpCity = minVehicle.getCampus().getCity().compareTo(currVehicle.getCampus().getCity());
                if (cmpCity > 0) {
                    minIndex = j;
                } else if (cmpCity == 0) {
                    int cmpMake = minVehicle.getMake().compareTo(currVehicle.getMake());
                    if (cmpMake > 0) {
                        minIndex = j;
                    } else if (cmpMake == 0) {
                        if (minVehicle.getObtained().compareTo(currVehicle.getObtained()) > 0) {
                            minIndex = j;
                        }
                    }
                }
            }
            //if not in correct spot, swap vehicle indexes
            if (minIndex != i) {
                Vehicle temp = get(i);
                set(i, get(minIndex));
                set(minIndex, temp);
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println(get(i));
        }
    }

    /**
     * Finds a vehicle in the fleet by its license plate.
     * @param plate license plate of vehicle
     * @return the vehicle of index in fleet or null if not found
     */
    public Vehicle findVehicle(String plate) {
        for (Vehicle v: this) {
            if (v.getPlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Getting input from a files and converting to Vehicle Type
     * @param scanner input from user
     * @return number of vehicles loaded
     */
    public int load(Scanner scanner) {
        int count = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            
            Scanner lineScanner = null;
            try {
                lineScanner = new Scanner(line);
                String plate = lineScanner.next();
                String obtainedStr = lineScanner.next();
                String makeStr = lineScanner.next();
                int mileage = lineScanner.nextInt();
                String campusStr = lineScanner.next();

                if (!Vehicle.isValidPlate(plate)) {
                    continue;
                }

                Date obtained = Date.fromString(obtainedStr);
                Make make = Make.valueOf(makeStr.toUpperCase());
                Campus campus = Campus.fromString(campusStr);

                Vehicle vehicle;
                char typeChar = Character.toUpperCase(plate.charAt(plate.length() -1));
                switch (typeChar) {
                    case 'S':
                        vehicle = new Sedan(plate, obtained, make, mileage, campus);
                        break;
                    case 'D':
                        vehicle = new Utility(plate, obtained, make, mileage, campus);
                        break;
                    case 'X':
                        vehicle = new Truck(plate, obtained, make, mileage, campus);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid vehicle type in plate: " + plate);
                 }
                if (!contains(vehicle)) {
                    super.add(vehicle);
                    count++;
                }      
            } catch (Exception e) {
                System.out.println("Skipping invalid input: " + line);
            } finally {
                if (lineScanner != null)
                    lineScanner.close();
            }
        }
        return count;
    }
}

