package software.metho.project1.entities;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * *@author Brian V;
 */

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    // Months constants
    public static final int JAN = 1, FEB = 2, MAR = 3, APR = 4, MAY = 5, JUN = 6, JUL =7, AUG = 8, SEP = 9, OCT = 10, NOV = 11, DEC = 12;
    //Days in months with non leap yr
    public static final int[] DAYS_IN_MONTHS = {
            // placed 0 so index runs 1-12 ([1] = JAN through till [12] = DEC
            0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    /**
     * Constructor for Date
     * @param year year of the date
     * @param month month of the date
     * @param day day of the date
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }



    /**
     *
     * @return if date is a leap year
     */
    public boolean isLeap() {
        if (this.year % QUADRENNIAL != 0) {// if not div by 4 then false
            return false;
        } else if (this.year % CENTENNIAL != 0) {// then if not div by 100 then true
            return true;
        } else if (this.year % QUATERCENTENNIAL == 0) {// if div by 400 then true
            return true;
        } else {
            return false;
        }
    }
    /**
     *
     * @return if date input is a valid date
     */
    public boolean isValid() {
        //Check year and months
        if (this.year <= 0) return false;
        if (this.month < JAN || this.month > DEC) return false;
        //max days & is leap for feb
        int maxDays = DAYS_IN_MONTHS[this.month];
        if (this.month == FEB && isLeap()) {
            maxDays = 29;
        }
        // Checking days
        return (this.day >= 1 && this.day <=maxDays);
    }
    /**
     *
     * @return if date is before current date
     */
    public boolean isPastDate() {
        Calendar today = Calendar.getInstance();
        int todayYear = today.get(Calendar.YEAR);
        int todayMonth = today.get(Calendar.MONTH) + 1;
        int todayDay = today.get(Calendar.DAY_OF_MONTH);

        Date currentDate = new Date(todayYear, todayMonth, todayDay);
        return this.compareTo(currentDate) < 0;
    }

    public boolean isFutureDate() {
        Calendar today = Calendar.getInstance();
        int todayYear = today.get(Calendar.YEAR);
        int todayMonth = today.get(Calendar.MONTH) + 1;
        int todayDay = today.get(Calendar.DAY_OF_MONTH);

        Date currentDate = new Date(todayYear, todayMonth, todayDay);
        return this.compareTo(currentDate) > 0;
    }

    public boolean beyondDate() {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH, 3);
        int todayYear = today.get(Calendar.YEAR);
        int todayMonth = today.get(Calendar.MONTH) + 1;
        int todayDay = today.get(Calendar.DAY_OF_MONTH);

        Date threemonthsDate = new Date(todayYear, todayMonth, todayDay);
        return this.compareTo(threemonthsDate) > 0;
    }
    public boolean durationExceedsWeek (Date endDate) {
        Calendar begin = Calendar.getInstance();
        begin.set(this.getYear(), this.getMonth() - 1, this.getDay());

        Calendar end = Calendar.getInstance();
        end.set(endDate.getYear(), endDate.getMonth() - 1, endDate.getDay());

        long duration = end.getTimeInMillis() - begin.getTimeInMillis();
        long days = duration / (1000 * 60 * 60 * 24);

        return days >= 7;
    }

    @Override
    public int compareTo(Date date) {
        if (this.year != date.year) return this.year - date.year;
        if (this.month != date.month) return this.month - date.month;
        return this.day - date.day;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;
            return this.year == date.year && this.month == date.month && this.day == date.day;
        }
        return false;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     *
     * @param dateString date in String form
     * @return dateString into Date type
     */
    public static Date fromString(String dateString) {
        String[] parts = dateString.split("/");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid date string: " + dateString);
        } try {
            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return new Date(year, month, day);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid date string: " + dateString);
        }
    }

    private int toDayCount() {
    int days = 0;

    // Add days for all full years before this.year
    for (int y = 0; y < year; y++) {
        days += (new Date(y, 1, 1).isLeap()) ? 366 : 365;
    }

    // Add days for all full months before this.month in current year
    for (int m = 1; m < month; m++) {
        if (m == FEB && isLeap()) {
            days += 29;
        } else {
            days += DAYS_IN_MONTHS[m];
        }
    }

    // Add days in the current month
    days += day;

    return days;
}

/**
 * Returns the absolute number of days between this date and another.
 */
public int daysBetween(Date other) {
    return Math.abs(this.toDayCount() - other.toDayCount());
}





    //Testbed for 4 invalid & 2 valid tests
    public static void main(String[] args) {
        //valid cases
        Date d1 = new Date(2024, 2,29);// Leap yr
        Date d2 = new Date(2024, 9,15);
        //invalid cases
        Date d3 = new Date(1940, 13,30);//test month
        Date d4 = new Date(-12, 2,21);//test yr
        Date d5 = new Date(2000, 6,50); //test day
        Date d6 = new Date(2023, 2,29);//non leap yr for 29

        Date[] dates = {d1, d2, d3, d4, d5, d6};

        for (Date date : dates) {
            System.out.println(date + " is it Valid? " + date.isValid() );
        }
    }
}
