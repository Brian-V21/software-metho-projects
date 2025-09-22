package software.metho.project1.entities;
//FOR DATE CLASS, need 4 invalid & 2 valid test cases...
//for testing isValid() method.

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    @Override
    public int compareTo(Date date) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return True;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    //Testbed
    public static void main(String[] args) {


    }
}
