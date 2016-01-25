package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private ArrayList<Room> roomList = new ArrayList<>();
    private ArrayList<Reservation> reservationList = new ArrayList<>();
    private ArrayList<Client> clientList = new ArrayList<>();

    private List<Room> getAllFreeRooms(Period period){
        ArrayList<Room> freeRooms = new ArrayList<>();
        for(Room room : roomList){
            boolean free = true;
            for(Reservation res : room.reservations){
                if(res.period.isOverlapping(period)){
                    free = false;
                }
            }
            if(free){
                freeRooms.add(room);
            }
        }
        return freeRooms;
    }

    private Room getRoomByName(String name){
        for(Room r : roomList){
            if(r.name.equals(name)){
                return r;
            }
        }
        return null;
    }

    public void loadRooms(String file) throws Exception {
        roomList.clear();
        clientList.clear();
        reservationList.clear();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = br.readLine()) != null) {
            String[] splited = line.split(",");
            if(splited[0].equals("CLIENT")){
                clientList.add(new Client(splited[1],Integer.parseInt(splited[2])));
            }
            if(splited[0].equals("ROOM")){
                roomList.add(new Room(splited[1], Integer.parseInt(splited[2]), Integer.parseInt(splited[3]) ));
            }
            if(splited[0].equals("RESERVATION")){
                Client client = null;
                for(Client c : clientList){
                    if(c.name.equals(splited[4])){
                        client = c;
                    }
                }

                Reservation res = new Reservation(Long.parseLong(splited[1]), new Period(splited[2],splited[3]), client, Integer.parseInt(splited[5]), Integer.parseInt(splited[6]));
                for(int i =7;i<splited.length;i++){
                    Room room = null;
                    for(Room r : roomList){
                        if(r.name.equals(splited[i])){
                            r.reservations.add(res);
                            room = r;
                        }
                    }
                    res.getRooms().add(room);
                }
                reservationList.add(res);
            }
        }
        br.close();
    }

    public void saveRooms(String file) throws IOException {
        FileWriter writer = new FileWriter(file);
        for(Client c : clientList){
            writer.append("CLIENT," + c.name + "," + c.nOfReservations + "\n");
        }
        for(Room room : roomList){
            writer.append("ROOM," + room.name + "," + room.nOfBeds + "," + room.quality + "\n");
        }
        for(Reservation res : reservationList){
            String rooms = "";
            for(Room r : res.getRooms()){
                rooms += r.getName() + ",";
            }
            rooms = rooms.substring(0,rooms.length()-1);
            writer.append("RESERVATION," +res.id + "," + res.period.getTextFormat() + "," + res.getClient() + "," + res.getClientDiscount() + "," + res.getTimeDiscount() + "," + rooms + "\n");
        }

        writer.flush();
        writer.close();
    }


    public void addRoom(String name, int nOfBeds, int quality) {
        roomList.add(new Room(name, nOfBeds, quality));
    }

    public void addClient(String name) {
        clientList.add(new Client(name));
    }


    public void deleteRoom(String name) throws Exception {
        for(int i = 0;i<roomList.size();i++){
            Room r = roomList.get(i);
            if(r.name.equals(name)){
                if(r.reservations.isEmpty()) {
                    roomList.remove(i);
                    return;
                }else{
                    throw new Exception("This room have reservations!");
                }
            }
        }
    }
    public void deleteReservation(long id){
        Reservation tmp = null;
        for(Reservation res : reservationList){
            if(res.id ==id){
                tmp = res;
            }
        }
        for(Room r : roomList){
            r.reservations.remove(tmp);
        }
        (tmp.getClient().nOfReservations)--;
        reservationList.remove(tmp);
    }

    public ArrayList<Room> getRooms(){
        return roomList;
    }

    public ArrayList<Reservation> getReservations(){
        return reservationList;
    }

    public ArrayList<Client> getClients(){
        return clientList;
    }

    public List<Room> findFreeRooms(Period period, List<Integer> rooms) {
        List<Room> ret = new ArrayList<>();
        for(Room r : getAllFreeRooms(period)){
            if(rooms.contains(r.nOfBeds)){
                ret.add(r);
            }
        }
        return ret;

    }

    public boolean makeReservation(Reservation request) {
        Period period = request.getPeriod();
        ArrayList<Room> tmp = new ArrayList<>();
        if(request.getClient() == null){
            return false;
        }
        for(Room r : request.getRooms()){
            if(r.isFree(period)){
                tmp.add(r);
            }else{
                return false; // room is not free
            }
        }

        for(Room r : tmp){
            r.reservations.add(request);
        }
        reservationList.add(request);
        (request.getClient().nOfReservations)++;

        return true;
    }

}   