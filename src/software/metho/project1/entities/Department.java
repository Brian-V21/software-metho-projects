package software.metho.project1.entities;
/**
 * *@author Brian V and Travis F;
 */
public enum Department { 
    COMPUTER_SCIENCE("Computer Science"), ELECTRICAL_ENGINEERING("Electrical Engineering"), 
INFORMATION_TECHNOLOGY("Information Technology"), MATH("Math"), BUISINESS_ANALYTICS("Buisiness Analytics and Information Technology"); 

private final String displayName;

    /**
     * Constructor for Department
     * @param displayName String for the Display name of Department
     */
    Department(String displayName) {
    this.displayName = displayName;
}

    /**
     *
     * @return String for the display name of Department
     */
    public String displayName() {
    return displayName;
}

    /**
     *
     * @return formatted String for the display name of Department
     */
    @Override
public String toString() {
    return displayName;
}

}

