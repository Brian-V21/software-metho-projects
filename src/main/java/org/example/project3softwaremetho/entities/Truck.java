package org.example.project3softwaremetho.entities;
import org.example.project3softwaremetho.util.Date;
/**
 * *@author Brian V and Travis F;
 */

public class Truck extends Vehicle {
    private static final double PER_MILE = 2.99;
    private static final double SUR_FLAT = 39.99;

    /**
     * Constructor for Truck (inherit Vehicle plate, obtained, make, mileage, and campus)
     * @param plate license plate for vehicle
     * @param obtained date vehicle was obtained
     * @param make the Make of the vehicle
     * @param mileage the amount of miles on the vehicle
     * @param campus the location of where the vehicle is
     */
    public Truck(String plate, Date obtained, Make make, int mileage, Campus campus) {
        super(plate, obtained, make, mileage, campus);
    }

    /**
     * Used to output type "Sedan"
     * @return the name of the vehicle type in String
     */
    @Override
    protected String typeName() {
        return "truck";
    }

    /**
     * Different amount is charged based on vehicle type (PER_MILE)
     * @param mileageUsed amount of miles used
     * @return the amount charged based on vehicle type Truck
     */
    @Override
    public double charge(int mileageUsed) {
        return mileageUsed * PER_MILE;
    }

    /**
     * extra charge after the initial charge based on amount of miles over limit
     * @param mileageUsed amount of miles used
     * @param surcharge whether there is a surcharge or not for vehicle
     * @return the surcharge based on vehicle type
     */
    @Override
    public double surcharge(int mileageUsed, boolean surcharge) {
        return surcharge ? SUR_FLAT : 0.0;
    }
}
