package software.metho.project1.entities;

public enum Employee {

    private Department dept;
    private Employee(Department d) {
        this.dept = d;
    }

    public Department getDept() {
        return dept;
    }

}
