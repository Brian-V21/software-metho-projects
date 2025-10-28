package org.example.project3softwaremetho.entities;
/**
 * *@author Brian V and Travis F;
 */
public class Booking {

    private Date begin;
    private Date end;
    private Employee employee;
    private Vehicle vehicle;

    /**
     * Constructs a Booking object
     * 
     * @param begin     Start date of the Booking
     * @param end       End date of the Booking
     * @param employee  The employee who made the booking
     * @param vehicle   The vehichle used
     */
    public Booking (Date begin, Date end, Employee employee, Vehicle vehicle) {
        this.begin = begin;
        this.end = end;
        this.employee = employee;
        this.vehicle = vehicle;
    }

    /**
     * Constructs a Booking object with only an end date and vehcle.
     * 
     * @param end End date of the booking
     * @param v Vehicle associated with the booking
     */
    public Booking (Date end, Vehicle v) {
        this.end = end;
        this.vehicle = v;
    }


    /**
     * Returns the start date
     * 
     * @return the beginning date
     */
    public Date getBegin() {
        return begin;
    }

    /**
     * Returns the end date
     * 
     * @return the end date
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Returns the employee of this booking
     * 
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Returns the vehicle associated with this booking
     * 
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Compares a booking to another object.
     * Two bookings are considered equal if they involve the same vehicle and have the same start and end dates
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
        return this.vehicle.equals(other.vehicle) 
            && this.begin.equals(other.begin) 
            && this.end.equals(other.end)
            && this.employee.equals(other.employee);
    }

    /**
     * Returns a string
     * 
     * @return String describing the vehicle, booking period, and employee.
     */
    @Override
    public String toString() {
        return vehicle.toString() + " [beginning " + begin.toString() +
            " ending " + end.toString() + ":" + employee.name() + "]";

    }

    /**
     * Testbed main() for Booking
     */
    public static void main(String[] args) {
        Date d1 = new Date(2025, 10 , 31);
        Date d2 = new Date(2025, 11, 2);
        Vehicle v = new Vehicle("67359S", d1, Make.FORD, 59644);
        Booking b = new Booking(d1, d2, Employee.KAUR, v);
        System.out.println(b);
    }

}
