package tests;

import app.Period;
import app.Reservation;
import app.Room;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRoom {

    @Test
    public void tests() throws Exception {
        Room room = new Room("room",2,2);
        Room room2 = new Room("room",2,3);
        Room room3 = new Room("room",3,2);
        assertTrue(room.getPrice() > 0);
        assertTrue(room2.getPrice() > room.getPrice());
        assertTrue(room3.getPrice() > room.getPrice());

        assertTrue(room.isFree(new Period("2011-01-01","2011-01-02")));

        room.getReservations().add(new Reservation(new Period("2011-01-02","2011-01-03"), 0));

        assertFalse(room.isFree(new Period("2011-01-01","2011-01-02")));

    }
}
