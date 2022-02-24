package az.iktlab.group.secret_society.DB;


import az.iktlab.group.secret_society.entity.Flight;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SQL {
    private static String url = "jdbc:postgresql://localhost:5432/project";
    private static String user = "postgres";
    private static String pass = "Nadir565";

    public static Flight data1 = new Flight();
    public static Scanner myReader;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    public static Statement getStatement() {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return statement;
    }

    public static void collectData() {
        File myObj = new File("C:\\Users\\FX506HCB\\Desktop\\secret-society-1\\src\\main\\java\\az\\iktlab\\group\\secret_society\\flights.txt");

        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] info;
            info = data.split("\t");

            data1.setFlightId(info[0]);
            data1.setFromW(info[1]);
            data1.setToW(info[2]);
            data1.setFlightDate(info[3]);
            data1.setFlightTime(info[4]);
            data1.setDuration(info[5]);
            data1.setFreeSeats(info[6]);

            String formatF = String.format("insert into helper.flights_info " +
                            "(flight_id, from_w, to_w, flight_date, flight_time, duration, free_seats)" +
                            "VALUES ('%s', 'KIEV', '%s' ,'%s', '%s', '%s' ,'%s')",
                    (data1.getFlightId()),

                    (data1.getToW()),
                    (data1.getFlightDate()),
                    (data1.getFlightTime()),
                    (data1.getDuration()),
                    (data1.getFreeSeats()));

            Statement stmt = getStatement();

            try {
                stmt.execute(formatF);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
