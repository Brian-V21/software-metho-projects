package software.metho.project1.entities;
//Design test cases for compareTO() method...
//One test case each--> return -1, return 1, & return 0

public class Vehicle implements Comparable<Vehicle> {
    private String plate; //License plate number
    private Date obtained; // date class, when got
    private Make make; // Make = enum class
    private int mileage; // current reading of odometer

    public Vehicle() {
        //default constructor
    }

    public Vehicle(String plate, Date obtained, Make make, int mileage) {
        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage; //odometer reading
    }
    //constructor for vehicle with plate only
    public Vehicle(String plate) {
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    public Make getMake() {
        return make;
    }

    public Date getObtained() {
        return obtained;
    }

    public int getMileage(){
        return mileage;
    }

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