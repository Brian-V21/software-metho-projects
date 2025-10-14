package software.metho.project1.entities;
/**
 * *@author Brian V and Travis F;
 */
import software.metho.project1.util.Date;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class VehicleTest {
    Vehicle sedan1, sedan2,sedan3;
    Vehicle utility1, utility2, utility3;
    Vehicle truck1, truck2, truck3;

    @org.junit.Before
    public void setUp() throws Exception {
        sedan1 = new Sedan("12340S", new Date(2020, 2, 2), Make.FORD, 65390, Campus.BUSCH);
        sedan2 = new Sedan("22340S", new Date(2019, 5, 12), Make.TOYOTA, 54321, Campus.LIVINGSTON);
        sedan3 = new Sedan("12340S", new Date(2020, 2, 2), Make.FORD, 65390, Campus.BUSCH); // identical plate for equality

        utility1 = new Utility("10000D", new Date(2020, 4, 10), Make.HONDA, 40000, Campus.CAMDEN);
        utility2 = new Utility("20000D", new Date(2021, 6, 15), Make.CHEVY, 35000, Campus.NEWARK);
        utility3 = new Utility("10000D", new Date(2020, 4, 10), Make.HONDA, 40000, Campus.CAMDEN); //Identical plate

        truck1 = new Truck("34123X", new Date(2011, 3, 11), Make.FORD, 23123, Campus.LIVINGSTON);
        truck2 = new Truck("44123X", new Date(2013, 8, 22), Make.TOYOTA, 21332, Campus.CAMDEN);
        truck3 = new Truck("34123X", new Date(2011, 3, 11), Make.FORD, 23123, Campus.LIVINGSTON); //identical plate
        }


    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void compareTo() {
        //Comparing sedans
        assertTrue(sedan1.compareTo(sedan2) < 0);
        assertTrue(sedan2.compareTo(sedan1) > 0);
        assertEquals(0, sedan1.compareTo(sedan3));

        //Comparing Trucks
        assertTrue(truck1.compareTo(truck2) < 0);
        assertTrue(truck2.compareTo(truck1) > 0);
        assertEquals(0, truck1.compareTo(truck3));

        //Comparing Utilities
        assertTrue(utility1.compareTo(utility2) < 0);
        assertTrue(utility2.compareTo(utility1) > 0);
        assertEquals(0, utility1.compareTo(utility3));
    }
}