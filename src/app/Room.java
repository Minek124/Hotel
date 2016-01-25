package app;

import java.util.ArrayList;

public class Room {
    String name;
    int nOfBeds;
    ArrayList<Reservation> reservations;
    int quality;

    public Room(String name, int nOfBeds, int quality){
        this.name = name;
        this.nOfBeds = nOfBeds;
        this.reservations = new ArrayList<>();
        this.quality = quality;
    }

    public ArrayList<Reservation> getReservations(){
        return reservations;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return Config.room_price + (nOfBeds * Config.bed_price) + quality * Config.qualityPrice;
    }

    public boolean isFree(Period p){
        for(Reservation r : reservations){
            if(r.period.isOverlapping(p)){
                return false;
            }
        }
        return true;
    }

    public String toString(){
        return name + "(" + nOfBeds + ")";
    }

}

