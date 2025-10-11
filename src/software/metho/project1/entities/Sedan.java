package software.metho.project1.entities;

public class Sedan extends Vehicle {
    @Override
    public double charge(int mileageUsed) {
        return 0;
    }

    @Override
    public double surcharge(int mileageUsed, boolean surcharge) {
        return 0;
    }
}
