package org.example.project3softwaremetho.collections;
import org.example.project3softwaremetho.entities.Booking;
import org.example.project3softwaremetho.entities.Department;
import org.example.project3softwaremetho.entities.Employee;
import org.example.project3softwaremetho.entities.Vehicle;
import org.example.project3softwaremetho.util.List;
import org.example.project3softwaremetho.util.Date;

/**
 * @author Brian V and Travis F
 */
public class Reservation extends List<Booking> {

    /**
     * Constructs an empty list of reservations
     */
    public Reservation() {
        super();
    }

    /**
     * Prints all active bookings ordered by city, license plate, beginning date.
     */
    public String printByVehicle() {
        StringBuilder sb = new StringBuilder();
        int n = size();
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                Booking a = get(min);
                Booking b = get(j);

                String cityA = a.getPickup().getCity();
                String cityB = b.getPickup().getCity();

                int cmp = cityA.compareTo(cityB);
                if (cmp > 0) {
                    min = j;
                } else if (cmp == 0) {
                    String plateA = a.getVehicle().getPlate();
                    String plateB = b.getVehicle().getPlate();
                    int cmpPlate = plateA.compareTo(plateB);
                    if (cmpPlate > 0) {
                        min = j;
                    } else if (cmpPlate == 0) {
                        if (a.getBegin().compareTo(b.getBegin()) > 0) {
                            min = j;
                        }
                    }
                }
            }
            if (min != i) {
                Booking tmp = get(i);
                set(i, get(min));
                set(min, tmp);
            }
        }
        for (int i = 0; i < n; i++) {
            sb.append(get(i)).append("\n");
        }
        return sb.toString();
    }


    /**
     * Prints the list of bookings ordered by department, then employee.
     */
    public String printByDept() {
        StringBuilder sb = new StringBuilder();
        int n = size();
            for (int i = 0; i < n - 1; i++) {
                int min = i;
                for (int j = i + 1; j < n; j++) {
                    Booking a = get(min);
                    Booking b = get(j);

                    String deptA = a.getEmployee().getDept().displayName();
                    String deptB = b.getEmployee().getDept().displayName();

                    int cmpDept = deptA.compareTo(deptB);
                    if (cmpDept > 0) {
                        min = j;
                    } else if (cmpDept == 0) {
                        String empA = a.getEmployee().name();
                        String empB = b.getEmployee().name();
                        if (empA.compareTo(empB) > 0) {
                            min = j;
                        }
                    }
                }
                if (min != i) {
                    Booking tmp = get(i);
                    set(i, get(min));
                    set(min, tmp);
                }
            }
            Department currentDept = null;
            for (int i = 0; i < n; i++) {
                Booking b = get(i);
                Department dept = b.getEmployee().getDept();

                if (currentDept != dept) {
                    currentDept = dept;
                    sb.append("--").append(dept.displayName()).append("--\n");
                }
                sb.append(b).append("\n");
            }
        return sb.toString();
    }

    /**
     * Checks whether a given vehicle already has an associated booking
     * @param vehicle the vehicle to check
     * @return true if a booking exists, false otherwise.
     */
    public boolean hasBookingForVehicle(Vehicle vehicle) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getVehicle().equals(vehicle)) 
                return true;
        }
        return false;
    }
    /**
     * Retrieves the booking matching a given vehicle and end date.
     * @param endDate the booking's end date
     * @param vehicle the vehicle associated with the booking
     * @return the booking if found, otherwise false
     */
    public Booking getBooking(Date endDate, Vehicle vehicle) {
        for (int i = 0; i < size(); i++) {
            Booking b = get(i);

            if (b.getVehicle().equals(vehicle) && b.getEnd().equals(endDate)) {
                return b;
            }
        }
        return null;
    }
    /**
     * Checks if the given booking is the earliest ending booking for its vehicle
     * @param targetBooking the booking to check
     * @return true if it is the earliest ending booking, otherwise false
     */
    public boolean isEarliestEndingDate(Booking targetBooking) {
        if (targetBooking == null) 
            return false;

        Vehicle targetVehicle = targetBooking.getVehicle();
        Date targetEnd = targetBooking.getEnd();

        for (int i = 0; i < size(); i++) {
            Booking b = get(i);
            if (b.getVehicle().equals(targetVehicle) &&
                    b.getEnd().compareTo(targetEnd) < 0) {
                return false;
            }
        }
        return true;
    }
    /**
     * Checks if the Reservation is empty
     * @return true if there are no bookings, otherwise false
     */
    public boolean isEmpty() {
        return size() == 0;
    }
    /**
     * Checks whether a vehicle is available in the given date range.
     * @param v the vehicle
     * @param startDate the requested start date
     * @param endDate the requested end date
     * @return if the vehicle is available, otherwise false
     */
    public boolean isAvailable(Vehicle v, Date startDate, Date endDate) {
        for (int i = 0; i < size(); i++) {
            Booking b = get(i);

            if(b.getVehicle().equals(v)) {
                Date bstart = b.getBegin();
                Date bend = b.getEnd();

                if(!(endDate.compareTo(bstart) < 0 || startDate.compareTo(bend) > 0)) {
                    return false;
                }
            }
            }
        return true;
    }
    /**
     * Checks whether an employee is availible in the given data range
     * @param emp the employee
     * @param startDate the requested start date
     * @param endDate the requested end date
     * @return true if the employee is available, otherwise false
     */
    public boolean isEmpAvailable(Employee emp, Date startDate, Date endDate) {
        for (int i = 0; i < size(); i++) {
            Booking b = get(i);
            if (b.getEmployee() == emp) {
                Date bstart = b.getBegin();
                Date bend = b.getEnd();

                if (!(endDate.compareTo(bstart) < 0 || startDate.compareTo(bend) > 0)) {
                    return false;
                }
            }
        }
    return true;
    }

}



