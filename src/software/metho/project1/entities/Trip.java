package software.metho.project1.entities;

public class Trip {

    private Booking booking;
    private int beginMileage;
    private int endMileage;

    public Trip(Booking booking, int beginMileage, int endMileage) {
        this.booking = booking;
        this.beginMileage = beginMileage;
        this.endMileage = endMileage;
    }

    public Booking getBooking() {
    return booking;
    }

    public int getBeginMileage() {
        return beginMileage;
    }

    public int getEndMileage() {
        return endMileage;
    }


    /**
     * Will return to this once booking is completed
     * 
     * Compares if two trips are equal to one another
     */
    @Override
    public boolean equals(Object obj) {
        
    }

    @Override
    public String toString() {
        
    }

    /**
     * Test Case for use once booking is done
     * 
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
