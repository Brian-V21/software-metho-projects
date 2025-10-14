package software.metho.project1.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import software.metho.project1.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VehicleSurchargeTest {

    Sedan sedanNoSurcharge, sedanBelowSurcharge, sedanAboveSurcharge;
    Truck  truckNoSurcharge, truckWithSurcharge;
    Utility utilityNoSurcharge,  utilityBelowSurcharge, utilityAboveSurcharge;

    @Before
    public void setUp() throws Exception {
        //Sedans
        sedanNoSurcharge = new Sedan("10001S", new Date(2023, 1 ,1), Make.FORD, 10000, Campus.BUSCH);
        sedanAboveSurcharge = new Sedan("10002S", new Date(2023, 5, 1), Make.TOYOTA, 50000, Campus.LIVINGSTON);
        sedanBelowSurcharge = new Sedan("10003S", new Date(2015, 3, 23), Make.HONDA, 200000, Campus.CAMDEN);

        //Utility
        utilityNoSurcharge = new Utility("10001D", new Date(2023, 1 ,1), Make.HONDA, 12000, Campus.BUSCH);
        utilityAboveSurcharge = new Utility("10002D", new Date(2020, 2, 23), Make.HONDA, 80000, Campus.CAMDEN);
        utilityBelowSurcharge = new Utility("10003D", new Date(2000, 4, 20), Make.CHEVY, 220000, Campus.COOK);

        //Truck
        truckNoSurcharge = new Truck("10001X", new Date(2001, 2, 10), Make.CHEVY, 10000, Campus.CAMDEN);
        truckWithSurcharge = new Truck("10002X", new Date(2001, 2, 10), Make.CHEVY, 102000, Campus.CAMDEN);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void surcharge() {
        double sedanBelow = sedanBelowSurcharge.surcharge(50, true);
        double sedanAbove =  sedanAboveSurcharge.surcharge(200, true);
        double utilityAbove = utilityAboveSurcharge.surcharge(200, true);
        double utilityBelow = utilityBelowSurcharge.surcharge(40, true);

        //surcharge below
        assertTrue(sedanBelow > 0 && sedanBelow < 32.99);
        assertTrue(utilityBelow > 0 && utilityBelow < 35.99);

        //surcharge above
        assertEquals(32.99, sedanAbove, 0.0);
        assertEquals(35.99, utilityAbove, 0.0);

        //no surcharge
        assertEquals(0.0, sedanNoSurcharge.surcharge(0, false), 0.0);
        assertEquals(0.0, utilityNoSurcharge.surcharge(0, false), 0.0);
        assertEquals(0.0, truckNoSurcharge.surcharge(0, false), 0.0);

        //Truck Surcharge
        assertTrue(truckWithSurcharge.surcharge(200, true) > 0);


    }
}