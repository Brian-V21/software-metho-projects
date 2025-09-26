package software.metho.project1.collections;
import software.metho.project1.entities.Vehicle;

public class Fleet {
    private static final int CAPACITY = 4;
    private static final int NOT_FOUND = -1;
    private Vehicle[] fleet;
    private int size;

    public Fleet() {
        this.fleet = new Vehicle[CAPACITY];
        this.size = 0;
    }

    public Fleet(Vehicle[] fleet, int size) {
        this.fleet = fleet;
        this.size = size;
    }

    //Search for a vehicle in fleet and return index or NOT_FOUND
    private int find(Vehicle vehicle) {
        for (int i = 0; i < size; i++) {
            if (fleet[i].equals(vehicle)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    //Resize CAPACITY of FLEET
    private void grow() {
        if (this.size == this.fleet.length) {
            Vehicle[] newFleet = new Vehicle[this.fleet.length + 4];
            for (int i = 0; i < size; i++) {
                newFleet[i] = fleet[i];
            }
            fleet = newFleet;
        }
    }

    private void add(Vehicle vehicle) {
        if (contains(vehicle)) {
            return;
        }
        if (size == this.fleet.length) {
            grow();
        }
        fleet[size] = vehicle;
        size++;

    }

    public void remove(Vehicle vehicle) {
        int index = find(vehicle);
        if (index == NOT_FOUND) return;

        for (int i = index; i < size - 1; i++) {
                fleet[i] = fleet[i + 1];
        }
        fleet[size - 1] = null;
        size--;
    }

    public boolean contains(Vehicle vehicle) {
        return find(vehicle) != NOT_FOUND;
    }

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
}
