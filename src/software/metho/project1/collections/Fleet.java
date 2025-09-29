package software.metho.project1.collections;
import software.metho.project1.entities.Vehicle;
/**
 * *@author Brian V;
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
     *
     * @param fleet collection of vehicles in fleet
     * @param size number of vehicles in fleet
     */
    public Fleet(Vehicle[] fleet, int size) {
        this.fleet = fleet;
        this.size = size;
    }

    /**
     *
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
     * add vehicle to fleet collection
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
     * remove vehicle from fleet collection
     * @param vehicle Vehicle Object
     */
    public void remove(Vehicle vehicle) {
        int index = find(vehicle);
        if (index == NOT_FOUND) return;

        for (int i = index; i < size - 1; i++) {
                fleet[i] = fleet[i + 1];
        }
        fleet[size - 1] = null;
        size--;
    }

    /**
     *
     * @param vehicle Vehicle Object
     * @return if vehicle is contained in fleet
     */
    public boolean contains(Vehicle vehicle) {
        return find(vehicle) != NOT_FOUND;
    }

    /**
     * print collection by make then date
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
     *
     * @param plate license plate of vehicle
     * @return the vehicle of index in fleet
     */
    public Vehicle findVehicle(String plate) {
        for (int i = 0; i < size; i++) {
            if (fleet[i].getPlate().equalsIgnoreCase(plate)) {
                return fleet[i];
            }
        }
        return null;
    }
}
