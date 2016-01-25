package app;

import java.util.ArrayList;
import java.util.List;

public class Reservation {
    long id;
    List<Room> rooms;
    Period period;
    private Client client;
    int timeDiscount;
    int clientDiscount;

    public void setClient(Client client){
        this.client = client;
        clientDiscount = client.getDiscount();
    }

    public double getPriceWithDiscount(){
        double price = 0;
        for(Room r : rooms){
            price += r.getPrice();
        }
        double timeDiscountMultiplier = 1 - ( timeDiscount/(double) 100);
        double clientDiscountMultiplier = 1 - ( clientDiscount/(double) 100);
        return (price * timeDiscountMultiplier * clientDiscountMultiplier * period.getDays());
    }

    public double getPrice(){
        double price = 0;
        for(Room r : rooms){
            price += r.getPrice();
        }
        return price * period.getDays();
    }

    public Period getPeriod(){
        return period;
    }

    public int getTimeDiscount(){
        return timeDiscount;
    }

    public int getClientDiscount(){
        return clientDiscount;
    }

    public List<Room> getRooms(){
        return rooms;
    }

    public Client getClient(){
        return client;
    }

    public String toString(){
        return "" + client + " " + period;
    }

    public Reservation(Period period, int timeDiscount){
        this.id = new java.util.Random().nextLong();
        this.period = period;
        this.rooms = new ArrayList<>();
        this.timeDiscount = timeDiscount;
        this.clientDiscount = 0;
    }

    public Reservation(long id, Period period, Client client, int clientDiscount, int timeDiscount){
        this.id = id;
        this.period = period;
        this.client = client;
        this.clientDiscount = clientDiscount;
        this.timeDiscount = timeDiscount;
        this.rooms = new ArrayList<>();
    }

}
