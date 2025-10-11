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
}
