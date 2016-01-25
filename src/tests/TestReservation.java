package tests;

import app.Client;
import app.Period;
import app.Reservation;
import app.Room;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestReservation {

    @Test
    public void testPrice() throws Exception {
        Reservation res = new Reservation(new Period("2011-01-01","2011-01-02"), 0);
        assertTrue(res.getPrice() == 0);
        assertTrue(res.getPriceWithDiscount() == 0);
        res.getRooms().add(new Room("room",2,2));
        double price = res.getPrice();
        double discountPrice = res.getPriceWithDiscount();
        assertTrue(price > 0);
        assertTrue(discountPrice > 0);
        assertTrue(discountPrice <= price);
        res.setClient(new Client("sss",7));
        assertTrue(discountPrice > res.getPriceWithDiscount());

    }
}
