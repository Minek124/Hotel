package tests;

import app.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestHotel {



    @Test
    public void loadRooms() throws Exception {
        Hotel h = new Hotel();

        h.loadRooms("test.csv");

        Client c = h.getClients().get(0);
        Client c2 = h.getClients().get(1);
        assertEquals(c.getName(), "Jan Kowalski");
        assertEquals(c2.getName(), "Adam Nowak");

        assertTrue(c.getDiscount() > c2.getDiscount());

        Room r = h.getRooms().get(0);
        Room r2 = h.getRooms().get(1);

        assertEquals(r.getName(), "Diamentowy");
        assertEquals(r2.getName(), "Platynowy");
        assertEquals(r.getReservations().size(), 2);


        for(Reservation res : r.getReservations()){
            assertTrue(h.getReservations().contains(res));
        }
        for(Reservation res : r2.getReservations()){
            assertTrue(h.getReservations().contains(res));
        }

        ArrayList<Reservation> reservations = h.getReservations();

        for(Reservation res : reservations){
            boolean found = false;
            for(Room room : h.getRooms()){
                if(room.getReservations().contains(res)){
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    public void saveRooms() throws Exception {
        Hotel h = new Hotel();
        h.loadRooms("test.csv");
        h.saveRooms("test2.csv");

        Hotel h2 = new Hotel();
        h2.loadRooms("test2.csv");


        assertEquals(h.getClients().toString(),h2.getClients().toString());
        assertEquals(h.getReservations().toString(),h2.getReservations().toString());
        assertEquals(h.getRooms().toString(),h2.getRooms().toString());
    }

    @Test
    public void addRoom() throws Exception {
        Hotel h = new Hotel();
        assertEquals(h.getRooms().size(),0);
        h.addRoom("sss",1,1);
        assertEquals(h.getRooms().size(),1);
        assertEquals(h.getRooms().get(0).getName(),"sss");
    }

    @Test
    public void addClient() throws Exception {
        Hotel h = new Hotel();
        assertEquals(h.getClients().size(),0);
        h.addClient("sss");
        assertEquals(h.getClients().size(),1);
        assertEquals(h.getClients().get(0).getName(),"sss");
    }

    @Test
    public void deleteRoom() throws Exception {
        Hotel h = new Hotel();
        h.addRoom("sss",1,1);
        Reservation res = new Reservation(1, new Period("2011-01-01","2011-01-02"), new Client("sss"),0,0);
        res.getRooms().add(h.getRooms().get(0));
        h.makeReservation(res);
        try {
            h.deleteRoom("sss");
        }catch(Exception e){
        }

        assertEquals(h.getRooms().size(),1);
    }

    @Test
    public void makeReservation() throws Exception {
        Hotel h = new Hotel();
        h.addRoom("sss",1,1);
        Reservation res = new Reservation(1, new Period("2011-01-01","2011-01-02"), new Client("sss"),0,0);
        res.getRooms().add(h.getRooms().get(0));
        h.makeReservation(res);

        assertEquals(h.getReservations().get(0),res);
        assertEquals(h.getRooms().get(0).getReservations().get(0),res);
    }
}
