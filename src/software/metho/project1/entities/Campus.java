package software.metho.project1.entities;

public enum Campus {
    BUSCH("New Brunswick"),  LIVINGSTON("New Brunswick"), COOK ("New Brunswick"), NEWARK("Newark"), CAMDEN("Camden");
    private final String city;

    private Campus(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }

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