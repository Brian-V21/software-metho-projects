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
}
