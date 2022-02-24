package az.iktlab.group.secret_society.controller;


import az.iktlab.group.secret_society.DAO.FlightDAO;
import az.iktlab.group.secret_society.DB.SQL;
import az.iktlab.group.secret_society.ENUM.Destination;
import az.iktlab.group.secret_society.console.Console;
import az.iktlab.group.secret_society.entity.Flight;
import az.iktlab.group.secret_society.service.TicketService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Controller implements FlightDAO {
    Scanner scanner = new Scanner(System.in);
    String key;
    Integer number;
    String planeID;
    public String formatU;

    public Integer getNumber() {
        return number;
    }

    Date flightDate = new Date();

    public static Flight flight = new Flight();

    public Controller() {
    }

    public Controller(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPlaneID() {
        return planeID;
    }

    public void setPlaneID(String planeID) {
        this.planeID = planeID;
    }

    public List<String> createList() throws IOException {
        List<String> list = Files.readAllLines(
                new File("C:\\Users\\FX506HCB\\Desktop\\secret-society-1\\src\\main\\java\\az\\iktlab\\group\\secret_society\\flights.txt").toPath(),
                Charset.defaultCharset());
        return list;
    }

    public String showListWithKey(List<String> list, String key) {
        String searchedList = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(key.toUpperCase())) {
                searchedList = (list.get(i));
                System.out.println(list.get(i));
            } else continue;
        }
        return searchedList;
    }

    public void getNextDay() {
        try {
            List<String> ListForNextDate = createList();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDateTime now = LocalDateTime.now();
            showAllFlightsDuring24Hours(ListForNextDate, dtf.format(now.plusDays(1)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showAllFlightsDuring24Hours(List<String> list, String tomorrow) {
        List<String> searchedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(tomorrow)) {
                searchedList.add(list.get(i));
                System.out.println(list.get(i));
            } else continue;
        }
    }

    public String getFirstWord(String mystring) {
        String[] arr = mystring.split("\t", 2);
        return arr[0];
    }

    public void showAll(List<String> list) {
        for (String s : list) {
            System.out.println(s);
            String helper = getFirstWord(s);
            setPlaneID(helper);
        }
        continueOrNot();
    }



    public void selectFlightInfo() throws IOException, ParseException {
        Scanner SCANNER = new Scanner(System.in);
        System.out.println("Enter Flight Id     :\t");
        String FlightId = SCANNER.next();

        List<String> ListForId = createList();
        String forDB = showListWithKey(ListForId, FlightId);

        String[] arrayOfString = forDB.split("\t");

        String strDate = arrayOfString[3] + " " + arrayOfString[4];

        Date date = new SimpleDateFormat("dd.MM.yyyy hh:mm").parse(strDate);


    }
    public void getKeyAsId(String key) {
        flight.setFlightId(key);
        try {
            List<String> ListForId = createList();
            showListWithKey(ListForId, flight.getFlightId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getKeyAsDestination(String key) {
        for (int i = 0; i < Destination.values().length; i++) {
            if (key.equals(String.valueOf(Destination.values()[i]))) {
                flight.setToW(String.valueOf(Destination.values()[i]));
            }
        }
    }

    public void filterData(List<String> ListForBooking, String[] array) {
        List<String> searchedList = new ArrayList<>();
        for (String s : ListForBooking) {
            if (s.contains(array[0].toUpperCase())) {
                searchedList.add(s);
            } else continue;
        }
        filterDataSecond(searchedList, array);
    }

    public void filterDataSecond(List<String> searchedList, String[] array) {
        List<String> searchedList2 = new ArrayList<>();
        for (String s : searchedList) {
            if (s.contains(array[1])) {
                searchedList2.add(s);
            } else continue;
        }
        getFullyFilteredDta(searchedList2, array);
    }

    public void getFullyFilteredDta(List<String> searchedList2, String[] array) {
        List<String> searchedList3 = new ArrayList<>();
        for (String s : searchedList2) {
            String myString = s.trim();
            String[] wordList = myString.split("\\s+");
            if ((Integer.parseInt(wordList[wordList.length - 1]) >= Integer.parseInt(array[2]))) {
                searchedList3.add(s);
            }
        }
        showAll(searchedList3);
    }

    public void getKeysForBooking(String[] array) {
        try {
            List<String> ListForBooking = createList();
            this.number = Integer.valueOf(array[2]);
            filterData(ListForBooking, array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String displayData(ResultSet rs, String keyString) throws SQLException {
        String answer = null;
        while (rs.next()) {
            answer = (rs.getString(keyString));
        }
        return answer;
    }


    @Override
    public  void checkFlightData() {
        String from = String.format("SELECT from_w FROM helper.flights_info WHERE flight_id = '%s'", planeID);
        String to = String.format("SELECT to_w FROM helper.flights_info WHERE flight_id = '%s'", planeID);
        String date = String.format("SELECT flight_date FROM helper.flights_info WHERE flight_id = '%s'", planeID);
        String time = String.format("SELECT flight_time FROM helper.flights_info WHERE flight_id = '%s'", planeID);
        String duration = String.format("SELECT duration FROM helper.flights_info WHERE flight_id = '%s'", planeID);

        try (Connection conn = SQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(from)) {
            String from_w = displayData(rs,"from_w");
            flight.setFromW(from_w);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        try (Connection conn = SQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(to)) {
            String to_w = displayData(rs,"to_w");
            flight.setToW(to_w);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        try (Connection conn = SQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(date)) {
            String flight_date = displayData(rs, "flight_date");
            flight.setFlightDate(flight_date);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        try (Connection conn = SQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(time)) {
            String flight_time = displayData(rs, "flight_time");
            flight.setFlightTime(flight_time);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        try (Connection conn = SQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(duration)) {
            String duratioN = displayData(rs, "duration");
            flight.setDuration(duratioN);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void continueOrNot() {
        System.out.println("If you want to continue Enter 'YES' otherwise 'Exit'\t");
        String continueKey = scanner.next();
        if(continueKey.equalsIgnoreCase("YES")) {
            checkFlightData();
            TicketService ticketService = new TicketService(getPlaneID(), number, flight);
            ticketService.createTicket();
        } else if (continueKey.equalsIgnoreCase("EXIT")) {
            Console console = new Console();
            try {
                console.setDirection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Wrong DATA! If you want to continue Enter 'YES' otherwise 'Exit'\t");
            continueOrNot();
        }
    }

}
