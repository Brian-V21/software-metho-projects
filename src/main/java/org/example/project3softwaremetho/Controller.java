package org.example.project3softwaremetho;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.project3softwaremetho.entities.*;
import org.example.project3softwaremetho.collections.*;
import org.example.project3softwaremetho.util.Date;
import org.example.project3softwaremetho.util.Sort;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class Controller {

    @FXML
    private TextArea outputTA;

    //Vehicle Management Tab
    @FXML
    private TextField plateTF, mileTF;

    @FXML
    private DatePicker vmDP; //Vehicle Management Date Picker

    @FXML
    private RadioButton chevyRB, fordRB, hondaRB, toyotaRB;

    @FXML
    private ToggleGroup makeGroup; //For RadioButton

    @FXML
    private ComboBox<Employee> employeeCB;

    @FXML
    private ComboBox<Campus> campusCB;

    @FXML
    private Button addFleetB, removeFleetB;

    //Vehicle Register Tab
    @FXML
    private DatePicker vrBeginDP, vrEndDP; //vehicle register begin & end date

    @FXML
    private ComboBox<Vehicle> vehicleCB;

    @FXML
    private Button loadTextB; //Load file button

    @FXML
    private ComboBox<Campus> campusCB2;

    @FXML
    private Button bookTripB, cancelTripB; //booking and cancelling buttons

    //Vehicle Return Tab
    @FXML
    private DatePicker vrEndTripDP2; //vehicle return date

    @FXML
    private ComboBox<Vehicle> vehicleCB2;

    @FXML
    private TextField newMileTF;

    @FXML
    private Button completeTripB;

    //Report Tab
    @FXML
    private Button printFleetB;

    @FXML
    private ComboBox<Reservation> printResCB;

    @FXML
    private ComboBox<Trip> printTripCB;

    //Fleet for vehicles
    private Fleet fleet = new Fleet();

    private final Reservation bookings = new Reservation();

    private final TripList completedTrips = new TripList();

    private enum ResReportOption { BY_LOCATION_PLATE_BEGIN, BY_DEPT_EMPLOYEE }
    private enum TripReportOption { BY_ENDING_DATE, COST_REPORT }

    /**
     * Initializes GUI components after FXML loading
     */
    public void initialize() {
        //RadioButton
        makeGroup = new ToggleGroup();
        chevyRB.setToggleGroup(makeGroup);
        fordRB.setToggleGroup(makeGroup);
        hondaRB.setToggleGroup(makeGroup);
        toyotaRB.setToggleGroup(makeGroup);

        chevyRB.setUserData(Make.CHEVY);
        fordRB.setUserData(Make.FORD);
        hondaRB.setUserData(Make.HONDA);
        toyotaRB.setUserData(Make.TOYOTA);

        //ComboBoxes
        employeeCB.getItems().addAll(Employee.values()); //in VM
        campusCB.getItems().addAll(Campus.values()); // in VM
        campusCB2.getItems().addAll(Campus.values()); // in V Register

        //Fill Vehicle ComboBoxes & avoid null pointer
        if (fleet != null) {
            ObservableList<Vehicle> vehiclesList = FXCollections.observableArrayList();
            for (Vehicle v : fleet) {
                vehiclesList.add(v);
            }
            vehicleCB.setItems(vehiclesList);
            vehicleCB2.setItems(vehiclesList);
        }
        
        // Populate report option ComboBoxes
        printResCB.setItems(FXCollections.observableArrayList(ResReportOption.BY_LOCATION_PLATE_BEGIN, ResReportOption.BY_DEPT_EMPLOYEE));

        printResCB.setCellFactory(cb -> new ListCell<>() {
            @Override protected void updateItem(ResReportOption opt, boolean empty) {
                super.updateItem(opt, empty);
                if (empty || opt == null) { setText(""); return; }
                setText(opt == ResReportOption.BY_LOCATION_PLATE_BEGIN
                    ? "Reservations by city / plate / begin"
                    : "Reservations by department / employee");
            }
        });
        printResCB.setButtonCell(printResCB.getCellFactory().call(null));

        printTripCB.setItems(FXCollections.observableArrayList(TripReportOption.BY_ENDING_DATE, TripReportOption.COST_REPORT));

        printTripCB.setCellFactory(cb -> new ListCell<>() {
            @Override protected void updateItem(TripReportOption opt, boolean empty) {
                super.updateItem(opt, empty);
                if (empty || opt == null) { setText(""); return; }
                setText(opt == TripReportOption.BY_ENDING_DATE
                    ? "Completed trips by ending date"
                    : "Cost report by department");
            }
        });
        printTripCB.setButtonCell(printTripCB.getCellFactory().call(null));

    }
    
    /**
     * Prints reservations sorted by city/plate/date based on selection in GUI.
     */
    @FXML
    void printResBySelected() {
        ResReportOption opt = printResCB.getSelectionModel().getSelectedItem();
        if (opt == null) {
            outputTA.setText("Please select a reservation option");
            return;
        }
        switch(opt) {
            case BY_LOCATION_PLATE_BEGIN -> handlePR(bookings);
            case BY_DEPT_EMPLOYEE -> handlePD(bookings);
        }
    }

    /**
     * Prints completed trip information for selected trip in GUI.
     */
    @FXML
    void printCTripBySelected() {
        TripReportOption opt = printTripCB.getSelectionModel().getSelectedItem();
        if (opt == null) {
            outputTA.setText("Please select a trip option");
            return;
        }
        switch (opt) {
            case BY_ENDING_DATE -> handlePT(null);
            case COST_REPORT -> handlePC();
        }
    }

    /**
     * Prints all fleet vehicles to output.
     */
    @FXML
    void printFleet() {
        handlePF();
    }

    /**
     * Completes a vehicle trip return using user-provided GUI input.
     */
    @FXML
    void completeTrip() {
        String plate = vehicleCB2.getValue().getPlate();
        String end = vrEndTripDP2.getValue().toString();
        String miles = newMileTF.getText();
        handleReturn(new String[] {"R", end, plate, miles});
    }

    @FXML
    void endTripSelected() {}
    @FXML
    void vehicleCBSelected() {}

    /**
     * Cancels a booking selected via GUI input fields.
     */
    @FXML
    void cancelBooking() {
        String plate = vehicleCB2.getValue().getPlate();
        String end = vrEndDP.getValue().toString();
        handleCancel(new String[]{"C", end, plate});
    }

    /**
     * Books a new trip based on user GUI selections.
     */
    @FXML
    void bookTrip() {
        Vehicle v = vehicleCB.getValue();
        Employee emp = employeeCB.getValue();
        Campus drop = campusCB2.getValue();

        String begin = vrBeginDP.getValue().toString();
        String end = vrEndDP.getValue().toString();

        handleBook(new String[]{"B", begin, end, v.getPlate(), emp.name(), drop.name()});
    }

    /**
     * Displays the currently selected vehicle.
     */
    @FXML
    void vehicleSelected() {
        Vehicle v = vehicleCB.getValue();
        if (v != null) {
            outputTA.appendText("Selected vehicle: " + v.getPlate() + "\n");
        }
    }

    @FXML
    void vrEndSelected() {
        bookingDatesPicker();
    }

    @FXML
    void vrBeginSelected() {
        bookingDatesPicker();
    }

    /**
     * Handles campus selection for booking tab.
     */
    @FXML
    void campusSelected() {
        Campus c = campusCB2.getValue();
        if (c != null) {
            outputTA.appendText("Drop-off campus:" + c + "\n");
        }
    }

    /**
     * Displayed selected employee on the screen
     */
    @FXML
    void employeeSelected() {
        if (employeeCB.getValue() != null) {
            outputTA.appendText("Employee: " + employeeCB.getValue().name() + "\n");
        }
    }

    /**
     * Validates vehicle management date selection.
     */
    @FXML
    void dateSelected() {
        if (vmDP.getValue() == null)
            return;
        Date obtained = Date.fromString(vmDP.getValue().toString());
        if (!obtained.isValid()) {
            outputTA.appendText(obtained + " - invalid calendar date.\n");
        } else if (!obtained.isPastDate()) {
            outputTA.appendText(obtained + " - is today or a future date.\n");
        }
    }
    
    /**
     * Removes a vehicle from the fleet based on plate text field
     */
    @FXML
    void removeFleet() {
        String plate = plateTF.getText() == null ? "" : plateTF.getText().trim();
        if (plate.isEmpty()) {
            outputTA.appendText("Enter a license plate to remove.\n");
            return;
        }
        handleDelete(new String[]{"D", plate});
    }

    /**
     * Adds a vehicle to the fleet.
     */
    @FXML
    void addFleet() {
        addVehicle(null);
    }
    /**
     * Loads vehicles from a text file via FileChooser
     * 
     * @param event triggered by pressing the "Load" button
     */
    @FXML
    void importFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Vehicle Text File");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage); //get the reference of the source file
        if (sourceFile == null) {
            outputTA.setText("No text file selected.");
            return;
        }
        try (Scanner fileScanner = new Scanner(sourceFile)) {
            int count = fleet.load(fileScanner);
            outputTA.appendText(count + " vehicles loaded into the fleet.\n");
            //load vehicles into ComboBox
            ObservableList<Vehicle> vehiclesList = FXCollections.observableArrayList();
            for (Vehicle v : fleet) {
                if (!vehiclesList.contains(v)) {
                    vehiclesList.add(v);
                }
            }
            vehicleCB.setItems(vehiclesList);
            vehicleCB2.setItems(vehiclesList);

        } catch (FileNotFoundException e) {
            outputTA.appendText(sourceFile.getName() + " not found\n");
        }
    }

    /**
     * Adds a vehicle to fleet
     * @param event input from user terminal
     */
    private void addVehicle(ActionEvent event) {
        String plate = plateTF.getText().toUpperCase().trim();
        if (plate.isBlank()) {
            outputTA.appendText("plate is empty\n");
            return;
        }
        if (plate.length() != 6) {
            outputTA.appendText("license plate is too short or too long. Needs to be 6 characters\n");
            return;
        }
        if (!Vehicle.isValidPlate(plate)) {
            outputTA.appendText(plate + " - invalid license plate.\n");
            return;
        }

        int mileage;
        try {
            mileage = Integer.parseInt(mileTF.getText());
            if (mileage <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            outputTA.appendText(mileTF.getText() + " - invalid mileage.\n");
            return;
        }

        Toggle selected = makeGroup.getSelectedToggle();
        if (selected == null) {
            outputTA.appendText("Please select a Make\n");
            return;
        }

        Make make;
        try {
            make = (Make) makeGroup.getSelectedToggle().getUserData();
            if (make == null) {
                outputTA.appendText("Invalid make.\n");
                return;
            }
        } catch (IllegalArgumentException e) {
            outputTA.appendText( "invalid make.\n");
            return;
        }

        Campus campus;
        try {
            campus = campusCB.getSelectionModel().getSelectedItem();
            if (campus == null) {
                outputTA.appendText("select a campus.\n");
                return;
            }
        } catch (Exception e) {
            outputTA.appendText("Select a campus.\n");
            return;
        }

        //Date
        if (vmDP.getValue() == null) {
            outputTA.appendText("Select a date.\n");
            return;
        }

        Date obtained = Date.fromString(vmDP.getValue().toString());
            if (!obtained.isValid()) {
                outputTA.appendText(obtained + " - invalid calendar date.\n");
                return;
            }
            if (!obtained.isPastDate()) {
                outputTA.appendText(obtained + " - is today or a future date.\n");
                return;
            }

        char type = Character.toUpperCase(plate.charAt(5));
        Vehicle v;
        switch(type) {
            case 'S': v = new Sedan(plate, obtained, make, mileage, campus);
                break;
            case 'D': v = new Utility(plate, obtained, make, mileage, campus);
                break;
            case 'X': v = new Truck(plate, obtained, make, mileage, campus);
                break;
            default:
                outputTA.appendText(plate + " - invalid license plate type.\n");
                return;
        }

        if (fleet.contains(v)) {
            outputTA.appendText(v + " is already in the fleet.\n");
            return;
        }
        fleet.add(v);
        outputTA.appendText(v + " has been added to the fleet.\n");
    }

    /**
     * Handles the deletion of a vehicle from fleet
     * @param t user input from Terminal
     */
    public void handleDelete(String[] t) {
        if (t.length < 2) {
            outputTA.appendText("Invalid input!\n");
            return;
        }

        String plate = t[1];
        Vehicle vehicle = fleet.findVehicle(plate);
        if (vehicle == null) {
            outputTA.appendText(plate + " is not in the fleet.\n");
            return;
        }

        if (bookings.hasBookingForVehicle(vehicle)) {
            outputTA.appendText(vehicle + " cannot be deleted, booked.\n");
            return;
        }
        fleet.remove(vehicle);
        outputTA.appendText(vehicle + " has been deleted from the fleet.\n");
    }

    /**
     * Handles the adding another booking into bookings collection
     * @param t user input from terminal
     */
    private void handleBook(String[] t) {
        if (t.length < 6) {
            outputTA.appendText("Invalid input!\n");
            return;
        }

        Date begin, end;
        try {
            begin = Date.fromString(t[1]);
            end = Date.fromString(t[2]);
            if (!begin.isValid()) {
                outputTA.appendText(t[1] + " is not a valid calendar date.\n");
                return;
            }
            if (!end.isValid()) {
                outputTA.appendText(t[2] + " is not a valid calendar date.\n");
                return;
            }
            if (!begin.isFutureDate()) {
                outputTA.appendText(t[1] + " Beginning date is not today or a future date.\n");
                return;
            }
            if (end.compareTo(begin) < 0) {
                outputTA.appendText(t[2] + " ending date must be equal or after the beginning date.\n");
                return;
            }
            if (begin.beyondDate()) {
                outputTA.appendText(t[1] + " beginning date beyond 3 months\n");
                return;
            }
            if (begin.durationExceedsWeek(end)) {
                outputTA.appendText(t[1] + " ~ " + t[2] + " duration is more than a week\n");
                return;
            }
        } catch (IllegalArgumentException e) {
            outputTA.appendText("Invalid date input\n");
            return;
        }

        String plate = t[3];
        Vehicle vehicle = fleet.findVehicle(plate);
        if (vehicle == null) {
            outputTA.appendText(plate + " is not in the fleet.\n");
            return;
        }

        Employee emp;
        try {
            emp = Employee.valueOf(t[4].toUpperCase());
        } catch (IllegalArgumentException e) {
            outputTA.appendText(t[4].toUpperCase() + " not an eligible employee to book.\n");
            return;
        }

        Campus dropoff;
        try {
            dropoff = Campus.fromString(t[5]);
        } catch (IllegalArgumentException e) {
            outputTA.appendText(e.getMessage());
            return;
        }

        if (!bookings.isAvailable(vehicle, begin, end)) {
            outputTA.appendText(vehicle.getPlate() + " - booking with " + begin + " ~ " + end + " not available\n");
            return;
        }
        if (!bookings.isEmpAvailable(emp, begin, end)) {
            outputTA.appendText(emp + " - has an existing booking conflicting with the beginning date " + begin + "\n");
            return;
        }

        Booking b = new Booking(begin, end, emp, vehicle, dropoff);
        bookings.add(b);
        outputTA.appendText(b + " booked.\n");
    }

    /**
     * Handles cancellation of a booking from bookings collection
     * @param t user input from terminal
     */
    private void handleCancel(String[] t) {
        if (t.length < 3) {
            outputTA.appendText("Invalid input!\n");
            return;
        }
        Date end;
        try {
            end = Date.fromString(t[1]);
            if (!end.isValid()) {
                outputTA.appendText(t[1] + " is not a valid date.\n");
                return;
            }
        } catch (IllegalArgumentException e) {
            outputTA.appendText(t[1] + "Invalid date input\n");
            return;
        }
        Vehicle v = fleet.findVehicle(t[2]);
        if (v == null) {
            outputTA.appendText(t[3] + ":" + t[1] + " ~ " + t[2] + " - Cannot find the booking\n");
            return;
        }
        Booking b = bookings.getBooking(end, v);
        if (b == null) {
            outputTA.appendText(v.getPlate() + " booked with ending date " + end + " - cannot find the booking.\n");
            return;
        }
        bookings.remove(b);
        outputTA.appendText("Booking canceled: " + b + "\n");
    }

    /**
     * Handles returning a vehicle after Trip has been completed
     * @param t user input from terminal
     */
    private void handleReturn(String[] t) {
        if (t.length < 4) {
            outputTA.appendText( "Invalid input!\n");
            return;
        }
        Date end;
        try {
            end = Date.fromString(t[1]);
            if (!end.isValid()) {
                outputTA.appendText(t[1] + " is not a valid date.\n");
                return;
            }
        } catch (IllegalArgumentException e) {
            outputTA.appendText("Invalid date input.\n");
            return;
        }

        Vehicle v = fleet.findVehicle(t[2]);
        if (v == null) {
            outputTA.appendText("Vehicle not found in fleet.\n");
            return;
        }

        int newMileage;
        try {
            newMileage = Integer.parseInt(t[3]);
        } catch (NumberFormatException e) {
            outputTA.appendText("Invalid mileage - current mileage: " + v.getMileage() + " entered mileage" + t[3]);
            return;
        }

        Booking b = bookings.getBooking(end, v);
        if (b == null) {
            outputTA.appendText(v.getPlate() + " booked with ending date " + end + " - cannot find booking\n");
            return;
        }
        if (!bookings.isEarliestEndingDate(b)) {
            outputTA.appendText(b.getVehicle().getPlate() + " booked with ending date " + b.getEnd() + " - returning not in order of ending date\n");
            return;
        }
        if (newMileage == 0) {
            outputTA.appendText(newMileage + " - invalid mileage\n");
            return;
        }
        if (newMileage <= v.getMileage()) {
            outputTA.appendText("Invalid mileage - current mileage: " + v.getMileage() + " entered mileage: " + newMileage);
            return;
        }

        int beginMileage = v.getMileage();
        v.setMileage(newMileage);

        boolean surcharge = (b.getDropoff() != null) && (b.getPickup() != b.getDropoff());
        Trip trip = new Trip(b, beginMileage, newMileage, surcharge);
        completedTrips.add(trip);

        if (b.getDropoff() != null)
            v.setCampus(b.getDropoff());

        bookings.remove(b);
        outputTA.appendText(trip.toString());
    }

    /**
     * Displays list of vehicles in fleet collection based off city, make, then date obtained
     */
    private void handlePF() {
        if (fleet.isEmpty()) {
            outputTA.appendText("There is no vehicle in the fleet.\n");
            return;
        }
        outputTA.appendText("*List of vehicles in the fleet, ordered by city, make, then date obtained.\n");
        Sort.sortFleetByLocationMakeDate(fleet);
        outputTA.appendText(fleet.printByMake());
        outputTA.appendText("*end of list.\n");
    }

    /**
     * Displays list of reservations based off city, license plate, then beginning date
     */
    private void handlePR(Reservation bookings) {
        if (bookings.isEmpty()) {
            outputTA.appendText("There is no booking record.\n");
            return;
        }
        outputTA.appendText("*List reservations ordered by city, license plate, then beginning date.\n");
        Sort.sortBookingsByLocationPlateBegin(bookings);
        bookings.printByVehicle();
        outputTA.appendText("*end of list.\n");
    }

    /**
     * Display list of reservations based off department then employee
     */
    private void handlePD(Reservation bookings) {
        if (bookings.isEmpty()) {
            outputTA.appendText("There is no booking record.\n");
            return;
        }
        outputTA.appendText("*List of reservations ordered by Department then employee.\n");
        bookings.printByDept();
        outputTA.appendText("*end of list.\n");
    }

    /**
     * Display list of completed trips based off ending date
     */
    private void handlePT(Trip bookings) {
        if (completedTrips.isEmpty()) {
            outputTA.appendText("There is no archived trips.\n");
            return;
        }
        outputTA.appendText("*List of completed trips ordered by ending date.\n");
        completedTrips.print();
        outputTA.appendText("*end of list.\n");

    }

    /**
     * Display cost based off department
     */
    private void handlePC() {
        if (completedTrips.isEmpty()) {
            outputTA.appendText("There is no archived trips.\n");
            return;
        }
        outputTA.appendText("*Cost report by department*\n");
        completedTrips.printCharges();
        outputTA.appendText("*end of list.\n");
    }

    /**
     * Validates date selections for booking tab.
     * @return true if dates are valid, otherwise false
     */
    private boolean bookingDatesPicker() {
        if (vrBeginDP.getValue() == null || vrEndDP.getValue() == null)
            return true;
        Date begin = Date.fromString(vrBeginDP.getValue().toString());
        Date end = Date.fromString(vrEndDP.getValue().toString());

        if (!begin.isValid()) {
            outputTA.appendText(vrBeginDP.getValue() + " is not a valid calendar date.\n");
                return false;
        }
        if (!end.isValid()) {
            outputTA.appendText(vrEndDP.getValue() + " is not a valid calendar date.\n");
                return false;
        }
        if (!begin.isFutureDate()) {
            outputTA.appendText(begin + " Beginning date is not today or a futre date");
                return false;
        }
        if (end.compareTo(begin) < 0) {
            outputTA.appendText(end + " ending date must be equal or after the beginning date.\n");
                return false;
        }
        if (begin.beyondDate()) {
            outputTA.appendText(begin + " beginning date is beyond 3 months.\n");
                return false;
        }
        if (begin.durationExceedsWeek(end)) {
            outputTA.appendText(begin + " ~ " + end + " duration is more than a week.\n");
                return false;
        }
        return true;
    }


}