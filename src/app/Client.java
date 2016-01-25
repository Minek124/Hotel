package app;

public class Client {
    String name;
    int nOfReservations;

    public Client(String name){
        this.name = name;
        this.nOfReservations = 0;
    }

    public Client(String name, int nOfReservations){
        this.name = name;
        this.nOfReservations = nOfReservations;
    }

    public String getName(){
        return name;
    }

    public int getDiscount(){
        if(nOfReservations >= Config.regular_custommer_threshold){
            return Config.regular_custommer_discount;
        }
        return 0;
    }

    public String toString(){
        return name;
    }

}
