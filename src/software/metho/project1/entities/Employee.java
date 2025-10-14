package software.metho.project1.entities;
/**
 * *@author Brian V and Travis F;
 */
public enum Employee {
    PATEL(Department.COMPUTER_SCIENCE), 
    LIM(Department.ELECTRICAL_ENGINEERING),
    ZIMNES(Department.COMPUTER_SCIENCE),
    HARPER(Department.ELECTRICAL_ENGINEERING),
    KAUR(Department.INFORMATION_TECHNOLOGY),
    TAYLOR(Department.MATH),
    RAMESH(Department.MATH),
    CERAVOLO(Department.BUISINESS_ANALYTICS);

    private Department dept;

    /**
     * Constructor for Employee
     * @param d variable for department
     */
    private Employee(Department d) {
        this.dept = d;
    }

    /**
     *
     * @return department of Employee
     */
    public Department getDept() {
        return dept;
    }

}
