package software.metho.project1.entities;

/**
 * *@author Brian V;
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
     *
     * @return license plate of vehicle
     */
    public String getPlate() {
        return plate;
    }

    /**
     *
     * @return make of vehicle
     */
    public Make getMake() {
        return make;
    }

    /**
     *
     * @return date vehicle was obtained
     */
    public Date getObtained() {
        return obtained;
    }

    /**
     *
     * @return miles on vehicle
     */
    public int getMileage(){
        return mileage;
    }

    /**
     *
     * @param mileage set miles of vehicle to another int
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Override
    public int compareTo(Vehicle vehicle) {
        if (this.plate.compareTo(vehicle.plate) > 0)
            return 1;
        if (this.plate.compareTo(vehicle.plate) < 0)
            return -1;
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) obj;
            return this.plate.equals(vehicle.plate);
        }
        return false;
    }

    @Override
    public String toString() {
        return plate + ":" + make + ":" + obtained + " [mileage:" + mileage + "]";
    }

    //Testbed for 3 instances of compareTo with -1, 0, & 1
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