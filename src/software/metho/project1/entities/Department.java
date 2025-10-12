package software.metho.project1.entities;
/**
 * *@author Brian V and Travis F;
 */
public enum Department { 
    COMPUTER_SCIENCE("Computer Science"), ELECTRICAL_ENGINEERING("Electrical Engineering"), 
INFORMATION_TECHNOLOGY("Information Technology"), MATH("Math"), BUISINESS_ANALYTICS("Buisiness Analytics and Information Technology"); 

private final String displayName;

Department(String displayName) {
    this.displayName = displayName;
}

public String displayName() {
    return displayName;
}

@Override
public String toString() {
    return displayName;
}

}

