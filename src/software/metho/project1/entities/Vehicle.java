package software.metho.project1.entities;

/**
 * *@author Brian V and Travis F;
 */
public class Vehicle implements Comparable<Vehicle> {
    private String plate; //License plate number
    private Date obtained; // date class, when got
    private Make make; // Make = enum class
    private int mileage; // current reading of odometer

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
    public Vehicle(String plate, Date obtained, Make make, int mileage) {
        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage; //odometer reading
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
     * Updates the vehicles mileage
     * @param mileage set miles of vehicle to another int
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /**
     * Compares this vehicle to another by license plate.
     * Returns a negative value if this plate comes before the other,
     * a positive value if it comes after, and a 0 if they are the same.
     * 
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
     * 
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
     * Returns a string representation of the vehicle.
     * @return a formatted string of vehicle details.
     */
    @Override
    public String toString() {
        return plate + ":" + make + ":" + obtained + " [mileage:" + mileage + "]";
    }

    //Testbed main() for 3 instances of compareTo with -1, 0, & 1
    public static void main (String[]args){
        Date d1 = new Date(2021, 6, 21);
        Date d2 = new Date(2024, 2, 3);

        Vehicle v1 = new Vehicle("QWE123", d1, Make.FORD, 521);
        Vehicle v2 = new Vehicle("ASD312", d2, Make.TOYOTA, 3312);
        Vehicle v3 = new Vehicle("QWE123", d1, Make.HONDA, 9123);

        //Test -1
        System.out.println("v1 comparing to v2: " + v1.compareTo(v2));
        //Test 1
        System.out.println("v2 comparing to v1: " + v2.compareTo(v1));
        //Test 0
        System.out.println("v1 comparing to v3: " + v1.compareTo(v3));

    }

}