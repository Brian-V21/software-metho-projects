package software.metho.project1.collections;
import software.metho.project1.entities.Vehicle;
/**
 * *@author Brian V and Travis F;
 */

public class Fleet {
    private static final int CAPACITY = 4;
    private static final int NOT_FOUND = -1;
    private Vehicle[] fleet;
    private int size;

    /**
     * Constructor for Fleet with vehicle and size of collection in fleet
     */
    public Fleet() {
        this.fleet = new Vehicle[CAPACITY];
        this.size = 0;
    }

    /**
     * Constructs a Fleet with a given array of vehicles and size.
     * @param fleet collection of vehicles in fleet
     * @param size number of vehicles in fleet
     */
    public Fleet(Vehicle[] fleet, int size) {
        this.fleet = fleet;
        this.size = size;
    }

    /**
     * Finds the index of a vehicle in the fleet.
     * @param vehicle Vehicle Object
     * @return Search for a vehicle in fleet and return index or NOT_FOUND
     */
    private int find(Vehicle vehicle) {
        for (int i = 0; i < size; i++) {
            if (fleet[i].equals(vehicle)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Resize CAPACITY of FLEET
     */
    private void grow() {
        if (this.size == this.fleet.length) {
            Vehicle[] newFleet = new Vehicle[this.fleet.length + 4];
            for (int i = 0; i < size; i++) {
                newFleet[i] = fleet[i];
            }
            fleet = newFleet;
        }
    }

    /**
     * Add vehicle to fleet collection
     * If the vehicle already exists, nothing happens
     * @param vehicle Vehicle Object
     */
    public void add(Vehicle vehicle) {
        if (contains(vehicle)) {
            return;
        }
        if (size == this.fleet.length) {
            grow();
        }
        fleet[size] = vehicle;
        size++;

    }

    /**
     * Remove vehicle from fleet collection
     * The vehicle at the removed index is replaces with the last vehicle
     * @param vehicle Vehicle Object
     */
    public void remove(Vehicle vehicle) {
        int idx = find(vehicle);
        if (idx == NOT_FOUND) {
            System.out.println("Vehicle not found in fleet");
            return;
        }

        Vehicle removed = fleet[idx]; // save before overwriting

        fleet[idx] = fleet[size - 1];
        fleet[size - 1] = null;
        size--;

        System.out.println(removed + " has been removed from the fleet.");
    }

    /**
     * Checks if a given vehicle exists in the fleet.
     * @param vehicle Vehicle Object
     * @return if vehicle is contained in fleet
     */
    public boolean contains(Vehicle vehicle) {
        return find(vehicle) != NOT_FOUND;
    }

    /**
     * Print collection by make then date
     */
    public void printByMake() {

        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                Vehicle minVehicle = fleet[minIndex];
                Vehicle currVehicle = fleet[j];

                if (currVehicle.getMake().compareTo(minVehicle.getMake()) < 0) {
                    minIndex = j;
                } else if (currVehicle.getMake().compareTo(minVehicle.getMake()) == 0 && currVehicle.getObtained().compareTo(minVehicle.getObtained()) < 0) {
                    minIndex = j;
                }
            }
            //if not in correct spot, swap vehicle indexes
            if (minIndex != i) {
                Vehicle temp = fleet[i];
                fleet[i] = fleet[minIndex];
                fleet[minIndex] = temp;
            }
        }
        for (int i = 0; i < size; i++) {
            System.out.println(fleet[i]);
        }
    }

    /**
     * Finds a vehicle in the fleet by its license plate.
     * @param plate license plate of vehicle
     * @return the vehicle of index in fleet or null if not found
     */
    public Vehicle findVehicle(String plate) {
        for (int i = 0; i < size; i++) {
            if (fleet[i].getPlate().equalsIgnoreCase(plate)) {
                return fleet[i];
            }
        }
        return null;
    }

    /**
     * Checks if the fleet is empty
     * @return true if there are no vehicles in the fleet, otherwise false
     */
    public boolean isEmpty() {
        return size == 0;
    }
}
