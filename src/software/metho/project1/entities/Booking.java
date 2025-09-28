package software.metho.project1.entities;

public class Booking {
    /* Wrote this in date but pretty this can be used in booking
    //Check if date is past or today date
    public boolean isPastOrPresent() {
        java.util.Calendar today = java.util.Calendar.getInstance();
        int currentYear = today.get(java.util.Calendar.YEAR);
        int currentMonth = today.get(java.util.Calendar.MONTH) + 1;
        int currentDay = today.get(java.util.Calendar.DAY_OF_MONTH);

        //checking yr, month, then last days
        if (year < currentYear) return true;
        if (year > currentYear) return false;
        if (month < currentMonth) return true;
        if (month > currentMonth) return false;
        return day <= currentDay;
    }
    //check if date is future date
    public boolean isFuture() {
        return !isPastOrPresent();
    } */

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
     * 
     * @return the beginning date
     */
    public Date getBegin() {
        return begin;
    }

    /**
     * 
     * @return the end date
     */
    public Date getEnd() {
        return end;
    }

    /**
     * 
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * 
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Booking))
            return false;
        Booking other = (Booking) obj;
        return this.vehicle.equals(other.vehicle) 
            && this.begin.equals(other.begin) 
            && this.end.equals(other.end);
    }

    @Override
    public String toString() {
        return vehicle.toString() + " [beginning " + begin.toString() +
            " ending " + end.toString() + ":" + employee.name() + "]";

    }

    public static void main(String[] args) {
        Date d1 = new Date(2025, 10 , 31);
        Date d2 = new Date(2025, 11, 2);
        Vehicle v = new Vehicle("67359S", d1, Make.FORD, 59644);
        Booking b = new Booking(d1, d2, Employee.KAUR, v);
        System.out.println(b);
    }

}
