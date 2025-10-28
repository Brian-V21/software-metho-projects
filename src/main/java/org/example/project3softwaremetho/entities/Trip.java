package org.example.project3softwaremetho.entities;
/**
 * *@author Brian V and Travis F;
 */
public class Trip {

    private Booking booking;
    private int beginMileage;
    private int endMileage;


    /**
     * Constructs a trip with a given booking and mileage readings.
     * 
     * @param booking the booking that initiated the trip
     * @param beginMileage the vehicle's mileage at the start of the trip
     * @param endMileage the vehicle's mileage at the end of the trip
     */
    public Trip(Booking booking, int beginMileage, int endMileage) {
        this.booking = booking;
        this.beginMileage = beginMileage;
        this.endMileage = endMileage;
    }

    /**
     * Returns the booking associated with the trip
     * @return the booking object.
     */
    public Booking getBooking() {
        return booking;
    }
    /**
     * Returns the mileage reading at the start of the trip
     * @return starting mileage.
     */
    public int getBeginMileage() {
        return beginMileage;
    }
    /**
     * Returns the mileage reading at the end of the trip
     * @return ending mileage
     */
    public int getEndMileage() {
        return endMileage;
    }


    /**
     * Will return to this once booking is completed
     * Compares if two trips are equal to one another
     * 
     * @param obj the object to compare to
     * @return true if the bookings are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Trip other = (Trip) obj;
        return this.booking.equals(other.booking);
    }

    /**
     * Returns a formatted string representing the trip.
     * 
     * @return a readable summary of the trip
     */
    @Override
    public String toString() {
        return booking.getVehicle().getPlate() + " " +
            booking.getBegin().toString() + " ~ " +
            booking.getEnd().toString() + " " +
            "original mileage: " + beginMileage + " " +
            "current mileage: " + endMileage + " " +
            "mileage used: " + (endMileage - beginMileage); 
    }

    /**
     * testbed main()
     * Test Case
     */
    public static void main(String[] args) {
        Date begin = new Date(2025, 10, 15);
        Date end = new Date(2025, 10, 15);
        Vehicle v = new Vehicle("58718D", new Date(2017, 1, 31), Make.TOYOTA, 64390);
        Booking b = new Booking(begin, end, Employee.KAUR, v);

        Trip trip = new Trip(b, 64390, 64500);
        System.out.println(trip.toString());
    }

}
