package tests;

import app.Period;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPeriod {

    @Test
    public void test() {
        Period p = null;
        Period p2 = null;
        Period p3 = null;
        Period p4 = null;
        Period p5 = null;
        try {
            p = new Period("2011-01-01","2011-01-02");
            p2 = new Period("2011-01-02","2011-01-03");
            p3 = new Period("2011-01-03","2011-01-04");
            p4 = new Period("2011-01-01","2011-01-05");
            p5 = new Period("2011-01-06","2011-02-08");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(p);
        assertEquals(p.getDays(),2);
        assertEquals(p5.getDays(),34);
        assertEquals(p.isOverlapping(p),true);
        assertEquals(p.isOverlapping(p2),true);
        assertEquals(p2.isOverlapping(p3),true);
        assertEquals(p.isOverlapping(p3),false);
        assertEquals(p.isOverlapping(p4),true);
        assertEquals(p4.isOverlapping(p),true);
    }

    @Test
    public void reversedDate() {
        Period p = null;
        try {
            p = new Period("2011-01-04","2011-01-02");
        } catch (Exception e) {
        }
        assertNull(p);
    }
}
