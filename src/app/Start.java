package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Start {

    static ArrayList<Client> clients = new ArrayList<>();
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static int list = 0;
    static JLabel infoLabel = new JLabel("-");
    static int discount = 0;
    static Reservation reservation;


    static DefaultListModel<String> list1 = new DefaultListModel<>();
    static DefaultListModel<String> list2 = new DefaultListModel<>();
    static DefaultListModel<String> list3 = new DefaultListModel<>();
    static JList<String> JList1 = new JList<>(list1);
    static JList<String> JList2 = new JList<>(list2);
    static JList<String> JList3 = new JList<>(list3);
    static JButton load = new JButton("Load");
    static JButton save = new JButton("Save");
    static JButton showRooms = new JButton("Show rooms");
    static JButton showReservations = new JButton("Show Reservations");
    static JButton delete = new JButton("Delete");
    static JButton addRoom = new JButton("Add room");
    static JButton addClient = new JButton("Add Client");
    static JButton reserve = new JButton("Reserve");
    static JLabel arrow = new JLabel(">>>");

    static JTextField fromField = new JTextField(10);
    static JTextField toField = new JTextField(10);
    static JTextField roomsField = new JTextField(10);
    static JTextField file = new JTextField(10);
    static JButton search = new JButton("Search");

    public static void main(String[] args){
        Hotel hotel = new Hotel();

        JScrollPane jsp1 = new JScrollPane(JList1);
        jsp1.setPreferredSize( new Dimension( 100, 300 ) );
        JScrollPane jsp2 = new JScrollPane(JList2);
        jsp2.setPreferredSize( new Dimension( 300, 300 ) );
        JScrollPane jsp3 = new JScrollPane(JList3);
        jsp3.setPreferredSize( new Dimension( 250, 300 ) );

        fromField.setText("2016-01-04");
        toField.setText("2016-01-06");
        roomsField.setText("1,3");
        file.setText("rooms.csv");

        JFrame frame = new JFrame();
        JPanel top = new JPanel();
        JPanel top2 = new JPanel();
        JPanel top3 = new JPanel();
        JPanel info = new JPanel();
        JPanel middle = new JPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Hotel");
        frame.setSize(750, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        panel.add(top);
        panel.add(top2);
        panel.add(top3);
        panel.add(info);
        panel.add(middle);

        top.add(file);
        top.add(load);
        top.add(save);

        top2.add(showRooms);
        top2.add(showReservations);
        top2.add(delete);
        top2.add(addRoom);
        top2.add(addClient);

        top3.add(fromField);
        top3.add(toField);
        top3.add(roomsField);
        top3.add(search);
        top3.add(reserve);

        info.add(infoLabel);

        middle.add(jsp1);
        middle.add(jsp2);
        middle.add(arrow);
        middle.add(jsp3);


        middle.updateUI();

        load.addActionListener(e -> {
            list1.clear();
            list2.clear();
            list3.clear();
            clients.clear();
            try {
                hotel.loadRooms(file.getText());
                showRooms.doClick();
                for(Client c : hotel.getClients()){
                    list1.addElement(c.name + " " + (c.getDiscount()>0 ? "*" : ""));
                    clients.add(c);
                }
                infoLabel.setText("Loaded successfully!");
            } catch (Exception e1) {
                e1.printStackTrace();
                infoLabel.setText("Csv file not found:" + file.getText());
            }

        });

        save.addActionListener(e -> {
            try {
                hotel.saveRooms(file.getText());
                infoLabel.setText("Saved successfully!");
            } catch (IOException e1) {
                infoLabel.setText("save failed!");
            }

        });

        showRooms.addActionListener(e -> {
            list2.clear();
            list3.clear();
            rooms.clear();
            for(Room r : hotel.getRooms()){
                list2.addElement(r.name + "(" + r.nOfBeds + ") " +  r.getPrice() + "$");
                rooms.add(r);
            }
            list = 1;
            infoLabel.setText("Room list:");
        });

        showReservations.addActionListener(e -> {
            list2.clear();
            list3.clear();
            reservations.clear();
            for(Reservation res : hotel.getReservations()){
                list2.addElement(res.toString());
                reservations.add(res);
            }
            list = 2;
            infoLabel.setText("Reservation list:");
        });

        delete.addActionListener(e -> {
            if(JList2.getSelectedIndex() == -1){
                return;
            }
            if(list == 1 || list == 3) {
                Room r = rooms.get(JList2.getSelectedIndex());
                try {
                    hotel.deleteRoom(r.name);
                    showRooms.doClick();
                    infoLabel.setText("Room deleted!");
                }catch(Exception e1){
                    infoLabel.setText(e1.getMessage());
                }
            }
            if(list == 2) {
                if(JList3.getSelectedIndex() == -1){
                    Reservation res = reservations.get(JList2.getSelectedIndex());
                    hotel.deleteReservation(res.id);
                    showReservations.doClick();
                }else{
                    Room r = rooms.get(JList3.getSelectedIndex());
                    Reservation res = reservations.get(JList2.getSelectedIndex());
                    res.getRooms().remove(r);
                    list3.remove(JList3.getSelectedIndex());
                    if(list3.isEmpty()){
                        hotel.deleteReservation(res.id);
                        showReservations.doClick();
                    }
                }
            }
        });

        reserve.addActionListener(e -> {
            if(JList1.getSelectedIndex() == -1){
                infoLabel.setText("Select client!");
                return;
            }
            if(list == 3) {
                if (hotel.makeReservation(reservation)) {
                    infoLabel.setText("Reserved Successfully!");
                    list1.clear();
                    list2.clear();
                    list3.clear();
                    clients.clear();
                    for(Client c : hotel.getClients()){
                        list1.addElement(c.name + " " + (c.getDiscount()>0 ? "*" : ""));
                        clients.add(c);
                    }
                } else {
                    infoLabel.setText("Reservation failed!!");
                }
            }
            if(list == 1 || list == 2) {
                infoLabel.setText("Find rooms first!");
            }
        });

        addRoom.addActionListener(e -> {
            JFrame frame2 = new JFrame();
            frame2.setTitle("Add Room");
            frame2.setSize(300, 200);
            frame2.setLocationRelativeTo(null);
            frame2.setVisible(true);

            JPanel panel2 = new JPanel();
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
            frame2.add(panel2);

            JTextField addRoomName = new JTextField(10);
            JTextField addRoomNOfBeds = new JTextField(10);
            JTextField addRoomQuality = new JTextField(10);
            JLabel label1 = new JLabel("Room name");
            JLabel label2 = new JLabel("Number of beds");
            JLabel label3 = new JLabel("Quality");

            JButton addRoom2 = new JButton("Add room");

            JPanel row1 = new JPanel();
            JPanel row2 = new JPanel();
            JPanel row3 = new JPanel();
            JPanel row4 = new JPanel();

            row1.add(label1);
            row1.add(addRoomName);
            row2.add(label2);
            row2.add(addRoomNOfBeds);
            row3.add(label3);
            row3.add(addRoomQuality);
            row4.add(addRoom2);

            panel2.add(row1);
            panel2.add(row2);
            panel2.add(row3);
            panel2.add(row4);

            addRoom2.addActionListener(e2 -> {
                hotel.addRoom(addRoomName.getText(), Integer.parseInt(addRoomNOfBeds.getText()), Integer.parseInt(addRoomQuality.getText()));
                frame2.dispose();
                showRooms.doClick();
            });

        });

        addClient.addActionListener(e -> {
            JFrame frame2 = new JFrame();
            frame2.setTitle("Add Client");
            frame2.setSize(300, 150);
            frame2.setLocationRelativeTo(null);
            frame2.setVisible(true);

            JPanel panel2 = new JPanel();
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
            frame2.add(panel2);

            JTextField field1 = new JTextField(10);
            JLabel label1 = new JLabel("Client name");

            JButton button = new JButton("Add Client");

            JPanel row1 = new JPanel();
            JPanel row2 = new JPanel();
            JPanel row3 = new JPanel();

            row1.add(label1);
            row1.add(field1);
            row3.add(button);

            panel2.add(row1);
            panel2.add(row2);
            panel2.add(row3);

            button.addActionListener(e2 -> {
                hotel.addClient(field1.getText());
                frame2.dispose();
                list1.clear();
                clients.clear();
                for(Client c : hotel.getClients()){
                    list1.addElement(c.name);
                    clients.add(c);
                }
            });


        });

        search.addActionListener(e -> {
            String roomsString = roomsField.getText();
            List<Integer> roomsRequest = new ArrayList<>();
            Period period;
            try {
                for(String s : roomsString.split(",")){
                    roomsRequest.add(Integer.parseInt(s));
                }
                period = new Period(fromField.getText(), toField.getText());
            }catch(Exception ex){
                infoLabel.setText(ex.getMessage());
                return;
            }
            discount = 0;
            if(period.getDaysFromNow() > Config.discount_15){
                discount = 15;
            }
            if(period.getDaysFromNow() > Config.discount_30){
                discount = 30;
            }
            if(period.getDaysFromNow() > Config.discount_60){
                discount = 60;
            }
            List<Room> roomsList = hotel.findFreeRooms(period, roomsRequest);
            list2.clear();
            list3.clear();
            rooms.clear();
            for(Room r : roomsList){
                rooms.add(r);
                list2.addElement(r.name + "(" + r.nOfBeds + ") " +  r.getPrice() + "$");
            }

            reservation = new Reservation(period, discount);
            if(JList1.getSelectedIndex() != -1){
                reservation.setClient(clients.get(JList1.getSelectedIndex()));
            }
            infoLabel.setText("Free rooms " + period + ":");
            list = 3;

        });

        JList1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (JList1.getSelectedIndex() == -1) {
                    return;
                }
                if(list == 1){
                    infoLabel.setText("Search first!");
                    return;
                }
                if(list == 2) {
                    list2.clear();
                    list3.clear();
                    reservations.clear();
                    String name = clients.get(JList1.getSelectedIndex()).name;
                    for (Reservation res : hotel.getReservations()) {
                        if(res.getClient().name.equals(name)){
                            list2.addElement(res.toString());
                            reservations.add(res);
                        }
                    }
                    infoLabel.setText(name + " reservation list:");

                }
                if(list == 3) {
                    if (e.getClickCount() == 1) {
                        reservation.setClient(clients.get(JList1.getSelectedIndex()));
                        String label = "Price:" + reservation.getPrice() + " time discounting:" + reservation.getTimeDiscount() + "% client discount:" + reservation.getClientDiscount() + "% final price:" + reservation.getPriceWithDiscount();
                        infoLabel.setText(label);
                    }
                }
            }
        });

        JList2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (JList2.getSelectedIndex() == -1) {
                    return;
                }
                if(list == 1){
                    Room r = rooms.get(JList2.getSelectedIndex());
                    list3.clear();
                    for(Reservation res : r.getReservations()){
                        list3.addElement(res.toString());
                    }
                    //infoLabel.setText("Search first!");
                    return;
                }
                if(list == 2){
                    if (e.getClickCount() == 1) {
                        Reservation res = reservations.get(JList2.getSelectedIndex());
                        list3.clear();
                        rooms.clear();
                        for(Room r : res.getRooms()){
                            list3.addElement(r.toString());
                            rooms.add(r);
                        }
                    }
                }
                if(list == 3) {
                    if (e.getClickCount() == 2) {
                        String s = list2.get(JList2.getSelectedIndex());
                        Room r = rooms.get(JList2.getSelectedIndex());
                        reservation.rooms.add(r);
                        list3.addElement(s);
                        rooms.remove(r);
                        list2.removeElement(s);
                        if (JList1.getSelectedIndex() != -1) {
                            reservation.setClient(clients.get(JList1.getSelectedIndex()));
                        }
                        String label = "Price:" + reservation.getPrice() + " Days:" + reservation.period.getDays() + " time discounting:" + reservation.getTimeDiscount() + "% client discount:" + reservation.getClientDiscount() + "% final price:" + reservation.getPriceWithDiscount();
                        infoLabel.setText(label);
                    }
                }
            }
        });

        JList3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (JList3.getSelectedIndex() == -1) {
                    return;
                }
                if(list == 1){
                    //infoLabel.setText("Search first!");
                    return;
                }
                if(list == 2){

                }
                if(list == 3) {
                    if (e.getClickCount() == 2) {
                        String s = list3.get(JList3.getSelectedIndex());
                        Room r = reservation.rooms.get(JList3.getSelectedIndex());
                        rooms.add(r);
                        list2.addElement(s);
                        reservation.rooms.remove(r);
                        list3.removeElement(s);
                        if (JList1.getSelectedIndex() != -1) {
                            reservation.setClient(clients.get(JList1.getSelectedIndex()));
                        }
                        String label = "Price:" + reservation.getPrice() + " time discounting:" + reservation.getTimeDiscount() + "% client discount:" + reservation.getClientDiscount() + "% final price:" + reservation.getPriceWithDiscount();
                        infoLabel.setText(label);
                    }
                }
            }
        });
    }
}
