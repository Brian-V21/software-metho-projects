package software.metho.project1.entities;

import software.metho.project1.util.Date;

public class Truck extends Vehicle {
    private static final double PER_MILE = 2.99;
    private static final double SUR_FLAT = 39.99;

    public Truck(String plate, Date obtained, Make make, int mileage, Campus campus) {
        super(plate, obtained, make, mileage, campus);
    }
    
    @Override
    protected String typeName() {
        return "truck";
    }

    @Override
    public double charge(int mileageUsed) {
        return mileageUsed * PER_MILE;
    }

    @Override
    public double surcharge(int mileageUsed, boolean surcharge) {
        return surcharge ? SUR_FLAT : 0.0;
    }
}
