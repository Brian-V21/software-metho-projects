package software.metho.project1.entities;

public class Sedan extends Vehicle {
    private static final double PER_MILE = 1.79;
    private static final double SUR_PER_MILE = 0.25;
    private static final double SUR_MAX = 32.99;
    
    public Sedan(String plate, Date obtained, Make make, int mileage, Campus campus) {
        super(plate, obtained, make, mileage, campus);
    }

    @Override
    protected String typeName() {
        return "sedan";
    }

    @Override
    public double charge(int mileageUsed) {
        return mileageUsed * PER_MILE;
    }

    @Override
    public double surcharge(int mileageUsed, boolean surcharge) {
        if (!surcharge)
            return 0;
        double raw = mileageUsed * SUR_PER_MILE;
        return raw > SUR_MAX ? SUR_MAX : raw;
    }
}
