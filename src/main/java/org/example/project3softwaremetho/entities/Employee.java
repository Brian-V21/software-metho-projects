package org.example.project3softwaremetho.entities;
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
    
    private Employee(Department d) {
        this.dept = d;
    }

    public Department getDept() {
        return dept;
    }

}
