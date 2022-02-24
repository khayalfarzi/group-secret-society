package az.iktlab.group.secret_society.service;



import az.iktlab.group.secret_society.DAO.FlightDAO;
import az.iktlab.group.secret_society.DB.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class FlightService implements FlightDAO {
    public static Scanner scanner = new Scanner(System.in);
    String userName;
    String userSurname;

    String id_t;
    String id_p;

    public String getId_t() {
        return id_t;
    }

    public void setId_t(String id_t) {
        this.id_t = id_t;
    }

    public String getId_p() {
        return id_p;
    }

    public void setId_p(String id_p) {
        this.id_p = id_p;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public void getDATA() {

        System.out.print("Enter Name      :\t");
        userName = scanner.next();
        setUserName(userName);

        System.out.print("Enter Surname   :\t");
        userSurname = scanner.next();
        setUserSurname(userSurname);

        checkFlightData();
        getMessage();
    }

    private String displayData(ResultSet rs, String keyString) throws SQLException {
        String answer = null;
        while (rs.next()) {
            answer = (rs.getString(keyString));
        }
        return answer;
    }

    public void getMessage() {
        System.out.println(
                "\nUser      :\t" + getUserSurname().toUpperCase() + " " + getUserName().toUpperCase() +
                "\nTicket ID :\t" + getId_t() +
                "\nPlane ID  :\t" + getId_p()
        );
    }

    @Override
    public void checkFlightData() {
        String ticketId = null, planeId = null;
        String ticket_id = String.format("SELECT ticket_id FROM helper.data_list WHERE name = '%s' AND surname = '%s'", getUserName().toUpperCase(), getUserSurname().toUpperCase());
        String plane_id = String.format("SELECT plane_id FROM helper.data_list WHERE name = '%s' AND surname = '%s'", getUserName().toUpperCase(), getUserSurname().toUpperCase());

        try (Connection conn = SQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(ticket_id)) {
            ticketId = displayData(rs, "ticket_id");
            setId_t(ticketId);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            getDATA();
        }

        try (Connection conn = SQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(plane_id)) {
            planeId = displayData(rs,"plane_id");
            setId_p(planeId);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            getDATA();
        }
    }
}
