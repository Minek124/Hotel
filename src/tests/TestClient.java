package tests;

import app.Client;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestClient {

    @Test
    public void testConstructor() {
        Client c = new Client("sss");
        assertEquals(0,c.getDiscount());

        Client c2 = new Client("sss",6);
        assertEquals(10,c2.getDiscount());
    }
}
