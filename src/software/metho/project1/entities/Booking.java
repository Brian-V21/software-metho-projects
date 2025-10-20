package software.metho.project1.entities;
import software.metho.project1.util.Date;

/**
 * *@author Brian V and Travis F;
 */
public class Booking {

    private Date begin;
    private Date end;
    private Employee employee;
    private Vehicle vehicle;

    private final Campus pickup;
    private Campus dropoff;

    /**
     * Constructs a Booking object
     * @param begin     Start date of the Booking
     * @param end       End date of the Booking
     * @param employee  The employee who made the booking
     * @param vehicle   The vehicle used
     * @param dropoff   The intended drop-off Campus
     */
    public Booking (Date begin, Date end, Employee employee, Vehicle vehicle, Campus dropoff) {
        this.begin = begin;
        this.end = end;
        this.employee = employee;
        this.vehicle = vehicle;
        this.pickup = vehicle.getCampus();
        this.dropoff = dropoff;
    }

    /**
     * Constructs a Booking object with only an end date and vehicle.
     * @param end End date of the booking
     * @param v Vehicle associated with the booking
     */
    public Booking (Date end, Vehicle v) {
        this.begin = null;
        this.end = end;
        this.vehicle = v;
        this.employee = null;
        this.pickup = null;
        this.dropoff = null;
    }


    /**
     * Returns the start date
     * @return the beginning date
     */
    public Date getBegin() {
        return begin;
    }

    /**
     * Returns the end date
     * @return the end date
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Returns the employee of this booking
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Returns the vehicle associated with this booking
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * @return the pickup Campus location for booking
     */
    public Campus getPickup() {
        return pickup;
    }

    /**
     * @return the drop off Campus location for booking
     */
    public Campus getDropoff() {
        return dropoff;
    }

    /**
     *
     * @param dropoff the campus drop off location that is being altered
     */
    public void setDropoff(Campus dropoff) {
        this.dropoff = dropoff;
    }

    /**
     * Compares a booking to another object.
     * Two bookings are considered equal if they involve the same vehicle and have the same start and end dates and the same employee
     * 
     * @param obj Object to compare
     * @return true if the object is a booking with the same vehicle and dates, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Booking))
            return false;
        Booking other = (Booking) obj;
        
        boolean sameVehicle = (this.vehicle == null && other.vehicle == null) || (this.vehicle != null && this.vehicle.equals(other.vehicle));
        boolean sameBegin = (this.begin == null && other.begin == null) || (this.begin != null && other.begin != null && this.begin.equals(other.begin));
        boolean sameEnd = (this.end == null && other.end == null) || (this.end != null && other.end != null && this.end.equals(other.end));
        boolean sameEmp = (this.employee == null && other.employee == null) || (this.employee != null && this.employee.equals(other.employee));
        
        return sameVehicle && sameBegin && sameEnd && sameEmp;
    }

    /**
     * Returns a string
     * 
     * @return String describing the vehicle, booking period, and employee.
     */
    @Override
    public String toString() {
        String plateMake = vehicle.getPlate() + ":" + vehicle.getMake().name();
        String pickupStr = pickup == null ? "" : (" [" + formatCampus(pickup) + ":" + pickup.getCity() + "]");
        String period = " " + (begin == null ? "" : begin.toString()) + " ~ " + (end == null ? "" : end.toString());
        String drop = dropoff == null ? "" : (" [drop off:" + formatCampus(dropoff) + "]");
        String emp = employee == null ? "" : (" [" + employee.name() + "]");
        
        return plateMake + pickupStr + period + drop + emp;
    }
    /**
     * @param c Campus object
     * @return a formatted string for Campus for Booking
     */
    private static String formatCampus(Campus c) {
        String s = c.name().toLowerCase();
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
