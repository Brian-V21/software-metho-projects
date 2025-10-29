package org.example.project3softwaremetho.entities;
/**
 * *@author Brian V and Travis F;
 */
public class Trip {

    private Booking booking;
    private int beginMileage;
    private int endMileage;
    private boolean surcharge;

    /**
     * Constructs a trip with a given booking and mileage readings.
     * @param booking the booking that initiated the trip
     * @param beginMileage the vehicle's mileage at the start of the trip
     * @param endMileage the vehicle's mileage at the end of the trip
     */
    public Trip(Booking booking, int beginMileage, int endMileage, boolean surcharge) {
        this.booking = booking;
        this.beginMileage = beginMileage;
        this.endMileage = endMileage;
        this.surcharge = surcharge;
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
     * Checks if there is a surcharge
     * @return false/true of surcharge
     */
    public boolean isSurcharge() {
        return surcharge;
    }

    /**
     * @return total miles used based of ending mileage minus beginning mileage
     */
    public int milesUsed() {
        return endMileage - beginMileage;
    }

    /**
     * @return location of where vehicle is getting picked up
     */
    public Campus getPickupCampus() {
        return booking.getPickup();
    }

    /**
     * @return location of where vehicle was dropped off
     */
    public Campus getDropoffCampus() {
        return booking.getDropoff();
    }

    /**
     * @return charge price based off miles used
     */
    public double getCharge() {
        return booking.getVehicle().charge(milesUsed());
    }

    /**
     * @return the amount of surcharge based off miles used and if there was a surcharge
     */
    public double getSurchargeAmount() {
        return booking.getVehicle().surcharge(milesUsed(), surcharge);
    }

    /**
     * @return total cost of use based off the regular charge amount plus surcharge if any
     */
    public double getTotalCost() {
        return getCharge() + getSurchargeAmount();
    }

    /**
     * Compares if two trips are equal to one another
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
     * @return a readable summary of the trip
     */
    @Override
    public String toString() {
        String dropoffLabel = getDropoffCampus() + (surcharge ? "**" : "");
        return "Trip completed: " + booking.getVehicle().getPlate() + " " +
            booking.getBegin().toString() + " ~ " +
            booking.getEnd().toString() + " " +
            "original mileage: " + beginMileage + " " +
            "current mileage: " + endMileage + " " +
            "mileage used: " + (milesUsed() + " " + 
            "[dropped off: " + dropoffLabel + "] " +
            "[picked up: " + getPickupCampus() + "]"); 
    }
}
