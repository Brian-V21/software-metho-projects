package software.metho.project1.entities;
/**
 * *@author Brian V and Travis F;
 */
import software.metho.project1.util.Date;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DateTest {
    private Date d1,d2,d3,d4,d5,d6;

    @org.junit.Before
    public void setUp() throws Exception {
        //valid cases
         d1 = new Date(2024, 2,29);// Leap yr
         d2 = new Date(2024, 9,15);
        //invalid cases
         d3 = new Date(1940, 13,30);//test month
         d4 = new Date(-12, 2,21);//test yr
         d5 = new Date(2000, 6,50); //test day
         d6 = new Date(2023, 2,29);//non leap yr for 29
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void isValid() {
        //valid
        assertTrue(d1.isValid());
        assertTrue(d2.isValid());
        //invalid
        assertFalse(d3.isValid());
        assertFalse(d4.isValid());
        assertFalse(d5.isValid());
        assertFalse(d6.isValid());
    }
}