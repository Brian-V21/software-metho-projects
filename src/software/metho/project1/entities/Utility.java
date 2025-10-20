package software.metho.project1.entities;

import software.metho.project1.util.Date;

/**
 * *@author Brian V and Travis F;
 */

public class Utility extends Vehicle {
    private static final double PER_MILE = 1.99;
    private static final double SUR_PER_MILE = 0.30;
    private static final double SUR_MAX = 35.99;

    /**
     * Constructor for Utility (inherit Vehicle plate, obtained, make, mileage, and campus)
     * @param plate license plate for vehicle
     * @param obtained date vehicle was obtained
     * @param make the Make of the vehicle
     * @param mileage the amount of miles on the vehicle
     * @param campus the location of where the vehicle is
     */
    public Utility(String plate, Date obtained, Make make, int mileage, Campus campus) {
        super(plate, obtained, make, mileage, campus);
    }

    /**
     * Used to output type "Sedan"
     * @return the name of the vehicle type in String
     */
    @Override
    protected String typeName() {
        return "utility";
    }

    /**
     * Different amount is charged based on vehicle type (PER_MILE)
     * @param mileageUsed amount of miles used
     * @return the amount charged based on type Utility
     */
    @Override
    public double charge(int mileageUsed) {
        return mileageUsed * PER_MILE;
    }

    /**
     * extra charge after the initial charge based on amount of miles over limit
     * @param mileageUsed amount of miles used
     * @param surcharge true if there is a surcharge or false otherwise
     * @return the surcharge based on vehicle type
     */
    @Override
    public double surcharge(int mileageUsed, boolean surcharge) {
        if (!surcharge)
            return 0;
        double raw = mileageUsed * SUR_PER_MILE;
        return raw > SUR_MAX ? SUR_MAX : raw;
    }
}
