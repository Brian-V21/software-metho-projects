package org.example.project3softwaremetho.entities;

/**
 * *@author Brian V and Travis F;
 */
public enum Campus {
    BUSCH("New Brunswick"),  LIVINGSTON("New Brunswick"), COOK ("New Brunswick"), NEWARK("Newark"), CAMDEN("Camden");
    private final String city;

    /**
     * Constructor for Campus
     * @param city String variable for the city name
     */
    private Campus(String city) {
        this.city = city;
    }

    /**
     * @return String city from Campus
     */
    public String getCity() {
        return city;
    }

    /**
     * @param s String variable for Campus
     * @return Campus type variable from String input
     */
    public static Campus fromString(String s) {
        String t = s.trim().toUpperCase();
        switch (t) {
            case "BUSCH": return BUSCH;
            case "LIVINGSTON": return LIVINGSTON;
            case "COOK": return COOK;
            case "NEWARK": return NEWARK;
            case "CAMDEN": return CAMDEN;
            default: throw new IllegalArgumentException(s + " - invalid campus.");
        }
    }
}