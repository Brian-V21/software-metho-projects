package org.example.project3softwaremetho.entities;
import org.example.project3softwaremetho.util.Date;

/**
 * *@author Brian V and Travis F;
 */
public abstract class Vehicle implements Comparable<Vehicle> {
    protected String plate; //License plate number
    protected Date obtained; // date class, when got
    protected Make make; // Make = enum class
    protected int mileage; // current reading of odometer
    protected Campus campus;

    /**
     * Default constructor for Vehicle
     */
    public Vehicle() {
        //default constructor
    }

    /**
     * Constructor for Vehicle
     * @param plate license plate of vehicle
     * @param obtained date vehicle was obtained
     * @param make make of the vehicle
     * @param mileage amount of miles on the vehicle
     */
    public Vehicle(String plate, Date obtained, Make make, int mileage, Campus campus) {
        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage; //odometer reading
        this.campus = campus;
    }

    /**
     * Constrcutor with only plate for Vehicle
     * @param plate license plate of vehicle
     */
    public Vehicle(String plate) {
        this.plate = plate;
    }

    /**
     * Gets the license plate of the vehcle.
     * @return license plate of vehicle
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Gets the make of the vehicle.
     * @return make of vehicle
     */
    public Make getMake() {
        return make;
    }

    /**
     * Gets the acquisition date of the vehicle.
     * @return date vehicle was obtained
     */
    public Date getObtained() {
        return obtained;
    }

    /**
     * Gets the current mileage of the vehicle.
     * @return miles on vehicle
     */
    public int getMileage(){
        return mileage;
    }

    /**
     * Get Campus of the vehicle
     * @return Campus location of vehicle
     */
    public Campus getCampus() {
        return campus;
    }

    /**
     * Sets the campus of the vehicle
     * @param c set campus to a new Campus
     */
    public void setCampus(Campus c) {
        this.campus = c;
    }

    /**
     * Updates the vehicles mileage
     * @param mileage set miles of vehicle to another int
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /**
     * @param mileageUsed amount of miles used
     * @return charge per mile
     */
    public abstract double charge(int mileageUsed);

    /**
     * @param mileageUsed amount of miles used
     * @param surcharge whether there is a surcharge or not for vehicle
     * @return charge and whether surcharge is implemented
     */
    public abstract double surcharge(int mileageUsed, boolean surcharge); 

    /**
     * @return lowercase type name of ("sedan", "utility", "truck") for toString formatting
     */
    protected abstract String typeName();

    /**
     * Compares this vehicle to another by license plate.
     * Returns a negative value if this plate comes before the other,
     * a positive value if it comes after, and a 0 if they are the same.
     * @param vehicle the vehicle to compare with
     * @return -1, 0, or 1 depending on the order of the license plates.
     */
    @Override
    public int compareTo(Vehicle vehicle) {
        if (this.plate.compareTo(vehicle.plate) > 0)
            return 1;
        if (this.plate.compareTo(vehicle.plate) < 0)
            return -1;
        return 0;
    }

    /**
     * Determines if this vehicle is equal to another object.
     * Vehicles are considered equal if they have the same license plate,
     * regardless of make, date obtained, or mileage.
     * @param obj the object to compare
     * @return true if the obj is a Vehicle with the same plate, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) obj;
            return this.plate.equals(vehicle.plate);
        }
        return false;
    }

    /**
     *
     * @return 0 if plate == null else plate.hashcode()
     */
    @Override
    public int hashCode() {
        return plate == null ? 0 : plate.hashCode();
    }


    /**
     * Returns a string representation of the vehicle.
     * @return a formatted string of vehicle details.
     */
    @Override
    public String toString() {
        return plate + "[" + make + ":" + typeName() + "] " + obtained + " " +  " [mileage:" + mileage + "] " + "[" + format(campus.name()) + ":" + campus.getCity() + "]";
    }

    /**
     * Title helper function for enum names (EX. BUSCH -> Busch)
     */
    private static String format(String enumName) {
        String lower = enumName.toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    /**
     * To check if plate is a valid input
     * @param plate String license plate of vehicle
     * @return if the String for plate is a valid plate
     */
    public static boolean isValidPlate(String plate) {
        if (plate == null || plate.length() != 6) {
            return false;
    }
        for (int i = 0; i < 5; i++) {
            if (!Character.isDigit(plate.charAt(i))) {
                return false;
            }
        }
        char t = plate.charAt(5);
        return t == 'S' || t == 'D' || t == 'X';
    }

    /**
     * based on 3 distinct char, S, D and X
     * @param plate the license plate of vehicle
     * @return the last character of plate to get vehicle type (s=sedan, x=truck, or d=utility)
     */
    public static char typeCode(String plate){
        return plate.charAt(5);
    }

    
}