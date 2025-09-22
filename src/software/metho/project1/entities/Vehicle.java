package software.metho.project1.entities;
//Design test cases for compareTO() method...
//One test case each--> return -1, return 1, & return 0

public class Vehicle implements Comparable<Vehicle> {
    private String plate; //License plate number
    private Date obtained; // date class, when got
    private Make make; // Make = enum class
    private int mileage; // current reading of odometer


    public Vehicle() {

    }

    public Vehicle(String plate, Date obtained, Make make, int mileage) {
        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage;
    }


    @Override
    public int compareTo(Vehicle vehicle) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {

        return True;
    }

    @Override
    public String toString() {
        return plate + ":" + make + ":" + obtained + " [mileage:" + mileage + "]";
    }

    //Testbed
    public static void main (String[]args){


    }
}